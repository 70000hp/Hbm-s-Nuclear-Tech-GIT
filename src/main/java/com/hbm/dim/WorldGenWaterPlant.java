package com.hbm.dim;

import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenWaterPlant extends WorldGenerator {

	public int seaLevel = 64;

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) {
		boolean flag = false;

		for(int l = 0; l < 128; ++l) {
			int px = x + rand.nextInt(8) - rand.nextInt(8);
			int py = y + rand.nextInt(4) - rand.nextInt(4);
			int pz = z + rand.nextInt(8) - rand.nextInt(8);

			if(py < seaLevel - 1 && world.getBlock(px, py, pz) == Blocks.water && world.getBlock(px, py - 1, pz) == ModBlocks.laythe_silt) {
				int type = rand.nextInt(7);

				switch(type) {
				case 0:
				case 1:
				case 2:
					world.setBlock(px, py, pz, ModBlocks.laythe_short, 0, 2);
					break;
				case 3:
					world.setBlock(px, py, pz, ModBlocks.laythe_glow, 0, 2);
					break;
				case 4:
				case 5:
					if(py < seaLevel - 2) {
						world.setBlock(px, py, pz, ModBlocks.plant_tall_laythe, 0, 2);
						world.setBlock(px, py + 1, pz, ModBlocks.plant_tall_laythe, 8, 2);
					}
					break;
				case 6:
					if(py < seaLevel - 4) {
						int height = 2 + rand.nextInt(Math.min(8, seaLevel - py - 2)); 
						for(int h = 0; h < height; ++h) {
							world.setBlock(px, py + h, pz, ModBlocks.laythe_kelp, 0, 1);
						}	                	
					}
					break;
				}

				flag = true;
			}
		}

		return flag;
	}

}
