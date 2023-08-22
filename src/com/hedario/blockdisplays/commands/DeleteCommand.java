package com.hedario.blockdisplays.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import com.hedario.blockdisplays.FloatingBlock;
import com.hedario.blockdisplays.configuration.Manager;

public class DeleteCommand extends BDCommand {

	public DeleteCommand() {
		super("delete", "/bd delete <name>", formatColors(Manager.getConfig().getString("Commands.Delete.Description")), new String[] { "delete", "d" });
		
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!hasPermission(sender) || !correctLength(sender, 0, 0, 1)) {
			return;
		}
		if (args.size() == 1) {
			String block = args.get(0);
			if (deleteFloatingBlock(block)) {
				this.sendMessage(sender, onDeleteSuccess().replace("%name%", block), true);
				return;
			} else {
				this.sendMessage(sender, onDeleteFail().replace("%name%", block), true);
				return;
			}
		}
	}
	
	public boolean deleteFloatingBlock(String floatingBlock) {
		if (FloatingBlock.exists(floatingBlock)) {
			if (FloatingBlock.isAlive(floatingBlock)) {
				FloatingBlock.getFloatingBlockByUUID(floatingBlock).remove();
			}
			Manager.getFloatingBlocksConfig().set("FloatingBlocks." + floatingBlock, null);
			Manager.floatingBlocksConfig.saveConfig();
			return true;
		} else {
			return false;
		}
	}
	
	public String onDeleteSuccess() {
		return Manager.getConfig().getString("Commands.Delete.OnSuccess");
	}
	
	public String onDeleteFail() {
		return Manager.getConfig().getString("Commands.Delete.NotFound");
	}
	
	@Override
	protected List<String> getTabCompletion(final CommandSender sender, final List<String> args) {
		List<String> list = new ArrayList<String>();
		if (!sender.hasPermission("blockdisplays.command.delete") || args.size() > 1) {
			return new ArrayList<String>();
		}
		for (String name : Manager.getFloatingBlocksConfig().getConfigurationSection("FloatingBlocks").getKeys(false)) {
			list.add(name);
		}
		return list;
	}
}
