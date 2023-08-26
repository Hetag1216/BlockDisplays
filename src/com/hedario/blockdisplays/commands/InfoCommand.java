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
		if (!validateFloatingBlock(sender, block)) {
			return;
		}
		sendMessage(sender, "&7-=-=-=-=-=-=-=-=-=-=- « &3" + block + " &7» -=-=-=-=-=-=-=-=-=-=-", false);
		sendMessage(sender, "&3World:&b " + FloatingBlock.getWorld(block).getName(), false);
		sendMessage(sender, "&3X:&b " + FloatingBlock.getX(block), false);
		sendMessage(sender, "&3Y:&b " + FloatingBlock.getY(block), false);
		sendMessage(sender, "&3Z:&b " + FloatingBlock.getZ(block), false);
		sendMessage(sender, "&3Pitch:&b " + FloatingBlock.getPitch(block), false);
		sendMessage(sender, "&3Yaw:&b " + FloatingBlock.getYaw(block), false);
		sendMessage(sender, "&3Size:&b " + FloatingBlock.getSize(block), false);
		sendMessage(sender, "&3UUID:&b " + FloatingBlock.getUUID(block), false);
		sendMessage(sender, "&3Material:&b " + FloatingBlock.getMaterial(block), false);
		sendMessage(sender, "&8« &bSettings &8» ", false);
		sendMessage(sender, "&3AutomaticRotation:&b " + FloatingBlock.isAutomaticallyRotating(block), false);
		sendMessage(sender, "&3AutomaticRotation interval:&b " + FloatingBlock.getAutomaticRotationInterval(block), false);
		sendMessage(sender, "&3Degrees:&b " + FloatingBlock.getAutomaticRotationDegrees(block), false);
		return;

	}

	private boolean validateFloatingBlock(CommandSender sender, String name) {
		if (!FloatingBlock.exists(name)) {
			sendMessage(sender, notFound().replace("%name%", name), true);
			return false;
		}
		if (!FloatingBlock.isAlive(name)) {
			sendMessage(sender, onInvalid().replace("%name%", name), true);
			return false;
		}
		return true;
	}

	private String onInvalid() {
		return Manager.getConfig().getString("Commands.Info.OnInvalid");
	}

	private String notFound() {
		return Manager.getConfig().getString("Commands.Info.NotFound");
	}

	@Override
	protected List<String> getTabCompletion(final CommandSender sender, final List<String> args) {
		if (!sender.hasPermission("blockdisplays.command.info") || args.size() > 1) {
			return new ArrayList<String>();
		}
		return FloatingBlock.getFloatingBlocks();
	}
}
