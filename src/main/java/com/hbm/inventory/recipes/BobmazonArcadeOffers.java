package com.hbm.inventory.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockPlushie;
import static com.hbm.inventory.OreDictManager.*;
import com.hbm.inventory.RecipesCommon.*;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ItemEnums;
import com.hbm.items.ItemGenericPart;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemArcadeComponent.EnumComponentType;
import net.minecraft.item.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BobmazonArcadeOffers extends SerializableRecipe {

	public static List<ArcadeOffer> recipes = new ArrayList();

	@Override
	public void registerDefaults() {
		//heat and steam
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.pump_steam), new OreDictStack(KEY_PLANKS, 16), new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.pump_electric), new ComparableStack(ModItems.arcade_parts, 8, EnumComponentType.MACHINE_PARTS), new OreDictStack(KEY_CIRCUIT_T1)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_ammo_press), new ComparableStack(ModItems.arcade_parts, 2, EnumComponentType.MACHINE_PARTS)));

		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_boiler), new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_stirling), new OreDictStack(KEY_PLANKS, 16), new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_sawmill), new OreDictStack(KEY_PLANKS, 16), new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_stirling_steel), new ComparableStack(ModBlocks.machine_stirling), new ComparableStack(ModItems.arcade_parts, 1, EnumComponentType.MILITARY_PACKAGE)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.furnace_steel), new ComparableStack(ModItems.arcade_parts, 6, EnumComponentType.MACHINE_PARTS), new OreDictStack(KEY_CIRCUIT_T1, 1)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_ashpit), new ComparableStack(ModItems.arcade_parts, 2, EnumComponentType.MACHINE_PARTS)));


		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_autosaw), new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS),  new OreDictStack(KEY_CIRCUIT_T1, 2)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.press_preheater), new ComparableStack(ModItems.arcade_parts, 2, EnumComponentType.MACHINE_PARTS)));

		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.heater_firebox), new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.heater_firebox), new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS)));


		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.heater_oven),
			new ComparableStack(ModItems.ingot_firebrick, 4), new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_crucible),
			new ComparableStack(ModItems.ingot_firebrick, 16), new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.foundry_basin, 4),
			new ComparableStack(ModItems.ingot_firebrick, 4), new ComparableStack(ModItems.arcade_parts, 1, EnumComponentType.MACHINE_PARTS)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.foundry_mold, 4),
			new ComparableStack(ModItems.ingot_firebrick, 4), new ComparableStack(ModItems.arcade_parts, 1, EnumComponentType.MACHINE_PARTS)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.foundry_outlet, 8),
			new ComparableStack(ModItems.ingot_firebrick, 4), new ComparableStack(ModItems.arcade_parts, 1, EnumComponentType.MACHINE_PARTS)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.foundry_channel, 16),
			new ComparableStack(ModItems.ingot_firebrick, 4), new ComparableStack(ModItems.arcade_parts, 1, EnumComponentType.MACHINE_PARTS)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_rotary_furnace),
			new ComparableStack(ModItems.ingot_firebrick, 16), new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS)));

		//electricity pre-oil
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_arc_welder), new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_soldering_station), new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_excavator), new ComparableStack(ModItems.arcade_parts, 8, EnumComponentType.MACHINE_PARTS)));

		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_assembly_machine),
			new ComparableStack(ModItems.arcade_parts, 6, EnumComponentType.MACHINE_PARTS),
			new OreDictStack(KEY_CIRCUIT_T1, 2)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_shredder),
			new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS),
			new OreDictStack(KEY_CIRCUIT_T1, 1)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_chemical_plant),
			new ComparableStack(ModItems.arcade_parts, 8, EnumComponentType.MACHINE_PARTS),
			new OreDictStack(KEY_CIRCUIT_T1, 2)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_mixer),
			new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS),
			new OreDictStack(KEY_CIRCUIT_T1, 1)));

		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_centrifuge),
			new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS),
			new OreDictStack(KEY_CIRCUIT_T1, 1),
			new ComparableStack(ModItems.arcade_parts, 1, EnumComponentType.MILITARY_PACKAGE)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_arc_furnace),
			new ComparableStack(ModItems.arcade_parts, 12, EnumComponentType.MACHINE_PARTS),
			new OreDictStack(KEY_CIRCUIT_T1, 6),
			new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MILITARY_PACKAGE)));

		//electricity, shortcuts and miscellanea
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.red_cable, 64), new ComparableStack(ModItems.arcade_parts, 2, EnumComponentType.MACHINE_PARTS)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.capacitor_bus, 4), new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_battery_potato, 1), new ComparableStack(ModItems.arcade_parts, 1, EnumComponentType.MACHINE_PARTS)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_battery, 1), new ComparableStack(ModItems.arcade_parts, 12, EnumComponentType.MACHINE_PARTS), new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MILITARY_PACKAGE)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_lithium_battery, 1), new ComparableStack(ModItems.item_expensive, 2, ItemEnums.EnumExpensiveType.HEAVY_FRAME), new OreDictStack(KEY_CIRCUIT_T2, 4), new ComparableStack(ModItems.arcade_parts, 8, EnumComponentType.MILITARY_PACKAGE)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.capacitor_copper, 1), new ComparableStack(ModItems.arcade_parts, 8, EnumComponentType.MACHINE_PARTS)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.capacitor_gold, 1), new ComparableStack(ModItems.arcade_parts, 8, EnumComponentType.MACHINE_PARTS), new OreDictStack(KEY_CIRCUIT_T1, 4)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.capacitor_niobium, 1), new ComparableStack(ModItems.arcade_parts, 12, EnumComponentType.MACHINE_PARTS), new OreDictStack(KEY_CIRCUIT_T2, 2)));

		//oil
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_fraction_tower), new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.fraction_spacer, 4), new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS)));

		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_well),
			new ComparableStack(ModItems.arcade_parts, 8, EnumComponentType.MACHINE_PARTS),
			new OreDictStack(KEY_CIRCUIT_T1, 1)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_solidifier),
			new ComparableStack(ModItems.arcade_parts, 6, EnumComponentType.MACHINE_PARTS),
			new OreDictStack(KEY_CIRCUIT_T1, 2)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_liquefactor),
			new ComparableStack(ModItems.arcade_parts, 6, EnumComponentType.MACHINE_PARTS),
			new OreDictStack(KEY_CIRCUIT_T1, 2)));


		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.heater_oilburner),
			new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS),
			new ComparableStack(ModItems.arcade_parts, 1, EnumComponentType.MILITARY_PACKAGE)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_diesel, 2),
			new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS),
			new OreDictStack(KEY_CIRCUIT_T1, 1)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_combustion_engine),
			new ComparableStack(ModItems.arcade_parts, 8, EnumComponentType.MACHINE_PARTS),
			new OreDictStack(KEY_CIRCUIT_T1, 2)));

		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.barrel_steel),
			new ComparableStack(ModItems.arcade_parts, 1, EnumComponentType.MILITARY_PACKAGE)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_fluidtank),
			new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS),
			new ComparableStack(ModItems.arcade_parts, 6, EnumComponentType.MILITARY_PACKAGE)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_catalytic_cracker),
			new ComparableStack(ModItems.arcade_parts, 8, EnumComponentType.MACHINE_PARTS),
			new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MILITARY_PACKAGE)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_refinery),
			new ComparableStack(ModItems.arcade_parts, 16, EnumComponentType.MACHINE_PARTS),
			new OreDictStack(KEY_CIRCUIT_T1, 6),
			new ComparableStack(ModItems.arcade_parts, 6, EnumComponentType.MILITARY_PACKAGE)));
		//oil, advanced
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_pumpjack),
			new ComparableStack(ModItems.item_expensive, 2, ItemEnums.EnumExpensiveType.HEAVY_FRAME),
			new OreDictStack(KEY_CIRCUIT_T1, 6),
			new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MILITARY_PACKAGE)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_coker),
			new ComparableStack(ModItems.item_expensive, 1, ItemEnums.EnumExpensiveType.HEAVY_FRAME),
			new ComparableStack(ModItems.arcade_parts, 6, EnumComponentType.MILITARY_PACKAGE)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_catalytic_reformer),
			new ComparableStack(ModItems.arcade_parts, 48, EnumComponentType.MACHINE_PARTS),
			new OreDictStack(KEY_CIRCUIT_T3, 12),
			new ComparableStack(ModItems.arcade_parts, 16, EnumComponentType.MILITARY_PACKAGE),
			new ComparableStack(ModItems.part_generic, 8, ItemGenericPart.EnumPartType.LDE)));

		//electricity, post-oil
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_crystallizer),
			new ComparableStack(ModItems.arcade_parts, 16, EnumComponentType.MACHINE_PARTS),
			new OreDictStack(KEY_CIRCUIT_T2, 2),
			new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MILITARY_PACKAGE)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_electrolyser),
			new ComparableStack(ModItems.item_expensive, 2, ItemEnums.EnumExpensiveType.HEAVY_FRAME),
			new OreDictStack(KEY_CIRCUIT_T2, 4),
			new ComparableStack(ModItems.arcade_parts, 8, EnumComponentType.MILITARY_PACKAGE),
			new ComparableStack(ModItems.item_expensive, 2, ItemEnums.EnumExpensiveType.LEAD_PLATING)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_gascent),
			new ComparableStack(ModItems.arcade_parts, 24, EnumComponentType.MACHINE_PARTS),
			new OreDictStack(KEY_CIRCUIT_T2, 2),
			new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MILITARY_PACKAGE),
			new ComparableStack(ModItems.item_expensive, 1, ItemEnums.EnumExpensiveType.LEAD_PLATING)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_purex),
			new ComparableStack(ModItems.item_expensive, 6, ItemEnums.EnumExpensiveType.HEAVY_FRAME),
			new OreDictStack(KEY_CIRCUIT_T2, 12),
			new ComparableStack(ModItems.arcade_parts, 12, EnumComponentType.MILITARY_PACKAGE),
			new ComparableStack(ModItems.item_expensive, 8, ItemEnums.EnumExpensiveType.LEAD_PLATING)));

	}

	@Override
	public String getFileName() {
		return "hbmBobmazonArcadeOffers.json";
	}

	@Override
	public Object getRecipeObject() {
		return recipes;
	}

	public static HashMap<Object, Object> getRecipes() {

		HashMap<Object, Object> recipes = new HashMap<>();

		for(ArcadeOffer recipe : BobmazonArcadeOffers.recipes) {
			List<AStack> inputs = new ArrayList<>();
			for(AStack stack : recipe.cost) if(stack != null) inputs.add(stack);
			recipes.put(inputs.toArray(new AStack[0]), recipe.product.copy());
		}

		return recipes;
	}

	@Override
	public void readRecipe(JsonElement recipe) {
		JsonObject obj = (JsonObject) recipe;

		ItemStack product = readItemStack(obj.get("product").getAsJsonArray());
		AStack[] cost = readAStackArray(obj.get("cost").getAsJsonArray());
		Requirement extraReq = Requirement.values()[obj.get("extraReq").getAsInt()];

		recipes.add(new ArcadeOffer(product,extraReq, cost));
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		ArcadeOffer offer = (ArcadeOffer) recipe;

		writer.name("product");
		writeItemStack(offer.product, writer);

		writer.name("cost").beginArray();
		for (AStack stack : offer.cost) writeAStack(stack, writer);
		writer.endArray();

		//writer.name("extraReq").value(offer.extraReq.ordinal());
	}

	@Override
	public void deleteRecipes() {
		recipes.clear();
	}

	public static class ArcadeOffer {
		public ItemStack product;
		public AStack[] cost;
		public Requirement extraReq;

		public ArcadeOffer(ItemStack product, AStack... cost){
			this(product, null, cost);
		}

		public ArcadeOffer(ItemStack product, Requirement extraReq, AStack... cost){
			this.product = product;
			this.cost = cost;
			this.extraReq = extraReq;
		}

	}

	public enum Requirement {
		EARLY,
		OIL,
		NUCLEAR_EARLY,
		NUCLEAR_LATE,
	}


}
