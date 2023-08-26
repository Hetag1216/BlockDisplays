package com.hedario.blockdisplays.commands;

import java.util.List;

import org.bukkit.command.CommandSender;

import com.hedario.blockdisplays.Rotation;
import com.hedario.blockdisplays.configuration.Manager;

public class ConfigReloadCommand extends BDCommand {

	public ConfigReloadCommand() {
		super("reload", "/bd reload", Manager.getConfig().getString("Commands.Reload.Description"), new String[] { "reload", "r" });
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!hasPermission(sender) || !correctLength(sender, args.size(), 0, 1)) {
			return;
		}
		try {
			Manager.reloadConfigs();
			Rotation.init();
			sendMessage(sender, "Loaded " + Rotation.getActiveRotations() + " instance(s) to rotate.", true);
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
