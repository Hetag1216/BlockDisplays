package com.hetag.blockdisplays.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.hetag.blockdisplays.blocks.FloatingBlock;
import com.hetag.blockdisplays.blocks.FloatingBlock.Sizes;
import com.hetag.blockdisplays.configuration.Manager;

public class CreateCommand extends BDCommand {

	public CreateCommand() {
		super("create", "/bd create <name> <material> <size>", Manager.getConfig().getString("Commands.Create.Description"), new String[] { "create", "c" });
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!isPlayer(sender) || !hasPermission(sender) || !this.correctLength(sender, args.size(), 3, 3)) {
			return;
		}
		final String name = args.get(0), sizeArg = args.get(2);
		final Material material = Material.matchMaterial(args.get(1));
		Sizes size;
		if (FloatingBlock.exists(name)) {
			sendMessage(sender, onExist().replace("%name%", name), true);
			return;
		}
		if (material == null) {
			sendMessage(sender, onMatMismatch().replace("%material%", args.get(1)), true);
			return;
		}
		if (sizeArg.equalsIgnoreCase("tiny")) {
			size = Sizes.Tiny;
		} else if (sizeArg.equalsIgnoreCase("small")) {
			size = Sizes.Small;
		} else if (sizeArg.equalsIgnoreCase("normal")) {
			size = Sizes.Normal;
		} else {
			sendMessage(sender, onSizeMismatch().replace("%size%", args.get(2)).replace("%possibleSizes%", "Tiny, Small, Normal"), true);
			return;
		}
		Player player = (Player) sender;
		new FloatingBlock(name, player.getLocation().add(0, 0, 0), material, size);
		sendMessage(player, onCreate().replace("%name%", args.get(0)), true);
		return;
	}

	public String onCreate() {
		return Manager.getConfig().getString("Commands.Create.OnCreate");
	}

	public String onExist() {
		return Manager.getConfig().getString("Commands.Create.AlreadyExists");
	}

	public String onMatMismatch() {
		return Manager.getConfig().getString("Commands.Create.MaterialMismatch");
	}

	public String onSizeMismatch() {
		return Manager.getConfig().getString("Commands.Create.SizeMismatch");
	}

	@Override
	protected List<String> getTabCompletion(final CommandSender sender, final List<String> args) {
		List<String> list = new ArrayList<String>();
		if (!sender.hasPermission("blockdisplays.command.create") || args.size() > 3) {
			return new ArrayList<String>();
		}
		if (args.size() == 1) {
			for (Material mat : Material.values()) {
				list.add(mat.name());
			}
		} else if (args.size() == 2) {
			list.add("tiny");
			list.add("small");
			list.add("normal");
		}
		return list;
	}
}
