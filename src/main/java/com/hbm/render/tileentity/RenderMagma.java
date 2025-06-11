package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.TileEntityMachineMagma;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;

public class RenderMagma extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		Tessellator tessellator = Tessellator.instance;

		GL11.glPushMatrix();
		{

			GL11.glTranslated(x + 0.5D, y, z + 0.5D);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_CULL_FACE);
			
			switch(tile.getBlockMetadata() - BlockDummyable.offset) {
			case 3: GL11.glRotatef(0, 0F, 1F, 0F); break;
			case 5: GL11.glRotatef(90, 0F, 1F, 0F); break;
			case 2: GL11.glRotatef(180, 0F, 1F, 0F); break;
			case 4: GL11.glRotatef(270, 0F, 1F, 0F); break;
			}
			
			GL11.glTranslated(0, -((BlockDummyable) ModBlocks.machine_magma).getHeightOffset(), 0);
			
			TileEntityMachineMagma drill = (TileEntityMachineMagma) tile;
			
			GL11.glShadeModel(GL11.GL_SMOOTH);
			bindTexture(ResourceManager.magma_drill_tex);
			ResourceManager.magma_drill.renderAllExcept("DrillHead", "Blades");

			float drillRotation = drill.prevDrillRotation + (drill.drillRotation - drill.prevDrillRotation) * interp;
			float lavaHeight = drill.prevLavaHeight + (drill.lavaHeight - drill.prevLavaHeight) * interp;
	
			GL11.glPushMatrix();
			{

				GL11.glRotatef(drillRotation, 0, 1, 0);
				ResourceManager.magma_drill.renderPart("DrillHead");
	
			}
			GL11.glPopMatrix();
	
			GL11.glPushMatrix();
			{

				GL11.glRotatef(-drillRotation, 0, 1, 0);
				ResourceManager.magma_drill.renderPart("Blades");
	
			}
			GL11.glPopMatrix();

			if(lavaHeight > 0.01F) {
				IIcon lava = Blocks.lava.getIcon(0, 0);
				RenderBlocks renderer = RenderBlocks.getInstance();

				bindTexture(TextureMap.locationBlocksTexture);

				tessellator.startDrawingQuads();
				renderer.setRenderBounds(0D, 0D, 0D, 1D, lavaHeight, 1D);
				tessellator.setNormal(0F, 1F, 0F);
				for(int ox = -1; ox <= 1; ox++) {
					for(int oz = -1; oz <= 1; oz++) {
						renderer.renderFaceYPos(Blocks.lava, ox - 0.5D, 0.0D, oz - 0.5D, lava);
					}
				}
				tessellator.draw();
			}
	
			GL11.glShadeModel(GL11.GL_FLAT);
			
		}
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_magma);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase( ) {
			public void renderInventory() {
				GL11.glTranslated(0, -2, 0);
				GL11.glScaled(3, 3, 3);
			}
			public void renderCommon() {
				GL11.glRotatef(90, 0F, 1F, 0F);
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.magma_drill_tex); ResourceManager.magma_drill.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}};
	}

}
