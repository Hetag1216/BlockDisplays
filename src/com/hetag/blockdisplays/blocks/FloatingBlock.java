package com.hetag.blockdisplays.blocks;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import com.hetag.blockdisplays.BlockDisplays;

public class FloatingBlock {
	public static ConcurrentHashMap<ArmorStand, FloatingBlock> instances = new ConcurrentHashMap<>();

	public ArmorStand as;

	public String name;
	public static long checker = System.currentTimeMillis();
	
	public enum Sizes {
		Small, Normal;
	}
	

	public FloatingBlock(String name, Location location, Material mat, Sizes size) {
		this.name = name;
        as = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        as.setHelmet(new ItemStack(mat, 1));
        as.setVisible(false);
        as.setGravity(false);
        switch (size) {
		default:
			as.setSmall(true);
			break;
        case Small:
        	as.setSmall(true);
        	break;
        case Normal:
        	as.setSmall(false);
        	as.getLocation().setY(as.getLocation().getY() - 1);
        	break;
        }
            double x = as.getLocation().getX();
            double y = as.getLocation().getY();
            double z = as.getLocation().getZ();
            UUID uuid = as.getUniqueId();
            String uuidString = uuid.toString();
    		
    		BlockDisplays.FloatingBlocks.getConfig().set("FloatingBlocks." + name + ".Location.World", as.getWorld().getName());
    		BlockDisplays.FloatingBlocks.getConfig().set("FloatingBlocks." + name + ".Location.X", x);
    		BlockDisplays.FloatingBlocks.getConfig().set("FloatingBlocks." + name + ".Location.Y", y);
    		BlockDisplays.FloatingBlocks.getConfig().set("FloatingBlocks." + name + ".Location.Z", z);
    		BlockDisplays.FloatingBlocks.getConfig().set("FloatingBlocks." + name + ".Location.Pitch", as.getLocation().getPitch());
    		BlockDisplays.FloatingBlocks.getConfig().set("FloatingBlocks." + name + ".Location.Yaw", as.getLocation().getYaw());
    		BlockDisplays.FloatingBlocks.getConfig().set("FloatingBlocks." + name + ".Size", size.toString());
    		BlockDisplays.FloatingBlocks.getConfig().set("FloatingBlocks." + name + ".Material", as.getHelmet().getType().toString());
    		BlockDisplays.FloatingBlocks.getConfig().set("FloatingBlocks." + name + ".UUID", uuidString);
        
		BlockDisplays.FloatingBlocks.saveConfig();	
		instances.put(as, this);
	}
	
	public static World getWorld(String name) {
		return Bukkit.getServer().getWorld(BlockDisplays.FloatingBlocks.getConfig().getString("FloatingBlocks." + name + ".Location.World"));
	}
	
	public static Integer getX(String name) {
		return Integer.valueOf(BlockDisplays.FloatingBlocks.getConfig().getInt("FloatingBlocks." + name + ".Location.X"));
	}
	
	public static Integer getY(String name) {
		return Integer.valueOf(BlockDisplays.FloatingBlocks.getConfig().getInt("FloatingBlocks." + name + ".Location.Y"));
	}
	
	public static Integer getZ(String name) {
		return Integer.valueOf(BlockDisplays.FloatingBlocks.getConfig().getInt("FloatingBlocks." + name + ".Location.Z"));
	}
	
	public static void rotateBlock(String name, float yaw) {
		Entity entity = getArmorStandByUUID(name);
		Location loc = getArmorStandByUUID(name).getLocation();
		loc.setYaw(loc.getYaw() + yaw);
		entity.teleport(loc);
	}
	
	public static Entity getArmorStandByUUID(String name) {
		String uuid = BlockDisplays.FloatingBlocks.getConfig().getString("FloatingBlocks." + name + ".UUID");
		for (Entity entity : FloatingBlock.getWorld(name).getEntities()) {
			if (entity.getUniqueId().equals(UUID.fromString(uuid))) {
				return entity;
			}
		}
		return null;
	}

	public static Location getLocation(String name) {
		int x = getX(name);
		int y = getY(name);
		int z = getZ(name);
		World w = getWorld(name);
		
		return new Location(w, x, y, z);
	}
}
