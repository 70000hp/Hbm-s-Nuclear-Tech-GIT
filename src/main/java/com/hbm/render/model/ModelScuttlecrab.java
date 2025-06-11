package com.hbm.render.model;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;

public class ModelScuttlecrab extends ModelBase {

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float rotationYaw, float rotationHeadYaw, float rotationPitch, float scale) {
		super.render(entity, limbSwing, limbSwingAmount, rotationYaw, rotationHeadYaw, rotationPitch, scale);

		GL11.glPushMatrix();
		{

			double cy0 = Math.sin(limbSwing * 6 % (Math.PI * 2));
			double cy1 = Math.sin(limbSwing * 6 % (Math.PI * 2) - Math.PI * 0.4);
			double cy2 = Math.sin(limbSwing * 6 % (Math.PI * 2) - Math.PI * 0.8);
			double cy3 = Math.sin(limbSwing * 6 % (Math.PI * 2) - Math.PI * 1.2);
			double cy4 = Math.sin(limbSwing * 6 % (Math.PI * 2) - Math.PI * 1.6);

			GL11.glRotatef(180.0F, 0, 0, 1);
			GL11.glTranslatef(0, -1.5F, 0);

			ResourceManager.scuttlecrab.renderPart("Body");

			GL11.glPushMatrix();
			{
				GL11.glTranslatef(-0.125F, 0.5625F, -0.3125F);
				GL11.glRotatef(rotationPitch, 1, 0, 0);
				GL11.glRotatef(rotationHeadYaw, 0, 1, 0);
				GL11.glTranslatef(0.125F, -0.5625F, 0.3125F);
				ResourceManager.scuttlecrab.renderPart("EyeL");
			}
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			{
				GL11.glTranslatef(0.125F, 0.5625F, -0.3125F);
				GL11.glRotatef(rotationPitch, 1, 0, 0);
				GL11.glRotatef(rotationHeadYaw, 0, 1, 0);
				GL11.glTranslatef(-0.125F, -0.5625F, 0.3125F);
				ResourceManager.scuttlecrab.renderPart("EyeR");
			}
			GL11.glPopMatrix();


			ResourceManager.scuttlecrab.renderPart("ClawL");
			ResourceManager.scuttlecrab.renderPart("ClawR");


			GL11.glPushMatrix();
			{
				GL11.glRotated(cy0 * 8, 0, 0, 1);
				ResourceManager.scuttlecrab.renderPart("LegL1");
			}
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			{
				GL11.glRotated(cy1 * 8, 0, 0, 1);
				ResourceManager.scuttlecrab.renderPart("LegL2");
			}
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			{
				GL11.glRotated(cy2 * 8, 0, 0, 1);
				ResourceManager.scuttlecrab.renderPart("LegL3");
			}
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			{
				GL11.glRotated(cy3 * 8, 0, 0, 1);
				ResourceManager.scuttlecrab.renderPart("LegL4");
			}
			GL11.glPopMatrix();


			GL11.glPushMatrix();
			{
				GL11.glRotated(cy1 * -8, 0, 0, 1);
				ResourceManager.scuttlecrab.renderPart("LegR1");
			}
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			{
				GL11.glRotated(cy2 * -8, 0, 0, 1);
				ResourceManager.scuttlecrab.renderPart("LegR2");
			}
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			{
				GL11.glRotated(cy3 * -8, 0, 0, 1);
				ResourceManager.scuttlecrab.renderPart("LegR3");
			}
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			{
				GL11.glRotated(cy4 * -8, 0, 0, 1);
				ResourceManager.scuttlecrab.renderPart("LegR4");
			}
			GL11.glPopMatrix();

		}
		GL11.glPopMatrix();
	}

}
