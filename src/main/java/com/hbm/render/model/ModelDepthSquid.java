package com.hbm.render.model;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySquid;

public class ModelDepthSquid extends ModelBase {

	private float interp; // why not just pass this into the fucking render method, Morbwank?

	@Override
	public void setLivingAnimations(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float interp) {
		this.interp = interp;
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float rotationYaw, float rotationHeadYaw, float rotationPitch, float scale) {
		super.render(entity, limbSwing, limbSwingAmount, rotationYaw, rotationHeadYaw, rotationPitch, scale);

		float tentacleAngle = 0;
		float squidPitch = 0;
		if(entity instanceof EntitySquid) {
			EntitySquid squid = (EntitySquid) entity;

			tentacleAngle = squid.lastTentacleAngle + (squid.tentacleAngle - squid.lastTentacleAngle) * interp;
			squidPitch = squid.prevSquidPitch + (squid.squidPitch - squid.prevSquidPitch) * interp;
		}

		GL11.glPushMatrix();
		{
			double cy0 = Math.sin(limbSwing % (Math.PI * 2));
			double cy1 = Math.sin(limbSwing % (Math.PI * 2) - Math.PI * 0.4);

			GL11.glTranslatef(0, 1.0F, 0);
			GL11.glRotatef(squidPitch, 1, 0, 0);

			ResourceManager.depthsquid.renderPart("Body");

			// Side fins
			GL11.glPushMatrix();
			{
				GL11.glRotated(cy1 * 20, 1, 0, 0);
				GL11.glRotated(cy0 * 10, 0, 1, 0);
				ResourceManager.depthsquid.renderPart("FinL");
			}
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			{
				GL11.glRotated(cy1 * 20, 1, 0, 0);
				GL11.glRotated(cy0 * -10, 0, 1, 0);
				ResourceManager.depthsquid.renderPart("FinR");
			}
			GL11.glPopMatrix();

			// dibblies
			for(int i = 0; i < 8; i++) {
				GL11.glPushMatrix();
				{
					GL11.glTranslated(0, -0.9, 0);
					GL11.glRotatef((float)i * -45.0F, 0, 1, 0);
					GL11.glRotatef(tentacleAngle * (180F / (float)Math.PI), 1, 0, 0);
					GL11.glTranslated(0, 0.9, 0);
					ResourceManager.depthsquid.renderPart("T0");
				}
				GL11.glPopMatrix();
			}

		}
		GL11.glPopMatrix();
	}

}
