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
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.hetag.blockdisplays.BlockDisplays;

public class FloatingBlock {
	public static ConcurrentHashMap<ArmorStand, FloatingBlock> instances = new ConcurrentHashMap<>();

	public ArmorStand as;
	public Zombie zombie;

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
			zombie = (Zombie) location.getWorld().spawnEntity(location, EntityType.ZOMBIE);
			zombie.setBaby(true);
			zombie.setGravity(false);
			zombie.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
			zombie.setSilent(true);
			zombie.setAI(false);
			ItemStack item = new ItemStack(mat, 1);
			zombie.getEquipment().setItemInMainHand(item);
        case Small:
            as = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
            as.setHelmet(new ItemStack(mat, 1));
            as.setVisible(false);
            as.setGravity(false);
        	as.setSmall(true);
        	break;
        case Normal:
            as = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
            as.setHelmet(new ItemStack(mat, 1));
            as.setVisible(false);
            as.setGravity(false);
        	as.setSmall(false);
        	as.getLocation().setY(as.getLocation().getY() - 1);
        	break;
        }
            double x = location.getX();
            double y = location.getY();
            double z = location.getZ();
            UUID uuid = null;
            if (size == Sizes.Normal || size == Sizes.Small) {
            	uuid = as.getUniqueId();
            } else if (size == Sizes.Tiny) {
            	uuid = zombie.getUniqueId();
            }
            String uuidString = uuid.toString();
    		
    		BlockDisplays.FloatingBlocks.getConfig().set("FloatingBlocks." + name + ".Location.World", location.getWorld().getName());
    		BlockDisplays.FloatingBlocks.getConfig().set("FloatingBlocks." + name + ".Location.X", x);
    		BlockDisplays.FloatingBlocks.getConfig().set("FloatingBlocks." + name + ".Location.Y", y);
    		BlockDisplays.FloatingBlocks.getConfig().set("FloatingBlocks." + name + ".Location.Z", z);
    		BlockDisplays.FloatingBlocks.getConfig().set("FloatingBlocks." + name + ".Location.Pitch", location.getPitch());
    		BlockDisplays.FloatingBlocks.getConfig().set("FloatingBlocks." + name + ".Location.Yaw", location.getYaw());
    		BlockDisplays.FloatingBlocks.getConfig().set("FloatingBlocks." + name + ".Size", size.toString());
		if (size == Sizes.Normal || size == Sizes.Small) {
			BlockDisplays.FloatingBlocks.getConfig().set("FloatingBlocks." + name + ".Material", as.getHelmet().getType().toString());
		} else if (size == Sizes.Tiny) {
			BlockDisplays.FloatingBlocks.getConfig().set("FloatingBlocks." + name + ".Material", zombie.getEquipment().getItemInMainHand().getType().toString());
		}
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
	
	public static Material getMaterial(String name) {
		return Material.valueOf(BlockDisplays.FloatingBlocks.getConfig().getString("FloatingBlocks." + name + ".Material"));
	}
	
	public static String getSize(String name) {
		return BlockDisplays.FloatingBlocks.getConfig().getString("FloatingBlocks." + name + ".Size");
	}
	
	public static UUID getUUID(String name) {
		return UUID.fromString(BlockDisplays.FloatingBlocks.getConfig().getString("FloatingBlocks." + name + ".UUID"));
	}
	
	public static double getYaw(String name) {
		return BlockDisplays.FloatingBlocks.getConfig().getDouble("FloatingBlocks." + name + ".Location.Yaw");
	}
	public static double getPitch(String name) {
		return BlockDisplays.FloatingBlocks.getConfig().getDouble("FloatingBlocks." + name + ".Location.Pitch");
	}
	
	public static void rotateBlock(String name, float yaw) {
		Entity entity = getFloatingBlockByUUID(name);
		Location loc = getFloatingBlockByUUID(name).getLocation();
		loc.setYaw(loc.getYaw() + yaw);
		entity.teleport(loc);
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

	public static Location getLocation(String name) {
		int x = getX(name);
		int y = getY(name);
		int z = getZ(name);
		World w = getWorld(name);
		
		return new Location(w, x, y, z);
	}
}
