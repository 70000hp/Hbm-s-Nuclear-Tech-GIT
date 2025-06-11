package com.hbm.dim.laythe.biome;

import com.hbm.dim.BiomeDecoratorCelestial;
import com.hbm.entity.mob.EntityScuttlecrab;

import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenLaytheCoast extends BiomeGenBaseLaythe {

	public BiomeGenLaytheCoast(int id) {
		super(id);
		this.setBiomeName("Laythe Reef");

		this.waterCreatures.add(new BiomeGenBase.SpawnListEntry(EntityScuttlecrab.class, 4, 1, 3));

		this.setHeight(new BiomeGenBase.Height(-0.4F, 0.01F));
		this.setTemperatureRainfall(0.2F, 0.2F);
		((BiomeDecoratorCelestial)theBiomeDecorator).waterPlantsPerChunk = 16;
		((BiomeDecoratorCelestial)theBiomeDecorator).coralPerChunk = 32;
	}
}
