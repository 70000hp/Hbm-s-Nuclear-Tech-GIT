package com.hbm.tileentity.machine;

import java.util.List;

import api.hbm.fluidmk2.IFluidStandardTransceiverMK2;
import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.inventory.container.ContainerFurnaceCombo;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIFurnaceCombo;
import com.hbm.inventory.recipes.CombinationRecipes;
import com.hbm.tileentity.IFluidCopiable;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachinePolluting;

import api.hbm.tile.IHeatSource;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityFurnaceCombination extends TileEntityMachinePolluting implements IFluidStandardTransceiverMK2, IGUIProvider, IFluidCopiable {

	public boolean wasOn;
	public int progress;
	public static int processTime = 20_000;

	public int heat;
	public static int maxHeat = 100_000;
	public static double diffusion = 0.25D;

	public FluidTank input;
	public FluidTank output;

	public TileEntityFurnaceCombination() {
		super(4, 50);
		this.input = new FluidTank(Fluids.NONE, 24_000);
		this.output = new FluidTank(Fluids.NONE, 24_000);
	}

	@Override
	public String getName() {
		return "container.furnaceCombination";
	}

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {
			this.tryPullHeat();
			input.setType(3, slots);

			if(this.worldObj.getTotalWorldTime() % 20 == 0) {
				for(int i = 2; i < 6; i++) {
					ForgeDirection dir = ForgeDirection.getOrientation(i);
					ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

					for(int y = yCoord; y <= yCoord + 1; y++) {
						for(int j = -1; j <= 1; j++) {
							this.trySubscribe(input.getTankType(), worldObj, xCoord + dir.offsetX * 2 + rot.offsetX * j, y, zCoord + dir.offsetZ * 2 + rot.offsetZ * j, dir);
							if(input.getTankType() != Fluids.NONE)  this.trySubscribe(input.getTankType(), worldObj, xCoord + dir.offsetX * 2 + rot.offsetX * j, y, zCoord + dir.offsetZ * 2 + rot.offsetZ * j, dir);

							if(output.getFill() > 0) this.sendFluid(output, worldObj, xCoord + dir.offsetX * 2 + rot.offsetX * j, y, zCoord + dir.offsetZ * 2 + rot.offsetZ * j, dir);
							this.sendSmoke(xCoord + dir.offsetX * 2 + rot.offsetX * j, y, zCoord + dir.offsetZ * 2 + rot.offsetZ * j, dir);
						}
					}
				}

				for(int x = xCoord - 1; x <= xCoord + 1; x++) {
					for(int z = zCoord - 1; z <= zCoord + 1; z++) {
						if(output.getFill() > 0) this.sendFluid(output, worldObj, x, yCoord + 2, z, ForgeDirection.UP);
						this.sendSmoke(x, yCoord + 2, z, ForgeDirection.UP);
					}
				}
			}

			this.wasOn = false;

			if(canSmelt()) {
				int burn = heat / 100;

				if(burn > 0) {
					this.wasOn = true;
					this.progress += burn;
					this.heat -= burn;

					if(progress >= processTime) {
						this.markChanged();
						progress -= this.processTime;

						finishRecipe(getMatchingRecipe());
					}

					List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(xCoord - 0.5, yCoord + 2, zCoord - 0.5, xCoord + 1.5, yCoord + 4, zCoord + 1.5));

					for(Entity e : entities) e.setFire(5);

					if(worldObj.getTotalWorldTime() % 10 == 0) this.worldObj.playSoundEffect(this.xCoord, this.yCoord + 1, this.zCoord, "hbm:weapon.flamethrowerShoot", 0.25F, 0.5F);
					if(worldObj.getTotalWorldTime() % 20 == 0) this.pollute(PollutionType.SOOT, PollutionHandler.SOOT_PER_SECOND * 3);
				}
			} else {
				this.progress = 0;
			}

			this.networkPackNT(50);
		} else {

			if(this.wasOn && worldObj.rand.nextInt(15) == 0) {
				worldObj.spawnParticle("lava", xCoord + 0.5 + worldObj.rand.nextGaussian() * 0.5, yCoord + 2, zCoord + 0.5 + worldObj.rand.nextGaussian() * 0.5, 0, 0, 0);
			}
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeBoolean(wasOn);
		buf.writeInt(heat);
		buf.writeInt(progress);
		input.serialize(buf);
		output.serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		wasOn = buf.readBoolean();
		heat = buf.readInt();
		progress = buf.readInt();
		input.deserialize(buf);
		output.deserialize(buf);
	}

	protected CombinationRecipes.CombinationRecipe lastValidRecipe;

	public CombinationRecipes.CombinationRecipe getMatchingRecipe() {

		if(lastValidRecipe != null && doesRecipeMatch(lastValidRecipe)) return lastValidRecipe;

		for(CombinationRecipes.CombinationRecipe rec : CombinationRecipes.recipes) {
			if(doesRecipeMatch(rec)) {
				lastValidRecipe = rec;
				return rec;
			}
		}

		return null;
	}

	public boolean doesRecipeMatch(CombinationRecipes.CombinationRecipe recipe) {

		if(recipe.inputFluid != null) {
			if(input.getTankType() != recipe.inputFluid.type) return false; // recipe needs fluid, fluid doesn't match
		}
		if(recipe.inputItem != null) {
			if(slots[0] == null) return false; // recipe needs item, no item present
			return recipe.inputItem.matchesRecipe(slots[0], true); // recipe needs item, item doesn't match
		} else {
			return slots[0] == null; // recipe does not need item, but item is present
		}
	}

	public boolean canSmelt() {
		CombinationRecipes.CombinationRecipe recipe = this.getMatchingRecipe();
		if(recipe == null) return false; // no matching recipe
		if(recipe.inputFluid != null && input.getFill() < recipe.inputFluid.fill) return false; // not enough input fluid
		if(recipe.inputItem != null && slots[0].stackSize < recipe.inputItem.stacksize) return false; // not enough input item
		if(recipe.outputFluid != null && recipe.outputFluid.fill + output.getFill() > output.getMaxFill() && recipe.outputFluid.type == output.getTankType()) return false; // too much output fluid

		if(recipe.outputItem != null && slots[1] != null && recipe.outputItem.stackSize + slots[1].stackSize > slots[1].getMaxStackSize()) return false; // too much output item
		if(recipe.outputItem != null && slots[1] != null && recipe.outputItem.getItem() != slots[1].getItem()) return false; // output item doesn't match
		if(recipe.outputItem != null && slots[1] != null && recipe.outputItem.getItemDamage() != slots[1].getItemDamage()) return false; // output meta doesn't match

		if(recipe.outputByproduct == null || slots[2] != null && recipe.outputByproduct.stackSize + slots[2].stackSize > slots[2].getMaxStackSize()) return false; // too much output item
		if(recipe.outputByproduct == null || slots[2] != null && recipe.outputByproduct.getItem() != slots[2].getItem()) return false; // output item doesn't match
		if(recipe.outputByproduct == null || slots[2] != null && recipe.outputByproduct.getItemDamage() != slots[2].getItemDamage()) return false; // output meta doesn't match

		return true;
	}

	protected void tryPullHeat() {

		if(this.heat >= this.maxHeat) return;

		TileEntity con = worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);

		if(con instanceof IHeatSource) {
			IHeatSource source = (IHeatSource) con;
			int diff = source.getHeatStored() - this.heat;

			if(diff == 0) {
				return;
			}

			if(diff > 0) {
				diff = (int) Math.ceil(diff * diffusion);
				source.useUpHeat(diff);
				this.heat += diff;
				if(this.heat > this.maxHeat)
					this.heat = this.maxHeat;
				return;
			}
		}

		this.heat = Math.max(this.heat - Math.max(this.heat / 1000, 1), 0);
	}

	public void finishRecipe(CombinationRecipes.CombinationRecipe recipe) {
		if(recipe.outputItem != null) {
			if(slots[1] == null) {
				slots[1] = recipe.outputItem.copy();
			} else {
				slots[1].stackSize += recipe.outputItem.stackSize;
			}
		}
		if(recipe.outputByproduct != null) {
			if(slots[2] == null) {
				slots[2] = recipe.outputByproduct.copy();
			} else {
				slots[2].stackSize += recipe.outputByproduct.stackSize;
			}
		}
		if(recipe.outputFluid != null) {
			output.setTankType(recipe.outputFluid.type);
			output.setFill(output.getFill() + recipe.outputFluid.fill);
		}
		if(recipe.inputItem != null) {
			this.decrStackSize(0, recipe.inputItem.stacksize);
		}
		if(recipe.inputFluid != null) {
			input.setFill(input.getFill() - recipe.inputFluid.fill);
		}
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int meta) {
		return new int[] { 0, 1, 2 };
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return i == 0;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i == 1 || i == 2;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.output.readFromNBT(nbt, "tank");
		this.progress = nbt.getInteger("prog");
		this.heat = nbt.getInteger("heat");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		this.output.writeToNBT(nbt, "tank");
		nbt.setInteger("prog", progress);
		nbt.setInteger("heat", heat);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerFurnaceCombo(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIFurnaceCombo(player.inventory, this);
	}

	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 1,
					yCoord,
					zCoord - 1,
					xCoord + 2,
					yCoord + 2.125,
					zCoord + 2
					);
		}

		return bb;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] {output};
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {output, smoke, smoke_leaded, smoke_poison};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {input};
	}
}
