package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityDysonConverterHE;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineDysonConverterHE extends BlockDummyable {

	public MachineDysonConverterHE(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityDysonConverterHE();
		if(meta >= 6) return new TileEntityProxyCombo(false, true, false);
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {2, 0, 4, 4, 1, 1};
	}

	@Override
	public int getOffset() {
		return 4;
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		x = x + dir.offsetX * o;
		z = z + dir.offsetZ * o;

		this.makeExtra(world, x - dir.offsetX * 4, y, z - dir.offsetZ * 4);
	}

}
