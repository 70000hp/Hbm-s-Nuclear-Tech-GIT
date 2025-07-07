package com.hbm.items.machine;

import com.hbm.items.ItemEnumMulti;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemArcadeComponent extends ItemEnumMulti {

	public ItemArcadeComponent() {
		super(EnumComponentType.class, true, true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(item, 1, EnumComponentType.MACHINE_PARTS.ordinal()));
		list.add(new ItemStack(item, 1, EnumComponentType.MILITARY_PACKAGE.ordinal()));
	}

	public static enum EnumComponentType {
		MACHINE_PARTS,
		MILITARY_PACKAGE
	}
}
