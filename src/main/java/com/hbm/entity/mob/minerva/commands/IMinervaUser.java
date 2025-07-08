package com.hbm.entity.mob.minerva.commands;

import com.hbm.entity.mob.minerva.MinervaAIManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.List;

public interface IMinervaUser {

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

	MinervaAIManager getManager();




}
