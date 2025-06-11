package com.hbm.explosion.vanillant.standard;

import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.interfaces.ICustomDamageHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class CustomDamageHandlerDyson implements ICustomDamageHandler {

	protected long energy;

	public CustomDamageHandlerDyson(long energy) {
		this.energy = energy;
	}

	@Override
	public void handleAttack(ExplosionVNT explosion, Entity entity, double distanceScaled) {
		if(entity instanceof EntityLivingBase)
			entity.attackEntityFrom(DamageSource.setExplosionSource(explosion.compat), (float)energy / 1_000);
	}
	
}
