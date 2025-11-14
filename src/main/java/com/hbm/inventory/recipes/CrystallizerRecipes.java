package com.hbm.inventory.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.hbm.inventory.OreDictManager.*;

import com.hbm.blocks.ModBlocks;

import com.hbm.inventory.FluidStack;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.FluidsF;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.inventory.recipes.loader.GenericRecipes;
import com.hbm.items.ItemEnums.EnumAshType;
import com.hbm.items.ItemEnums.EnumChunkType;
import com.hbm.items.ItemEnums.EnumPlantType;
import com.hbm.items.ItemEnums.EnumTarType;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemChemicalDye;
import com.hbm.items.machine.ItemChemicalDye.EnumChemDye;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.items.machine.ItemScraps;
import com.hbm.items.special.ItemBedrockOreNew;
import com.hbm.items.special.ItemBedrockOre.EnumBedrockOre;
import com.hbm.items.special.ItemBedrockOreNew.BedrockOreGrade;
import com.hbm.items.special.ItemBedrockOreNew.BedrockOreType;
import com.hbm.items.special.ItemPlasticScrap.ScrapType;
import com.hbm.main.MainRegistry;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

//This time we're doing this right
//...right?
public class CrystallizerRecipes extends GenericRecipes<GenericRecipe> {

	public static final CrystallizerRecipes INSTANCE = new CrystallizerRecipes();

	@Override public int inputItemLimit() { return 1; }
	@Override public int inputFluidLimit() { return 1; }
	@Override public int outputItemLimit() { return 1; }
	@Override public int outputFluidLimit() { return 1; }

	@Override public String getFileName() { return "hbmCrystallizer.json"; }
	@Override public GenericRecipe instantiateRecipe(String name) { return new GenericRecipe(name); }

