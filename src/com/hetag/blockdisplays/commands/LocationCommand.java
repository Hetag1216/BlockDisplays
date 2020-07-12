package com.hetag.blockdisplays.commands;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import com.hetag.blockdisplays.blocks.FloatingBlock;
import com.hetag.blockdisplays.configuration.Manager;

public class LocationCommand extends BDCommand {

	public LocationCommand() {
		super("location", "/bd location <name> <coordinate - x|y|z> <value>", formatColors(Manager.getConfig().getString("Commands.Location.Description")),	new String[] { "location", "loc" });
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (this.hasPermission(sender)) {
			if (args.size() == 3) {
				String floatingBlock = args.get(0);
				if (!FloatingBlock.exists(floatingBlock)) {
					sendMessage(sender, notFound().replace("%name%", floatingBlock), true);
					return;
				} else {
					if (!FloatingBlock.isAlive(floatingBlock)) {
						sendMessage(sender, onInvalid().replace("%name%", floatingBlock), true);
						return;
					}
				}
				String coord = args.get(1);
				if (!isNumeric(args.get(2))) {
					sendMessage(sender, notNumeric().replace("%value%", args.get(2)), true);
					return;
				}
				double value = Double.valueOf(args.get(2));
				Location location = FloatingBlock.getLocation(floatingBlock);

				switch (coord) {
				case "x":
					location.add(value, 0, 0);
					break;
				case "y":
					location.add(0, value, 0);
					break;
				case "z":
					location.add(0, 0, value);
					break;
				}
				FloatingBlock.getFloatingBlockByUUID(floatingBlock).teleport(location);
				FloatingBlock.updateLocation(floatingBlock);
				sendMessage(sender, onEdit().replace("%name%", floatingBlock).replace("%coord%", coord).replace("%value%", String.valueOf(value)), true);
				
			} else {
				sendMessage(sender, this.getProperUsage(), false);
			}
		} else {
			return;
		}
	}

	private String onEdit() {
		return Manager.getConfig().getString("Commands.Location.OnEdit");
	}

	private String notNumeric() {
		return Manager.getConfig().getString("Commands.Location.NotNumeric");
	}

	private String notFound() {
		return Manager.getConfig().getString("Commands.Location.NotFound");
	}

	private String onInvalid() {
		return Manager.getConfig().getString("Commands.Location.OnInvalid");
	}
}
