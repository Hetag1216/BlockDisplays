package com.hetag.blockdisplays.commands;

import java.util.List;

import org.bukkit.command.CommandSender;

import com.hetag.blockdisplays.BlockDisplays;
import com.hetag.blockdisplays.blocks.FloatingBlock;
import com.hetag.blockdisplays.configuration.Manager;

public class DeleteCommand extends BDCommand {

	public DeleteCommand() {
		super("delete", "/bd delete <name>", formatColors(Manager.getConfig().getString("Commands.Delete.Description")), new String[] { "delete", "d" });
		
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!hasPermission(sender) || !correctLength(sender, 0, 0, 1)) {
			return;
		}
		String block = args.get(0);
		if (deleteFloatingBlock(block)) {
			this.sendMessage(sender, onDeleteSuccess().replace("%name%", block), true);
			return;
		} else {
			this.sendMessage(sender, onDeleteFail().replace("%name%", block), true);
			return;
		}
	}
	
	public boolean deleteFloatingBlock(String floatingBlock) {
		if (FloatingBlock.exists(floatingBlock)) {
			if (FloatingBlock.isAlive(floatingBlock)) {
				FloatingBlock.getFloatingBlockByUUID(floatingBlock).remove();
			}
			BlockDisplays.FloatingBlocks.getConfig().set("FloatingBlocks." + floatingBlock, null);
			BlockDisplays.FloatingBlocks.saveConfig();
			return true;
		} else {
			return false;
		}
	}
	
	public String onDeleteSuccess() {
		return Manager.getConfig().getString("Commands.Delete.OnSuccess");
	}
	
	public String onDeleteFail() {
		return Manager.getConfig().getString("Commands.Delete.OnFail");
	}
}
