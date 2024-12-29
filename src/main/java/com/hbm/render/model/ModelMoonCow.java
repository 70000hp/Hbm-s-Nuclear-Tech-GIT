package com.hbm.render.model;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.util.RenderUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class ModelMoonCow extends ModelCow {
	
	private static ResourceLocation glass = new ResourceLocation(RefStrings.MODID, "textures/blocks/glass_boron.png");

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);

		GL11.glPushMatrix();
		{

			Minecraft.getMinecraft().renderEngine.bindTexture(glass);

			if(isChild) {
				GL11.glTranslatef(0.0F, this.field_78145_g * f5, this.field_78151_h * f5);
			}

			GL11.glTranslatef(head.offsetX, head.offsetY, head.offsetZ);
			
			GL11.glTranslatef(head.rotationPointX * f5, head.rotationPointY * f5, head.rotationPointZ * f5);
			GL11.glRotatef(head.rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(head.rotateAngleY * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(head.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);

			GL11.glTranslatef(0, 0, -0.25F);

			GL11.glScalef(0.7F, 0.7F, 0.7F);

			RenderUtil.renderBlock(Tessellator.instance);

		}
		GL11.glPopMatrix();
	}

}
