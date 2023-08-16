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
		sendMessage(sender, "&3Author: &b" + BlockDisplays.getInstance().getDescription().getAuthors().toString().replace("[", "").toString().replace("]", ""), false);
		sendMessage(sender, "&3Compatible Minecraft Version(s): &b 1.13 - 1.14 - 1.15 - 1.16", false);
		sendMessage(sender, "", false);
		sendMessage(sender, "&8« &bUseful links &8»", false);
		sendMessage(sender, "&aSpigot page &8» &bhttps://www.spigotmc.org/resources/blockdisplays-decorate-your-server-1-13-x-1-14-x-1-15-x-1-16-x.76007/", false);
		sendMessage(sender, "&aGithub &8» &bhttps://github.com/Hetag1216/BlockDisplays", false);
		sendMessage(sender, "&aCheck out my other resources at &8» &bhttps://www.spigotmc.org/members/_hetag1216_.243334/", false);
		sendMessage(sender, "&aDiscord &8» &bhttps://discord.gg/yqs9UJs", false);
	}
}
