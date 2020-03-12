package com.hetag.blockdisplays.commands;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

import com.hetag.blockdisplays.BlockDisplays;
import com.hetag.blockdisplays.blocks.FloatingBlock;
import com.hetag.blockdisplays.configuration.Manager;

public class ListCommand extends BDCommand {

	public ListCommand() {
		super("list", "/bd list", formatColors(Manager.getConfig().getString("Commands.List.Description")), new String[] { "list", "l" });
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!hasPermission(sender) || !correctLength(sender, 0, 0, 1)) {
			return;
		}
		ConfigurationSection cs = BlockDisplays.FloatingBlocks.getConfig().getConfigurationSection("FloatingBlocks");
		if (cs != null) {
			sendMessage(sender, "Existing Blocks: ", true);
			for (String block : cs.getKeys(false)) {
				sendMessage(sender, "&b" + block + "&8 (&3X: &b" + FloatingBlock.getX(block).toString() + " &3Y: &b" + FloatingBlock.getY(block).toString() + " &3Z: &b" + FloatingBlock.getZ(block).toString() + "&8)", false);
			}
		} else {
			sendMessage(sender, notFound(), true);
		}
	}
	
	public String notFound() {
		return Manager.getConfig().getString("Commands.List.NotFound");
	}

}
