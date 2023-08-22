package com.hedario.blockdisplays;

import java.util.ArrayList;
import java.util.List;

import com.hedario.blockdisplays.configuration.Manager;

public class Rotation {
	public String name;
	public long interval;
	public long lastRotation;
	public static List<Rotation> ALL_BLOCKS = new ArrayList<>();

	public Rotation(String name, long interval) {
		this.name = name;
		this.interval = interval;
		this.lastRotation = System.currentTimeMillis();
		ALL_BLOCKS.add(this);
	}
	
	public static void init() {
		BlockDisplays.getInstance().getServer().getScheduler().cancelTasks(BlockDisplays.getInstance());
		if (!ALL_BLOCKS.isEmpty())
			ALL_BLOCKS.clear();
		check();
		if (getRotatingBlocks() != null)
			progressAll();
		BlockDisplays.log.info("Loaded " + Rotation.getActiveRotations() + " instance(s) to rotate.");
	}

	public static void check() {
		for (String blocks : getRotatingBlocks()) {
			long interval = Manager.getFloatingBlocksConfig().getLong("FloatingBlocks." + blocks + ".AutomaticRotation.Interval");
			new Rotation(blocks, interval);
		}
	}

	public static void update(String blockName, long interval) {
		removeFromName(blockName);
		if (Manager.getFloatingBlocksConfig().contains("FloatingBlocks")) {
			if (Manager.getFloatingBlocksConfig().getBoolean("FloatingBlocks." + blockName + ".AutomaticRotation.Enabled")) {
				new Rotation(blockName, interval);
			}
		}
	}

	public static void progressRotation() {
		for (Rotation rotations : ALL_BLOCKS) {
			if (System.currentTimeMillis() >= rotations.getLastRotation() + rotations.getInterval()) {
				if (FloatingBlock.validateFloatingBlock(rotations.getName())) {
					FloatingBlock.rotateBlock(rotations.getName(), FloatingBlock.getAutomaticRotationDegrees(rotations.getName()));
					rotations.setLastRotation(System.currentTimeMillis());
					return;
				}
			}
		}
	}

	public static List<String> getRotatingBlocks() {
		List<String> list = new ArrayList<String>();
		if (Manager.getFloatingBlocksConfig().contains("FloatingBlocks")) {
			for (String keys : Manager.getFloatingBlocksConfig().getConfigurationSection("FloatingBlocks").getKeys(false)) {
				if (Manager.getFloatingBlocksConfig().getBoolean("FloatingBlocks." + keys + ".AutomaticRotation.Enabled")) {
					list.add(keys);
				}
			}
		}
		return list;
	}

	public static void removeFromName(final String name) {
		for (Rotation rot : ALL_BLOCKS) {
			if (rot.getName().equalsIgnoreCase(name)) {
				ALL_BLOCKS.remove(rot);
				break;
			}
		}
	}

	public static final void progressAll() {
		Runnable r = new Runnable() {
			public void run() {
				progressRotation();
			}
		};
		BlockDisplays.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(BlockDisplays.plugin, r, 60, 1);
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

	public static int getActiveRotations() {
		return ALL_BLOCKS.size();
	}
}
