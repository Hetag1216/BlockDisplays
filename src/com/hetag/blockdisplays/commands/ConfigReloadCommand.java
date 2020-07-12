package com.hetag.blockdisplays.commands;

import java.util.List;

import org.bukkit.command.CommandSender;

import com.hetag.blockdisplays.configuration.Manager;

public class ConfigReloadCommand extends BDCommand {

	public ConfigReloadCommand() {
		super("reload", "/bd reload", formatColors(Manager.getConfig().getString("Commands.Reload.Description")), new String[] { "reload", "r" });
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!hasPermission(sender) || !correctLength(sender, 0, 0, 1)) {
			return;
		}
		try {
		Manager.defaultConfig.saveConfig();
		Manager.defaultConfig.reloadConfig();
		sendMessage(sender, onReload(), true);
		} catch (Exception e) {
			e.printStackTrace();
			sendMessage(sender, onReloadFail(), true);
		}
	}

	public String onReload() {
		return Manager.getConfig().getString("Commands.Reload.OnReload");
	}
	
	public String onReloadFail() {
		return Manager.getConfig().getString("Commands.Reload.OnFail");
	}
}
