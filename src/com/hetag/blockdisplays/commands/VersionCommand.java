package com.hetag.blockdisplays.commands;

import java.util.List;

import org.bukkit.command.CommandSender;

import com.hetag.blockdisplays.BlockDisplays;
import com.hetag.blockdisplays.configuration.Manager;

public class VersionCommand extends BDCommand {

	public VersionCommand() {
		super("version", "/bd version", formatColors(Manager.getConfig().getString("Commands.Version.Description")), new String[] { "version", "v" });
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!hasPermission(sender) || !correctLength(sender, 0, 0, 1)) {
			return;
		}
		sendMessage(sender, "", true);
		sendMessage(sender, "&3Version: &b" + BlockDisplays.getInstance().getDescription().getVersion(), false);
		sendMessage(sender, "&3Version: &b" + BlockDisplays.getInstance().getDescription().getAuthors().toString().replace("[", "").toString().replace("]", ""), false);
		sendMessage(sender, "&3Compatible Minecraft Version(s): &b 1.13 - 1.14", false);
		sendMessage(sender, "&3Using Protocol: &b" + BlockDisplays.getProtocol().toString(), false);
	}

}
