package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.inventory.SlotUpgrade;
import com.hbm.items.ModItems;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.tileentity.machine.TileEntityMachineCrystallizer;

import api.hbm.energymk2.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCrystallizer extends ContainerBase {


	public ContainerCrystallizer(InventoryPlayer invPlayer, IInventory crys) {
		super(invPlayer,crys);

		//Input
		this.addSlotToContainer(new Slot(crys, 0, 35, 45));
		//Battery
		this.addSlotToContainer(new Slot(crys, 1, 152, 72));
		//Output
		this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, crys, 2,89,45));
		//Upgrades
		this.addSlotToContainer(new SlotUpgrade(crys, 5, 53, 72));
		this.addSlotToContainer(new SlotUpgrade(crys, 6, 71, 72));
		//Fluid ID
		this.addSlotToContainer(new Slot(crys, 7, 8, 72));

		this.playerInv(invPlayer, 8, 122);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack rStack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if(slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			rStack = stack.copy();
			SlotCraftingOutput.checkAchievements(player, stack);

			if(index <= tile.getSizeInventory() - 1) {
				if(!this.mergeItemStack(stack, 8, this.inventorySlots.size(), true)) {
					return null;
				}
			} else {

				if(rStack.getItem() instanceof IBatteryItem || rStack.getItem() == ModItems.battery_creative) {
					if(!this.mergeItemStack(stack, 1, 2, false)) return null;
				} else if(rStack.getItem() instanceof IItemFluidIdentifier) {
					if(!this.mergeItemStack(stack, 7, 8, false)) return null;
				} else if(rStack.getItem() instanceof ItemMachineUpgrade) {
					if(!this.mergeItemStack(stack, 5, 7, false)) return null;
				} else
					if(!this.mergeItemStack(stack, 0, 1, false)) return null;
			}

			if(stack.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}
		}

		return rStack;
	}

}
