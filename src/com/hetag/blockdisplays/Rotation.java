package com.hetag.blockdisplays;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import com.hetag.blockdisplays.blocks.FloatingBlock;

public class Rotation {
	public String name;
	public long interval;
	public long lastRotation;
	public static FileConfiguration config = BlockDisplays.FloatingBlocks.getConfig();
	public static List<Rotation> ALL_BLOCKS = new ArrayList<>();

	public Rotation(String name, long interval) {
		this.name = name;
		this.interval = interval;
		this.lastRotation = System.currentTimeMillis();
		ALL_BLOCKS.add(this);
	}

	public static void check() {
		if (config.contains("FloatingBlocks")) {
			for (String keys : config.getConfigurationSection("FloatingBlocks").getKeys(false)) {
				if (config.getBoolean("FloatingBlocks." + keys + ".AutomaticRotation.Enabled")) {
					long interval = config.getLong("FloatingBlocks." + keys + ".AutomaticRotation.Interval");
					new Rotation(keys, interval);
				}
			}
		}
	}

	public static void update(String blockName, long interval) {
		for (Rotation rotations : ALL_BLOCKS) {
			if (rotations.getName().equalsIgnoreCase(blockName)) {
				rotations.setInterval(interval);
				rotations.setLastRotation(System.currentTimeMillis());
			}
		}
		new Rotation(blockName, interval);
	}

	public static void progressRotation() {
		for (Rotation rotations : ALL_BLOCKS) {
			if (System.currentTimeMillis() >= rotations.getLastRotation() + rotations.getInterval()) {
				if (FloatingBlock.exists(rotations.getName()) && FloatingBlock.isAlive(rotations.getName())) {
				FloatingBlock.rotateBlock(rotations.getName(), 10);
				rotations.setLastRotation(System.currentTimeMillis());
				return;
				}
			}
		}
	}
	
	public static String getRotatingBlocks() {
		if (config.contains("FloatingBlocks")) {
			for (String keys : config.getConfigurationSection("FloatingBlocks").getKeys(false)) {
				if (config.getBoolean("FloatingBlocks." + keys + ".AutomaticRotation.Enabled")) {
					return keys;
				}
			}
		}
		return null;
	}
	
	public static long getRotatingBlocksInterval() {
		if (config.contains("FloatingBlocks")) {
			for (String keys : config.getConfigurationSection("FloatingBlocks").getKeys(false)) {
				if (config.getBoolean("FloatingBlocks." + keys + ".AutomaticRotation.Enabled")) {
					long interval = config.getLong("FloatingBlocks." + keys + ".AutomaticRotation.Interval");
					return interval;
				}
			}
		}
		return 0;
	}

	public static final void progressAllRotations() {
		Runnable r = new Runnable() {
			public void run() {
			progressRotation();
			}
		};
		BlockDisplays.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(BlockDisplays.plugin, r, 0, 1);
	}
	
	public String getName() {
		return name;
	}

	public String setName(String name) {
		return this.name = name;
	}

	public long getInterval() {
		return interval;
	}

	public long setInterval(long interval) {
		return this.interval = interval;
	}

	public long getLastRotation() {
		return this.lastRotation;
	}

	public long setLastRotation(long lastRotation) {
		return this.lastRotation = lastRotation;
	}
}
