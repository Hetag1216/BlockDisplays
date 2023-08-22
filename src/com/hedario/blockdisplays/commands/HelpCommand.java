package com.hedario.blockdisplays.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.CommandSender;

import com.hedario.blockdisplays.configuration.Manager;

public class HelpCommand extends BDCommand {
	private String invalidTopic;

	public HelpCommand() {
		super("help", "/bd help <Page/Topic>", Manager.getConfig().getString("Commands.Help.Description"), new String[] { "help", "h" });
		this.invalidTopic = Manager.getConfig().getString("Commands.Help.InvalidTopic");
	}

	public void execute(CommandSender sender, List<String> args) {
		if ((!hasPermission(sender)) || (!correctLength(sender, args.size(), 0, 1))) {
			return;
		}
		if (args.size() == 0) {
			List<String> strings = new ArrayList<>();
			for (BDCommand command : instances.values()) {
				if ((!command.getName().equalsIgnoreCase("help")) && (sender.hasPermission("blockdisplays.command." + command.getName()))) {
					strings.add(command.getProperUse());
				}
			}
			Collections.sort(strings);
			Collections.reverse(strings);
			Collections.reverse(strings);

			for (String s : getPage(strings, 1, false)) {
				sendMessage(sender, "&a" + s, false);
			}
			return;
		}
		String arg = ((String) args.get(0)).toLowerCase();
		if (isNumeric(arg)) {
			List<String> strings = new ArrayList<>();
			for (BDCommand command : instances.values()) {
				strings.add(command.getProperUse());
			}
			for (String s : getPage(strings, Integer.valueOf(arg).intValue(), true)) {
				sendMessage(sender, "&a" + s, false);
			}
		} else if (instances.keySet().contains(arg)) {
			((BDCommand) instances.get(arg)).help(sender, true);
		} else {
			sendMessage(sender, this.invalidTopic, false);
		}
	}
	
	@Override
	protected List<String> getTabCompletion(final CommandSender sender, final List<String> args) {
		List<String> list = new ArrayList<String>();
		if (!sender.hasPermission("blockdisplays.command.help")) {
			return new ArrayList<String>();
		}
		for (String commands : instances.keySet()) {
			list.add(commands);
		}
		return list;
	}
}
