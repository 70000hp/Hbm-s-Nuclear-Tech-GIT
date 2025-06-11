package com.hbm.dim.laythe.biome;

import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenLaytheIslands extends BiomeGenBaseLaythe {

	public BiomeGenLaytheIslands(int id) {
		super(id);
		this.setBiomeName("Laythe Islands");
		this.waterColorMultiplier = 0x5b209a;
		
		this.setHeight(new BiomeGenBase.Height(0.256F, 0.05F));
		this.setTemperatureRainfall(0.2F, 0.2F);
	}
}