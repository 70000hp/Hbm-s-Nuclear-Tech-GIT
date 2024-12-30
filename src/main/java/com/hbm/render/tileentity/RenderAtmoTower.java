package com.hbm.render.tileentity;

import com.hbm.blocks.ModBlocks;
import com.hbm.render.item.ItemRenderBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderAtmoTower extends TileEntitySpecialRenderer implements  IItemRendererProvider{

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {

		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.0D, y, z + 0.0D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180F, 0F, 1F, 0F);
		switch(tileEntity.getBlockMetadata() - 10) {
		case 2:
			GL11.glRotatef(0F, 0F, 1F, 0F);
			GL11.glTranslatef(0F, 0F, -1F);
			break;
		case 3:
			GL11.glRotatef(180F, 0F, 1F, 0F);
			GL11.glTranslatef(1F, 0F, 0F);
			break;
		case 4:
			GL11.glRotatef(90F, 0F, 1F, 0F);
			GL11.glTranslatef(1F, 0F, -1F);
			break;
		case 5:
			GL11.glRotatef(270F, 0F, 1F, 0F);
			break;
		}
		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.atmo_tower_tex);
		ResourceManager.atmo_tower.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_atmo_tower);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase( ) {
			public void renderInventory() {
				GL11.glTranslated(0, -3.5, 0);
				GL11.glScaled(2, 2, 2);
			}
			public void renderCommon() {
				GL11.glRotatef(90, 0F, 1F, 0F);
				bindTexture(ResourceManager.atmo_tower_tex);
				ResourceManager.atmo_tower.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}};
	}
}
