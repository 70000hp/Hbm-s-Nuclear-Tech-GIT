package com.hbm.tileentity.machine.albion;

import com.hbm.inventory.container.ContainerPADetector;
import com.hbm.inventory.gui.GUIPADetector;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.util.fauxpointtwelve.DirPos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityPADetector extends TileEntityCooledBase implements IGUIProvider {
	
	public TileEntityPADetector() {
		super(5);
	}

	@Override
	public String getName() {
		return "container.paDetector";
	}

	@Override
	public DirPos[] getConPos() {
		return new DirPos[] {
				
		};
	}

	@Override
	public long getMaxPower() {
		return 10_000_000;
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 4,
					yCoord - 2,
					zCoord - 4,
					xCoord + 5,
					yCoord + 3,
					zCoord + 5
					);
		}
		
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerPADetector(player.inventory, this);
	}

	@Override
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIPADetector(player.inventory, this);
	}
}
