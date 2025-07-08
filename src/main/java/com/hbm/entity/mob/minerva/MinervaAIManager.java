package com.hbm.entity.mob.minerva;

import com.hbm.entity.mob.minerva.commands.IMinervaUser;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MinervaAIManager {

	public boolean pathingToEnemy;
	public int sinceLastScan;

	double distToEnemy;

	/**To be used when the entity does not have group behavior, otherwise enemies are stored in AIGroup*/
	ArrayList<EntityLivingBase> enemyCacheIndivdual = new ArrayList<>();
	EntityLivingBase primaryTarget;

	EntityLivingBase entity;
	IMinervaUser minervaUser;

	public MinervaAIManager(EntityLivingBase entity){
		this.entity = entity;
		minervaUser = (IMinervaUser) entity;
	}
	public boolean scanForEnemies(IEntitySelector selector, int range){
		List<EntityLivingBase> targets = entity.worldObj.selectEntitiesWithinAABB(EntityPlayer.class, entity.boundingBox.expand(range, range, range), selector);
		if(targets.isEmpty())
			return false;
       	if(minervaUser.getGroup() != null){
			minervaUser.getGroup().requestScan(entity, selector, range);
		} else {
			enemyCacheIndivdual.clear();
			enemyCacheIndivdual.addAll(targets);
		}
	   	return true;
	}
	public boolean hasEnemy(){
		if(minervaUser.getGroup() != null){
			return minervaUser.getGroup().primaryTarget != null;
		} else {
			return primaryTarget != null;
		}
	}
	public EntityLivingBase getEnemy(){
		if(minervaUser.getGroup() != null){
			return minervaUser.getGroup().primaryTarget;
		}
		return primaryTarget;
	}
	public void updateTargets(){
		if(minervaUser.getGroup() != null){
			minervaUser.getGroup().updateTarget();
		}
	}

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
