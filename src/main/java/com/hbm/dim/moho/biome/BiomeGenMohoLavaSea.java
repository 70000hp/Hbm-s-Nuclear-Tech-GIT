package com.hbm.dim.moho.biome;

import com.hbm.blocks.ModBlocks;

import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenMohoLavaSea extends BiomeGenBaseMoho {

	public static final BiomeGenBase.Height height = new BiomeGenBase.Height(-0.8F, 0.01F);

	public BiomeGenMohoLavaSea(int id) {
		super(id);
		this.setBiomeName("Moho Lava Sea");

		this.setHeight(height);

		this.topBlock = ModBlocks.moho_regolith;
		this.fillerBlock = ModBlocks.moho_regolith; // thiccer regolith due to uhhhhhh...................
	}

}