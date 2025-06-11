package com.hbm.commands;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import com.hbm.config.SpaceConfig;
import com.hbm.dim.CelestialBody;
import com.hbm.dim.DebugTeleporter;
import com.hbm.dim.SolarSystem;
import com.hbm.dim.SolarSystemWorldSavedData;
import com.hbm.dim.orbit.OrbitalStation;
import com.hbm.items.ItemVOTVdrive;
import com.hbm.items.ItemVOTVdrive.Destination;
import com.hbm.items.ModItems;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

public class CommandStations extends CommandBase {

	@Override
	public String getCommandName() {
		return "ntmstations";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return String.format(Locale.US, 
			"%s/%s launch %s- Spawns a station for the held drive.\n" +
			"%s/%s tp %s- Teleport to held drive station.\n" +
			"%s/%s list %s- Lists all active stations.\n" +
			"%s/%s fetch <id|name> %s- Creates a drive programmed with a specific station ID or name.",
			EnumChatFormatting.GREEN, getCommandName(), EnumChatFormatting.LIGHT_PURPLE,
			EnumChatFormatting.GREEN, getCommandName(), EnumChatFormatting.LIGHT_PURPLE,
			EnumChatFormatting.GREEN, getCommandName(), EnumChatFormatting.LIGHT_PURPLE,
			EnumChatFormatting.GREEN, getCommandName(), EnumChatFormatting.LIGHT_PURPLE
		);
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if(!(sender instanceof EntityPlayer)) {
			showMessage(sender, "commands.satellite.should_be_run_as_player", true);
			return;
		} else if(args.length == 0) {
			throw new WrongUsageException(getCommandUsage(sender), new Object[0]);
		}

		EntityPlayerMP player = getCommandSenderAsPlayer(sender);
		SolarSystemWorldSavedData data = SolarSystemWorldSavedData.get(player.worldObj);

		switch (args[0]) {
		case "launch":
			Destination dest = getStationDestination(sender, player.getHeldItem());
			if(dest == null) return;

			OrbitalStation.addStation(dest.x, dest.z, CelestialBody.getBody(player.worldObj));

			showMessage(sender, "commands.station.launched", false);
			
			break;
		case "tp":
			Destination destination = getStationDestination(sender, player.getHeldItem());
			if(destination == null) return;

			int dimensionId = destination.body.getDimensionId();

			int x = destination.x;
			int z = destination.z;

			if(dimensionId == SpaceConfig.orbitDimension) {
				x = x * OrbitalStation.STATION_SIZE + (OrbitalStation.STATION_SIZE / 2);
				z = z * OrbitalStation.STATION_SIZE + (OrbitalStation.STATION_SIZE / 2);	
			}

			player.mountEntity(null);

			if(player.dimension != dimensionId) {
				if(dimensionId == SpaceConfig.orbitDimension) {
					DebugTeleporter.teleport(player, dimensionId, x + 0.5D, 130.0D, z + 0.5D, false);
				} else {
					DebugTeleporter.teleport(player, dimensionId, x + 0.5D, 300.0D, z + 0.5D, true);
				}
			} else {
				if(dimensionId == SpaceConfig.orbitDimension) {
					player.setPositionAndUpdate(x + 0.5D, 130.0D, z + 0.5D);
				} else {
					int y = player.worldObj.getHeightValue(x, z);
					player.setPositionAndUpdate(x + 0.5D, y + 1, z + 0.5D);
				}
			}

			if(dimensionId == SpaceConfig.orbitDimension) {
				WorldServer targetWorld = DimensionManager.getWorld(SpaceConfig.orbitDimension);
				OrbitalStation.spawn(targetWorld, x, z);
			}

			showMessage(sender, "commands.station.teleported", false);
			
			break;
		case "list":
			boolean hasAnyStations = false;
			for(OrbitalStation station : data.getStations().values()) {
				if(!station.hasStation) continue;

				String messageText = "0x" + Integer.toHexString(new ChunkCoordIntPair(station.dX, station.dZ).hashCode()).toUpperCase();
				if(station.name != "") messageText += " - " + station.name;

				showMessage(sender, messageText, false);

				hasAnyStations = true;
			}

			if(!hasAnyStations) {
				showMessage(sender, "commands.station.no_stations", true);
			}

			break;
		case "fetch":
			if(args.length < 2 || args[1] == null || args[1] == "") {
				showMessage(sender, "commands.station.invalid_station", true);
			} else {
				boolean hasMatch = false;
				String toMatch = args[1];
				for(int i = 2; i < args.length; i++) {
					toMatch += " " + args[i];
				}

				toMatch = toMatch.trim();

				for(OrbitalStation station : data.getStations().values()) {
					String stationId = "0x" + Integer.toHexString(new ChunkCoordIntPair(station.dX, station.dZ).hashCode()).toUpperCase();
					if(station.name.trim().equalsIgnoreCase(toMatch) || stationId.equalsIgnoreCase(toMatch)) {
						ItemStack drive = new ItemStack(ModItems.full_drive, 1, SolarSystem.Body.ORBIT.ordinal());
	
						drive.stackTagCompound = new NBTTagCompound();
						drive.stackTagCompound.setInteger("x", station.dX);
						drive.stackTagCompound.setInteger("z", station.dZ);
						drive.stackTagCompound.setBoolean("Processed", true);
						drive.stackTagCompound.setString("stationName", station.name);

						player.inventory.addItemStackToInventory(drive);

						showMessage(sender, "commands.station.drive_created", false);

						hasMatch = true;

						break;
					}
				}

				if(!hasMatch) {
					showMessage(sender, "commands.station.no_match", true);
				}
			}

			break;
		}
	}

	private static void showMessage(ICommandSender sender, String error, boolean isError) {
		ChatComponentTranslation message = new ChatComponentTranslation(error);
		message.getChatStyle().setColor(isError ? EnumChatFormatting.RED : EnumChatFormatting.GREEN);
		sender.addChatMessage(message);
	}

	private static Destination getStationDestination(ICommandSender sender, ItemStack stack) {
		if(stack == null || !(stack.getItem() instanceof ItemVOTVdrive)) {
			showMessage(sender, "commands.station.invalid_drive", true);
			return null;
		}

		Destination destination = ItemVOTVdrive.getDestination(stack);

		return destination;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		if(!(sender instanceof EntityPlayer)) {
			return Collections.emptyList();
		}
		if(args.length < 1) {
			return Collections.emptyList();
		}
		if(args.length == 1) {
			return getListOfStringsMatchingLastWord(args, "launch", "tp", "list", "fetch");
		}
		return Collections.emptyList();
	}
	
}
