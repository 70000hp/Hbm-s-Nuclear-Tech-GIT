package com.hbm.tileentity.machine;

import com.hbm.blocks.machine.BlockFurnaceSpace;
import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.init.Items;
import net.minecraft.tileentity.TileEntityFurnace;

public class TileEntityFurnaceSpace extends TileEntityFurnace {

	// Overrides the vanilla furnace TE to check airbreathing

	@Override
	public void updateEntity() {
		boolean flag = this.furnaceBurnTime > 0;
		boolean flag1 = false;

		if(this.furnaceBurnTime > 0) {
			--this.furnaceBurnTime;
		}

		if(!this.worldObj.isRemote) {
			if(this.furnaceBurnTime != 0 || this.furnaceItemStacks[1] != null && this.furnaceItemStacks[0] != null) {
				if(this.furnaceBurnTime == 0 && this.canSmelt()) {
					int burnTime = getItemBurnTime(this.furnaceItemStacks[1]);

					// If the machine can't combust, provide no burning (lava works without an atmosphere though!)
					if(burnTime > 0 && this.furnaceItemStacks[1].getItem() != Items.lava_bucket && !TileEntityMachineBase.breatheAir(worldObj, xCoord, yCoord, zCoord, 0)) {
						burnTime = 0;
					}

					this.currentItemBurnTime = this.furnaceBurnTime = burnTime;

					if(this.furnaceBurnTime > 0) {
						flag1 = true;

						if(this.furnaceItemStacks[1] != null) {
							--this.furnaceItemStacks[1].stackSize;

							if(this.furnaceItemStacks[1].stackSize == 0) {
								this.furnaceItemStacks[1] = furnaceItemStacks[1].getItem().getContainerItem(furnaceItemStacks[1]);
							}
						}
					}
				}

				if(this.isBurning() && this.canSmelt()) {
					++this.furnaceCookTime;

					if(this.furnaceCookTime == 200) {
						this.furnaceCookTime = 0;
						this.smeltItem();
						flag1 = true;
					}
				} else {
					this.furnaceCookTime = 0;
				}
			}

			if(flag != this.furnaceBurnTime > 0) {
				flag1 = true;
				BlockFurnaceSpace.updateFurnaceBlockState(this.furnaceBurnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			}
		}

		if(flag1) {
			this.markDirty();
		}
	}

}
