package com.hbm.tileentity.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.IDysonConverter;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.tile.IHeatSource;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityDysonConverterTU extends TileEntityMachineBase implements IDysonConverter, IHeatSource {

	public int heatEnergy;

    public TileEntityDysonConverterTU() {
        super(0);
    }

    @Override
    public String getName() {
		return "container.machineDysonConverterTU";
    }

    @Override
    public void updateEntity() { }

    @Override
    public boolean provideEnergy(int x, int y, int z, long energy) {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		int rx = xCoord + dir.offsetX * 6;
		int ry = yCoord + 1;
		int rz = zCoord + dir.offsetZ * 6;

		if(x != rx || y != ry || z != rz) return false;

		if(energy > Integer.MAX_VALUE) {
			heatEnergy = Integer.MAX_VALUE;
			return true;
		}
        heatEnergy += energy;
        if(heatEnergy < 0) heatEnergy = Integer.MAX_VALUE; // prevent overflow

		return true;
    }

	@Override
	public long maximumEnergy() {
		return Integer.MAX_VALUE;
	}

	@Override
	public int getHeatStored() {
		return heatEnergy;
	}

	@Override
	public void useUpHeat(int heat) {
		heatEnergy = Math.max(0, heatEnergy - heat);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.heatEnergy = nbt.getInteger("heatEnergy");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("heatEnergy", heatEnergy);
	}

	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
				xCoord - 6,
				yCoord,
				zCoord - 6,
				xCoord + 7,
				yCoord + 6,
				zCoord + 7
			);
		}

		return bb;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

}
