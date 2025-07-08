package com.hbm.entity.mob.minerva.commands;

import com.hbm.util.fauxpointtwelve.BlockPos;

public class AICommand {

	/**Coordinates associated with a command, if any are needed*/
	public BlockPos pos;
	public int commandID;

	public AICommand(int commandID){
		this.commandID = commandID;
	}
	public AICommand(int commandID, int x, int y, int z){
		this.commandID = commandID;
		pos = new BlockPos(x, y, z);
	}

	public enum commandEnums{
		COMMAND_IDLE,
		COMMAND_FOLLOW,
		COMMAND_RETREAT
	}

}
