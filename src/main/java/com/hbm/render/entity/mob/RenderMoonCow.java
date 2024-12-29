package com.hbm.render.entity.mob;

import com.hbm.entity.mob.EntityMoonCow;
import com.hbm.lib.RefStrings;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderMoonCow extends RenderLiving {

	public static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID, "textures/entity/moon_cow.png");

	public RenderMoonCow(ModelBase mainModel, float shadowSize) {
		super(mainModel, shadowSize);
	}

	protected ResourceLocation getEntityTexture(Entity entity) {
		return this.getEntityTexture((EntityMoonCow) entity);
	}

	protected ResourceLocation getEntityTexture(EntityMoonCow entity) {
		return texture;
	}

}
