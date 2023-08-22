package com.hedario.blockdisplays.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

import com.hedario.blockdisplays.configuration.Manager;

public class ListCommand extends BDCommand {
	

	public ListCommand() {
		super("list", "/bd list", formatColors(Manager.getConfig().getString("Commands.List.Description")), new String[] { "list", "l" });
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!hasPermission(sender) || !correctLength(sender, 0, 0, 1)) {
			return;
		}
		if (args.size() == 0) {
			List<String> strings = new ArrayList<>();
			ConfigurationSection cs = Manager.getFloatingBlocksConfig().getConfigurationSection("FloatingBlocks");
			if (cs != null) {
				for (String block : cs.getKeys(false)) {
					strings.add(block);
				}
			} else {
				sendMessage(sender, notFound(), true);
				return;
			}
			Collections.sort(strings);
			Collections.reverse(strings);
			for (String formatted : getPage(strings, 1, true)) {
				sendMessage(sender, "&b" + formatted, false);
			}
			return;
		} else if (args.size() == 1) {
			String arg = args.get(0).toLowerCase();
			if (isNumeric(arg)) {
				List<String> strings = new ArrayList<>();
				ConfigurationSection cs = Manager.getFloatingBlocksConfig().getConfigurationSection("FloatingBlocks");
				if (cs != null) {
					for (String blocks : cs.getKeys(false)) {
						strings.add(blocks);
					}
				} else {
					sendMessage(sender, notFound(), true);
				}
				for (String formatted : getPage(strings, Integer.valueOf(arg), true)) {
					sendMessage(sender, "&b" + formatted, false);
				}
			}
		}
	}
	
	public String notFound() {
		return Manager.getConfig().getString("Commands.List.NotFound");
	}
}
