package com.hbm.entity.mob;

import com.hbm.items.ModItems;

import net.minecraft.item.Item;
import net.minecraft.world.World;

public class EntityScutterfish extends EntityFish {

	public EntityScutterfish(World world) {
		super(world, 1.5, 6.0F);
	}

	@Override
	protected Item getDropItem() {
		return ModItems.scuttertail;
	}

}
