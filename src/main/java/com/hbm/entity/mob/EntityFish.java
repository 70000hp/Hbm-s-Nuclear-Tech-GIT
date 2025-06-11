package com.hbm.entity.mob;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public abstract class EntityFish extends EntityWaterMob {

	private Vec3 currentSwimTarget;

	public EntityFish(World world, double swimSpeed, float watchDistance) {
		super(world);
		this.getNavigator().setCanSwim(true);

		this.tasks.addTask(0, new EntityAIWatchClosest(this, EntityPlayer.class, watchDistance));
		this.tasks.addTask(1, new EntityAILookIdle(this));
		this.tasks.addTask(2, new EntityAIWander(this, swimSpeed));

		this.currentSwimTarget = Vec3.createVectorHelper(posX, posY, posZ);
		this.setSize(2.0F, 2.0F);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15.0);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(10.0);
	}

	@Override
	protected void damageEntity(DamageSource source, float amount) {
		super.damageEntity(source, amount);
	}

	@Override
	protected void updateAITasks() {
		super.updateAITasks();

		if(!this.isInWater()) {
			this.rotationPitch += 0.2F;
			this.rotationYaw = 0.0F;
			this.setJumping(true);
			if(this.onGround) {
				this.addVelocity(0.4 * rand.nextDouble() - 0.4 * rand.nextDouble(), 0.0, 0.4 * rand.nextDouble() - 0.4 * rand.nextDouble());
			}
		}
	}

	@Override
	protected boolean isAIEnabled() {
	   return true;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		this.updateSwimTarget();
		this.moveTowardsTarget();
		updateRotation();
	}

	private void updateSwimTarget() {
		if(rand.nextInt(200) == 0 || !this.isInWater()) {
			double targetX = this.posX + (rand.nextDouble() - 0.5) * 20.0;
			double targetY = MathHelper.clamp_double(this.posY + (rand.nextDouble() - 0.5) * 8.0, 0, this.worldObj.getHeight() - 1);
			double targetZ = this.posZ + (rand.nextDouble() - 0.5) * 20.0;
			this.currentSwimTarget = Vec3.createVectorHelper(targetX, targetY, targetZ);
		}
	}

	private void moveTowardsTarget() {
		double deltaX = this.currentSwimTarget.xCoord - this.posX;
		double deltaY = this.currentSwimTarget.yCoord - this.posY;
		double deltaZ = this.currentSwimTarget.zCoord - this.posZ;
		double distance = MathHelper.sqrt_double(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);

		double dirX = deltaX / distance;
		double dirY = deltaY / distance;
		double dirZ = deltaZ / distance;

		double speed = 0.01; // You can adjust this value for different speeds
		this.motionX += dirX * speed + (rand.nextDouble() - 0.5) * 0.02; // Added randomness
		this.motionY += dirY * speed + (rand.nextDouble() - 0.5) * 0.02;
		this.motionZ += dirZ * speed + (rand.nextDouble() - 0.5) * 0.02;

		updateRotation((float)dirX, (float)dirY, (float)dirZ);
	}

	@Override
	public void moveEntityWithHeading(float strafe, float forward) {
		if(this.isInWater()) {
			this.motionY *= 0.8F;
			this.moveEntity(this.motionX, this.motionY, this.motionZ);

			this.prevLimbSwingAmount = this.limbSwingAmount;
			double d0 = this.posX - this.prevPosX;
			double d1 = this.posZ - this.prevPosZ;
			float f6 = MathHelper.sqrt_double(d0 * d0 + d1 * d1) * 4.0F;

			if(f6 > 1.0F) {
				f6 = 1.0F;
			}

			this.limbSwingAmount += (f6 - this.limbSwingAmount) * 0.4F;
			this.limbSwing += this.limbSwingAmount;
		} else {
			super.moveEntityWithHeading(strafe, forward);
		}
	}

	private void updateRotation() {
		double deltaX = this.motionX;
		double deltaZ = this.motionZ;
		double deltaY = this.motionY;
		float targetYaw = (float) (Math.atan2(deltaZ, deltaX) * (180D / Math.PI)) - 90F;
		float targetPitch = (float) -(Math.atan2(deltaY, Math.sqrt(deltaX * deltaX + deltaZ * deltaZ)) * (180D / Math.PI));

		this.rotationYaw = this.updateRotation(this.rotationYaw, targetYaw, 10.0F);
		this.rotationPitch = this.updateRotation(this.rotationPitch, targetPitch, 10.0F);
	}

	private float updateRotation(float currentRotation, float targetRotation, float maxIncrement) {
		float deltaRotation = MathHelper.wrapAngleTo180_float(targetRotation - currentRotation);
		if(deltaRotation > maxIncrement) {
			deltaRotation = maxIncrement;
		}
		if(deltaRotation < -maxIncrement) {
			deltaRotation = -maxIncrement;
		}
		return currentRotation + deltaRotation;
	}

}

