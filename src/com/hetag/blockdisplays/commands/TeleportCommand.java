package com.hetag.blockdisplays.commands;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.hetag.blockdisplays.blocks.FloatingBlock;
import com.hetag.blockdisplays.configuration.Manager;

public class TeleportCommand extends BDCommand {

	public TeleportCommand() {
		super("teleport", "/bd teleport <name>", formatColors(Manager.getConfig().getString("Commands.Teleport.Description")), new String[] { "teleport", "t", "tp" });
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!hasPermission(sender) || !correctLength(sender, 0, 0, 1)) {
			return;
		}
		Player p = (Player) sender;
		FloatingBlock.getArmorStandByUUID(args.get(0)).teleport(p.getLocation());
		sendMessage(sender, onTeleport().replace("%name%", args.get(0)), true);
		if (args.size() < 1 || args.size() > 1) {
			sender.sendMessage(getProperUsage());
		}
	}

	public String onTeleport() {
		return Manager.getConfig().getString("Commands.Teleport.OnTeleport");
	}
}
