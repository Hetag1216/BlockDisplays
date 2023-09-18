package com.hedario.blockdisplays.configuration;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.configuration.file.FileConfiguration;

import com.hedario.blockdisplays.commands.EditCommand;
import com.hedario.blockdisplays.commands.Executor;

public class Manager {
	public static Config defaultConfig;
	public static Config floatingBlocksConfig;

	public Manager() {
		defaultConfig = new Config(new File("config.yml"));
		floatingBlocksConfig = new Config(new File("FloatingBlocks.yml"));
		loadConfig(defaultConfig);
	}
	
	public static void reloadConfigs() {
		defaultConfig.reloadConfig();
		floatingBlocksConfig.reloadConfig();
	}

	private void loadConfig(Config configurationFile) {
		FileConfiguration config = null;
		if (configurationFile == defaultConfig) {
			config = defaultConfig.getConfig();
			config.addDefault("Settings.Metrics.Enabled", true);
			config.addDefault("Settings.Updater.Enabled", true);
			config.addDefault("Settings.Announcer.Enabled", true);
			
			config.addDefault("Settings.Language.NoPermission", "&cYou don't own sufficent permissions.");
			config.addDefault("Settings.Language.MustBePlayer", "&cYou must be a player in order to do this.");
			config.addDefault("Settings.Language.ChatPrefix", "&8[&bBlockDisplays&8]&b ");
			config.addDefault("Settings.Language.InvalidBlock", "&3%name%&b exists but cannot be found in the world, please re-create the block.");
			config.addDefault("Settings.Language.BlockNotFound", "&3%name%&b doesn't exist.");
			
			ArrayList<String> helpLines = new ArrayList<>();
			Executor.help = helpLines;
			config.addDefault("Commands.HelpLines", helpLines);
			helpLines.add("&8/&3bd &ahelp &bDisplay commands help.");	
			config.addDefault("Commands.Help.InvalidTopic", "&cPlease specify a valid topic.");
			config.addDefault("Commands.Help.ProperUsage", "&3Proper usage: &b&o");
			config.addDefault("Commands.Help.Description", "&bShows information about a command.");
			
			config.addDefault("Commands.Version.Description", "&bShows plugin's version and information.");
			
			config.addDefault("Commands.Create.Description", "&bCreates a block at the player's standing position.");
			config.addDefault("Commands.Create.OnCreate", "&bSuccesfully created &3%name%&b.");
			config.addDefault("Commands.Create.AlreadyExists", "&bA block with the name of &3%name%&b already exists.");
			config.addDefault("Commands.Create.MaterialMismatch", "&3%material%&b couldn't be matched, please use a different material type.");
			config.addDefault("Commands.Create.SizeMismatch", "&3%size%&b couldn't be found, please choose a size between these: &3%possibleSizes%&b.");
			
			config.addDefault("Commands.Delete.Description", "&bDeletes an existing block.");
			config.addDefault("Commands.Delete.OnSuccess", "&3%name% &bwas succesfully removed.");
			config.addDefault("Commands.Delete.NotFound", "&3%name% &bcouldn't be found.");
			
			config.addDefault("Commands.List.Description", "&bShows a list of existing blocks.");
			
			config.addDefault("Commands.Info.Description", "&bShows details about an existing block.");
			
			config.addDefault("Commands.Edit.Description", "&bEdits the existing block depending on the given variable.");
			config.addDefault("Commands.Edit.NotNumeric", "&3%value%&b is not numeric, specify a value in numbers.");
			config.addDefault("Commands.Edit.OnWrongArgument", "&bThe provided argument '&3%argument%&b' is either wrong or invalid.");
			config.addDefault("Commands.Edit.Location.OnEdit", "&bSuccesfully edited &3%coord% &bby &3%value%&b for &3%name%&b.");
			config.addDefault("Commands.Edit.Rotate.OnEdit", "&bThe block was succesfully rotated of &3%value%&b degree(s).");
			config.addDefault("Commands.Edit.Settings.OnEdit.AutomaticRotation.Status", "&bAutomatic rotation has been set to &3%value%&b for &3%name%&b.");
			config.addDefault("Commands.Edit.Settings.OnEdit.AutomaticRotation.Interval", "&bAutomatic rotation interval has been set to &3%value%&bms for &3%name%&b.");
			config.addDefault("Commands.Edit.Settings.OnEdit.AutomaticRotation.Degrees", "&bAutomatic rotation degree(s) have been set to &3%value%&b for &3%name%&b.");
			ArrayList<String> usageDescription = new ArrayList<String>();
			EditCommand.usageDescription = usageDescription;
			config.addDefault("Commands.Edit.UsageDescription", usageDescription);
			usageDescription.add("&bProper Usage: /bd edit <name> <variable> <extra arguments...>");
			usageDescription.add("");
			usageDescription.add("&bAvailable variables: &3Location, Rotate, Settings.");
			usageDescription.add("");
			usageDescription.add("&3Location usage: &b/bd edit <name> Location <coord> <value>");
			usageDescription.add("&bAvailable coords' inputs: &3x&b, &3y&b, &3z");
			usageDescription.add("");
			usageDescription.add("&3Rotate usage: &b/bd edit <name> Rotate <value>");
			usageDescription.add("");
			usageDescription.add("&3Settings usage: &b/bd edit <name> Settings <setting> <value>");
			usageDescription.add("&bAvailable settings: &3AutomaticRotation&b, &3Interval&b, &3Degrees.");
			usageDescription.add("&bThe value for the &3AutomaticRotation&b setting must be &atrue&b or &cfalse&b.");
			usageDescription.add("&bThe values for the &3Interval&b setting must be a set number determined in milliseconds. - &8(1000 = 1 second)");
			usageDescription.add("&bThe values for the &3Degrees&b setting can be any number which determines the block's facing angles.");
			
			
			config.addDefault("Commands.Teleport.Description", "&bTeleports an existing block to player's position or a player to an existing block.");
			config.addDefault("Commands.Teleport.TeleportHere", "&3%name% &bwas succesfully teleported.");
			config.addDefault("Commands.Teleport.TeleportTo", "&bYou've succesfully been teleported to &3%name%&b.");
			
			config.addDefault("Commands.Reload.Description", "&bReloads plugin's configurations.");
			config.addDefault("Commands.Reload.OnReload", "&aPlugin's configurations were succesfully reloaded.");
			config.addDefault("Commands.Reload.OnFail", "&cPlugin's configurations couldn't be reloaded correctly, printing stack trace in console.");
			defaultConfig.saveConfig();
		} else if (configurationFile == floatingBlocksConfig) {
			config = floatingBlocksConfig.getConfig();
			floatingBlocksConfig.saveConfig();			
		} else {
			return;
		}
	}

	  public static FileConfiguration getConfig() {
	    return defaultConfig.getConfig();
	  }
	  
	  public static FileConfiguration getFloatingBlocksConfig() {
		  return floatingBlocksConfig.getConfig();
	  }
}
