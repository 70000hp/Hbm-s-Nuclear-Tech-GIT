package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.TileEntityHydroponic;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderHydroponic extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		if(!(tile instanceof TileEntityHydroponic)) return;
		TileEntityHydroponic hydro = (TileEntityHydroponic) tile;

		GL11.glPushMatrix();
		{

			GL11.glTranslated(x + 0.5, y, z + 0.5);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_CULL_FACE);

			switch(tile.getBlockMetadata() - BlockDummyable.offset) {
			case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
			case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
			case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
			case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
			}

			GL11.glShadeModel(GL11.GL_SMOOTH);

			bindTexture(ResourceManager.hydroponic_tex);
			ResourceManager.hydroponic.renderPart("Base");

			if(hydro.power >= 200) {
				GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
				GL11.glDisable(GL11.GL_CULL_FACE);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

				GL11.glDepthMask(false);
				ResourceManager.hydroponic.renderPart("Lights");
				GL11.glDepthMask(true);

				GL11.glDisable(GL11.GL_BLEND);
				GL11.glEnable(GL11.GL_CULL_FACE);
				GL11.glPopAttrib();
			}

			GL11.glShadeModel(GL11.GL_FLAT);

		}
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.hydrobay);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -1.75, 0);
				GL11.glScaled(3, 3, 3);
			}
			public void renderCommon() {
				bindTexture(ResourceManager.hydroponic_tex);
				ResourceManager.hydroponic.renderAll();
			}
		};
	}

}
