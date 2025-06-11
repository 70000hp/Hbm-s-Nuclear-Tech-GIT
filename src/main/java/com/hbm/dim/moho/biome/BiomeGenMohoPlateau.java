package com.hbm.dim.moho.biome;

import com.hbm.blocks.ModBlocks;

import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenMohoPlateau extends BiomeGenBaseMoho {

	public static final BiomeGenBase.Height height = new BiomeGenBase.Height(0.255F, 0.432F);

	public BiomeGenMohoPlateau(int id) {
		super(id);
		this.setBiomeName("Moho Plateau");

		this.setHeight(height);

		this.topBlock = ModBlocks.moho_regolith;
		this.fillerBlock = ModBlocks.moho_regolith; // thiccer regolith due to uhhhhhh...................
	}

}