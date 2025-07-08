package com.hbm.entity.mob.raider;

import com.hbm.entity.mob.glyphid.GlyphidStats;
import com.hbm.entity.mob.minerva.AIGroup;
import com.hbm.entity.mob.minerva.AICommand;
import com.hbm.entity.mob.minerva.IMinervaUser;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Queue;

public class EntityRaider extends EntityMob implements IMinervaUser {

	public int homeX;
	public int homeY;
	public int homeZ;
	public boolean hasHome;

	public boolean hasGroup;
	AIGroup group;

	Queue<AICommand> taskQueue;

	public EntityRaider(World world){
		super(world);
		this.setSize(0.6F, 1.8F);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(GlyphidStats.getStats().getGrunt().health);
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.5D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

	}

	@Override
	public EntityLivingBase getUser() {
		return this;
	}

	@Override
	public Collection<AICommand> getCommands() {
		return taskQueue;
	}

	@Override
	public boolean canGroup() {
		return true;
	}

	@Override
	public AIGroup getGroup() {
		return group;
	}

	@Override
	public void setGroup(AIGroup group) {
		group.addToGroup(this);
		this.group = group;
	}

	@Override
	public EntityLivingBase getEnemy() {
		return getAttackTarget();
	}

	@Override
	public void setEnemy(EntityLivingBase enemy) {
		setTarget(enemy);
	}

	public class RaiderTargetSelector implements IEntitySelector{

		@Override
		public boolean isEntityApplicable(Entity p_82704_1_) {
			return false;
		}
	}

}
