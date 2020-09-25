package com.hetag.blockdisplays.commands;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import com.hetag.blockdisplays.BlockDisplays;
import com.hetag.blockdisplays.blocks.FloatingBlock;
import com.hetag.blockdisplays.configuration.Manager;

public class EditCommand extends BDCommand {

	public static List<String> usageDescription;

	public EditCommand() {
		super("edit", "/bd edit", formatColors(Manager.getConfig().getString("Commands.Edit.Description")),
				new String[] { "edit", "edi" });
		usageDescription = Manager.getConfig().getStringList("Commands.Edit.UsageDescription");
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (this.hasPermission(sender)) {
			// if (args.size() > 0) {
			if (args.size() <= 1) {
				for (String line : usageDescription) {
					sendMessage(sender, line, false);
				}
				return;
			}
			String floatingBlock = args.get(0);

			if (!FloatingBlock.exists(floatingBlock)) {
				sendMessage(sender, notFound().replace("%name%", floatingBlock), true);
				return;
			} else {
				if (!FloatingBlock.isAlive(floatingBlock)) {
					sendMessage(sender, onInvalid().replace("%name%", floatingBlock), true);
					return;
				}
			}

			String var = args.get(1);

			if (var.equalsIgnoreCase("location")) {
				if (args.size() != 4) {
					for (String line : usageDescription) {
						sendMessage(sender, line, false);
					}
					return;
				}
				String coord = args.get(2);
				if (!isNumeric(args.get(3))) {
					sendMessage(sender, notNumeric().replace("%value%", args.get(2)), true);
					return;
				}
				double value = Double.valueOf(args.get(3));
				Location location = FloatingBlock.getLocation(floatingBlock);
				if (coord.equalsIgnoreCase("x")) {
					location.add(value, 0, 0);
				} else if (coord.equalsIgnoreCase("y")) {
					location.add(0, value, 0);
				} else if (coord.equalsIgnoreCase("z")) {
					location.add(0, 0, value);
				} else {
					sendMessage(sender, onWrongArgument().replace("%argument%", coord), true);
					return;
				}
				FloatingBlock.getFloatingBlockByUUID(floatingBlock).teleport(location);
				FloatingBlock.updateLocation(floatingBlock);
				sendMessage(sender, onEdit_Location().replace("%name%", floatingBlock).replace("%coord%", coord).replace("%value%", String.valueOf(value)), true);

			} else if (var.equalsIgnoreCase("rotate")) {
				if (args.size() != 3) {
					for (String line : usageDescription) {
						sendMessage(sender, line, false);
					}
					return;
				}
				if (FloatingBlock.exists(floatingBlock)) {
					if (FloatingBlock.isAlive(floatingBlock)) {
						if (isNumeric(args.get(2))) {
							float yaw = Float.valueOf(args.get(2));
							FloatingBlock.rotateBlock(floatingBlock, yaw);
							sendMessage(sender, onEdit_Rotate().replace("%value%", args.get(2)), true);
						} else {
							sendMessage(sender, notNumeric().replace("%value%", args.get(2)), true);
							return;
						}
					} else {
						sendMessage(sender, onInvalid().replace("%name%", floatingBlock), true);
						return;
					}
				} else {
					sendMessage(sender, notFound().replace("%name%", floatingBlock), true);
					return;
				}

			} else if (var.equalsIgnoreCase("settings")) {
				if (args.size() != 4) {
					for (String line : usageDescription) {
						sendMessage(sender, line, false);
					}
					return;
				}
				String setting = args.get(2);
				String value = args.get(3);
				if (setting.equalsIgnoreCase("AutomaticRotation")) {
					if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
						BlockDisplays.FloatingBlocks.getConfig().set("FloatingBlocks." + floatingBlock + ".AutomaticRotation.Enabled", Boolean.valueOf(value));
						sendMessage(sender, onEdit_Settings_AutomaticRotation().replace("%value%", value).replace("%name%", floatingBlock), true);
						BlockDisplays.FloatingBlocks.saveConfig();
					}
				} else if (setting.equalsIgnoreCase("Interval")) {
					if (isNumeric(value)) {
						BlockDisplays.FloatingBlocks.getConfig().set("FloatingBlocks." + floatingBlock + ".AutomaticRotation.Interval", Integer.valueOf(value));
						sendMessage(sender, onEdit_Settings_AutomaticRotation_Interval().replace("%value%", value).replace("%name%", floatingBlock), true);
						BlockDisplays.FloatingBlocks.saveConfig();
					} else {
						sendMessage(sender, notNumeric().replace("%value%", value), true);
						return;
					}
				} else if (setting.equalsIgnoreCase("Degrees")) {
					if (isNumeric(value)) {
						BlockDisplays.FloatingBlocks.getConfig().set("FloatingBlocks." + floatingBlock + ".AutomaticRotation.Degrees", Float.valueOf(value));
						sendMessage(sender, onEdit_Settings_AutomaticRotation_Degrees().replace("%value%", value).replace("%name%", floatingBlock), true);
						BlockDisplays.FloatingBlocks.saveConfig();
					} else {
						sendMessage(sender, notNumeric().replace("%value%", value), true);
						return;
					}
				} else {
					sendMessage(sender, onWrongArgument().replace("%argument%", setting), true);
					return;
				}
			} else {
				sendMessage(sender, onWrongArgument().replace("%argument%", var), true);
				return;
			}
		}
	}

	private String onEdit_Location() {
		return Manager.getConfig().getString("Commands.Edit.Location.OnEdit");
	}

	private String onEdit_Rotate() {
		return Manager.getConfig().getString("Commands.Edit.Rotate.OnEdit");
	}

	private String onEdit_Settings_AutomaticRotation() {
		return Manager.getConfig().getString("Commands.Edit.Settings.OnEdit.AutomaticRotation.Status");
	}

	private String onEdit_Settings_AutomaticRotation_Interval() {
		return Manager.getConfig().getString("Commands.Edit.Settings.OnEdit.AutomaticRotation.Interval");
	}
	
	private String onEdit_Settings_AutomaticRotation_Degrees() {
		return Manager.getConfig().getString("Commands.Edit.Settings.OnEdit.AutomaticRotation.Degrees");
	}

	private String notNumeric() {
		return Manager.getConfig().getString("Commands.Edit.NotNumeric");
	}

	private String notFound() {
		return Manager.getConfig().getString("Commands.Edit.NotFound");
	}

	private String onInvalid() {
		return Manager.getConfig().getString("Commands.Edit.OnInvalid");
	}

	private String onWrongArgument() {
		return Manager.getConfig().getString("Commands.Edit.OnWrongArgument");
	}
}