package com.hbm.entity.mob.minerva;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public interface IMinervaUser {

	EntityLivingBase getUser();

	Collection<AICommand> getCommands();

	boolean canGroup();

	AIGroup getGroup();

	void setGroup(AIGroup group);

	default void addCommand(AICommand command){
		getCommands().add(command);
	}

	default void clearCommands(){
		getCommands().clear();
	}

	default void makeGroup(World world, Class<IMinervaUser> typeCheck, int radius, int x, int y, int z, AICommand command){
		if(!world.isRemote) {
			AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(x, y, z, x, y, z).expand(radius, radius, radius);
			List<Entity> targets = world.getEntitiesWithinAABB(typeCheck, bb);

			for(Entity e : targets) {
				((IMinervaUser)e).addCommand(command);
			}
		}
	}

	default void communicateCommand(AICommand command, boolean priority){
		for(IMinervaUser commandUser : getGroup().members){
			if(priority) commandUser.clearCommands();
			commandUser.addCommand(command);
		}
	}

	public default boolean findEnemies(IEntitySelector selector, int range){
		List<EntityLivingBase> targets = getUser().worldObj.selectEntitiesWithinAABB(EntityPlayer.class, getUser().boundingBox.expand(range, range, range), selector);
		if(targets.isEmpty())
			return false;
		if(getGroup() != null){
			getGroup().requestScan(getUser(), selector, range);
		} else {
			setEnemy(targets.get(0));
		}
		return true;
	}

	public EntityLivingBase getEnemy();

	public void setEnemy(EntityLivingBase enemy);


	public static class TargetDistanceSorter implements Comparator<Entity>
	{
		public Entity referenceEntity;

		public TargetDistanceSorter(Entity entity)
		{
			this.referenceEntity = entity;
		}

		public int compare(Entity entity1, Entity entity2)
		{
			double d0 = this.referenceEntity.getDistanceSqToEntity(entity1);
			double d1 = this.referenceEntity.getDistanceSqToEntity(entity2);
			return Double.compare(d0, d1);
		}
	}




}
