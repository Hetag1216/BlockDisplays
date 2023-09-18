package com.hedario.blockdisplays;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import com.hedario.blockdisplays.commands.BDCommand;
import com.hedario.blockdisplays.configuration.Manager;

public class FloatingBlock {

	public ArmorStand as;

	public String name;
	public Location location;
	public static long checker = System.currentTimeMillis();
	
	public enum Sizes {
		Tiny, Small, Normal;
	}

	public FloatingBlock(String name, Location location, Material mat, Sizes size) {
		this.name = name;
		this.location = location;
        switch (size) {
		case Tiny:
			as = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
            as.setVisible(false);
            as.setGravity(false);
            as.setArms(true);
        	as.setSmall(true);
        	as.setInvulnerable(true);
        	as.getEquipment().setItemInMainHand(new ItemStack(mat, 1));
			break;
        case Small:
            as = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
            as.getEquipment().setHelmet(new ItemStack(mat, 1));
            as.setVisible(false);
            as.setGravity(false);
        	as.setSmall(true);
        	as.setInvulnerable(true);
        	break;
        case Normal:
            as = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
            as.getEquipment().setHelmet(new ItemStack(mat, 1));
            as.setVisible(false);
            as.setGravity(false);
        	as.setSmall(false);
        	as.setInvulnerable(true);
        	break;
        }
            double x = location.getX();
            double y = location.getY();
            double z = location.getZ();
    		Manager.getFloatingBlocksConfig().set("FloatingBlocks." + name + ".Location.World", location.getWorld().getName());
    		Manager.getFloatingBlocksConfig().set("FloatingBlocks." + name + ".Location.X", x);
    		Manager.getFloatingBlocksConfig().set("FloatingBlocks." + name + ".Location.Y", y);
    		Manager.getFloatingBlocksConfig().set("FloatingBlocks." + name + ".Location.Z", z);
    		Manager.getFloatingBlocksConfig().set("FloatingBlocks." + name + ".Location.Pitch", Math.round(location.getPitch()));
    		Manager.getFloatingBlocksConfig().set("FloatingBlocks." + name + ".Location.Yaw", Math.round(location.getYaw()));
    		Manager.getFloatingBlocksConfig().set("FloatingBlocks." + name + ".Size", size.name());
    		Manager.getFloatingBlocksConfig().set("FloatingBlocks." + name + ".AutomaticRotation.Enabled", false);
    		Manager.getFloatingBlocksConfig().set("FloatingBlocks." + name + ".AutomaticRotation.Interval", 500);
    		Manager.getFloatingBlocksConfig().set("FloatingBlocks." + name + ".AutomaticRotation.Degrees", 20);
		if (size == Sizes.Normal || size == Sizes.Small) {
			Manager.getFloatingBlocksConfig().set("FloatingBlocks." + name + ".Material", as.getEquipment().getHelmet().getType().toString());
		} else if (size == Sizes.Tiny) {
			Manager.getFloatingBlocksConfig().set("FloatingBlocks." + name + ".Material", as.getEquipment().getItemInMainHand().getType().toString());
		}
		Manager.getFloatingBlocksConfig().set("FloatingBlocks." + name + ".UUID", as.getUniqueId().toString());
		Manager.floatingBlocksConfig.saveConfig();
	}
	
	public static World getWorld(String name) {
		return Bukkit.getServer().getWorld(Manager.getFloatingBlocksConfig().getString("FloatingBlocks." + name + ".Location.World"));
	}
	
	public static float getX(String name) {
		return (float) Manager.getFloatingBlocksConfig().getDouble("FloatingBlocks." + name + ".Location.X");
	}
	
	public static float getY(String name) {
		return (float) Manager.getFloatingBlocksConfig().getDouble("FloatingBlocks." + name + ".Location.Y");
	}
	
	public static float getZ(String name) {
		return (float) Manager.getFloatingBlocksConfig().getDouble("FloatingBlocks." + name + ".Location.Z");
	}
	
	public static Material getMaterial(String name) {
		return Material.valueOf(Manager.getFloatingBlocksConfig().getString("FloatingBlocks." + name + ".Material"));
	}
	
	public static String getSize(String name) {
		return Manager.getFloatingBlocksConfig().getString("FloatingBlocks." + name + ".Size");
	}
	
