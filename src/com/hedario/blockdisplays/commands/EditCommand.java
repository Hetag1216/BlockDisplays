package com.hedario.blockdisplays.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import com.hedario.blockdisplays.FloatingBlock;
import com.hedario.blockdisplays.Rotation;
import com.hedario.blockdisplays.configuration.Manager;

public class EditCommand extends BDCommand {

	public static List<String> usageDescription;

	public EditCommand() {
		super("edit", "/bd edit", formatColors(Manager.getConfig().getString("Commands.Edit.Description")), new String[] { "edit", "edi" });
		usageDescription = Manager.getConfig().getStringList("Commands.Edit.UsageDescription");
	}
	@Override
	public void execute(CommandSender sender, List<String> args) {
	    if (!hasPermission(sender)) {
	        return;
	    }

	    if (!correctLength(sender, args.size(), 2, 4)) {
	        showUsageDescription(sender);
	        return;
	    }

	    String name = args.get(0);

	    if (!validateFloatingBlock(sender, name)) {
	        return;
	    }

	    String subCommand = args.get(1);
	    if (subCommand.equalsIgnoreCase("location")) {
	        handleLocationCommand(sender, name, args);
	    } else if (subCommand.equalsIgnoreCase("rotate")) {
	        handleRotateCommand(sender, name, args);
	    } else if (subCommand.equalsIgnoreCase("settings")) {
	        handleSettingsCommand(sender, name, args);
	    } else {
	        sendMessage(sender, onWrongArgument().replace("%argument%", subCommand), true);
	    }
	}

	private void showUsageDescription(CommandSender sender) {
	    for (String line : usageDescription) {
	        sendMessage(sender, line, false);
	    }
	}

	private boolean validateFloatingBlock(CommandSender sender, String name) {
	    if (!FloatingBlock.exists(name)) {
	        sendMessage(sender, notFound().replace("%name%", name), true);
	        return false;
	    }
	    if (!FloatingBlock.isAlive(name)) {
	        sendMessage(sender, onInvalid().replace("%name%", name), true);
	        return false;
	    }
	    return true;
	}
	
	private void handleLocationCommand(CommandSender sender, String name, List<String> args) {
	    if (args.size() != 4) {
	        showUsageDescription(sender);
	        return;
	    }
	    String coord = args.get(2);
	    double value = Double.parseDouble(args.get(3));
	    if (!isNumeric(args.get(3))) {
	        sendMessage(sender, notNumeric().replace("%value%", coord), true);
	        return;
	    }
	    Location location = FloatingBlock.getLocation(name);
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
	    FloatingBlock.updateLocation(name, location);
	    sendMessage(sender, edit_loc().replace("%name%", name).replace("%coord%", coord).replace("%value%", String.valueOf(value)), true);
	}

	private void handleRotateCommand(CommandSender sender, String name, List<String> args) {
	    if (args.size() != 3) {
	        showUsageDescription(sender);
	        return;
	    }

	    if (isNumeric(args.get(2))) {
	        float yaw = Float.parseFloat(args.get(2));
	        FloatingBlock.rotateBlock(name, yaw);
	        sendMessage(sender, edit_rot().replace("%value%", args.get(2)), true);
	    } else {
	        sendMessage(sender, notNumeric().replace("%value%", args.get(2)), true);
	    }
	}

	private void handleSettingsCommand(CommandSender sender, String name, List<String> args) {
	    if (args.size() != 4) {
	        showUsageDescription(sender);
	        return;
	    }

	    String setting = args.get(2);
	    String value = args.get(3);

	    if (setting.equalsIgnoreCase("AutomaticRotation")) {
	        if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
	            Manager.getFloatingBlocksConfig().set("FloatingBlocks." + name + ".AutomaticRotation.Enabled", Boolean.valueOf(value));
	            sendMessage(sender, edit_autoRot().replace("%value%", value).replace("%name%", name), true);
	            Manager.floatingBlocksConfig.saveConfig();
	        }
	    } else if (setting.equalsIgnoreCase("Interval")) {
	        if (isNumeric(value)) {
	            Manager.getFloatingBlocksConfig().set("FloatingBlocks." + name + ".AutomaticRotation.Interval", Integer.valueOf(value));
	            sendMessage(sender, edit_autoRotInterval().replace("%value%", value).replace("%name%", name), true);
	            Manager.floatingBlocksConfig.saveConfig();
	        } else {
	            sendMessage(sender, notNumeric().replace("%value%", value), true);
	        }
	    } else if (setting.equalsIgnoreCase("Degrees")) {
	        if (isNumeric(value)) {
	            Manager.getFloatingBlocksConfig().set("FloatingBlocks." + name + ".AutomaticRotation.Degrees", Float.valueOf(value));
	            sendMessage(sender, edit_autoRotDegrees().replace("%value%", value).replace("%name%", name), true);
	            Manager.floatingBlocksConfig.saveConfig();
	        } else {
	            sendMessage(sender, notNumeric().replace("%value%", value), true);
	        }
	    } else {
	        sendMessage(sender, onWrongArgument().replace("%argument%", setting), true);
	    }
	    Rotation.update(name, FloatingBlock.getAutomaticRotationInterval(name));
	}

	private String edit_loc() {
		return Manager.getConfig().getString("Commands.Edit.Location.OnEdit");
	}

	private String edit_rot() {
		return Manager.getConfig().getString("Commands.Edit.Rotate.OnEdit");
	}

	private String edit_autoRot() {
		return Manager.getConfig().getString("Commands.Edit.Settings.OnEdit.AutomaticRotation.Status");
	}

	private String edit_autoRotInterval() {
		return Manager.getConfig().getString("Commands.Edit.Settings.OnEdit.AutomaticRotation.Interval");
	}
	
	private String edit_autoRotDegrees() {
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
	
	@Override
	protected List<String> getTabCompletion(final CommandSender sender, final List<String> args) {
		List<String> list = new ArrayList<String>();
		if (!sender.hasPermission("blockdisplays.command.edit") || args.size() > 4) {
			return new ArrayList<String>();
		}
		if (args.size() == 0) {
			return FloatingBlock.getFloatingBlocks();
		} else if (args.size() == 1) {
			list.add("location");
			list.add("rotate");
			list.add("settings");
		} else if (args.size() == 2) {
			if (args.get(1).equalsIgnoreCase("location")) {
				list.add("x");
				list.add("y");
				list.add("z");
			} else if (args.get(1).equalsIgnoreCase("rotate")) {
				list.add("-45");
				list.add("20.5348");
				list.add("180");
			} else if (args.get(1).equalsIgnoreCase("settings")) {
				list.add("AutomaticRotation");
				list.add("Degrees");
				list.add("Interval");
			} else {
				return list;
			}
		} else if (args.size() == 3) {
			if (args.get(2).equalsIgnoreCase("x") || args.get(2).equalsIgnoreCase("y") || args.get(2).equalsIgnoreCase("z")) {
				list.add("-1");
				list.add("0.6");
				list.add("2");
			} else if (args.get(2).equalsIgnoreCase("automaticrotation")) {
				list.add("true");
				list.add("false");
			} else if (args.get(2).equalsIgnoreCase("degrees")) {
				list.add("-45");
				list.add("20.5348");
				list.add("180");
			} else if (args.get(2).equalsIgnoreCase("interval")) {
				list.add("500");
			} else {
				return list;
			}
		}
		return list;
	}
}