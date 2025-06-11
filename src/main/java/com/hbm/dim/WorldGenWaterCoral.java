package com.hbm.dim;

import java.util.Random;

import com.hbm.blocks.BlockCoral;
import com.hbm.blocks.ModBlocks;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenWaterCoral extends WorldGenerator {

	public int seaLevel = 64;

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) {
		boolean flag = false;

		for(int l = 0; l < 16; ++l) {
			int px = x + rand.nextInt(8) - rand.nextInt(8);
			int py = y + rand.nextInt(4) - rand.nextInt(4);
			int pz = z + rand.nextInt(8) - rand.nextInt(8);

			if(!world.blockExists(px, py, pz)) continue;

			if(py < seaLevel - 1 && world.getBlock(px, py, pz).getMaterial() == Material.water && world.getBlock(px, py - 1, pz) == ModBlocks.laythe_silt) {

				int meta = rand.nextInt(BlockCoral.EnumCoral.values().length) + 8;

				world.setBlock(px, py - 1, pz, ModBlocks.laythe_coral_block, meta, 2);

				int oy = 0;
				int ox = 0;
				int oz = 0;
				while(oy < 8) {
					if(py + oy > seaLevel - 3) break;
					if(rand.nextBoolean()) break;

					if(rand.nextBoolean()) {
						if(rand.nextBoolean()) {
							ox += rand.nextBoolean() ? 1 : -1;
						} else {
							oz += rand.nextBoolean() ? 1 : -1;
						}
					}

					if(world.blockExists(px + ox, py + oy, pz + oz)) world.setBlock(px + ox, py + oy, pz + oz, ModBlocks.laythe_coral_block, meta, 2);
					if(rand.nextBoolean() && world.blockExists(px + ox + 1, py + oy, pz + oz)) world.setBlock(px + ox + 1, py + oy, pz + oz, ModBlocks.laythe_coral, meta, 2);
					if(rand.nextBoolean() && world.blockExists(px + ox - 1, py + oy, pz + oz)) world.setBlock(px + ox - 1, py + oy, pz + oz, ModBlocks.laythe_coral, meta, 2);
					if(rand.nextBoolean() && world.blockExists(px + ox, py + oy, pz + oz + 1)) world.setBlock(px + ox, py + oy, pz + oz + 1, ModBlocks.laythe_coral, meta, 2);
					if(rand.nextBoolean() && world.blockExists(px + ox, py + oy, pz + oz - 1)) world.setBlock(px + ox, py + oy, pz + oz - 1, ModBlocks.laythe_coral, meta, 2);

					oy++;
				}
				
				if(world.blockExists(px + ox, py + oy, pz + oz)) world.setBlock(px + ox, py + oy, pz + oz, ModBlocks.laythe_coral, meta, 2);

				flag = true;
			}
		}

		return flag;
	}

}
