package com.hetag.blockdisplays.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;

import com.hetag.blockdisplays.BlockDisplays;
import com.hetag.blockdisplays.configuration.Manager;

public class Executor {
	private BlockDisplays plugin;

	public Executor(BlockDisplays plugin) {
		this.plugin = plugin;
		init();
	}

	public static String[] commandaliases = { "bd", "blockdisplays" };
	public static List<String> help;

	private void init() {
		PluginCommand bd = this.plugin.getCommand("bd");
		new HelpCommand();
		new CreateCommand();
		new DeleteCommand();
		new ListCommand();
		new VersionCommand();
		new RotateCommand();
		new TeleportCommand();
		new ConfigReloadCommand();
		new InfoCommand();
		new LocationCommand();
		help = Manager.getConfig().getStringList("Commands.HelpLines");

		CommandExecutor exe = new CommandExecutor() {
			public boolean onCommand(CommandSender s, Command c, String label, String[] args) {
				if ((args.length == 0) && (Arrays.asList(Executor.commandaliases).contains(label.toLowerCase()))) {
					for (String line : Executor.help) {
						s.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
					}
					return true;
				}
				List<String> sendingArgs = Arrays.asList(args).subList(1, args.length);
				for (BDCommand command : BDCommand.instances.values()) {
					if (Arrays.asList(command.getAliases()).contains(args[0].toLowerCase())) {
						command.execute(s, sendingArgs);
						return true;
					}
				}
				return true;
			}
		};
		bd.setExecutor(exe);
	}
}
