package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.TileEntityDysonLauncher;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderDysonLauncher extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		{

			TileEntityDysonLauncher launcher = (TileEntityDysonLauncher) tileEntity;

			GL11.glTranslated(x + 0.5D, y, z + 0.5D);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_CULL_FACE);

			switch(tileEntity.getBlockMetadata() - BlockDummyable.offset) {
			case 2: GL11.glRotatef(0, 0F, 1F, 0F); break;
			case 4: GL11.glRotatef(90, 0F, 1F, 0F); break;
			case 3: GL11.glRotatef(180, 0F, 1F, 0F); break;
			case 5: GL11.glRotatef(270, 0F, 1F, 0F); break;
			}

			GL11.glShadeModel(GL11.GL_SMOOTH);
			bindTexture(ResourceManager.dyson_spinlaunch_tex);
			ResourceManager.dyson_spinlaunch.renderPart("Launch");

			float t = launcher.lastRotation + (launcher.rotation - launcher.lastRotation) * f;

			GL11.glPushMatrix();
			{

				GL11.glTranslatef(0, 8.5F, 0);
				GL11.glRotatef(45.0F, 1, 0, 0);
				GL11.glRotatef(t, 0, 1, 0);
				GL11.glRotatef(-45.0F, 1, 0, 0);
				GL11.glTranslatef(0, -8.5F, 0);

				ResourceManager.dyson_spinlaunch.renderPart("The_Thing_That_Rotates");

				if(launcher.satCount > 0 && !launcher.isSpinningDown) {
					ResourceManager.dyson_spinlaunch.renderPart("Payload");
				}

			}
			GL11.glPopMatrix();

			if(launcher.isSpinningDown) {
				float p = launcher.payloadTicks + f;

				GL11.glTranslatef(1.0F, 8.5F, 0);
				GL11.glRotatef(45.0F, 1, 0, 0);
				GL11.glRotatef(90.0F, 0, 1, 0);
				GL11.glTranslatef(p * 10.0F, 0, 0);
				GL11.glRotatef(-45.0F, 1, 0, 0);
				GL11.glTranslatef(0, -8.5F, 0);

				ResourceManager.dyson_spinlaunch.renderPart("Payload");
			}

			GL11.glShadeModel(GL11.GL_FLAT);

		}
		GL11.glPopMatrix();
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -2, 0);
				GL11.glScaled(3.0D, 3.0D, 3.0D);
			}
			public void renderCommon() {
				GL11.glScaled(0.25, 0.25, 0.25);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.dyson_spinlaunch_tex);
				ResourceManager.dyson_spinlaunch.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}
		};
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.dyson_launcher);
	}

}
