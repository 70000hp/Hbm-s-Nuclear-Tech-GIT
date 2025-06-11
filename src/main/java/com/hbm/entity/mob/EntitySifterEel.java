package com.hbm.entity.mob;

import net.minecraft.world.World;

public class EntitySifterEel extends EntityFish implements IEntityEnumMulti {

	public enum SifterEel {
		PLAIN,
		FAST,
		EXOTIC,
		PHASED,
		ELEMENTAL,
		PERFECT,
	}

	public SifterEel type;

	public EntitySifterEel(World world) {
		super(world, 1.8, 8.0F);

		type = SifterEel.values()[world.rand.nextInt(SifterEel.values().length)];
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Enum getEnum() {
		return type;
	}

}
