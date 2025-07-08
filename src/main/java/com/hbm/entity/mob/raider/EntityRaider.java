package com.hbm.entity.mob.raider;

import com.hbm.entity.mob.ai.EntityAIFireGun;
import com.hbm.entity.mob.ai.EntityAINearestAttackableTargetNT;
import com.hbm.entity.mob.glyphid.GlyphidStats;
import com.hbm.entity.mob.minerva.MinervaAIManager;
import com.hbm.entity.mob.minerva.commands.AIGroup;
import com.hbm.entity.mob.minerva.commands.AICommand;
import com.hbm.entity.mob.minerva.commands.IMinervaUser;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Queue;

public class EntityRaider extends EntityMob implements IMinervaUser {

	public int homeX;
	public int homeY;
	public int homeZ;

	public boolean hasGroup;
	AIGroup group;

	Queue<AICommand> taskQueue;
	MinervaAIManager manager;

	public EntityRaider(World world){
		super(world);
		this.setSize(0.6F, 1.8F);
		manager = new MinervaAIManager(this);
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
	public MinervaAIManager getManager() {
		return manager;
	}

	public class RaiderTargetSelector implements IEntitySelector

}
