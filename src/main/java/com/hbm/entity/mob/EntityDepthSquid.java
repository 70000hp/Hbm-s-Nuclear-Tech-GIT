package com.hbm.entity.mob;

import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityDepthSquid extends EntitySquid implements IEntityEnumMulti {

	public enum DepthSquid {
		AQUA,
		BLACK,
		ORANGE,
		OURPLE,
		RED,
		SILVER,
		VICIOUS,
	}

	public DepthSquid type;

	public EntityDepthSquid(World world) {
		super(world);

		type = DepthSquid.values()[world.rand.nextInt(DepthSquid.values().length)];
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Enum getEnum() {
		return type;
	}

	@Override
	public void moveEntityWithHeading(float strafe, float forward) {
		super.moveEntityWithHeading(strafe, forward);

		// reimplement limb swing because... mojang squid implementation just blasts through everything.
        this.prevLimbSwingAmount = this.limbSwingAmount;
        double d0 = this.posX - this.prevPosX;
        double d1 = this.posZ - this.prevPosZ;
        float f6 = MathHelper.sqrt_double(d0 * d0 + d1 * d1) * 4.0F;

        if(f6 > 1.0F) {
            f6 = 1.0F;
        }

        this.limbSwingAmount += (f6 - this.limbSwingAmount) * 0.4F;
        this.limbSwing += this.limbSwingAmount;
	}

}
