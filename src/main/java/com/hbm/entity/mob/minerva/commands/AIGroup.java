package com.hbm.entity.mob.minerva.commands;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget.Sorter;

import java.util.ArrayList;

public class AIGroup {


	public ArrayList<IMinervaUser> members;
	public ArrayList<EntityLivingBase> enemyCacheCommon;

	public EntityLivingBase primaryTarget;

	public void addToGroup(IMinervaUser member){
		members.add(member);
	}
	public void removeFromGroup(IMinervaUser member){
		members.remove(member);
	}

	public void addGroupCommand(AICommand task){
		for(IMinervaUser member : members){
			member.addCommand(task);
		}
	}

	public void overrideGroupCommand(AICommand task){
		for(IMinervaUser member : members){
			member.clearCommands();
			member.addCommand(task);
		}
	}

	public boolean requestScan(EntityLivingBase scanner, IEntitySelector selector, int range){

	}

	public void updateTarget(){
		enemyCacheCommon.remove(primaryTarget);
	}






}
