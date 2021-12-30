package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityMachineCentrifuge;
import com.hbm.tileentity.machine.TileEntityMachineGasCent;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderCentrifuge extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		
		switch(tileEntity.getBlockMetadata() - 10) { // (:
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}

		if(tileEntity instanceof TileEntityMachineCentrifuge) {
			bindTexture(ResourceManager.centrifuge_new_tex);
			ResourceManager.centrifuge_new.renderAll();
		}

		if(tileEntity instanceof TileEntityMachineGasCent) {
			GL11.glRotatef(180, 0F, 1F, 0F);
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glShadeModel(GL11.GL_SMOOTH);
			bindTexture(ResourceManager.gascent_tex);
			ResourceManager.gascent.renderPart("Centrifuge");
			ResourceManager.gascent.renderPart("Flag");
			GL11.glShadeModel(GL11.GL_FLAT);
		}
		
		GL11.glEnable(GL11.GL_CULL_FACE);

		GL11.glPopMatrix();
	}
}
