package com.hetag.blockdisplays.commands;

import java.util.List;

import org.bukkit.command.CommandSender;

import com.hetag.blockdisplays.BlockDisplays;
import com.hetag.blockdisplays.Rotation;
import com.hetag.blockdisplays.configuration.Manager;

public class ConfigReloadCommand extends BDCommand {

	public ConfigReloadCommand() {
		super("reload", "/bd reload", formatColors(Manager.getConfig().getString("Commands.Reload.Description")),
				new String[] { "reload", "r" });
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!hasPermission(sender) || !correctLength(sender, 0, 0, 1)) {
			return;
		}
		try {
			// Manager.defaultConfig.saveConfig();
			Manager.defaultConfig.reloadConfig();
			BlockDisplays.FloatingBlocks.reloadConfig();
			if (!BlockDisplays.getInstance().getServer().getScheduler().getPendingTasks().isEmpty()) {
				BlockDisplays.getInstance().getServer().getScheduler().getPendingTasks().clear();
			}
			Rotation.ALL_BLOCKS.clear();
			Rotation.check();
			Rotation.progressAllRotations();
			if (Rotation.getRotatingBlocks() != null) {
				Rotation.update(Rotation.getRotatingBlocks(), Rotation.getRotatingBlocksInterval());
			}
			Manager.defaultConfig.saveConfig();
			BlockDisplays.FloatingBlocks.saveConfig();
			BlockDisplays.log.info("Loaded " + Rotation.getActiveRotations() + " instance(s) to rotate.");
			sendMessage(sender, onReload(), true);
			sendMessage(sender, "Loaded " + Rotation.getActiveRotations() + " instance(s) to rotate.", true);
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
