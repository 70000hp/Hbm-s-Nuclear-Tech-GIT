package com.hbm.tileentity.network;

import api.hbm.energy.IEnergyConductor;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityConnector extends TileEntityPylonBase {

	@Override
	public ConnectionType getConnectionType() {
		return ConnectionType.SINGLE;
	}

	@Override
	public Vec3 getMountPos() {
		return Vec3.createVectorHelper(0.5, 0.5, 0.5);
	}

	@Override
	public double getMaxWireLength() {
		return 10;
	}

	@Override
	protected void connect() {
		
		super.connect();

		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata()).getOpposite();
		
		TileEntity te = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
		
		if(te instanceof IEnergyConductor) {
			
			IEnergyConductor conductor = (IEnergyConductor) te;
			
			if(!conductor.canConnect(dir.getOpposite()))
				return;
			
			if(this.getPowerNet() == null && conductor.getPowerNet() != null) {
				conductor.getPowerNet().joinLink(this);
			}
			
			if(this.getPowerNet() != null && conductor.getPowerNet() != null && this.getPowerNet() != conductor.getPowerNet()) {
				conductor.getPowerNet().joinNetworks(this.getPowerNet());
			}
		}
	}

	@Override
	public boolean canConnect(ForgeDirection dir) { //i've about had it with your fucking bullshit
		return true;
	}
}
