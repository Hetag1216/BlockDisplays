package com.hetag.blockdisplays.commands;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.hetag.blockdisplays.blocks.FloatingBlock;
import com.hetag.blockdisplays.configuration.Manager;

public class TeleportCommand extends BDCommand {

	public TeleportCommand() {
		super("teleport", "/bd teleport <name>", formatColors(Manager.getConfig().getString("Commands.Teleport.Description")), new String[] { "teleport", "t", "tp" });
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!hasPermission(sender)) {
			return; 
		}
		Player p = (Player) sender;
		Entity block = FloatingBlock.getFloatingBlockByUUID(args.get(0));
		block.teleport(p.getLocation());
		FloatingBlock.updateLocation(args.get(0));
		sendMessage(sender, onTeleport().replace("%name%", args.get(0)), true);
		
		if (args.size() > 1 || args.size() < 1) {
			sendMessage(sender, this.getProperUsage(), true);
		}
	}

	public String onTeleport() {
		return Manager.getConfig().getString("Commands.Teleport.OnTeleport");
	}
}
