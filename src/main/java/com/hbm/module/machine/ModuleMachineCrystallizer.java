package com.hbm.module.machine;

import api.hbm.energymk2.IEnergyHandlerMK2;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.recipes.CrystallizerRecipes;
import com.hbm.inventory.recipes.PUREXRecipes;
import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.inventory.recipes.loader.GenericRecipes;
import net.minecraft.item.ItemStack;

public class ModuleMachineCrystallizer extends ModuleMachineBase {
	public ModuleMachineCrystallizer(int index, IEnergyHandlerMK2 battery, ItemStack[] slots) {
		super(index, battery, slots);
		this.inputSlots = new int[1];
		this.outputSlots = new int[1];
		this.inputTanks = new FluidTank[1];
		this.outputTanks = new FluidTank[1];
	}

	@Override
	public GenericRecipes getRecipeSet() {
		return CrystallizerRecipes.INSTANCE;
	}

	public ModuleMachineCrystallizer itemInput(int start) { for(int i = 0; i < inputSlots.length; i++) inputSlots[i] = start + i; return this; }
	public ModuleMachineCrystallizer itemOutput(int start) { for(int i = 0; i < outputSlots.length; i++) outputSlots[i] = start + i; return this; }
	public ModuleMachineCrystallizer fluidInput(FluidTank a) { inputTanks[0] = a; return this; }
	public ModuleMachineCrystallizer fluidOutput(FluidTank a) { outputTanks[0] = a; return this; }

	@Override
	public GenericRecipe getRecipe() {
		outer : for(Object object : getRecipeSet().recipeOrderedList){
			GenericRecipe recipe = (GenericRecipe) object;
			if(recipe.inputItem != null) {
				for(int i = 0; i < Math.min(recipe.inputItem.length, inputSlots.length); i++) {
					if(!recipe.inputItem[i].matchesRecipe(slots[inputSlots[i]], false)) continue outer;
				}
			}
			if(recipe.inputFluid != null) {
				for(int i = 0; i < Math.min(recipe.inputFluid.length, inputTanks.length); i++) {
					if(inputTanks[i].getFill() < recipe.inputFluid[i].fill) continue outer;
				}
			}
			return recipe;
		}
		return null;
	}
}
