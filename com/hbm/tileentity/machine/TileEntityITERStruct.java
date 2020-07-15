package com.hbm.tileentity.machine;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityITERStruct extends TileEntity implements ITickable {

	public static int[][][] layout = new int[][][] {

		new int[][] {
			new int[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			new int[] {0,0,0,0,0,1,1,1,1,1,0,0,0,0,0},
			new int[] {0,0,0,0,1,1,1,1,1,1,1,0,0,0,0},
			new int[] {0,0,0,1,1,0,0,0,0,0,1,1,0,0,0},
			new int[] {0,0,1,1,0,0,0,0,0,0,0,1,1,0,0},
			new int[] {0,1,1,0,0,0,0,0,0,0,0,0,1,1,0},
			new int[] {0,1,1,0,0,0,0,0,0,0,0,0,1,1,0},
			new int[] {0,1,1,0,0,0,0,3,0,0,0,0,1,1,0},
			new int[] {0,1,1,0,0,0,0,0,0,0,0,0,1,1,0},
			new int[] {0,1,1,0,0,0,0,0,0,0,0,0,1,1,0},
			new int[] {0,0,1,1,0,0,0,0,0,0,0,1,1,0,0},
			new int[] {0,0,0,1,1,0,0,0,0,0,1,1,0,0,0},
			new int[] {0,0,0,0,1,1,1,1,1,1,1,0,0,0,0},
			new int[] {0,0,0,0,0,1,1,1,1,1,0,0,0,0,0},
			new int[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		},
		new int[][] {
			new int[] {0,0,0,0,0,0,1,1,1,0,0,0,0,0,0},
			new int[] {0,0,0,0,1,1,0,0,0,1,1,0,0,0,0},
			new int[] {0,0,0,1,0,0,0,0,0,0,0,1,0,0,0},
			new int[] {0,0,1,0,0,1,1,1,1,1,0,0,1,0,0},
			new int[] {0,1,0,0,1,0,2,2,2,0,1,0,0,1,0},
			new int[] {0,1,0,1,0,2,0,2,0,2,0,1,0,1,0},
			new int[] {1,0,0,1,2,0,0,2,0,0,2,1,0,0,1},
			new int[] {1,0,0,1,2,2,2,3,2,2,2,1,0,0,1},
			new int[] {1,0,0,1,2,0,0,2,0,0,2,1,0,0,1},
			new int[] {0,1,0,1,0,2,0,2,0,2,0,1,0,1,0},
			new int[] {0,1,0,0,1,0,2,2,2,0,1,0,0,1,0},
			new int[] {0,0,1,0,0,1,1,1,1,1,0,0,1,0,0},
			new int[] {0,0,0,1,0,0,0,0,0,0,0,1,0,0,0},
			new int[] {0,0,0,0,1,1,0,0,0,1,1,0,0,0,0},
			new int[] {0,0,0,0,0,0,1,1,1,0,0,0,0,0,0}
		},
		new int[][] {
			new int[] {0,0,0,0,0,0,1,1,1,0,0,0,0,0,0},
			new int[] {0,0,0,0,1,1,0,0,0,1,1,0,0,0,0},
			new int[] {0,0,0,4,0,0,0,0,0,0,0,4,0,0,0},
			new int[] {0,0,4,0,0,1,1,1,1,1,0,0,4,0,0},
			new int[] {0,1,0,0,1,0,2,2,2,0,1,0,0,1,0},
			new int[] {0,1,0,1,0,2,0,0,0,2,0,1,0,1,0},
			new int[] {1,0,0,1,2,0,0,0,0,0,2,1,0,0,1},
			new int[] {1,0,0,1,2,0,0,3,0,0,2,1,0,0,1},
			new int[] {1,0,0,1,2,0,0,0,0,0,2,1,0,0,1},
			new int[] {0,1,0,1,0,2,0,0,0,2,0,1,0,1,0},
			new int[] {0,1,0,0,1,0,2,2,2,0,1,0,0,1,0},
			new int[] {0,0,4,0,0,1,1,1,1,1,0,0,4,0,0},
			new int[] {0,0,0,4,0,0,0,0,0,0,0,4,0,0,0},
			new int[] {0,0,0,0,1,1,0,0,0,1,1,0,0,0,0},
			new int[] {0,0,0,0,0,0,1,1,1,0,0,0,0,0,0}
		}
	};
	
	int age;

	@Override
	public void update() {

		if(world.isRemote)
			return;

		age++;

		if(age < 20)
			return;

		age = 0;
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}