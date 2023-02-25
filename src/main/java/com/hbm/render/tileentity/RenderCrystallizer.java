package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityMachineCrystallizer;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderCrystallizer extends TileEntitySpecialRenderer<TileEntityMachineCrystallizer> {

	@Override
	public boolean isGlobalRenderer(TileEntityMachineCrystallizer te) {
		return true;
	}
	
	@Override
	public void render(TileEntityMachineCrystallizer te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GlStateManager.enableLighting();
        GlStateManager.disableCull();

		switch(te.getBlockMetadata() - 10) {
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}

		TileEntityMachineCrystallizer crys = (TileEntityMachineCrystallizer)te;

		GlStateManager.shadeModel(GL11.GL_SMOOTH);
        bindTexture(ResourceManager.crystallizer_tex);
        ResourceManager.crystallizer.renderPart("Body");
        bindTexture(ResourceManager.crystallizer_window_tex);
        ResourceManager.crystallizer.renderPart("Windows");

        GL11.glPushMatrix();
        GL11.glRotatef(crys.prevAngle + (crys.angle - crys.prevAngle) * partialTicks, 0, 1, 0);
        bindTexture(ResourceManager.crystallizer_spinner_tex);
        ResourceManager.crystallizer.renderPart("Spinner");
        GL11.glPopMatrix();

		GlStateManager.shadeModel(GL11.GL_FLAT);

        GL11.glPopMatrix();
	}
}
