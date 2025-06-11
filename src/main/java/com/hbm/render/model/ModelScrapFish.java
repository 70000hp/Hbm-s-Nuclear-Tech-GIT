package com.hbm.render.model;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;

public class ModelScrapFish extends ModelBase {

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float rotationYaw, float rotationHeadYaw, float rotationPitch, float scale) {
		super.render(entity, limbSwing, limbSwingAmount, rotationYaw, rotationHeadYaw, rotationPitch, scale);

		GL11.glPushMatrix();
		{

			double cy0 = Math.sin(limbSwing % (Math.PI * 2));
			double cy1 = Math.sin(limbSwing % (Math.PI * 2) - Math.PI * 0.2);
			double cy2 = Math.sin(limbSwing % (Math.PI * 2) - Math.PI * 0.4);
			double cy3 = Math.sin(limbSwing % (Math.PI * 2) - Math.PI * 0.6);
			double cy4 = Math.sin(limbSwing % (Math.PI * 2) - Math.PI * 0.8);
			double cy5 = Math.sin(limbSwing % (Math.PI * 2) - Math.PI * 1.0);

			GL11.glRotatef(180.0F, 0, 0, 1);
			GL11.glTranslatef(0, -1.5F, 0);

			ResourceManager.scrapfish.renderPart("Body");

			// Head
			GL11.glPushMatrix();
			{
				GL11.glTranslatef(0, 0, -0.3125F);
				GL11.glRotatef(rotationPitch, 1, 0, 0);
				GL11.glRotatef(rotationHeadYaw, 0, 1, 0);
				GL11.glTranslatef(0, 0, 0.3125F);
				ResourceManager.scrapfish.renderPart("Head");

			}
			GL11.glPopMatrix();

			// Side fins
			GL11.glPushMatrix();
			{
				GL11.glRotated(cy0 * 20, 1, 0, 0);
				ResourceManager.scrapfish.renderPart("FinL");
			}
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			{
				GL11.glRotated(cy0 * -20, 1, 0, 0);
				ResourceManager.scrapfish.renderPart("FinR");
			}
			GL11.glPopMatrix();

			//Ventral fins
			GL11.glPushMatrix();
			{
				GL11.glRotated(cy1 * 20, 1, 0, 0);
				ResourceManager.scrapfish.renderPart("VentralFL");
			}
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			{
				GL11.glRotated(cy1 * -20, 1, 0, 0);
				ResourceManager.scrapfish.renderPart("VentralFR");
			}
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			{
				GL11.glRotated(cy2 * 20, 1, 0, 0);
				ResourceManager.scrapfish.renderPart("VentralBL");
			}
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			{
				GL11.glRotated(cy2 * -20, 1, 0, 0);
				ResourceManager.scrapfish.renderPart("VentralBR");
			}
			GL11.glPopMatrix();

			// Tail fin
			GL11.glPushMatrix();
			{
				GL11.glRotated(cy3 * 10, 0, 1, 0);
				ResourceManager.scrapfish.renderPart("Dorsal");

				GL11.glRotated(cy4 * 6, 0, 1, 0);
				ResourceManager.scrapfish.renderPart("Tail");

				GL11.glRotated(cy5 * 4, 0, 1, 0);
				ResourceManager.scrapfish.renderPart("TailFin");
			}
			GL11.glPopMatrix();

		}
		GL11.glPopMatrix();
	}

}
