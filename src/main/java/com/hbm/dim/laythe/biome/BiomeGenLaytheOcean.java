package com.hbm.dim.laythe.biome;

import com.hbm.entity.mob.EntityDepthSquid;
import com.hbm.entity.mob.EntityScrapFish;
import com.hbm.entity.mob.EntitySifterEel;

import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenLaytheOcean extends BiomeGenBaseLaythe {

	public BiomeGenLaytheOcean(int id) {
		super(id);
		this.setBiomeName("Sagan Sea");

		this.waterCreatures.add(new BiomeGenBase.SpawnListEntry(EntityScrapFish.class, 2, 1, 4));
		this.waterCreatures.add(new BiomeGenBase.SpawnListEntry(EntitySifterEel.class, 1, 1, 4));
		this.waterCreatures.add(new BiomeGenBase.SpawnListEntry(EntityDepthSquid.class, 1, 1, 4));

		this.setHeight(new BiomeGenBase.Height(-1.8F, 0.24F));
		this.setTemperatureRainfall(0.2F, 0.2F);
	}
}
