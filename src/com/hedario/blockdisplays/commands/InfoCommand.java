package com.hedario.blockdisplays.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import com.hedario.blockdisplays.FloatingBlock;
import com.hedario.blockdisplays.configuration.Manager;

public class InfoCommand extends BDCommand {

	public InfoCommand() {
		super("info", "/bd info <name>", Manager.getConfig().getString("Commands.Info.Description"), new String[] { "info", "i" });
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!hasPermission(sender) || !correctLength(sender, args.size(), 1, 1)) {
			return;
		}
		String block = args.get(0);
		if (!FloatingBlock.validateFloatingBlock(sender, block)) {
			return;
		}
		sendMessage(sender, "&7-=-=-=-=-=-=-=-=-=-=- « &3" + block + " &7» -=-=-=-=-=-=-=-=-=-=-", false);
		sendMessage(sender, "&3World &7» &b" + FloatingBlock.getWorld(block).getName(), false);
		sendMessage(sender, "&3X &7» &b" + FloatingBlock.getX(block), false);
		sendMessage(sender, "&3Y &7» &b" + FloatingBlock.getY(block), false);
		sendMessage(sender, "&3Z &7» &b" + FloatingBlock.getZ(block), false);
		sendMessage(sender, "&3Pitch &7» &b" + FloatingBlock.getPitch(block), false);
		sendMessage(sender, "&3Yaw &7» &b" + FloatingBlock.getYaw(block), false);
		sendMessage(sender, "&3Size &7» &b" + FloatingBlock.getSize(block), false);
		sendMessage(sender, "&3UUID &7» &b" + FloatingBlock.getUUID(block), false);
		sendMessage(sender, "&3Material &7» &b" + FloatingBlock.getMaterial(block), false);
		sendMessage(sender, "&8« &bSettings &8» ", false);
		sendMessage(sender, "&3AutomaticRotation &7» &b" + FloatingBlock.isAutomaticallyRotating(block), false);
		sendMessage(sender, "&3AutomaticRotation interval &7» &b" + FloatingBlock.getAutomaticRotationInterval(block), false);
		sendMessage(sender, "&3Degrees &7» &b" + FloatingBlock.getAutomaticRotationDegrees(block), false);
		return;

	}

	@Override
	protected List<String> getTabCompletion(final CommandSender sender, final List<String> args) {
		if (!sender.hasPermission("blockdisplays.command.info") || args.size() > 1) {
			return new ArrayList<String>();
		}
		return FloatingBlock.getFloatingBlocks();
	}
}
