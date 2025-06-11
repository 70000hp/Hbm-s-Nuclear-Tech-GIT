package com.hbm.blocks.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.atmosphere.IBlockSealable;
import com.hbm.tileentity.machine.TileEntityFurnaceSpace;

import net.minecraft.block.BlockFurnace;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockFurnaceSpace extends BlockFurnace implements IBlockSealable {

	public BlockFurnaceSpace(boolean lit) {
		super(lit);
	}

	public static void updateFurnaceBlockState(boolean lit, World world, int x, int y, int z) {
		int l = world.getBlockMetadata(x, y, z);
		TileEntity tileentity = world.getTileEntity(x, y, z);
		BlockFurnace.field_149934_M = true;

		if(lit) {
			world.setBlock(x, y, z, ModBlocks.lit_furnace);
		} else {
			world.setBlock(x, y, z, ModBlocks.furnace);
		}

		BlockFurnace.field_149934_M = false;
		world.setBlockMetadataWithNotify(x, y, z, l, 2);

		if(tileentity != null) {
			tileentity.validate();
			world.setTileEntity(x, y, z, tileentity);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityFurnaceSpace();
	}

	@Override
	public boolean isSealed(World world, int x, int y, int z) {
		return false;
	}

}
