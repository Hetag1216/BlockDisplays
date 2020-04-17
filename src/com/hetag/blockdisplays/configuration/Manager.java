package com.hetag.blockdisplays.configuration;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.configuration.file.FileConfiguration;

import com.hetag.blockdisplays.commands.Executor;

public class Manager {
	public static Config defaultConfig;

	public Manager() {
		defaultConfig = new Config(new File("config.yml"));
		loadConfig(ConfigType.DEFAULT);
	}

	private void loadConfig(ConfigType type) {
		if (type == ConfigType.DEFAULT) {
		FileConfiguration config = defaultConfig.getConfig();
		config.addDefault("Settings.Language.NoPermission", "&cYou don't own sufficent permissions!");
		config.addDefault("Settings.Language.MustBePlayer", "&cYou must be a player in order to do this!");
		config.addDefault("Settings.Language.ChatPrefix", "&8[&bBlockDisplays&8]&b ");
		
		ArrayList<String> helpLines = new ArrayList<>();
		Executor.help = helpLines;
		config.addDefault("Commands.HelpLines", helpLines);
		helpLines.add("&8/&3bd &ahelp &7Display commands help.");	
		
		config.addDefault("Commands.Help.Required", "&7Required");
		config.addDefault("Commands.Help.Optional", "&7Optional");
		config.addDefault("Commands.Help.InvalidTopic", "&7Please specify a valid topic.");
		config.addDefault("Commands.Help.ProperUsage", "&3Proper usage: &b&o");
		config.addDefault("Commands.Help.Description", "Shows information about a command.");
		
		config.addDefault("Commands.Version.Description", "Shows plugin's version and information.");
		
		config.addDefault("Commands.Create.Description", "Creates a block at the player's standing position.");
		config.addDefault("Commands.Create.OnCreate", "Succesfully created &3%name%&b!");
		config.addDefault("Commands.Create.AlreadyExists", "A block with the name of &3%name%&b already exists!");
		config.addDefault("Commands.Create.MaterialUnMatch", "&3%material%&b couldn't be matched, please use a different material type!");
		config.addDefault("Commands.Create.SizeUnMatch", "&3%size%&b couldn't be found, please choose a size between these: &3%possibleSizes%&b.");
		
		config.addDefault("Commands.Delete.Description", "Deletes an existing block.");
		config.addDefault("Commands.Delete.OnSuccess", "&3%name% &bwas succesfully removed!");
		config.addDefault("Commands.Delete.OnFail", "&3%name% &bcouldn't be found!");
		
		config.addDefault("Commands.List.Description", "Shows a list of existing blocks.");
		config.addDefault("Commands.List.NotFound", "There currently aren't any existing blocks.");
		
		config.addDefault("Commands.Info.Description", "Shows details about an existing block.");
		config.addDefault("Commands.Info.OnInvalid", "&3%name%&b doesn't exist!");
		
		config.addDefault("Commands.Rotate.Description", "Rotates an existing block's face direction.");
		config.addDefault("Commands.Rotate.OnRotate", "The block was succesfully rotated of &3%value%&b degree(s)");
		config.addDefault("Commands.Rotate.OnInvalid", "Couldn't find &3%name%&b!");
		
		config.addDefault("Commands.Teleport.Description", "Teleports an existing block to player's position.");
		config.addDefault("Commands.Teleport.OnTeleport", "&3%name% &bwas succesfully teleported!");
		
		config.addDefault("Commands.Reload.Description", "Reloads plugin's configurations.");
		config.addDefault("Commands.Reload.OnReload", "&aPlugin's configurations were succesfully reloaded!");
		config.addDefault("Commands.Reload.onFail", "&cPlugin's configurations couldn't be reloaded correctly, printing stack trace in console.");
		defaultConfig.saveConfig();
		}
	}

	  public static FileConfiguration getConfig() {
	    return defaultConfig.getConfig();
	  }
}
