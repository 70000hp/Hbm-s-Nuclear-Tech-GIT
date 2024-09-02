package com.hbm.tileentity.machine.rbmk;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.rbmk.RBMKBase;
import com.hbm.blocks.machine.rbmk.RBMKRod;
import com.hbm.entity.projectile.EntityRBMKDebris.DebrisType;
import com.hbm.handler.CompatHandler;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.handler.rbmkmk2.RBMKHandler;
import com.hbm.handler.rbmkmk2.RBMKHandler.NeutronStream;
import com.hbm.handler.rbmkmk2.ItemRBMKRodFluxCurve;
import com.hbm.inventory.container.ContainerRBMKRod;
import com.hbm.inventory.gui.GUIRBMKRod;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemRBMKRod;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.ColumnType;
import com.hbm.util.CompatEnergyControl;
import com.hbm.util.ParticleUtil;

import api.hbm.tile.IInfoProviderEC;
import com.hbm.util.fauxpointtwelve.BlockPos;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;

import static com.hbm.handler.rbmkmk2.RBMKHandler.*;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityRBMKRod extends TileEntityRBMKSlottedBase implements IRBMKFluxReceiver, IRBMKLoadable, SimpleComponent, IInfoProviderEC, CompatHandler.OCComponent {

	// New system!!
	// Used for receiving flux (calculating outbound flux/burning rods)
	public double fluxRatio;
	public double fluxQuantity;
	public double lastFluxQuantity;

	public boolean hasRod;

	public TileEntityRBMKRod() {
		super(1);
	}

	@Override
	public String getName() {
		return "container.rbmkRod";
	}

	@Override
	public boolean isModerated() {
		return ((RBMKRod)this.getBlockType()).moderated;
	}

	@Override
	public int trackingRange() {
		return 25;
	}

	@Override
	public void receiveFlux(NeutronStream stream) {
		double fastFlux = this.fluxQuantity * this.fluxRatio;
		double fastFluxIn = stream.fluxQuantity * stream.fluxRatio;

		this.fluxQuantity += stream.fluxQuantity;
		fluxRatio = (fastFlux + fastFluxIn) / fluxQuantity;
	}

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {

			if(slots[0] != null && slots[0].getItem() instanceof ItemRBMKRod) {

				ItemRBMKRod rod = ((ItemRBMKRod)slots[0].getItem());

				double fluxRatioOut;
				double fluxQuantityOut;

				if (rod instanceof ItemRBMKRodFluxCurve) { // Experimental flux ratio curve rods!
					ItemRBMKRodFluxCurve rodCurve = (ItemRBMKRodFluxCurve) rod;

					fluxRatioOut = rodCurve.fluxRatioOut(this.fluxRatio, ItemRBMKRod.getEnrichment(slots[0]));

					double fluxIn;

					fluxIn = rodCurve.fluxFromRatio(this.fluxQuantity, this.fluxRatio);

					fluxQuantityOut = rod.burn(worldObj, slots[0], fluxIn);
				} else {
					NType rType = rod.rType;
					if (rType == NType.SLOW)
						fluxRatioOut = 0;
					else
						fluxRatioOut = 1;

					double fluxIn = fluxFromType(rod.nType);
					fluxQuantityOut = rod.burn(worldObj, slots[0], fluxIn);
				}

				rod.updateHeat(worldObj, slots[0], 1.0D);
				this.heat += rod.provideHeat(worldObj, slots[0], heat, 1.0D);

				if(!this.hasLid()) {
					ChunkRadiationManager.proxy.incrementRad(worldObj, xCoord, yCoord, zCoord, (float) (this.fluxQuantity * 0.05F));
				}

				super.updateEntity();

				if(this.heat > this.maxHeat()) {

					if(RBMKDials.getMeltdownsDisabled(worldObj)) {
						ParticleUtil.spawnGasFlame(worldObj, xCoord + 0.5, yCoord + RBMKDials.getColumnHeight(worldObj) + 0.5, zCoord + 0.5, 0, 0.2, 0);
					} else {
						this.meltdown();
					}
					this.lastFluxQuantity = 0;
					this.fluxQuantity = 0;
					return;
				}

				if(this.heat > 10_000) this.heat = 10_000;

				this.lastFluxQuantity = this.fluxQuantity;

				this.fluxQuantity = 0;

				spreadFlux(fluxQuantityOut, fluxRatioOut);

				hasRod = true;

			} else {

				this.lastFluxQuantity = 0;
				this.fluxQuantity = 0;
				this.fluxRatio = 0;

				hasRod = false;

				super.updateEntity();
			}
		}
	}

	private double fluxFromType(NType type) {

		switch(type) {
		case SLOW: return (this.fluxQuantity * (1 - this.fluxRatio) + Math.min(this.fluxRatio * 0.5, 1));
		case FAST: return (this.fluxQuantity * (1 - this.fluxRatio) + Math.min(this.fluxRatio * 0.3, 1));
		case ANY: return this.fluxQuantity;
		}

		return 0.0D;
	}

	public static final ForgeDirection[] fluxDirs = new ForgeDirection[] {
			ForgeDirection.NORTH,
			ForgeDirection.EAST,
			ForgeDirection.SOUTH,
			ForgeDirection.WEST
	};

	public void spreadFlux(double flux, double ratio) {

		BlockPos pos = new BlockPos(this);

		if (flux == 0) {
			// simple way to remove the node from the cache when no flux is going into it!
			removeNode(pos);
			return;
		}

		RBMKHandler.RBMKNode node = getNode(pos);

		if(node == null) {
			node = RBMKHandler.makeNode(this);
			addNode(node);
		}

		for(ForgeDirection dir : fluxDirs) {

			Vec3 neutronVector = Vec3.createVectorHelper(dir.offsetX, dir.offsetY, dir.offsetZ);

			// Create new neutron streams
			new NeutronStream(node, neutronVector, flux, ratio);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		if (nbt.hasKey("fluxFast") || nbt.hasKey("fluxSlow")) {
			// recalculate new values to keep stable operations
			this.fluxQuantity = nbt.getDouble("fluxFast") + nbt.getDouble("fluxSlow");
			if (this.fluxQuantity > 0)
				this.fluxRatio = nbt.getDouble("fluxFast") / fluxQuantity;
			else
				this.fluxRatio = 0;
		} else {
			this.fluxQuantity = nbt.getDouble("fluxQuantity");
			this.fluxRatio = nbt.getDouble("fluxRatio");
		}
		this.hasRod = nbt.getBoolean("hasRod");
	}

	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setDouble("fluxSlow", this.lastFluxQuantity * (1 - fluxRatio));
		nbt.setDouble("fluxFast", this.lastFluxQuantity * fluxRatio);
		nbt.setBoolean("hasRod", this.hasRod);
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeDouble(this.fluxQuantity);
		buf.writeDouble(this.fluxRatio);
		buf.writeBoolean(this.hasRod);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.fluxQuantity = buf.readDouble();
		this.fluxRatio = buf.readDouble();
		this.hasRod = buf.readBoolean();
	}

	public void getDiagData(NBTTagCompound nbt) {
		diag = true;
		this.writeToNBT(nbt);
		diag = false;

		if(slots[0] != null && slots[0].getItem() instanceof ItemRBMKRod) {

			ItemRBMKRod rod = ((ItemRBMKRod)slots[0].getItem());

			nbt.setString("f_yield", rod.getYield(slots[0]) + " / " + rod.yield + " (" + (rod.getEnrichment(slots[0]) * 100) + "%)");
			nbt.setString("f_xenon", rod.getPoison(slots[0]) + "%");
			nbt.setString("f_heat", rod.getCoreHeat(slots[0]) + " / " + rod.getHullHeat(slots[0])  + " / " + rod.meltingPoint);
		}
	}

	@Override
	public void onMelt(int reduce) {

		boolean moderated = this.isModerated();
		int h = RBMKDials.getColumnHeight(worldObj);
		reduce = MathHelper.clamp_int(reduce, 1, h);

		if(worldObj.rand.nextInt(3) == 0)
			reduce++;

		boolean corium = slots[0] != null && slots[0].getItem() instanceof ItemRBMKRod;

		if(corium && slots[0].getItem() == ModItems.rbmk_fuel_drx)
			RBMKBase.digamma = true;

		slots[0] = null;

		if(corium) {

			for(int i = h; i >= 0; i--) {
				worldObj.setBlock(xCoord, yCoord + i, zCoord, ModBlocks.corium_block, 5, 3);
				worldObj.markBlockForUpdate(xCoord, yCoord + i, zCoord);
			}

			int count = 1 + worldObj.rand.nextInt(RBMKDials.getColumnHeight(worldObj));

			for(int i = 0; i < count; i++) {
				spawnDebris(DebrisType.FUEL);
			}
		} else {
			this.standardMelt(reduce);
		}

		if(moderated) {

			int count = 2 + worldObj.rand.nextInt(2);

			for(int i = 0; i < count; i++) {
				spawnDebris(DebrisType.GRAPHITE);
			}
		}

		spawnDebris(DebrisType.ELEMENT);

		if(this.getBlockMetadata() == RBMKBase.DIR_NORMAL_LID.ordinal() + RBMKBase.offset)
			spawnDebris(DebrisType.LID);
	}

	@Override
	public RBMKHandler.RBMKType getRBMKType() {
		return RBMKHandler.RBMKType.ROD;
	}

	@Override
	public ColumnType getConsoleType() {
		return ColumnType.FUEL;
	}

	@Override
	public NBTTagCompound getNBTForConsole() {
		NBTTagCompound data = new NBTTagCompound();

		if(slots[0] != null && slots[0].getItem() instanceof ItemRBMKRod) {

			ItemRBMKRod rod = ((ItemRBMKRod)slots[0].getItem());
			data.setDouble("enrichment", rod.getEnrichment(slots[0]));
			data.setDouble("xenon", rod.getPoison(slots[0]));
			data.setDouble("c_heat", rod.getHullHeat(slots[0]));
			data.setDouble("c_coreHeat", rod.getCoreHeat(slots[0]));
			data.setDouble("c_maxHeat", rod.meltingPoint);
		}

		return data;
	}

	@Override
	public boolean canLoad(ItemStack toLoad) {
		return toLoad != null && slots[0] == null;
	}

	@Override
	public void load(ItemStack toLoad) {
		slots[0] = toLoad.copy();
		this.markDirty();
	}

	@Override
	public boolean canUnload() {
		return slots[0] != null;
	}

	@Override
	public ItemStack provideNext() {
		return slots[0];
	}

	@Override
	public void unload() {
		slots[0] = null;
		this.markDirty();
	}

	// do some opencomputer stuff
	@Override
	@Optional.Method(modid = "OpenComputers")
	public String getComponentName() {
		return "rbmk_fuel_rod";
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getHeat(Context context, Arguments args) {
		return new Object[] {heat};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getFluxQuantity(Context context, Arguments args) {
		return new Object[] {fluxQuantity};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getFluxRatio(Context context, Arguments args) {
		return new Object[] {fluxRatio};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getDepletion(Context context, Arguments args) {
		if(slots[0] != null && slots[0].getItem() instanceof ItemRBMKRod) {
			return new Object[] {ItemRBMKRod.getEnrichment(slots[0])};
		}
		return new Object[] {"N/A"};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getXenonPoison(Context context, Arguments args) {
		if(slots[0] != null && slots[0].getItem() instanceof ItemRBMKRod) {
			return new Object[] {ItemRBMKRod.getPoison(slots[0])};
		}
		return new Object[] {"N/A"};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getCoreHeat(Context context, Arguments args) {
		if(slots[0] != null && slots[0].getItem() instanceof ItemRBMKRod) {
			return new Object[] {ItemRBMKRod.getCoreHeat(slots[0])};
		}
		return new Object[] {"N/A"};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getSkinHeat(Context context, Arguments args) {
		if(slots[0] != null && slots[0].getItem() instanceof ItemRBMKRod) {
			return new Object[] {ItemRBMKRod.getHullHeat(slots[0])};
		}
		return new Object[] {"N/A"};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getType(Context context, Arguments args) {
		if(slots[0] != null && slots[0].getItem() instanceof ItemRBMKRod) {
			return new Object[] {slots[0].getItem().getUnlocalizedName()};
		}
		return new Object[] {"N/A"};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getInfo(Context context, Arguments args) {
		List<Object> returnValues = new ArrayList<>();
		if(slots[0] != null && slots[0].getItem() instanceof ItemRBMKRod) {
			returnValues.add(ItemRBMKRod.getHullHeat(slots[0]));
			returnValues.add(ItemRBMKRod.getCoreHeat(slots[0]));
			returnValues.add(ItemRBMKRod.getEnrichment(slots[0]));
			returnValues.add(ItemRBMKRod.getPoison(slots[0]));
			returnValues.add(slots[0].getItem().getUnlocalizedName());
		} else
			for (int i = 0; i < 5; i++)
				returnValues.add("N/A");

		return new Object[] {
				heat, returnValues.get(0), returnValues.get(1),
				fluxQuantity, fluxRatio, returnValues.get(2), returnValues.get(3), returnValues.get(4),
				((RBMKRod)this.getBlockType()).moderated, xCoord, yCoord, zCoord};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getModerated(Context context, Arguments args) {
		return new Object[] {((RBMKRod)this.getBlockType()).moderated};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getCoordinates(Context context, Arguments args) {
		return new Object[] {xCoord, yCoord, zCoord};
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerRBMKRod(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIRBMKRod(player.inventory, this);
	}

	@Override
	public void provideExtraInfo(NBTTagCompound data) {
		if(slots[0] != null && slots[0].getItem() instanceof ItemRBMKRod) {
			data.setDouble(CompatEnergyControl.D_DEPLETION_PERCENT, ((1.0D - ItemRBMKRod.getEnrichment(slots[0])) * 100_000.0D) / 1_000.0D);
			data.setDouble(CompatEnergyControl.D_XENON_PERCENT, ItemRBMKRod.getPoison(slots[0]));
			data.setDouble(CompatEnergyControl.D_SKIN_C, ItemRBMKRod.getHullHeat(slots[0]));
			data.setDouble(CompatEnergyControl.D_CORE_C, ItemRBMKRod.getCoreHeat(slots[0]));
			data.setDouble(CompatEnergyControl.D_MELT_C, ((ItemRBMKRod) slots[0].getItem()).meltingPoint);
		}
	}
}
