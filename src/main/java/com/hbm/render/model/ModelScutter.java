package com.hbm.render.model;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;

public class ModelScutter extends ModelBase {

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float rotationYaw, float rotationHeadYaw, float rotationPitch, float scale) {
		super.render(entity, limbSwing, limbSwingAmount, rotationYaw, rotationHeadYaw, rotationPitch, scale);

		GL11.glPushMatrix();
		{

			double cy0 = Math.sin(limbSwing % (Math.PI * 2));
			double cy1 = Math.sin(limbSwing % (Math.PI * 2) - Math.PI * 0.2);
			double cy2 = Math.sin(limbSwing % (Math.PI * 2) - Math.PI * 0.4);
			double cy3 = Math.sin(limbSwing % (Math.PI * 2) - Math.PI * 0.6);

			GL11.glRotatef(180.0F, 0, 0, 1);
			GL11.glTranslatef(0, -1.5F, 0);

			ResourceManager.scutterfish.renderPart("body");

			// Head
			GL11.glPushMatrix();
			{
				GL11.glRotatef(rotationPitch, 1, 0, 0);
				GL11.glRotatef(rotationHeadYaw, 0, 1, 0);
				ResourceManager.scutterfish.renderPart("head");
			}
			GL11.glPopMatrix();

			// Side fins
			GL11.glPushMatrix();
			{
				GL11.glRotated(cy0 * 20, 0, 0, 1);
				ResourceManager.scutterfish.renderPart("leftfin1");
			}
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			{
				GL11.glRotated(cy0 * -20, 0, 0, 1);
				ResourceManager.scutterfish.renderPart("rightfin1");
			}
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			{
				GL11.glRotated(cy1 * 20, 0, 0, 1);
				ResourceManager.scutterfish.renderPart("leftfin2");
			}
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			{
				GL11.glRotated(cy1 * -20, 0, 0, 1);
				ResourceManager.scutterfish.renderPart("rightfin2");
			}
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			{
				GL11.glRotated(cy2 * 20, 0, 0, 1);
				ResourceManager.scutterfish.renderPart("leftfin3");
			}
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			{
				GL11.glRotated(cy2 * -20, 0, 0, 1);
				ResourceManager.scutterfish.renderPart("rightfin3");
			}
			GL11.glPopMatrix();

			// Tail fin
			GL11.glPushMatrix();
			{
				GL11.glRotated(cy1 * 10, 0, 0, 1);
				GL11.glRotated(cy2 * -5, 1, 0, 0);
				ResourceManager.scutterfish.renderPart("tailmeat");
				GL11.glRotated(cy3 * -5, 1, 0, 0);
				ResourceManager.scutterfish.renderPart("tailthin");
			}
			GL11.glPopMatrix();

		}
		GL11.glPopMatrix();
	}

}

