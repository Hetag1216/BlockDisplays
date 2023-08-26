package com.hedario.blockdisplays.commands;

import java.util.List;

import org.bukkit.command.CommandSender;

import com.hedario.blockdisplays.BlockDisplays;
import com.hedario.blockdisplays.configuration.Manager;

public class VersionCommand extends BDCommand {

	public VersionCommand() {
		super("version", "/bd version", formatColors(Manager.getConfig().getString("Commands.Version.Description")), new String[] { "version", "v" });
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!hasPermission(sender) || !correctLength(sender, 0, 0, 1)) {
			return;
		}
		sendMessage(sender, "&8&m-----&r "+ getPrefix() + "&8&m-----", false);
		sendMessage(sender, "&3Version &7» &b" + BlockDisplays.plugin.getDescription().getVersion(), false);
		sendMessage(sender, "&3API version &7» &b" + BlockDisplays.plugin.getDescription().getAPIVersion(), false);
		sendMessage(sender, "&3Author &7» &b" + BlockDisplays.plugin.getDescription().getAuthors().toString().replace("[", "").toString().replace("]", ""), false);
		sendMessage(sender, "&3Compatible Minecraft Version(s) &7» &b1.16.x, 1.17.x, 1.18.x, 1.19.x, 1.20.x", false);
		sendMessage(sender, "&3BlockDisplays' Java requirements &7» &b Java 16+", false);
		sendMessage(sender, "&3System Java version &7» &b" + System.getProperty("java.version"), false);
		sendMessage(sender, "", false);
		sendMessage(sender, "&3Spigot page &7» &bhttps://www.spigotmc.org/resources/blockdisplays.76007/", false);
		sendMessage(sender, "&3Github &7» &bhttps://github.com/Hetag1216/BlockDisplays", false);
		sendMessage(sender, "&3Check out my other resources at &7» &bhttps://www.spigotmc.org/members/_hetag1216_.243334/", false);
		sendMessage(sender, "&3Discord &7» &bhttps://discord.gg/yqs9UJs", false);
	}
}
