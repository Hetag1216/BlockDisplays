package com.hetag.blockdisplays;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.hetag.blockdisplays.commands.Executor;
import com.hetag.blockdisplays.configuration.Config;
import com.hetag.blockdisplays.configuration.Manager;
import com.hetag.blockdisplays.reflection.BDProtocol;
import com.hetag.blockdisplays.reflection.V1_13.Protocol_1_13;
import com.hetag.blockdisplays.reflection.V1_14.Protocol_1_14;
import com.hetag.blockdisplays.reflection.V1_15.Protocol_1_15;
import com.hetag.blockdisplays.reflection.V1_16.Protocol_1_16;

public class BlockDisplays extends JavaPlugin {
	public static BlockDisplays plugin;
	public static Logger log;
	public static Config FloatingBlocks;
	public static BDProtocol protocol;

	public void onEnable() {
		plugin = this;
		log = getLogger();
		log.info("-=-=-=-= BlockDisplays " + plugin.getDescription().getVersion() + " =-=-=-=-");
		checkProtocol();
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

	public void checkProtocol() {
		String version = Bukkit.getServer().getClass().getPackage().getName();
		String formmatedVersion = version.substring(version.lastIndexOf(".") + 1);

		switch (formmatedVersion) {
		default:
			protocol = new Protocol_1_13();
			break;
		case "v1_13_R2":
		case "v1_13_R1":
			protocol = new Protocol_1_13();
			break;
		case "v1_14_R1":
			protocol = new Protocol_1_14();
			break;
		case "v1_15_R1":
			protocol = new Protocol_1_15();
			break;
		case "v1_16_R1":
			protocol = new Protocol_1_16();
			break;
		}
		if (protocol.equals(new Protocol_1_13())) {
			log.info("Using protocol for 1.13 versions compatibility!");
		} else if (protocol.equals(new Protocol_1_14())) {
			log.info("Using protocol for 1.14 versions compatibility!");
		} else if (protocol.equals(new Protocol_1_15())) {
			log.info("Using protocol for 1.15 versions compatibility!");
		} else if (protocol.equals(new Protocol_1_16())) {
			log.info("Using protocol for 1.16 versions compatibility!");
		}
	}

	public static BDProtocol getProtocol() {
		return protocol;
	}
}
