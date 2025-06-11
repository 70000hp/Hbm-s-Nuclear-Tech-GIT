package com.hbm.dim.mapgen;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class MapGenPlateau extends MapGenBase {

	public Block stoneBlock;
	public Block surfrock;
	public Block fillblock;

	public NoiseGeneratorPerlin plateauNoise;
	public double noiseScale = 0.05;
	public int maxPlateauAddition = 12;
	public int stepHeight = 6;
	public int topsoilThickness = 2;

	public BiomeGenBase applyToBiome;

	public MapGenPlateau(World world) {
		this.plateauNoise = new NoiseGeneratorPerlin(world.rand, 4);
	}

	@Override
	public void func_151539_a(IChunkProvider provider, World world, int chunkX, int chunkZ, Block[] blocks) {
		int[][] plateauTops = new int[16][16];
		int[][] baseHeights = new int[16][16];
		for(int localX = 0; localX < 16; localX++) {
			for(int localZ = 0; localZ < 16; localZ++) {
				int baseHeight = getSurfaceHeight(blocks, localX, localZ);
				baseHeights[localX][localZ] = baseHeight;
				double noiseVal = plateauNoise.func_151601_a((chunkX * 16 + localX) * noiseScale, (chunkZ * 16 + localZ) * noiseScale);
				int plateauAddition = (int)(((noiseVal + 1) / 2.0) * maxPlateauAddition);
				plateauAddition = (plateauAddition / stepHeight) * stepHeight;
				plateauTops[localX][localZ] = baseHeight + plateauAddition;
			}
		}

		for(int localX = 0; localX < 16; localX++) {
			for(int localZ = 0; localZ < 16; localZ++) {
				if(applyToBiome != null) {
					BiomeGenBase biome = world.getBiomeGenForCoords(localX + chunkX * 16, localZ + chunkZ * 16);
					if(biome != applyToBiome) continue;
				}
				int baseHeight = baseHeights[localX][localZ];
				int plateauTop = plateauTops[localX][localZ];
				for(int y = baseHeight + 1; y < 256; y++) {
					int index = (localX * 16 + localZ) * 256 + y;
					if(y < plateauTop - topsoilThickness) {
						blocks[index] = stoneBlock;
					} else if(y < plateauTop) {
						boolean sameLeft  = (localX - 1 < 0)    || (plateauTops[localX - 1][localZ] == plateauTop);
						boolean sameRight = (localX + 1 >= 16)  || (plateauTops[localX + 1][localZ] == plateauTop);
						boolean sameFront = (localZ - 1 < 0)    || (plateauTops[localX][localZ - 1] == plateauTop);
						boolean sameBack  = (localZ + 1 >= 16)  || (plateauTops[localX][localZ + 1] == plateauTop);

						if(y == plateauTop - 1 && sameLeft && sameRight && sameFront && sameBack) {
							blocks[index] = fillblock;
						} else {
							blocks[index] = surfrock;
						}
					} else {
						blocks[index] = Blocks.air;
					}
				}
			}
		}
	}

	private int getSurfaceHeight(Block[] blocks, int localX, int localZ) {
		int baseHeight = 0;
		for(int y = 255; y >= 0; y--) {
			int index = (localX * 16 + localZ) * 256 + y;
			if(blocks[index] != Blocks.air) {
				baseHeight = y;
				break;
			}
		}
		return baseHeight;
	}

}
