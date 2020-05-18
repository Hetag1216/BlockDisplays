package com.hetag.blockdisplays.commands;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;

import com.hetag.blockdisplays.BlockDisplays;
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
			if (BlockDisplays.FloatingBlocks.getConfig().contains("FloatingBlocks." + floatingBlock)) {
				//String uuid = BlockDisplays.FloatingBlocks.getConfig().getString("FloatingBlocks." + floatingBlock + ".UUID");
				for (Entity ent : FloatingBlock.getWorld(floatingBlock).getEntities()) {
					if (ent.getUniqueId().equals(FloatingBlock.getUUID(floatingBlock))) {
						if (isNumeric(args.get(1))) {
							float yaw = Float.valueOf(args.get(1));
							FloatingBlock.rotateBlock(floatingBlock, yaw);
							sendMessage(sender, onRotate().replace("%value%", args.get(1)), true);
						}
					}
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
