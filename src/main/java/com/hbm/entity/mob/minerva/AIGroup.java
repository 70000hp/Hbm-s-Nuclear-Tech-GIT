package com.hbm.entity.mob.minerva;

import com.hbm.util.Vec3NT;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.List;

public class AIGroup {


	public ArrayList<IMinervaUser> members = new ArrayList<>();
	public List<EntityLivingBase> enemyCacheCommon;

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

	public EntityLivingBase getLeaderEntity(){
		if (members.get(0) == null) {
			members.remove(0);
		}
		return members.get(0).getUser();
	}

	public void overrideGroupCommand(AICommand task){
		for(IMinervaUser member : members){
			member.clearCommands();
			member.addCommand(task);
		}
	}

	public void requestScan(EntityLivingBase scanner, IEntitySelector selector, int range){
		Vec3NT entityPos = new Vec3NT(scanner.posX,scanner.posY,scanner.posZ);
		Vec3NT leaderPos = new Vec3NT(getLeaderEntity().posX, getLeaderEntity().posY, getLeaderEntity().posZ);

		boolean rangeCheck = entityPos.distanceTo(leaderPos) > range;

		if(rangeCheck || scanner.equals(getLeaderEntity())){
			enemyCacheCommon = scanner.worldObj.selectEntitiesWithinAABB(EntityPlayer.class, scanner.boundingBox.expand(range, range, range), selector);
		}

	}

	public void updateTarget(){
		enemyCacheCommon.remove(primaryTarget);
	}






}
