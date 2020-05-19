package com.hetag.blockdisplays.commands;

import java.util.List;

import org.bukkit.command.CommandSender;

import com.hetag.blockdisplays.blocks.FloatingBlock;
import com.hetag.blockdisplays.configuration.Manager;

public class RotateCommand extends BDCommand {

	public RotateCommand() {
		super("rotate", "/bd rotate <name> <value>", formatColors(Manager.getConfig().getString("Commands.Rotate.Description")), new String[] { "rotate", "r" });
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!hasPermission(sender) || !correctLength(sender, 0, 0, 2)) {
			return;
		}
		if (args.size() == 2) {
			String floatingBlock = args.get(0);
				if (FloatingBlock.exists(floatingBlock) && FloatingBlock.isAlive(floatingBlock)) {
					if (isNumeric(args.get(1))) {
						float yaw = Float.valueOf(args.get(1));
						FloatingBlock.rotateBlock(floatingBlock, yaw);
						sendMessage(sender, onRotate().replace("%value%", args.get(1)), true);
					}
			} else {
				sendMessage(sender, onInvalid().replace("%name%", floatingBlock), true);
			}
		}
		if (args.size() > 2 || args.size() < 2) {
			sender.sendMessage(getProperUsage());
		}
	}
	
	public String onRotate() {
		return Manager.getConfig().getString("Commands.Rotate.OnRotate");
	}

	public String onInvalid() {
		return Manager.getConfig().getString("Commands.Rotate.OnInvalid");
	}
}