	public static UUID getUUID(String name) {
		return UUID.fromString(Manager.getFloatingBlocksConfig().getString("FloatingBlocks." + name + ".UUID"));
	}
	
	public static double getYaw(String name) {
		return Manager.getFloatingBlocksConfig().getDouble("FloatingBlocks." + name + ".Location.Yaw");
	}
	
	public static double getPitch(String name) {
		return Manager.getFloatingBlocksConfig().getDouble("FloatingBlocks." + name + ".Location.Pitch");
	}
	
	public static boolean isAutomaticallyRotating(String name) {
		return Manager.getFloatingBlocksConfig().getBoolean("FloatingBlocks." + name + ".AutomaticRotation.Enabled");
	}
	
	public static long getAutomaticRotationInterval(String name) {
		return Manager.getFloatingBlocksConfig().getLong("FloatingBlocks." + name + ".AutomaticRotation.Interval");
	}
	
	public static float getAutomaticRotationDegrees(String name) {
		return (float) Manager.getFloatingBlocksConfig().getDouble("FloatingBlocks." + name + ".AutomaticRotation.Degrees");
	}
	
	public static void rotateBlock(String name, float yaw) {
		Entity entity = getFloatingBlockByUUID(name);
		Location loc = getFloatingBlockByUUID(name).getLocation();
		loc.setYaw(loc.getYaw() + yaw);
		entity.teleport(loc);
	}
	
	public static void updateLocation(String name, final Location location) {
		FloatingBlock.getFloatingBlockByUUID(name).teleport(location);
		Manager.getFloatingBlocksConfig().set("FloatingBlocks." + name + ".Location.X", location.getX());
		Manager.getFloatingBlocksConfig().set("FloatingBlocks." + name + ".Location.Y", location.getY());
		Manager.getFloatingBlocksConfig().set("FloatingBlocks." + name + ".Location.Z", location.getZ());
		Manager.getFloatingBlocksConfig().set("FloatingBlocks." + name + ".Location.Yaw", Math.round(location.getYaw()));
		Manager.getFloatingBlocksConfig().set("FloatingBlocks." + name + ".Location.Pitch", Math.round(location.getPitch()));
		Manager.floatingBlocksConfig.saveConfig();
	}
	
	public static Entity getFloatingBlockByUUID(String name) {
		UUID uuid = getUUID(name);
		for (Entity entity : FloatingBlock.getWorld(name).getEntities()) {
			if (entity.getUniqueId().equals(uuid)) {
				return entity;
			}
		}
		return null;
	}
	/**
	 * Checks if the name is contained in the config file.
	 * @param name
	 * @return
	 */
	public static boolean exists(String name) {
		if (Manager.getFloatingBlocksConfig().contains("FloatingBlocks." + name)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks whether or not the block exists in the world by matching the UUID.
	 * @param name
	 * @return
	 */
	public static boolean isAlive(String name) {
		if (getWorld(name) != null) {
			if (getFloatingBlockByUUID(name) != null)
			return true;
		}
		return false;
	}

	public static List<String> getFloatingBlocks() {
		List<String> fbs = new ArrayList<String>();
		for (String names : Manager.getFloatingBlocksConfig().getConfigurationSection("FloatingBlocks").getKeys(false)) {
			fbs.add(names);
		}
		return fbs;
	}

	public static Location getLocation(String name) {
		float x = getX(name);
		float y = getY(name);
		float z = getZ(name);
		World w = getWorld(name);
		
		return new Location(w, x, y, z);
	}
	
	public static boolean validateFloatingBlock(String name) {
	    if (!exists(name) || !isAlive(name)) {
	        return false;
	    }
	    return true;
	}
	
	public static boolean validateFloatingBlock(CommandSender sender, String name) {
		if (!exists(name)) {
			BDCommand.pSendMessage(sender, Manager.getConfig().getString("Settings.Language.BlockNotFound").replace("%name%", name), true);
			return false;
		}
		if (!isAlive(name)) {
			BDCommand.pSendMessage(sender, Manager.getConfig().getString("Settings.Language.InvalidBlock").replace("%name%", name), true);
			return false;
		}
		return true;
	}
}
