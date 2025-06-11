package com.hbm.dim;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerDiversify extends GenLayer {

	private final BiomeGenBase[] biomes;
	private final int chance;

	public GenLayerDiversify(long l, GenLayer parent, int chance, BiomeGenBase... biomes) {
		super(l);
		this.parent = parent;
		this.biomes = biomes;
		this.chance = chance;
	}

	@Override
	public int[] getInts(int x, int z, int width, int depth) {
		return diversify(x, z, width, depth);
	}

	private int[] diversify(int x, int z, int width, int height) {
		int input[] = this.parent.getInts(x, z, width, height);
		int output[] = IntCache.getIntCache(width * height);

		for(int zOut = 0; zOut < height; zOut++) {
			for(int xOut = 0; xOut < width; xOut++) {
				int i = xOut + zOut * width;
				int center = input[i];
				initChunkSeed(xOut + x, zOut + z);
				if(nextInt(chance) == 0) {
					output[i] = biomes[nextInt(biomes.length)].biomeID;
				} else
					output[i] = center;
			}
		}
		return output;
	}

}
