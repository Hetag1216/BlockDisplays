package com.hetag.blockdisplays.commands;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.hetag.blockdisplays.BlockDisplays;
import com.hetag.blockdisplays.blocks.FloatingBlock;
import com.hetag.blockdisplays.blocks.FloatingBlock.Sizes;
import com.hetag.blockdisplays.configuration.Manager;

public class CreateCommand extends BDCommand {

	public CreateCommand() {
		super("create", "/bd create <name> <material> <size>", formatColors(Manager.getConfig().getString("Commands.Create.Description")), new String[] { "create", "c" });
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (this.isPlayer(sender) && this.hasPermission(sender) || !correctLength(sender, 0, 3, 3)) {
			if (args.size() == 3) {
				if (BlockDisplays.FloatingBlocks.getConfig().contains("FloatingBlocks." + args.get(0))) {
					sendMessage(sender, onExist().replace("%name%", args.get(0)), true);
					return;
				}
				Material mat = Material.matchMaterial(args.get(1));
				if (mat == null) {
					sendMessage(sender, onMaterialUnMatch().replace("%material%", args.get(1)), true);
					return;
				}
				Sizes size = null;
				if (args.get(2).equalsIgnoreCase("small")) {
					size = Sizes.Small;
				} else if (args.get(2).equalsIgnoreCase("normal")) {
					size = Sizes.Normal;
				} else if (args.get(2).equalsIgnoreCase("tiny")) {
					size = Sizes.Tiny;
				} else {
					String possibleSizes = "Tiny, Small, Normal";
					sendMessage(sender, onSizeUnMatch().replace("%size%", args.get(2)).replace("%possibleSizes%", possibleSizes), true);
					return;
				}
				Player player = (Player) sender;
				new FloatingBlock(args.get(0), player.getLocation().add(0, 0, 0), mat, size);
				sendMessage(player, onCreate().replace("%name%", args.get(0)), true);
				return;
			}
			if (args.size() > 3 || args.size() < 3) {
				sender.sendMessage(getProperUsage());
			}
		} else {
			return;
		}
	}
	
	public String onCreate() {
		return formatColors(Manager.getConfig().getString("Commands.Create.OnCreate"));
	}
	
	public String onExist() {
		return formatColors(Manager.getConfig().getString("Commands.Create.AlreadyExists"));
	}
	
	public String onMaterialUnMatch() {
		return formatColors(Manager.getConfig().getString("Commands.Create.MaterialUnMatch"));
	}
	
	public String onSizeUnMatch() {
		return formatColors(Manager.getConfig().getString("Commands.Create.SizeUnMatch"));
	}
}
