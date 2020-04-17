package com.hetag.blockdisplays.commands;

import java.util.List;

import org.bukkit.command.CommandSender;

import com.hetag.blockdisplays.BlockDisplays;
import com.hetag.blockdisplays.blocks.FloatingBlock;
import com.hetag.blockdisplays.configuration.Manager;

public class InfoCommand extends BDCommand {

	public InfoCommand() {
		super("info", "/bd info <name>", formatColors(Manager.getConfig().getString("Commands.Info.Description")), new String[] { "info", "i" });
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!hasPermission(sender)) {
			return;
		}
		if (args.size() == 1) {
			String block = args.get(0);
			if (BlockDisplays.FloatingBlocks.getConfig().contains("FloatingBlocks." + block)) {
				sendMessage(sender, "&8« &b" + block + " &8»", false);
				sendMessage(sender, "&3World:&b " + FloatingBlock.getWorld(block).getName(), false);
				sendMessage(sender, "&3X:&b " + FloatingBlock.getX(block), false);
				sendMessage(sender, "&3Y:&b " + FloatingBlock.getY(block), false);
				sendMessage(sender, "&3Z:&b " + FloatingBlock.getZ(block), false);
				sendMessage(sender, "&3Pitch:&b " + FloatingBlock.getPitch(block), false);
				sendMessage(sender, "&3Yaw:&b " + FloatingBlock.getYaw(block), false);
				sendMessage(sender, "&3Size:&b " + FloatingBlock.getSize(block), false);
				sendMessage(sender, "&3UUID:&b " + FloatingBlock.getUUID(block), false);
				sendMessage(sender, "&3Material:&b " + FloatingBlock.getMaterial(block), false);
			} else {
				sendMessage(sender, onInvalid().replace("%name%", block), true);
			}
		}
		if (args.size() < 1 || args.size() > 1) {
			sendMessage(sender, this.getProperUsage(), false);
		}
	}
	
	public static String onInvalid() {
		return Manager.getConfig().getString("Commands.Info.OnInvalid");
	}
}
