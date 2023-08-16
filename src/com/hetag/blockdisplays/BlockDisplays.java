package com.hetag.blockdisplays;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import com.hetag.blockdisplays.commands.Executor;
import com.hetag.blockdisplays.configuration.Config;
import com.hetag.blockdisplays.configuration.Manager;

public class BlockDisplays extends JavaPlugin {
	public static BlockDisplays plugin;
	public static Logger log;
	public static Config FloatingBlocks;

	public void onEnable() {
		plugin = this;
		log = getLogger();
		log.info("-=-=-=-= BlockDisplays " + plugin.getDescription().getVersion() + " =-=-=-=-");
		try {
			FloatingBlocks = new Config(new File("FloatingBlocks.yml"));
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
		Rotation.check();
		Rotation.progressAllRotations();
		log.info("Loaded " + Rotation.getActiveRotations() + " instance(s) to rotate.");
		log.info("Succesfully enabled BlockDisplays!");
		log.info("-=-=-=-= -=- =-=-=-=-");
	}

	public void onDisable() {
		try {
			FloatingBlocks.saveConfig();
			Manager.defaultConfig.saveConfig();
		} catch (Exception e) {
			e.printStackTrace();
			log.warning("An error occurred while trying to save the configurations files beforet shutting down.");
		}
		log.info("Succesfully disabled BlockDisplays!");

	}

	public static BlockDisplays getInstance() {
		return plugin;
	}
}