	@Override
	public void registerDefaults() {

		this.register(new GenericRecipe("crys.cement").setup(60, 1_000)
			.inputItems(new ComparableStack(ModItems.powder_calcium))
			.inputFluids(new FluidStack(Fluids.SULFURIC_ACID, 500))
			.outputItems(new ItemStack(ModItems.powder_cement, 8)));

		this.register(new GenericRecipe("crys.tikite").setup(120, 5_000)
			.inputItems(new ComparableStack(ModBlocks.ore_tikite))
			.inputFluids(new FluidStack(Fluids.SULFURIC_ACID, 500))
			.outputItems(new ItemStack(ModItems.crystal_trixite))
			.outputFluids(new FluidStack(Fluids.VITRIOL, 500)));

		this.register(new GenericRecipe("crys.diamond").setup(120, 1_500)
			.inputItems(new ComparableStack(ModBlocks.gravel_diamond))
			.inputFluids(new FluidStack(Fluids.SULFURIC_ACID, 500))
			.outputItems(new ItemStack(ModItems.crystal_diamond))
			.outputFluids(new FluidStack(Fluids.VITRIOL, 500)));

		this.register(new GenericRecipe("crys.srn").setup(120, 3_000)
			.inputItems(new OreDictStack(SRN.ingot()))
			.inputFluids(new FluidStack(Fluids.SULFURIC_ACID, 500))
			.outputItems(new ItemStack(ModItems.crystal_schraranium))
			.outputFluids(new FluidStack(Fluids.VITRIOL, 500)));

		this.register(new GenericRecipe("crys.fiberglass").setup(60, 2_000)
			.inputItems(new OreDictStack(KEY_SAND))
			.inputFluids(new FluidStack(FluidsF.BORIC_ACID, 500))
			.outputItems(new ItemStack(ModItems.ingot_fiberglass))
			.outputFluids(new FluidStack(Fluids.VITRIOL, 250)));

		this.register(new GenericRecipe("crys.quartz").setup(60, 2_000)
			.inputItems(new OreDictStack(SI.ingot()))
			.inputFluids(new FluidStack(Fluids.SULFURIC_ACID, 500))
			.outputItems(new ItemStack(Items.quartz, 2)));

		this.register(new GenericRecipe("crys.cinnMercury").setup(120, 2_000)
			.inputItems(new OreDictStack(CINNABAR.crystal()))
			.inputFluids(new FluidStack(Fluids.SULFURIC_ACID, 500))
			.outputItems(new ItemStack(ModItems.ingot_mercury, 3))
			.outputFluids(new FluidStack(Fluids.VITRIOL, 500)));

		this.register(new GenericRecipe("crys.redMercury").setup(120, 2_000)
			.inputItems(new OreDictStack(REDSTONE.block()))
			.inputFluids(new FluidStack(Fluids.SULFURIC_ACID, 500))
			.outputItems(new ItemStack(ModItems.ingot_mercury, 3))
			.outputFluids(new FluidStack(Fluids.VITRIOL, 500)));

		this.register(new GenericRecipe("crys.borax").setup(120, 3_000)
			.inputItems(new OreDictStack(BORAX.dust()))
			.inputFluids(new FluidStack(Fluids.SULFURIC_ACID, 500))
			.outputItems(new ItemStack(ModItems.powder_sodium))
			.outputFluids(new FluidStack(FluidsF.BORIC_ACID, 400)));

		this.register(new GenericRecipe("crys.reinfStone").setup(30, 3_500)
			.inputItems(new ComparableStack(Blocks.cobblestone))
			.inputFluids(new FluidStack(Fluids.SULFURIC_ACID, 500))
			.outputItems(new ItemStack(ModBlocks.reinforced_stone)));

		this.register(new GenericRecipe("crys.brickObsidian").setup(30, 3_500)
			.inputItems(new ComparableStack(ModBlocks.gravel_obsidian))
			.inputFluids(new FluidStack(Fluids.SULFURIC_ACID, 100))
			.outputItems(new ItemStack(ModBlocks.brick_obsidian)));


		this.register(new GenericRecipe("crys.infernalFuel").setup(40, 1_500)
			.inputItems(new ComparableStack(ModItems.coal_infernal))
			.inputFluids(new FluidStack(Fluids.NITRIC_ACID, 100))
			.outputItems(new ItemStack(ModItems.solid_fuel))
			.outputFluids(new FluidStack(Fluids.SYNGAS, 250)));

		this.register(new GenericRecipe("crys.gneissLithium").setup(120, 3_500)
			.inputItems(new ComparableStack(ModBlocks.stone_gneiss))
			.inputFluids(new FluidStack(Fluids.NITRIC_ACID, 500))
			.outputItems(new ItemStack(ModItems.powder_lithium)));

		this.register(new GenericRecipe("crys.cadmium").setup(200, 10_000)
			.inputItems(new ComparableStack(DictFrame.fromOne(ModItems.plant_item, EnumPlantType.MUSTARDWILLOW, 10)))
			.inputFluids(new FluidStack(Fluids.RADIOSOLVENT, 250))
			.outputItems(new ItemStack(ModItems.powder_cadmium)));

		this.register(new GenericRecipe("crys.arsenic").setup(200, 5_000)
			.inputItems(new ComparableStack(ModItems.scrap_oil, 8))
			.inputFluids(new FluidStack(Fluids.RADIOSOLVENT, 250))
			.outputItems(new ItemStack(ModItems.nugget_arsenic)));

		this.register(new GenericRecipe("crys.fuller").setup(600, 50_000)
			.inputItems(new ComparableStack(DictFrame.fromOne(ModItems.powder_ash, EnumAshType.FULLERENE)))
			.inputFluids(new FluidStack(Fluids.XYLENE, 1_000))
			.outputItems(new ItemStack(ModItems.ingot_cft)));

		this.register(new GenericRecipe("crys.meteorite").setup(120, 3_000)
			.inputItems(new ComparableStack(ModItems.powder_meteorite))
			.inputFluids(new FluidStack(Fluids.NITRIC_ACID, 500))
			.outputItems(new ItemStack(ModItems.fragment_meteorite)));

		this.register(new GenericRecipe("crys.latexToRubber").setup(120, 3_000)
			.inputItems(new OreDictStack(LATEX.ingot()))
			.inputFluids(new FluidStack(Fluids.SOURGAS, 25))
			.outputItems(new ItemStack(ModItems.ingot_rubber)));

		this.register(new GenericRecipe("crys.secretM").setup(200, 30_000)
			.inputItems(new ComparableStack(ModItems.meteorite_sword_treated))
			.inputFluids(new FluidStack(Fluids.NITRIC_ACID, 10_000))
			.outputItems(new ItemStack(ModItems.meteorite_sword_etched)));

		for(int i = 0; i < ScrapType.values().length; i++) {
			this.register(new GenericRecipe("crys.scrap" + i).setup(100, 3_000)
				.inputItems(new ComparableStack(ModItems.scrap_plastic, 1, i))
				.inputFluids(new FluidStack(Fluids.SULFURIC_ACID, 500))
				.outputItems(new ItemStack(ModItems.circuit_star_piece, 1, i)));
		}

		FluidStack nitric = new FluidStack(Fluids.NITRIC_ACID, 500);
		FluidStack organic = new FluidStack(Fluids.SOLVENT, 500);
		FluidStack hiperf = new FluidStack(Fluids.RADIOSOLVENT, 500);

		int oreTime = 200;

		for(EnumBedrockOre ore : EnumBedrockOre.values()) {
			int i = ore.ordinal();
			this.register(new GenericRecipe("crys." + ModItems.ore_centrifuged.getUnlocalizedName() + ore.oreName).setup(200, 2_000)
				.inputItems(new ComparableStack(ModItems.ore_centrifuged, 1, i))
				.inputFluids(new FluidStack(Fluids.WATER, 1_000))
				.outputItems(new ItemStack(ModItems.ore_cleaned, 1, i)));

			/*registerRecipe(new ComparableStack(ModItems.ore_separated, 1, i),			new CrystallizerRecipe(new ItemStack(ModItems.ore_purified, 1, i), oreTime), sulfur);
			registerRecipe(new ComparableStack(ModItems.ore_separated, 1, i),			new CrystallizerRecipe(new ItemStack(ModItems.ore_nitrated, 1, i), oreTime), nitric);
			registerRecipe(new ComparableStack(ModItems.ore_nitrocrystalline, 1, i),	new CrystallizerRecipe(new ItemStack(ModItems.ore_deepcleaned, 1, i), oreTime), organic);
			registerRecipe(new ComparableStack(ModItems.ore_nitrocrystalline, 1, i),	new CrystallizerRecipe(new ItemStack(ModItems.ore_seared, 1, i), oreTime), hiperf);*/
		}

		for(BedrockOreType type : BedrockOreType.values()) {
			this.register(new GenericRecipe("crys." + BedrockOreGrade.BASE.name() + type.name()).setup(200, 2_000)
				.inputItems(new ComparableStack(ItemBedrockOreNew.make(BedrockOreGrade.BASE, type)))
				.inputFluids(new FluidStack(Fluids.WATER, 1_000))
				.outputItems(ItemBedrockOreNew.make(BedrockOreGrade.BASE_WASHED, type)));

			this.register(new GenericRecipe("crys." + BedrockOreGrade.BASE_ROASTED.name() + type.name()).setup(200, 2_000)
				.inputItems(new ComparableStack(ItemBedrockOreNew.make(BedrockOreGrade.BASE_ROASTED, type)))
				.inputFluids(new FluidStack(Fluids.WATER, 1_000))
				.outputItems(ItemBedrockOreNew.make(BedrockOreGrade.BASE_WASHED, type)));

			this.register(new GenericRecipe("crys." + BedrockOreGrade.PRIMARY.name() + type.name()).setup(200, 2_000)
				.inputItems(new ComparableStack(ItemBedrockOreNew.make(BedrockOreGrade.PRIMARY, type)))
				.inputFluids(new FluidStack(Fluids.WATER, 1_000))
				.outputItems(ItemBedrockOreNew.make(BedrockOreGrade.PRIMARY_SULFURIC, type)));

		}

		/// COMPAT CERTUS QUARTZ ///
		List<ItemStack> quartz = OreDictionary.getOres("crystalCertusQuartz");
		if(quartz != null && !quartz.isEmpty()) {
			ItemStack qItem = quartz.get(0).copy();
			qItem.stackSize = 12;
			this.register(new GenericRecipe("crys.certus").setup(100, 500)
				.inputItems(new OreDictStack("oreCertusQuartz"))
				.inputFluids(new FluidStack(Fluids.SULFURIC_ACID, 200))
				.outputItems(qItem));
		}

		/// COMPAT WHITE PHOSPHORUS DUST ///
		List<ItemStack> dustWhitePhosphorus = OreDictionary.getOres(P_WHITE.dust());
		if(dustWhitePhosphorus != null && !dustWhitePhosphorus.isEmpty()) {
			this.register(new GenericRecipe("crys.phosphorus").setup(100, 500)
				.inputItems(new OreDictStack(P_WHITE.dust()))
				.inputFluids(new FluidStack(Fluids.AROMATICS, 50))
				.outputItems(new ItemStack(ModItems.ingot_phosphorus)));
		}

		/// COMPAT CINNABAR DUST ///
		List<ItemStack> dustCinnabar = OreDictionary.getOres(CINNABAR.dust());
		if(dustCinnabar != null && !dustCinnabar.isEmpty()) {
			this.register(new GenericRecipe("crys.cinnabar").setup(100, 500)
				.inputItems(new OreDictStack(CINNABAR.dust()))
				.inputFluids(new FluidStack(Fluids.SULFURIC_ACID, 50))
				.outputItems(new ItemStack(ModItems.cinnebar)));
		}

		this.register(new GenericRecipe("crys.moonStone").setup(200, 30_000)
			.inputItems(new ComparableStack(ModBlocks.moon_turf, 16))
			.inputFluids(new FluidStack(Fluids.NITRIC_ACID, 10_000))
			.outputItems(new ItemStack(ModItems.chunk_ore, 1, EnumChunkType.MOONSTONE.ordinal())));

	}

