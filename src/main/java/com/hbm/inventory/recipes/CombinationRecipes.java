package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.hbm.inventory.OreDictManager.*;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.BlockEnums;
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.FluidsF;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ItemEnums.EnumAshType;
import com.hbm.items.ItemEnums.EnumBriquetteType;
import com.hbm.items.ItemEnums.EnumCokeType;
import com.hbm.items.ItemEnums.EnumTarType;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.items.special.ItemBedrockOreNew;
import com.hbm.items.special.ItemBedrockOreNew.ProcessingGrade;
import com.hbm.items.special.ItemBedrockOreNew.BedrockOreType;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CombinationRecipes extends SerializableRecipe {

	public static List<CombinationRecipe> recipes = new ArrayList();


	@Override
	public void registerDefaults() {
		//coals
		recipes.add(new CombinationRecipe()
			.in(new OreDictStack(COAL.gem()))
			.out(DictFrame.fromOne(ModItems.coke, EnumCokeType.COAL))
			.out(new FluidStack(Fluids.COALCREOSOTE, 100)));
		recipes.add(new CombinationRecipe()
			.in(new OreDictStack(COAL.dust()))
			.out(DictFrame.fromOne(ModItems.coke, EnumCokeType.COAL))
			.out(new FluidStack(Fluids.COALCREOSOTE, 200)));
		recipes.add(new CombinationRecipe()
			.in(new ComparableStack(DictFrame.fromOne(ModItems.briquette, EnumBriquetteType.COAL)))
			.out(DictFrame.fromOne(ModItems.coke, EnumCokeType.COAL, 2))
			.out(new FluidStack(Fluids.COALCREOSOTE, 250)));

		//lignites
		recipes.add(new CombinationRecipe()
			.in(new OreDictStack(LIGNITE.gem()))
			.out(DictFrame.fromOne(ModItems.coke, EnumCokeType.LIGNITE), new ItemStack(ModItems.sulfur_small, 6))
			.out(new FluidStack(Fluids.COALCREOSOTE, 100)));
		recipes.add(new CombinationRecipe()
			.in(new OreDictStack(LIGNITE.dust()))
			.out(DictFrame.fromOne(ModItems.coke, EnumCokeType.LIGNITE), new ItemStack(ModItems.sulfur_small, 6))
			.out(new FluidStack(Fluids.COALCREOSOTE, 150)));
		recipes.add(new CombinationRecipe()
			.in(new ComparableStack(DictFrame.fromOne(ModItems.briquette, EnumBriquetteType.LIGNITE)))
			.out(DictFrame.fromOne(ModItems.coke, EnumCokeType.LIGNITE, 2), new ItemStack(ModItems.sulfur))
			.out(new FluidStack(Fluids.COALCREOSOTE, 200)));

		//woods
		recipes.add(new CombinationRecipe()
			.in(new OreDictStack(KEY_LOG))
			.out(new ItemStack(Items.coal, 1 ,1))
			.out(new FluidStack(Fluids.WOODOIL, 250)));
		recipes.add(new CombinationRecipe()
			.in(new OreDictStack(KEY_SAPLING))
			.out(DictFrame.fromOne(ModItems.powder_ash, EnumAshType.WOOD, 2))
			.out(new FluidStack(Fluids.WOODOIL, 250)));
		recipes.add(new CombinationRecipe()
			.in(new ComparableStack(DictFrame.fromOne(ModItems.briquette, EnumBriquetteType.WOOD)))
			.out(new ItemStack(Items.coal, 1 ,1))
			.out(new FluidStack(Fluids.WOODOIL, 750)));
		recipes.add(new CombinationRecipe()
			.in(new FluidStack(Fluids.WOODOIL, 250))
			.out(DictFrame.fromOne(ModItems.powder_ash, EnumAshType.WOOD, 2), new ItemStack(ModItems.niter)));

		//tars
		recipes.add(new CombinationRecipe()
			.in(new ComparableStack(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.CRUDE)))
			.out(DictFrame.fromOne(ModItems.coke, EnumCokeType.PETROLEUM)));
		recipes.add(new CombinationRecipe()
			.in(new ComparableStack(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.CRACK)))
			.out(DictFrame.fromOne(ModItems.coke, EnumCokeType.PETROLEUM)));
		recipes.add(new CombinationRecipe()
			.in(new ComparableStack(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.COAL)))
			.out(DictFrame.fromOne(ModItems.coke, EnumCokeType.COAL)));
		recipes.add(new CombinationRecipe()
			.in(new ComparableStack(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.WOOD)))
			.out(DictFrame.fromOne(ModItems.coke, EnumCokeType.COAL)));

		//misc minerals
		recipes.add(new CombinationRecipe()
			.in(new OreDictStack(CHLOROCALCITE.dust()))
			.out(new ItemStack(ModItems.powder_calcium))
			.out(new FluidStack(Fluids.CHLORINE, 250)));
		recipes.add(new CombinationRecipe()
			.in(new OreDictStack(MOLYSITE.dust()))
			.out(new ItemStack(ModItems.powder_iron))
			.out(new FluidStack(Fluids.CHLORINE, 250)));
		recipes.add(new CombinationRecipe()
			.in(new OreDictStack(CINNABAR.gem()))
			.out(new ItemStack(ModItems.sulfur))
			.out(new FluidStack(Fluids.MERCURY, 100)));
		recipes.add(new CombinationRecipe()
			.in(new ComparableStack(Items.glowstone_dust))
			.out(new ItemStack(ModItems.sulfur))
			.out(new FluidStack(Fluids.CHLORINE, 250)));
		recipes.add(new CombinationRecipe()
			.in(new ComparableStack(Items.glowstone_dust))
			.out(new ItemStack(ModItems.sulfur))
			.out(new FluidStack(Fluids.CHLORINE, 250)));
		recipes.add(new CombinationRecipe()
			.in(new OreDictStack(SODALITE.gem()))
			.out(new ItemStack(ModItems.powder_sodium))
			.out(new FluidStack(Fluids.CHLORINE, 100)));
		recipes.add(new CombinationRecipe()
			.in(new OreDictStack(CRYOLITE.gem()))
			.out(new ItemStack(ModItems.powder_aluminium))
			.out(new FluidStack(Fluids.LYE, 300)));
		recipes.add(new CombinationRecipe()
			.in(new OreDictStack(CRYOLITE.gem()))
			.out(new ItemStack(ModItems.powder_aluminium))
			.out(new FluidStack(Fluids.LYE, 300)));
		recipes.add(new CombinationRecipe()
			.in(new OreDictStack(LIMESTONE.dust()))
			.out(new ItemStack(ModItems.powder_calcium, 2))
			.out(new FluidStack(Fluids.CARBONDIOXIDE, 50)));
		recipes.add(new CombinationRecipe()
			.in(new OreDictStack(NA.dust()))
			.out(new FluidStack(Fluids.SODIUM, 100)));
		recipes.add(new CombinationRecipe()
			.in(new FluidStack(FluidsF.BORIC_ACID, 100))
			.in(new OreDictStack(KEY_SAND))
			.out(new ItemStack(ModBlocks.glass_boron, 2)));

		//utilities
		recipes.add(new CombinationRecipe()
			.in(new ComparableStack(Items.reeds))
			.out(new ItemStack(Items.sugar, 2))
			.out(new FluidStack(Fluids.ETHANOL, 150)));
		recipes.add(new CombinationRecipe()
			.in(new ComparableStack(ModBlocks.plant_flower, 1, 3))
			.out(new FluidStack(Fluids.ETHANOL, 50)));
		recipes.add(new CombinationRecipe()
			.in(new ComparableStack(Blocks.clay))
			.out(new ItemStack(Blocks.brick_block, 1)));

		//oreproc
		recipes.add(new CombinationRecipe()
			.in(new ComparableStack(ModBlocks.stone_resource, 1, BlockEnums.EnumStoneType.MALACHITE.ordinal()))
			.out(new ItemStack(ModItems.powder_copper, 2), new ItemStack(ModItems.sulfur_small, 6)));
		recipes.add(new CombinationRecipe()
			.in(new ComparableStack(ModBlocks.stone_resource, 1, BlockEnums.EnumStoneType.GALENA.ordinal()))
			.out(new ItemStack(ModItems.powder_lead, 2), new ItemStack(ModItems.sulfur_small, 6)));

		/*
		for(BedrockOreType type : BedrockOreType.values()) {
			recipes.add(new CombinationRecipe()
				.in(new ComparableStack(ItemBedrockOreNew.make(ProcessingGrade.BASE, type)))
				.out(ItemBedrockOreNew.make(ProcessingGrade.BASE_ROASTED, type))
				.out(new FluidStack(Fluids.VITRIOL, 50)));
			recipes.add(new CombinationRecipe()
				.in(new ComparableStack(ItemBedrockOreNew.make(ProcessingGrade.PRIMARY, type)))
				.out(ItemBedrockOreNew.make(ProcessingGrade.PRIMARY_ROASTED, type))
				.out(new FluidStack(Fluids.VITRIOL, 50)));
			recipes.add(new CombinationRecipe()
				.in(new ComparableStack(ItemBedrockOreNew.make(ProcessingGrade.SULFURIC_BYPRODUCT, type)))
				.out(ItemBedrockOreNew.make(ProcessingGrade.SULFURIC_ROASTED, type))
				.out(new FluidStack(Fluids.VITRIOL, 50)));
			recipes.add(new CombinationRecipe()
				.in(new ComparableStack(ItemBedrockOreNew.make(ProcessingGrade.SOLVENT_BYPRODUCT, type)))
				.out(ItemBedrockOreNew.make(ProcessingGrade.SOLVENT_ROASTED, type))
				.out(new FluidStack(Fluids.VITRIOL, 50)));
			recipes.add(new CombinationRecipe()
				.in(new ComparableStack(ItemBedrockOreNew.make(ProcessingGrade.RAD_BYPRODUCT, type)))
				.out(ItemBedrockOreNew.make(ProcessingGrade.RAD_ROASTED, type))
				.out(new FluidStack(Fluids.VITRIOL, 50)));
		}*/
	}


	public static HashMap getRecipes() {
		HashMap<Object[], Object[]> map = new HashMap<Object[], Object[]>();

		for(CombinationRecipe rec : recipes) {

			Object[] in = null;
			Object[] out = null;

			if(rec.inputFluid != null && rec.inputItem != null) in = new Object[] {ItemFluidIcon.make(rec.inputFluid), rec.inputItem};
			if(rec.inputFluid != null && rec.inputItem == null) in = new Object[] {ItemFluidIcon.make(rec.inputFluid)};
			if(rec.inputFluid == null && rec.inputItem != null) in = new Object[] {rec.inputItem};

			if(rec.outputFluid != null && rec.outputItem != null && rec.outputByproduct == null) out = new Object[] {rec.outputItem, ItemFluidIcon.make(rec.outputFluid)};
			if(rec.outputFluid != null && rec.outputItem == null && rec.outputByproduct == null) out = new Object[] {ItemFluidIcon.make(rec.outputFluid)};
			if(rec.outputFluid == null && rec.outputItem != null && rec.outputByproduct == null) out = new Object[] {rec.outputItem};

			if(rec.outputFluid != null && rec.outputItem != null && rec.outputByproduct != null) out = new Object[] {rec.outputItem, rec.outputByproduct, ItemFluidIcon.make(rec.outputFluid)};
			if(rec.outputFluid != null && rec.outputItem == null && rec.outputByproduct != null) out = new Object[] {ItemFluidIcon.make(rec.outputFluid)};
			if(rec.outputFluid == null && rec.outputItem != null && rec.outputByproduct != null) out = new Object[] {rec.outputItem, rec.outputByproduct};

			if(in != null && out != null) {
				map.put(in, out);
			}
		}

		return map;
	}

	@Override
	public String getFileName() {
		return "hbmCombination.json";
	}

	@Override
	public Object getRecipeObject() {
		return recipes;
	}

	@Override
	public void readRecipe(JsonElement recipe) {
		JsonObject obj = (JsonObject) recipe;

		AStack inputItem = obj.has("inputItem") ? this.readAStack(obj.get("inputItem").getAsJsonArray()) : null;
		FluidStack inputFluid = obj.has("inputFluid") ? this.readFluidStack(obj.get("inputFluid").getAsJsonArray()) : null;
		ItemStack outputItem = obj.has("outputItem") ? this.readItemStack(obj.get("outputItem").getAsJsonArray()) : null;
		FluidStack outputFluid = obj.has("outputFluid") ? this.readFluidStack(obj.get("outputFluid").getAsJsonArray()) : null;

		recipes.add(new CombinationRecipe().in(inputFluid).in(inputItem).out(outputFluid).out(outputItem));
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {

		CombinationRecipe rec = (CombinationRecipe) recipe;

		if(rec.inputFluid != null) { writer.name("inputFluid"); this.writeFluidStack(rec.inputFluid, writer); }
		if(rec.inputItem != null) { writer.name("inputItem"); this.writeAStack(rec.inputItem, writer); }
		if(rec.outputFluid != null) { writer.name("outputFluid"); this.writeFluidStack(rec.outputFluid, writer); }
		if(rec.outputItem != null) { writer.name("outputItem"); this.writeItemStack(rec.outputItem, writer); }
	}

	@Override
	public void deleteRecipes() {
		recipes.clear();
	}

	public static class CombinationRecipe {
		public FluidStack inputFluid;
		public AStack inputItem;
		public ItemStack outputItem;
		public ItemStack outputByproduct;
		public FluidStack outputFluid;

		public CombinationRecipe() {}

		public CombinationRecipe in(FluidStack stack) { this.inputFluid = stack; return this; }
		public CombinationRecipe in(AStack stack) { this.inputItem = stack; return this; }
		public CombinationRecipe out(FluidStack stack) { this.outputFluid = stack; return this; }
		public CombinationRecipe out(ItemStack stack) { this.outputItem = stack; return this; }
		public CombinationRecipe out(ItemStack stack, ItemStack byproduct) {
			this.outputItem = stack;
			this.outputByproduct = byproduct;
			return this;
		}

	}


}
