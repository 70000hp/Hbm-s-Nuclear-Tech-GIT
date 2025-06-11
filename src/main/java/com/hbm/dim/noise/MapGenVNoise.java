package com.hbm.dim.noise;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.MapGenBase;

import java.util.Random;

public class MapGenVNoise extends MapGenBase {

	//"absurd" MY ASS
	public Block fluidBlock;
	public Block surfBlock;
	public Block rockBlock;
	public int cellSize = 32;
	public double crackSize = 2.0;
	public int plateStartY = 55;
	public int plateThickness = 35;
	public double shapeExponent = 2.0;

	public BiomeGenBase applyToBiome;

	@Override
	public void func_151539_a(IChunkProvider provider, World world, int chunkX, int chunkZ, Block[] blocks) {
		long seed = world.getSeed();
		int startX = chunkX * 16;
		int startZ = chunkZ * 16;

		for(int lx = 0; lx < 16; lx++) {
			for(int lz = 0; lz < 16; lz++) {

				if(applyToBiome != null) {
					BiomeGenBase biome = world.getBiomeGenForCoords(lx + chunkX * 16, lz + chunkZ * 16);
					if(biome != applyToBiome) continue;
				}

				int gx = startX + lx;
				int gz = startZ + lz;
				double d0 = Double.MAX_VALUE, d1 = Double.MAX_VALUE;
				int cellX = gx / cellSize;
				int cellZ = gz / cellSize;

				for(int dx = -2; dx <= 2; dx++) {
					for(int dz = -2; dz <= 2; dz++) {
						double[] center = getCellCenter(cellX + dx, cellZ + dz, seed);
						double dist = distance(gx, gz, center[0], center[1]);
						if(dist < d0) {
							d1 = d0;
							d0 = dist;
						} else if(dist < d1) {
							d1 = dist;
						}
					}
				}

				double edge = d1 - d0;
				if(edge > crackSize) {
					double alpha = (edge - crackSize) / (cellSize - crackSize);
					alpha = Math.max(0, Math.min(1, alpha));
					alpha = Math.pow(alpha, shapeExponent);
					int topY = plateStartY + (int)(plateThickness * alpha);

					for(int y = plateStartY; y < topY && y < 256; y++) {
						int index = (lx * 16 + lz) * 256 + y;

						if(blocks[index] == Blocks.air || blocks[index] == fluidBlock) {
							blocks[index] = rockBlock;
						}
					}
					if(topY > plateStartY) {
						int topIndex = (lx * 16 + lz) * 256 + (topY - 1);
						blocks[topIndex] = surfBlock;
					}
				}
			}
		}
	}


	//i cant math for shit to be honest
	private double[] getCellCenter(int cellX, int cellZ, long worldSeed) {
		long hash = (long) cellX * 341873128712L + (long) cellZ * 132897987541L + worldSeed;
		Random rand = new Random(hash);
		double offsetX = (rand.nextDouble() - 0.5) * cellSize;
		double offsetZ = (rand.nextDouble() - 0.5) * cellSize;
		double centerX = cellX * (double) cellSize + cellSize / 2.0 + offsetX;
		double centerZ = cellZ * (double) cellSize + cellSize / 2.0 + offsetZ;
		return new double[]{centerX, centerZ};
	}

	private double distance(double x1, double z1, double x2, double z2) {
		double dx = x1 - x2;
		double dz = z1 - z2;
		return Math.sqrt(dx * dx + dz * dz);
	}

}
