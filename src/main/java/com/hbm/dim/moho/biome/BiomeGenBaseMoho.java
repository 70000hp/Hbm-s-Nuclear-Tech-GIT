package com.hbm.dim.moho.biome;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.SpaceConfig;
import com.hbm.dim.BiomeDecoratorCelestial;
import com.hbm.dim.BiomeGenBaseCelestial;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public abstract class BiomeGenBaseMoho extends BiomeGenBaseCelestial {

	public static final BiomeGenBase mohoCrag = new BiomeGenMohoCrag(SpaceConfig.mohoBiome);
	public static final BiomeGenBase mohoBasalt = new BiomeGenMohoBasalt(SpaceConfig.mohoBasaltBiome);
	public static final BiomeGenBase mohoLavaSea = new BiomeGenMohoLavaSea(SpaceConfig.mohoLavaBiome);
	public static final BiomeGenBase mohoPlateau = new BiomeGenMohoPlateau(SpaceConfig.mohoPlateauBiome);

	public BiomeGenBaseMoho(int id) {
		super(id);
		this.setDisableRain();

		BiomeDecoratorCelestial decorator = new BiomeDecoratorCelestial(ModBlocks.moho_stone);
		decorator.lavaCount = 50;
		this.theBiomeDecorator = decorator;

		this.setTemperatureRainfall(1.0F, 0.0F);
	}

	@Override
	public void genTerrainBlocks(World world, Random rand, Block[] blocks, byte[] meta, int x, int z, double noise) {
		Block block = this.topBlock;
		byte b0 = (byte) (this.field_150604_aj & 255);
		Block block1 = this.fillerBlock;
		int k = -1;
		int l = (int) (noise / 8.0D + 8.0D + rand.nextDouble() * 0.50D);
		int bx = x & 15;
		int bz = z & 15;
		int s = blocks.length / 256;

		for(int by = 255; by >= 0; --by) {
			int i = (bz * 16 + bx) * s + by;

			if(by <= 0 + rand.nextInt(5)) {
				blocks[i] = Blocks.bedrock;
			} else {
				Block block2 = blocks[i];

				if(block2 != null && block2.getMaterial() != Material.air) {
					if(block2 == ModBlocks.moho_stone) {
						if(k == -1) {
							if(l <= 0) {
								block = null;
								b0 = 0;
								block1 = ModBlocks.moho_stone;
							} else if(by >= 59 && by <= 64) {
								block = this.topBlock;
								b0 = (byte) (this.field_150604_aj & 255);
								block1 = this.fillerBlock;
							}

							if(by < 63 && (block == null || block.getMaterial() == Material.air)) {
								if(this.getFloatTemperature(x, by, z) < 0.15F) {
									block = this.topBlock;
									b0 = 0;
								} else {
									block = this.topBlock;
									b0 = 0;
								}
							}

							k = l;

							if(by >= 62) {
								blocks[i] = block;
								meta[i] = b0;
							} else if(by < 56 - l) {
								block = null;
								block1 = ModBlocks.moho_stone;
								blocks[i] = ModBlocks.basalt;
							} else {
								blocks[i] = block1;
							}
						} else if(k > 0) {
							--k;
							blocks[i] = block1;

							if(k == 0 && block1 == Blocks.sand) {
								k = rand.nextInt(4) + Math.max(0, by - 63);
								block1 = Blocks.sandstone;
							}
						}
					}
				} else {
					k = -1;
				}
			}
		}
	}

}
