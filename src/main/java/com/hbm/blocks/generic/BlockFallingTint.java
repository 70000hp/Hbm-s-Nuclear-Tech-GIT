package com.hbm.blocks.generic;

import java.awt.Color;

import com.hbm.blocks.BlockFallingNT;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class BlockFallingTint extends BlockFallingNT {
    
    private final String[] textureNames;
	public IIcon[] icons;

	public BlockFallingTint(Material mat) {
		super(mat);
		this.textureNames = new String[0];
	}
	
	public BlockFallingTint(Material mat, String... extraTextures) {
		super(mat);
		this.textureNames = extraTextures;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		long l = (long) (x * 3129871) ^ (long)y * 116129781L ^ (long)z;
		l = l * l * 42317861L + l * 11L;
		int i = (int)(l >> 16 & 3L);
		return icons[(int)(Math.abs(i) % icons.length)];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return icons[0];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		icons = new IIcon[textureNames.length + 1];
		icons[0] = reg.registerIcon(textureName);
		for(int i = 0; i < textureNames.length; i++) {
			icons[i + 1] = reg.registerIcon(RefStrings.MODID + ":" + textureNames[i]);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		return Color.HSBtoRGB(0F, 0F, 1F - meta / 15F);
	}

}