	public void makeSimpleOreProc(DictFrame mat){
		this.register(new GenericRecipe("crys." + mat.mats[0]).setup(120, 1_500)
			.inputItems(new OreDictStack(mat.gem(), 1))
			.inputFluids(new FluidStack(Fluids.SULFURIC_ACID, 500))
			.outputItems(new OreDictStack(mat.crystal(), 1).toStacks().get(0).copy())
			.outputFluids(new FluidStack(Fluids.VITRIOL, 500)));	}

	public static int getAmount(ItemStack stack) {
		return 0;
	}

	public static HashMap getRecipes() {

		HashMap<Object, Object> recipes = new HashMap<Object, Object>();

		for(GenericRecipe recipe : INSTANCE.recipeOrderedList) {
			List input = new ArrayList();
			if(recipe.inputItem != null) for(AStack stack : recipe.inputItem) input.add(stack);
			if(recipe.inputFluid != null) for(FluidStack stack : recipe.inputFluid) input.add(ItemFluidIcon.make(stack));
			List output = new ArrayList();
			if(recipe.outputItem != null) for(GenericRecipes.IOutput stack : recipe.outputItem) output.add(stack.getAllPossibilities());
			if(recipe.outputFluid != null) for(FluidStack stack : recipe.outputFluid) output.add(ItemFluidIcon.make(stack));
			recipes.put(input.toArray(), output.toArray());
		}

		return recipes;
	}
	
}
