package org.matrixnetwork.stats2;

import com.sun.net.httpserver.HttpServer;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.*;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.message.GZipEncoder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.hibernate.Session;
import org.matrixnetwork.stats2.handler.StatsHandler;
import org.matrixnetwork.stats2.listener.StatsListener;
import org.matrixnetwork.stats2.manager.DataManager;
import org.matrixnetwork.stats2.rest.AuthResource;
import org.matrixnetwork.stats2.rest.PlayerKillResource;
import org.matrixnetwork.stats2.rest.SkinResource;
import org.matrixnetwork.stats2.rest.StatsResource;
import org.matrixnetwork.stats2.rest.filter.CorsFilter;
import org.matrixnetwork.stats2.util.JacksonFeature;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.File;
import java.net.URI;

public class MatrixStats extends JavaPlugin {

    private static MatrixStats plugin;
    private static Economy econ;
    private HttpServer httpServer;
    protected static boolean TEST = false;

    public HttpServer getHttpServer() {
        return httpServer;
    }

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

    @ParametersAreNonnullByDefault
    @SuppressWarnings("removal")
    public MatrixStats(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
        plugin = this;
    }

    public MatrixStats() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        if (!setupEconomy() && !TEST) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!",
                    getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getServer().getPluginManager().registerEvents(new StatsListener(), this);

        StatsHandler.init();

        ResourceConfig rc = new ResourceConfig();
        rc.register(AuthResource.class);
        rc.register(SkinResource.class);
        rc.register(PlayerKillResource.class);
        rc.register(StatsResource.class);
        rc.register(CorsFilter.class);
        rc.register(new GZipEncoder());
        rc.register(JacksonFeature.class);

        rc.property(ServerProperties.WADL_FEATURE_DISABLE, true);

        httpServer = JdkHttpServerFactory.createHttpServer(
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
        if (httpServer != null) {
            try{
                httpServer.stop(0);
            } catch (IllegalStateException ignored) {

            }
        }
        getLogger().info("Disabled");
    }

}