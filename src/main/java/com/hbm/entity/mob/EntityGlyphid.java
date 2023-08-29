package com.hbm.entity.mob;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockGlyphidSpawner;
import com.hbm.config.MobConfig;
import com.hbm.entity.logic.EntityWaypoint;
import com.hbm.entity.pathfinder.PathFinderUtils;
import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.interfaces.IExplosionSFX;
import com.hbm.explosion.vanillant.standard.*;
import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.ResourceManager;

import com.hbm.potion.HbmPotion;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraft.util.*;

import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityGlyphid extends EntityMob {

	//I might have overdone it a little bit
	public boolean hasHome = false;
	public int homeX;
	public int homeY;
	public int homeZ;
	protected int currentTask = 0;

	//both of those below are used for digging, so the glyphid remembers what it was doing
	protected int previousTask;
	protected EntityWaypoint previousWaypoint;
	public int taskX;
	public int taskY;
	public int taskZ;

	//used for digging, bigger glyphids have a longer reach
	public int blastSize = Math.min((int) (3 * (getScale()))/2, 5);
    public int blastResToDig = Math.min((int) (50 * (getScale() * 2)), 150);
	public boolean shouldDig;

	EntityWaypoint taskWaypoint = null;
	public EntityGlyphid(World world) {
		super(world);
		/*this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
		this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
		this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));*/
		this.setSize(1.75F, 1F);
	}

	public ResourceLocation getSkin() {
		return ResourceManager.glyphid_tex;
	}

	public double getScale() {
		return 1.0D;
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(16, new Byte((byte) 0)); //wall climbing
		this.dataWatcher.addObject(17, new Byte((byte) 0b11111)); //armor
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(1D);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5D);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if(!worldObj.isRemote) {
			if(!hasHome) {
				homeX = (int) posX;
				homeY = (int) posY;
				homeZ = (int) posZ;
				hasHome = true;
			}

			if(this.isPotionActive(Potion.blindness)) {
				onBlinded();
			}

            if(getCurrentTask() == 4){

				if(isAtDestination()) {
					setCurrentTask(0, null);
				}

			} else if (getCurrentTask() == 6 && ticksExisted % 20 == 0 && isAtDestination()) {
				swingItem();

				ExplosionVNT vnt = new ExplosionVNT(worldObj, taskX, taskY + 2, taskZ, blastSize, this);
				vnt.setBlockAllocator(new BlockAllocatorGlyphidDig(blastResToDig));
				vnt.setBlockProcessor(new BlockProcessorStandard().setNoDrop());
				vnt.setEntityProcessor(null);
				vnt.setPlayerProcessor(null);
				vnt.explode();

				this.setCurrentTask(previousTask, previousWaypoint);
			}

			this.setBesideClimbableBlock(isCollidedHorizontally);

			if(ticksExisted % 100 == 0) {
				this.swingItem();
			}
		}
	}


	@Override
	protected void dropFewItems(boolean byPlayer, int looting) {
		super.dropFewItems(byPlayer, looting);
        Item drop = isBurning() ? ModItems.glyphid_meat_grilled : ModItems.glyphid_meat;
		if(rand.nextInt(2) == 0) this.entityDropItem(new ItemStack(drop, ((int)getScale()*2)  + looting), 0F);
	}

	@Override
	protected Entity findPlayerToAttack() {
		if(this.isPotionActive(Potion.blindness)) return null;

		EntityPlayer entityplayer = this.worldObj.getClosestVulnerablePlayerToEntity(this, useExtendedTargeting() ? 128D : 16D);
		return entityplayer != null && (MobConfig.rampantExtendedTargetting || canEntityBeSeen(entityplayer)) ? entityplayer : null;
	}

	@Override
	protected void updateWanderPath() {
		if(getCurrentTask() == 0) {
			super.updateWanderPath();
		}
	}

	@Override
	protected void updateEntityActionState() {
		super.updateEntityActionState();

		if(!this.isPotionActive(Potion.blindness)) {
			if (!this.hasPath()) {

				// hell yeah!!
				if (useExtendedTargeting() && this.entityToAttack != null) {
					this.setPathToEntity(PathFinderUtils.getPathEntityToEntityPartial(worldObj, this, this.entityToAttack, 16F, true, false, true, true));
				} else if (getCurrentTask() != 0) {
					this.worldObj.theProfiler.startSection("stroll");

					if (!isAtDestination()) {

						if (taskWaypoint != null) {

							taskX = (int) taskWaypoint.posX;
							taskY = (int) taskWaypoint.posY;
							taskZ = (int) taskWaypoint.posZ;

							if (taskWaypoint.highPriority) {
								setTarget(taskWaypoint);
							}

						}

						if (taskX != 0) {
                            if(MobConfig.rampantDig) {

								MovingObjectPosition obstacle = findWaypointObstruction();
								if (getScale() >= 1 && getCurrentTask() != 6 && obstacle != null) {
									digToWaypoint(obstacle);
								} else {
									Vec3 vec = Vec3.createVectorHelper(posX, posY, posZ);
									int maxDist = (int) (Math.sqrt(vec.squareDistanceTo(taskX, taskY, taskZ)) * 1.2);
									this.setPathToEntity(PathFinderUtils.getPathEntityToCoordPartial(worldObj, this, taskX, taskY, taskZ, maxDist, true, false, true, true));
								}

							} else {
								Vec3 vec = Vec3.createVectorHelper(posX, posY, posZ);
								int maxDist = (int) (Math.sqrt(vec.squareDistanceTo(taskX, taskY, taskZ)) * 1.2);
								this.setPathToEntity(PathFinderUtils.getPathEntityToCoordPartial(worldObj, this, taskX, taskY, taskZ, maxDist, true, false, true, true));
							}
						}
					}
					this.worldObj.theProfiler.endSection();

				}
			}
		}

	}


	public void onBlinded(){
		this.entityToAttack = null;
		this.setPathToEntity(null);
		fleeingTick = 80;

		if(getScale() >= 1.25){
			if(ticksExisted % 20 == 0) {
				for (int i = 0; i < 16; i++) {
					float angle = (float) Math.toRadians(360D / 16 * i);
					Vec3 rot = Vec3.createVectorHelper(0, 0, 4);
					rot.rotateAroundY(angle);
					Vec3 pos = Vec3.createVectorHelper(this.posX, this.posY + 1, this.posZ);
					Vec3 nextPos = Vec3.createVectorHelper(this.posX + rot.xCoord, this.posY + 1, this.posZ + rot.zCoord);
					MovingObjectPosition mop = this.worldObj.rayTraceBlocks(pos, nextPos);

					if (mop != null && mop.typeOfHit == mop.typeOfHit.BLOCK) {

						Block block = worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ);

						if (block == ModBlocks.lantern) {
							rotationYaw = 360F / 16 * i;
							swingItem();
							//this function is incredibly useful for breaking blocks naturally but obfuscated
							//jesus fucking christ who the fuck runs forge?
							worldObj.func_147480_a(mop.blockX, mop.blockY, mop.blockZ, false);
						}

					}
				}
			}
		}
	}

	public boolean useExtendedTargeting() {
		return MobConfig.rampantExtendedTargetting || PollutionHandler.getPollution(worldObj, (int) Math.floor(posX), (int) Math.floor(posY), (int) Math.floor(posZ), PollutionType.SOOT) >= MobConfig.targetingThreshold;
	}

	@Override
	protected boolean canDespawn() {
		return entityToAttack == null && getCurrentTask() == 0;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {

		if(!source.isDamageAbsolute() && !source.isUnblockable() && !worldObj.isRemote && !source.isFireDamage() && !source.getDamageType().equals(ModDamageSource.s_cryolator)) {
			byte armor = this.dataWatcher.getWatchableObjectByte(17);

			if(armor != 0) { //if at least one bit of armor is present

				if(amount < getDamageThreshold()) return false;

				 //chances of armor being broken off
				if(amount > 1 && isArmorBroken(amount)) {
					breakOffArmor();
					amount *= 0.25F;
				}

				amount -= getDamageThreshold();
				if(amount < 0) return true;
			}

			amount = this.calculateDamage(amount);
		}

		if(source.isFireDamage()) {
			//you might be thinking, why would fire damage be nerfed?
			//thing is, it bypasses glyphid chitin, making it unbelievably powerful, so this was the most reasonable solution
			amount *= 0.6F;
		} else if(source.getDamageType().equals("player")) {
			amount *= 2F;
		} else if(source == ModDamageSource.acid || source.equals(new DamageSource(ModDamageSource.s_acid))){
			amount = 0;
		}

		if(this.isPotionActive(HbmPotion.phosphorus.getId())){
			amount *= 1.5F;
		}

		return super.attackEntityFrom(source, amount);
	}

	public boolean isArmorBroken(float amount) {
		return this.rand.nextInt(100) <= Math.min(Math.pow(amount * 0.5, 2), 100);
	}

	public float calculateDamage(float amount) {

		byte armor = this.dataWatcher.getWatchableObjectByte(17);
		int divisor = 1;

		for(int i = 0; i < 5; i++) {
			if((armor & (1 << i)) > 0) {
				divisor++;
			}
		}

		amount /= divisor;

		return amount;
	}

	public float getDamageThreshold() {
		return 0.5F;
	}

	public void breakOffArmor() {
		byte armor = this.dataWatcher.getWatchableObjectByte(17);
		List<Integer> indices = Arrays.asList(0, 1, 2, 3, 4);
		Collections.shuffle(indices);

		for(Integer i : indices) {
			byte bit = (byte) (1 << i);
			if((armor & bit) > 0) {
				armor &= ~bit;
				armor = (byte) (armor & 0b11111);
				this.dataWatcher.updateObject(17, armor);
				worldObj.playSoundAtEntity(this, "mob.zombie.woodbreak", 1.0F, 1.25F);
				break;
			}
		}
	}

	@Override
	protected void updateArmSwingProgress() {
		int i = this.swingDuration();

		if(this.isSwingInProgress) {
			++this.swingProgressInt;

			if(this.swingProgressInt >= i) {
				this.swingProgressInt = 0;
				this.isSwingInProgress = false;
			}
		} else {
			this.swingProgressInt = 0;
		}

		this.swingProgress = (float) this.swingProgressInt / (float) i;
	}

	public int swingDuration() {
		return 15;
	}

	@Override
	public void setInWeb() { }

	@Override
	public boolean isOnLadder() {
		return this.isBesideClimbableBlock();
	}

	public boolean isBesideClimbableBlock() {
		return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}

	public void setBesideClimbableBlock(boolean climbable) {
		byte watchable = this.dataWatcher.getWatchableObjectByte(16);

		if(climbable) {
			watchable = (byte) (watchable | 1);
		} else {
			watchable &= -2;
		}

		this.dataWatcher.updateObject(16, Byte.valueOf(watchable));
	}

	@Override
	public boolean attackEntityAsMob(Entity victum) {
		if(this.isSwingInProgress) return false;
		this.swingItem();
		return super.attackEntityAsMob(victum);
	}


	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.ARTHROPOD;
	}

	/// TASK SYSTEM START ///
	public int getCurrentTask(){
		return currentTask;
	}

	public EntityWaypoint getWaypoint(){
		return taskWaypoint;
	}

	/**
	 * Sets a new task for the glyphid to do, a waypoint alongside with that task, and refreshes their waypoint coordinates
	 * @param task The task the glyphid is to do, refer to carryOutTask()
	 * @param waypoint The waypoint for the task, can be null
	 */
	public void setCurrentTask(int task, @Nullable EntityWaypoint waypoint){
		currentTask =  task;
		taskWaypoint = waypoint;
		if (taskWaypoint != null) {

			taskX = (int) taskWaypoint.posX;
			taskY = (int) taskWaypoint.posY;
			taskZ = (int) taskWaypoint.posZ;

			if (taskWaypoint.highPriority) {
				setTarget(taskWaypoint);
			}

		}
		carryOutTask();
	}

	/**
	 * Handles the task system, used mainly for things that only need to be done once, such as setting targets
	 */
	public void carryOutTask(){
		int task = getCurrentTask();

		switch(task){

			//call for reinforcements
			case 1: if(taskWaypoint != null){
				communicate(4, taskWaypoint);
				setCurrentTask(4, taskWaypoint);
			}  break;

			//expand the hive, used by the scout
			//case 2: expandHive(null);

			//retreat
			case 3:

				if (!worldObj.isRemote && taskWaypoint == null) {

					//Then, Come back later
					EntityWaypoint additional =  new EntityWaypoint(worldObj);
					additional.setLocationAndAngles(posX, posY, posZ, 0 , 0);

					//First, go home and get reinforcements
					EntityWaypoint home = new EntityWaypoint(worldObj);
					home.setWaypointType(1);
 					home.setAdditionalWaypoint(additional);
					home.setHighPriority();
					home.setLocationAndAngles(homeX, homeY, homeZ, 0, 0);
					worldObj.spawnEntityInWorld(home);

					this.taskWaypoint = home;
					communicate(4, home);
					setCurrentTask(4, taskWaypoint);

					break;
				}

			break;

			//the fourth task (case 4) is to just follow the waypoint path
			//fifth task is used only in the scout and big man johnson, for terraforming

			//dig
			case 6:
				shouldDig = true;
				break;

			default: break;
			
		}

	}

    public void communicate(int task, @Nullable EntityWaypoint waypoint) {
		int radius = waypoint != null ? waypoint.radius : 4;

		AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(
				this.posX - radius,
				this.posY - radius,
				this.posZ - radius,
				this.posX + radius,
				this.posY + radius,
				this.posZ + radius);

		List<Entity> bugs = worldObj.getEntitiesWithinAABBExcludingEntity(this, bb);
		for (Entity e: bugs){
			if(e instanceof EntityGlyphid && !(e instanceof EntityGlyphidScout)){
				if(((EntityGlyphid) e).getCurrentTask() != task){
					((EntityGlyphid) e).setCurrentTask(task, waypoint);
				}
			}
		}
	}

    /** What each type of glyphid does when it is time to expand the hive.
	 * @return Whether it has expanded successfully or not
	 * **/
	public boolean expandHive(){
		return false;
	}

	public boolean isAtDestination() {
		int destinationRadius = taskWaypoint != null ? (int) Math.pow(taskWaypoint.radius, 2) : 25;

		return this.getDistanceSq(taskX, taskY, taskZ) <= destinationRadius;
	}
    ///TASK SYSTEM END

	///DIGGING SYSTEM START

	/** Handles the special digging system, used in Rampant mode due to high potential for destroyed bases**/
	public MovingObjectPosition findWaypointObstruction(){
		Vec3 bugVec = Vec3.createVectorHelper(posX, posY + getEyeHeight(), posZ);
		Vec3 waypointVec =  Vec3.createVectorHelper(taskX, taskY, taskZ);
		//incomplete forge docs my beloved
		MovingObjectPosition obstruction = worldObj.func_147447_a(bugVec, waypointVec, false, true, false);
		if(obstruction != null){
			Block blockHit = worldObj.getBlock(obstruction.blockX, obstruction.blockY, obstruction.blockZ);
			if(blockHit.getExplosionResistance(null) <= blastResToDig){
				return obstruction;
			}
		}
		return null;
	}

	public void digToWaypoint(MovingObjectPosition obstacle){

		EntityWaypoint target =  new EntityWaypoint(worldObj);
		target.setLocationAndAngles(obstacle.blockX, obstacle.blockY, obstacle.blockZ, 0 , 0);
		target.radius = 5;
		worldObj.spawnEntityInWorld(target);

		previousTask = getCurrentTask();
		previousWaypoint =  getWaypoint();

		setCurrentTask(6, target);

		Vec3 vec = Vec3.createVectorHelper(posX, posY, posZ);
		int maxDist = (int) (Math.sqrt(vec.squareDistanceTo(taskX, taskY, taskZ)) * 1.2);
		this.setPathToEntity(PathFinderUtils.getPathEntityToCoordPartial(worldObj, this, taskX, taskY, taskZ, maxDist, true, false, true, true));

		communicate(6, target);

	}
	///DIGGING END

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setByte("armor", this.dataWatcher.getWatchableObjectByte(17));

		nbt.setBoolean("hasHome", hasHome);
		nbt.setInteger("homeX", homeX);
		nbt.setInteger("homeY", homeY);
		nbt.setInteger("homeZ", homeZ);

		nbt.setInteger("taskX", taskX);
		nbt.setInteger("taskY", taskY);
		nbt.setInteger("taskZ", taskZ);

		nbt.setInteger("task", currentTask);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		this.dataWatcher.updateObject(17, nbt.getByte("armor"));

		this.hasHome = nbt.getBoolean("hasHome");
		this.homeX = nbt.getInteger("homeX");
		this.homeY = nbt.getInteger("homeY");
		this.homeZ = nbt.getInteger("homeZ");

		this.taskX = nbt.getInteger("taskX");
		this.taskY = nbt.getInteger("taskY");
		this.taskZ = nbt.getInteger("taskZ");

		this.currentTask = nbt.getInteger("task");
	}
}
