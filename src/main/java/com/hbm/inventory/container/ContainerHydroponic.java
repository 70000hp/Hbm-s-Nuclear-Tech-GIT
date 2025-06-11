package com.hbm.inventory.container;

import com.hbm.inventory.SlotTakeOnly;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerHydroponic extends ContainerBase {

	public ContainerHydroponic(InventoryPlayer invPlayer, IInventory inv) {
		super(invPlayer, inv);

		// Inputs
		addSlotToContainer(new Slot(inv, 0, 67, 18));
		addSlotToContainer(new Slot(inv, 1, 67, 54));

		// Battery
		addSlotToContainer(new Slot(inv, 2, 147, 54));

		// Outputs
		for(int i = 0; i < 3; i++) addSlotToContainer(new SlotTakeOnly(inv, i + 3, 111, 18 + i * 18));

		playerInv(invPlayer, 8, 104, 162);
	}

}
