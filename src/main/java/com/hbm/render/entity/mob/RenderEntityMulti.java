package com.hbm.render.entity.mob;

import java.util.Locale;

import com.hbm.entity.mob.IEntityEnumMulti;
import com.hbm.lib.RefStrings;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderEntityMulti extends RenderLiving {

	private ResourceLocation[] textures;

	@SuppressWarnings("rawtypes")
	public RenderEntityMulti(ModelBase model, Class<? extends Enum> theEnum, float shadowSize) {
		super(model, shadowSize);

		Enum[] order = theEnum.getEnumConstants();
		textures = new ResourceLocation[order.length];
		for(int i = 0; i < order.length; i++) {
			textures[i] = new ResourceLocation(RefStrings.MODID, "textures/entity/" + theEnum.getSimpleName().toLowerCase(Locale.US) + "_" + order[i].name().toLowerCase(Locale.US) + ".png");
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		Enum entityEnum = ((IEntityEnumMulti) entity).getEnum();
		return textures[entityEnum.ordinal()];
	}

}
