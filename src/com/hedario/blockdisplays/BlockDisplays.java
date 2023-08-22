package com.hedario.blockdisplays;

import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.hedario.blockdisplays.commands.BDCommand;
import com.hedario.blockdisplays.commands.Executor;
import com.hedario.blockdisplays.configuration.Manager;
import com.hedario.blockdisplays.utils.Metrics;
import com.hedario.blockdisplays.utils.UpdateChecker;

public class BlockDisplays extends JavaPlugin {
	public static BlockDisplays plugin;
	public static Logger log;
	private boolean updater, metrics, announcer;

	public void onEnable() {
		plugin = this;
		log = getLogger();
		log.info("-=-=-=-= BlockDisplays " + plugin.getDescription().getVersion() + " =-=-=-=-");
		try {
			new Manager();
			log.info("Configurations succesfully registered!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			new Executor(this);
			log.info("Commands succesfully registered!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Rotation.init();
		updater = Manager.getConfig().getBoolean("Settings.Updater.Enabled");
		if (updater) {
			checkForUpdates();
		}
		metrics = Manager.getConfig().getBoolean("Settings.Metrics.Enabled");
		if (metrics) {
	        int pluginId = 19611;
	        new Metrics(this, pluginId);
	        log.info("Metrics has been enabled, thank you!");
		} else {
			log.info("Metrics will be disabled.");
		}
		log.info("Loaded " + Rotation.getActiveRotations() + " instance(s) to rotate.");
		log.info("Succesfully enabled BlockDisplays!");
		
		log.info("-=-=-=-= -=- =-=-=-=-");
		announcer = Manager.getConfig().getBoolean("Settings.Announcer.Enabled");
		if (announcer) {
			Runnable announcer = new Runnable() {
				@Override
				public void run() {
					for (Player players : getServer().getOnlinePlayers()) {
						if (players.hasPermission("blockdisplays.*") || players.isOp()) {
							players.sendMessage(BDCommand.getPrefix() + "BlockDisplays is brought to you freely, if you wish to support the project, please consider making a donation!");
						}
					}
				}
			};
			getInstance().getServer().getScheduler().runTaskTimerAsynchronously(plugin, announcer, 200L, 36000L);
		}
	}
	
	/**
	 * Checks for plugin's update from the official spigot page.
	 */
	private void checkForUpdates() {
		new UpdateChecker(this, 76007).getVersion(version -> {
			log.info("-=-=-=-= AreaReloader Updater =-=-=-=-");
			if (this.getDescription().getVersion().equals(version)) {
				log.info("You're running the latest version of the plugin!");
			} else {
				log.info("AreaReloader " + version + " is now available!");
				log.info("You're running AreaReloader " + this.getDescription().getVersion());
				log.info("DOWNLOAD IT AT: https://www.spigotmc.org/resources/areareloader.70655/");
			}
			log.info("-=-=-=-= -=- =-=-=-=-");
		});
	}
	public void onDisable() {
		try {
			Manager.defaultConfig.saveConfig();
			Manager.floatingBlocksConfig.saveConfig();
		} catch (Exception e) {
			e.printStackTrace();
			log.warning("An error occurred while trying to save the configurations files before shutting down.");
		}
		log.info("Succesfully disabled BlockDisplays!");

	}

	public static BlockDisplays getInstance() {
		return plugin;
	}
}
