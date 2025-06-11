package com.hbm.blocks.generic;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockLogNT extends BlockLog {

	private String sideIcon;
	private String topIcon;

	public BlockLogNT(String sideIcon, String topIcon) {
		this.sideIcon = sideIcon;
		this.topIcon = topIcon;
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		this.field_150167_a = new IIcon[1];
		this.field_150166_b = new IIcon[1];
		this.field_150167_a[0] = reg.registerIcon(sideIcon);
		this.field_150166_b[0] = reg.registerIcon(topIcon);
	}

}
