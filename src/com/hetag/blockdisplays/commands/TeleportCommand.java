package com.hetag.blockdisplays.commands;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.hetag.blockdisplays.blocks.FloatingBlock;
import com.hetag.blockdisplays.configuration.Manager;

public class TeleportCommand extends BDCommand {

	public TeleportCommand() {
		super("teleport", "/bd teleport <name>", formatColors(Manager.getConfig().getString("Commands.Teleport.Description")), new String[] { "teleport", "tp" });
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!hasPermission(sender) || !correctLength(sender, 0, 0, 1)) {
			return;
		}
		if (args.size() == 1) {
			Player p = (Player) sender;
			String block = args.get(0);
			if (FloatingBlock.exists(block)) {
				if (FloatingBlock.isAlive(block)) {
					Entity blocky = FloatingBlock.getFloatingBlockByUUID(args.get(0));
					blocky.teleport(p.getLocation());
					FloatingBlock.updateLocation(args.get(0));
					sendMessage(sender, onTeleport().replace("%name%", args.get(0)), true);
					return;
				} else {
					sendMessage(sender, onInvalid().replace("%name%", args.get(0)), true);
					return;
				}
			} else {
				sendMessage(sender, notFound().replace("%name%", args.get(0)), true);
				return;
			}
		}
	}

	public String onTeleport() {
		return Manager.getConfig().getString("Commands.Teleport.OnTeleport");
	}
	
	private String notFound() {
		return Manager.getConfig().getString("Commands.Teleport.NotFound");
	}
	
	private String onInvalid() {
		return Manager.getConfig().getString("Commands.Teleport.OnInvalid");
	}
}
