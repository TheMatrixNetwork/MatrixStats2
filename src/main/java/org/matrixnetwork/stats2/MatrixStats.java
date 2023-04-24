package org.matrixnetwork.stats2;

import com.sun.net.httpserver.HttpServer;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.message.GZipEncoder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.matrixnetwork.stats2.handler.StatsHandler;
import org.matrixnetwork.stats2.listener.StatsListener;
import org.matrixnetwork.stats2.rest.AuthResource;
import org.matrixnetwork.stats2.rest.SkinResource;
import org.matrixnetwork.stats2.rest.StatsResource;
import org.matrixnetwork.stats2.rest.filter.CorsFilter;
import org.matrixnetwork.stats2.util.JacksonFeature;

import java.net.URI;

public class MatrixStats extends JavaPlugin {

    private static MatrixStats plugin;
    private static Economy econ;
    private HttpServer server;

    public static Economy getEcon() {
        return econ;
    }

    public static org.bukkit.plugin.Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void onLoad() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        if (!setupEconomy()) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!",
                    getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getServer().getPluginManager().registerEvents(new StatsListener(), this);

        StatsHandler.init();

        ResourceConfig rc = new ResourceConfig();
        rc.packages("org.matrixnetwork.stats.rest");
        rc.register(StatsResource.class);
        rc.register(SkinResource.class);
        rc.register(AuthResource.class);
        rc.register(CorsFilter.class);
        rc.register(new GZipEncoder());
        rc.register(JacksonFeature.class);

        rc.property(ServerProperties.WADL_FEATURE_DISABLE, true);

        server = JdkHttpServerFactory.createHttpServer(
                URI.create("http://localhost:8081/api"), rc);

        getLogger().info("Listening on http://localhost:8081/api !");
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer()
                .getServicesManager()
                .getRegistration(Economy.class);

        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    @Override
    public void onDisable() {
        if (server != null)
            server.stop(0);
        getLogger().info("Disabled");
    }

}