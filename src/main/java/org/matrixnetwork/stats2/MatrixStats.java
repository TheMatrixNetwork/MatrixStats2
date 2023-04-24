package org.matrixnetwork.stats2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import net.milkbowl.vault.economy.Economy;
import net.skinsrestorer.api.SkinsRestorerAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
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
import java.util.List;

public class MatrixStats extends JavaPlugin{
	
	private static MatrixStats plugin;
	private HttpServer server;
	private static Economy econ;

	public static Economy getEcon() {
		return econ;
	}

	@Override
	public void onLoad() {
		plugin = this;
	}
	
	@Override
	public void onEnable() {
		if (!setupEconomy() ) {
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
				URI.create( "http://localhost:8081/api" ), rc );

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
		if(server != null)
			server.stop( 0 );
		getLogger().info("Disabled");
	}
	
	public static org.bukkit.plugin.Plugin getPlugin() {
		return plugin;
	}

}