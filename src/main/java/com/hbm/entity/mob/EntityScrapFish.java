package com.hbm.entity.mob;

import com.hbm.itempool.ItemPool;
import com.hbm.itempool.ItemPoolsComponent;

import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class EntityScrapFish extends EntityFish implements IEntityEnumMulti {

	public enum ScrapFish {
		STEEL,
		ALUMINIUM,
		ISOTOPE,
		CADMIUM,
		TECH,
		BLOOD,
		HORROR,
	}

	public ScrapFish type;

	public EntityScrapFish(World world) {
		super(world, 0.8, 4.0F);

		type = ScrapFish.values()[world.rand.nextInt(ScrapFish.values().length)];
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Enum getEnum() {
		return type;
	}

	@Override
	protected void dropFewItems(boolean wasPlayer, int looting) {
		WeightedRandomChestContent[] pool = ItemPool.getPool(ItemPoolsComponent.POOL_MACHINE_PARTS);
        int j = rand.nextInt(3 + looting) + 1;

        for(int k = 0; k < j; ++k) {
            this.entityDropItem(ItemPool.getStack(pool, rand).copy(), 0.0F);
        }
    }

}
