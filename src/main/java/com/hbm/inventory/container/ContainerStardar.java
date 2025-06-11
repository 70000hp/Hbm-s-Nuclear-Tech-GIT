package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityMachineStardar;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class ContainerStardar extends ContainerBase {

	public ContainerStardar(InventoryPlayer player, TileEntityMachineStardar stardar) {
		super(player, stardar);

		this.addSlotToContainer(new Slot(stardar, 0, 150, 124));

		playerInv(player, 8, 174, 232);
	}

}
