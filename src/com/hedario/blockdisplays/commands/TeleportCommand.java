package com.hedario.blockdisplays.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.hedario.blockdisplays.FloatingBlock;
import com.hedario.blockdisplays.configuration.Manager;

public class TeleportCommand extends BDCommand {

	public TeleportCommand() {
		super("teleport", "/bd teleport <name> <to|here>", formatColors(Manager.getConfig().getString("Commands.Teleport.Description")), new String[] { "teleport", "tp" });
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!hasPermission(sender) || !correctLength(sender, args.size(), 2, 2) || !isPlayer(sender)) {
			return;
		}
		final String name = args.get(0);
		final Player player = (Player) sender;
		if (!FloatingBlock.validateFloatingBlock(sender, name)) {
			return;
		}
		if (args.get(1).equalsIgnoreCase("to")) {
			player.teleport(FloatingBlock.getLocation(name));
			this.sendMessage(sender, teleportTo().replace("%name%", name), true);
		} else if (args.get(1).equalsIgnoreCase("here")) {
			FloatingBlock.updateLocation(name, player.getLocation());
			sendMessage(sender, teleportHere().replace("%name%", name), true);
			return;
		}
	}

	public String teleportHere() {
		return Manager.getConfig().getString("Commands.Teleport.TeleportHere");
	}
	
	public String teleportTo() {
		return Manager.getConfig().getString("Commands.Teleport.TeleportTo");
	}
	
	@Override
	protected List<String> getTabCompletion(final CommandSender sender, final List<String> args) {
		List<String> list = new ArrayList<String>();
		if (!sender.hasPermission("blockdisplays.command.teleport") || args.size() > 2) {
			return new ArrayList<String>();
		}
		if (args.size() == 0) {
			return FloatingBlock.getFloatingBlocks();
		} else if (args.size() == 1) {
			list.add("here");
			list.add("to");
		} else {
			return new ArrayList<String>();
		}
		return list;
	}
}
