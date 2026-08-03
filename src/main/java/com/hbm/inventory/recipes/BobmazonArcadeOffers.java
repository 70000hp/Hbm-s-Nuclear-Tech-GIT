package com.hbm.inventory.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.ModBlocks;

import static com.hbm.inventory.OreDictManager.*;

import com.hbm.inventory.FluidStack;
import com.hbm.inventory.RecipesCommon.*;
import com.hbm.inventory.recipes.loader.GenericRecipes;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ItemEnums.*;
import com.hbm.items.ItemGenericPart;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemArcadeComponent.EnumComponentType;
import com.hbm.util.BobMathUtil;
import com.hbm.util.i18n.I18nUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class BobmazonArcadeOffers extends SerializableRecipe {

	public static List<ArcadeOffer> recipes = new ArrayList<>();
	public static HashMap<String,ArcadeOffer> searchMap = new HashMap<>();

	@Override
	public void registerDefaults() {
		//heat and steam
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.pump_steam), new OreDictStack(KEY_PLANKS, 16), new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.pump_electric), new ComparableStack(ModItems.arcade_parts, 8, EnumComponentType.MACHINE_PARTS), new OreDictStack(KEY_CIRCUIT_T1)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_ammo_press), new ComparableStack(ModItems.arcade_parts, 2, EnumComponentType.MACHINE_PARTS)));

		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_boiler), new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_stirling), new OreDictStack(KEY_PLANKS, 16), new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_sawmill), new OreDictStack(KEY_PLANKS, 16), new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_stirling_steel), new ComparableStack(ModBlocks.machine_stirling), new ComparableStack(ModItems.arcade_parts, 1, EnumComponentType.MILITARY_PACKAGE)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.furnace_steel), new ComparableStack(ModItems.arcade_parts, 6, EnumComponentType.MACHINE_PARTS), new OreDictStack(KEY_CIRCUIT_T1, 1)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_ashpit), new ComparableStack(ModItems.arcade_parts, 2, EnumComponentType.MACHINE_PARTS)));


		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_autosaw), new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS),  new OreDictStack(KEY_CIRCUIT_T1, 2)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.press_preheater), new ComparableStack(ModItems.arcade_parts, 2, EnumComponentType.MACHINE_PARTS)));

		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.heater_firebox), new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS)));


		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.heater_oven),
			new ComparableStack(ModItems.ingot_firebrick, 4), new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_crucible),
			new ComparableStack(ModItems.ingot_firebrick, 16), new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.foundry_basin, 4),
			new ComparableStack(ModItems.ingot_firebrick, 4), new ComparableStack(ModItems.arcade_parts, 1, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.foundry_mold, 4),
			new ComparableStack(ModItems.ingot_firebrick, 4), new ComparableStack(ModItems.arcade_parts, 1, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.foundry_outlet, 8),
			new ComparableStack(ModItems.ingot_firebrick, 4), new ComparableStack(ModItems.arcade_parts, 1, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.foundry_channel, 16),
			new ComparableStack(ModItems.ingot_firebrick, 4), new ComparableStack(ModItems.arcade_parts, 1, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_rotary_furnace),
			new ComparableStack(ModItems.ingot_firebrick, 16), new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS)));

		//electricity pre-oil
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_arc_welder), new ComparableStack(ModItems.arcade_parts, 6, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_soldering_station), new ComparableStack(ModItems.arcade_parts, 6, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_intake), new ComparableStack(ModItems.arcade_parts, 2, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_excavator), new ComparableStack(ModItems.arcade_parts, 8, EnumComponentType.MACHINE_PARTS)));

		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_assembly_machine),
			new ComparableStack(ModItems.arcade_parts, 6, EnumComponentType.MACHINE_PARTS),
			new OreDictStack(KEY_CIRCUIT_T1, 2)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_shredder),
			new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS),
			new OreDictStack(KEY_CIRCUIT_T1, 1)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_chemical_plant),
			new ComparableStack(ModItems.arcade_parts, 8, EnumComponentType.MACHINE_PARTS),
			new OreDictStack(KEY_CIRCUIT_T1, 2)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_mixer),
			new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS),
			new OreDictStack(KEY_CIRCUIT_T1, 1)));

		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_centrifuge),
			new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS),
			new OreDictStack(KEY_CIRCUIT_T1, 1),
			new ComparableStack(ModItems.arcade_parts, 1, EnumComponentType.MILITARY_PACKAGE)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_arc_furnace),
			new ComparableStack(ModItems.arcade_parts, 12, EnumComponentType.MACHINE_PARTS),
			new OreDictStack(KEY_CIRCUIT_T1, 6),
			new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MILITARY_PACKAGE)));

		//electricity shortcuts and miscellanea
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.red_cable, 64), new ComparableStack(ModItems.arcade_parts, 2, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.red_wire_coated, 64), new ComparableStack(ModItems.arcade_parts, 2, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.red_cable_paintable, 64), new ComparableStack(ModItems.arcade_parts, 2, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.cable_detector, 4), new ComparableStack(ModItems.arcade_parts, 2, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.cable_diode, 4), new ComparableStack(ModItems.arcade_parts, 2, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.cable_switch, 4), new ComparableStack(ModItems.arcade_parts, 2, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.red_cable_gauge, 4), new ComparableStack(ModItems.arcade_parts, 2, EnumComponentType.MACHINE_PARTS)));

		registerRecipe(new ArcadeOffer(new ItemStack(ModItems.cable_drum, 2), new ComparableStack(ModItems.arcade_parts, 2, EnumComponentType.MACHINE_PARTS)));

		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.red_pylon, 8), new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.red_pylon_medium_steel, 4), new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.red_pylon_large, 4), new ComparableStack(ModItems.arcade_parts, 8, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.substation, 2), new ComparableStack(ModItems.arcade_parts, 8, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.red_connector, 16), new ComparableStack(ModItems.arcade_parts, 2, EnumComponentType.MACHINE_PARTS)));

		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.capacitor_bus, 4), new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_battery_potato, 1), new ComparableStack(ModItems.arcade_parts, 1, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_battery, 1), new ComparableStack(ModItems.arcade_parts, 12, EnumComponentType.MACHINE_PARTS), new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MILITARY_PACKAGE)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_lithium_battery, 1), new ComparableStack(ModItems.item_expensive, 2, EnumExpensiveType.HEAVY_FRAME), new OreDictStack(KEY_CIRCUIT_T2, 4), new ComparableStack(ModItems.arcade_parts, 8, EnumComponentType.MILITARY_PACKAGE)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.capacitor_copper, 1), new ComparableStack(ModItems.arcade_parts, 8, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.capacitor_gold, 1), new ComparableStack(ModItems.arcade_parts, 8, EnumComponentType.MACHINE_PARTS), new OreDictStack(KEY_CIRCUIT_T1, 4)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.capacitor_niobium, 1), new ComparableStack(ModItems.arcade_parts, 12, EnumComponentType.MACHINE_PARTS), new OreDictStack(KEY_CIRCUIT_T2, 2)));

		//NTlogistics
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.conveyor, 64), new ComparableStack(ModItems.arcade_parts, 2, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.conveyor_double, 64), new ComparableStack(ModItems.arcade_parts, 3, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.conveyor_triple, 64), new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.conveyor_express, 64), new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS)));

		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.conveyor_chute, 24), new ComparableStack(ModItems.arcade_parts, 3, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.conveyor_lift, 24), new ComparableStack(ModItems.arcade_parts, 3, EnumComponentType.MACHINE_PARTS)));

		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.crane_extractor, 16), new ComparableStack(ModItems.arcade_parts, 2, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.crane_inserter, 16), new ComparableStack(ModItems.arcade_parts, 2, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.crane_grabber, 16), new ComparableStack(ModItems.arcade_parts, 2, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.crane_splitter, 16), new ComparableStack(ModItems.arcade_parts, 2, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.crane_router, 16), new ComparableStack(ModItems.arcade_parts, 2, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.crane_boxer, 16), new ComparableStack(ModItems.arcade_parts, 2, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.crane_unboxer, 16), new ComparableStack(ModItems.arcade_parts, 2, EnumComponentType.MACHINE_PARTS)));

		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.drone_crate, 4),
			new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS),
			new OreDictStack(KEY_CIRCUIT_T1, 1),
			new ComparableStack(ModItems.arcade_parts, 1, EnumComponentType.MILITARY_PACKAGE)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.drone_waypoint, 8),
			new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS),
			new OreDictStack(KEY_CIRCUIT_T1, 1)));

		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.drone_dock, 2),
			new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS),
			new OreDictStack(KEY_CIRCUIT_T1, 2),
			new ComparableStack(ModItems.arcade_parts, 1, EnumComponentType.MILITARY_PACKAGE)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.drone_crate_provider, 8),
			new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS),
			new OreDictStack(KEY_CIRCUIT_T1, 1),
			new ComparableStack(ModItems.arcade_parts, 1, EnumComponentType.MILITARY_PACKAGE)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.drone_crate_requester, 8),
			new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS),
			new OreDictStack(KEY_CIRCUIT_T1, 1),
			new ComparableStack(ModItems.arcade_parts, 1, EnumComponentType.MILITARY_PACKAGE)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.drone_waypoint_request, 8),
			new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS),
			new OreDictStack(KEY_CIRCUIT_T1, 1),
			new ComparableStack(ModItems.arcade_parts, 1, EnumComponentType.MILITARY_PACKAGE)));

		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.pneumatic_tube, 64), new ComparableStack(ModItems.arcade_parts, 6, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.pneumatic_tube_paintable, 64), new ComparableStack(ModItems.arcade_parts, 6, EnumComponentType.MACHINE_PARTS)));

		//misc fluid stuff
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.fluid_duct_box, 64), new ComparableStack(ModItems.arcade_parts, 2, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.fluid_duct_neo, 64), new ComparableStack(ModItems.arcade_parts, 2, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.fluid_duct_exhaust, 64), new ComparableStack(ModItems.arcade_parts, 2, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.fluid_duct_paintable, 64), new ComparableStack(ModItems.arcade_parts, 2, EnumComponentType.MACHINE_PARTS)));

		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.fluid_duct_gauge, 4), new ComparableStack(ModItems.arcade_parts, 2, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.fluid_pump, 4), new ComparableStack(ModItems.arcade_parts, 2, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.fluid_switch, 4), new ComparableStack(ModItems.arcade_parts, 2, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.fluid_valve, 4), new ComparableStack(ModItems.arcade_parts, 2, EnumComponentType.MACHINE_PARTS)));

		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_drain, 1), new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS)));

		//steam cycle
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_steam_engine), new ComparableStack(ModItems.arcade_parts, 8, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_condenser), new ComparableStack(ModItems.arcade_parts, 1, EnumComponentType.MACHINE_PARTS)));

		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_tower_small),
			new ComparableStack(ModItems.arcade_parts, 8, EnumComponentType.MACHINE_PARTS),
			new ComparableStack(ModItems.arcade_parts, 2, EnumComponentType.MILITARY_PACKAGE)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_tower_large),
			new ComparableStack(ModItems.item_expensive, 1, EnumExpensiveType.HEAVY_FRAME),
			new ComparableStack(ModItems.arcade_parts, 6, EnumComponentType.MILITARY_PACKAGE)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_large_turbine),
			new ComparableStack(ModItems.arcade_parts, 12, EnumComponentType.MACHINE_PARTS),
			new OreDictStack(KEY_CIRCUIT_T2, 2),
			new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MILITARY_PACKAGE)));

		//oil
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_fraction_tower), new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.fraction_spacer, 4), new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS)));

		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_well),
			new ComparableStack(ModItems.arcade_parts, 8, EnumComponentType.MACHINE_PARTS),
			new OreDictStack(KEY_CIRCUIT_T1, 1)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_solidifier),
			new ComparableStack(ModItems.arcade_parts, 6, EnumComponentType.MACHINE_PARTS),
			new OreDictStack(KEY_CIRCUIT_T1, 2)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_liquefactor),
			new ComparableStack(ModItems.arcade_parts, 6, EnumComponentType.MACHINE_PARTS),
			new OreDictStack(KEY_CIRCUIT_T1, 2)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_compressor),
			new ComparableStack(ModItems.arcade_parts, 6, EnumComponentType.MACHINE_PARTS),
			new OreDictStack(KEY_CIRCUIT_T1, 2)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_compressor_compact),
			new ComparableStack(ModItems.arcade_parts, 6, EnumComponentType.MACHINE_PARTS),
			new OreDictStack(KEY_CIRCUIT_T1, 2)));

		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.heater_oilburner),
			new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS),
			new ComparableStack(ModItems.arcade_parts, 1, EnumComponentType.MILITARY_PACKAGE)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_diesel, 2),
			new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS),
			new OreDictStack(KEY_CIRCUIT_T1, 1)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_combustion_engine),
			new ComparableStack(ModItems.arcade_parts, 8, EnumComponentType.MACHINE_PARTS),
			new OreDictStack(KEY_CIRCUIT_T1, 2)));

		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.barrel_steel),
			new ComparableStack(ModItems.arcade_parts, 1, EnumComponentType.MILITARY_PACKAGE)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_fluidtank),
			new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS),
			new ComparableStack(ModItems.arcade_parts, 6, EnumComponentType.MILITARY_PACKAGE)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_catalytic_cracker),
			new ComparableStack(ModItems.arcade_parts, 8, EnumComponentType.MACHINE_PARTS),
			new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MILITARY_PACKAGE)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_refinery),
			new ComparableStack(ModItems.arcade_parts, 16, EnumComponentType.MACHINE_PARTS),
			new OreDictStack(KEY_CIRCUIT_T1, 6),
			new ComparableStack(ModItems.arcade_parts, 6, EnumComponentType.MILITARY_PACKAGE)));
		//oil, advanced
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_pumpjack),
			new ComparableStack(ModItems.item_expensive, 2, EnumExpensiveType.HEAVY_FRAME),
			new OreDictStack(KEY_CIRCUIT_T1, 6),
			new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MILITARY_PACKAGE)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_coker),
			new ComparableStack(ModItems.item_expensive, 1, EnumExpensiveType.HEAVY_FRAME),
			new ComparableStack(ModItems.arcade_parts, 6, EnumComponentType.MILITARY_PACKAGE)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_industrial_boiler),
			new ComparableStack(ModItems.arcade_parts, 12, EnumComponentType.MACHINE_PARTS),
			new ComparableStack(ModItems.arcade_parts, 2, EnumComponentType.MILITARY_PACKAGE),
			new OreDictStack(POLYMER.ingot(), 6)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_catalytic_reformer),
			new ComparableStack(ModItems.arcade_parts, 48, EnumComponentType.MACHINE_PARTS),
			new OreDictStack(KEY_CIRCUIT_T3, 12),
			new ComparableStack(ModItems.arcade_parts, 16, EnumComponentType.MILITARY_PACKAGE),
			new ComparableStack(ModItems.part_generic, 8, ItemGenericPart.EnumPartType.LDE)));

		//electricity, post-oil
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_crystallizer),
			new ComparableStack(ModItems.arcade_parts, 16, EnumComponentType.MACHINE_PARTS),
			new OreDictStack(KEY_CIRCUIT_T2, 2),
			new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MILITARY_PACKAGE)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_electrolyser),
			new ComparableStack(ModItems.item_expensive, 2, EnumExpensiveType.HEAVY_FRAME),
			new OreDictStack(KEY_CIRCUIT_T2, 4),
			new ComparableStack(ModItems.arcade_parts, 8, EnumComponentType.MILITARY_PACKAGE),
			new ComparableStack(ModItems.item_expensive, 2, EnumExpensiveType.LEAD_PLATING)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_gascent),
			new ComparableStack(ModItems.arcade_parts, 8, EnumComponentType.MACHINE_PARTS),
			new OreDictStack(KEY_CIRCUIT_T2, 1),
			new ComparableStack(ModItems.arcade_parts, 2, EnumComponentType.MILITARY_PACKAGE),
			new ComparableStack(ModItems.item_expensive, 1, EnumExpensiveType.LEAD_PLATING)));
		registerRecipe(new ArcadeOffer(new ItemStack(ModBlocks.machine_purex),
			new ComparableStack(ModItems.item_expensive, 6, EnumExpensiveType.HEAVY_FRAME),
			new OreDictStack(KEY_CIRCUIT_T2, 12),
			new ComparableStack(ModItems.arcade_parts, 12, EnumComponentType.MILITARY_PACKAGE),
			new ComparableStack(ModItems.item_expensive, 8, EnumExpensiveType.LEAD_PLATING)));

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

	public void registerRecipe(ArcadeOffer offer){
		recipes.add(offer);
		searchMap.put(offer.name, offer);
	}

	@Override
	public void readRecipe(JsonElement recipe) {
		JsonObject obj = (JsonObject) recipe;

		String name = obj.get("name").getAsString();
		ItemStack product = readItemStack(obj.get("product").getAsJsonArray());
		AStack[] cost = readAStackArray(obj.get("cost").getAsJsonArray());
		Requirement extraReq = Requirement.values()[obj.get("extraReq").getAsInt()];

		registerRecipe(new ArcadeOffer(name, product,extraReq, cost));
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		ArcadeOffer offer = (ArcadeOffer) recipe;

		writer.name("name").value(offer.name);

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
		public String name;
		public ItemStack product;
		public AStack[] cost;
		public Requirement extraReq;

		public ArcadeOffer(ItemStack product, AStack... cost){
			this(product.getUnlocalizedName(), product, null, cost);
		}

		public ArcadeOffer(String name,ItemStack product, Requirement extraReq, AStack... cost){
			this.name = name;
			this.product = product;
			this.cost = cost;
			this.extraReq = extraReq;
		}

		public List<String> print() {
			List<String> list = new ArrayList();
			list.add(EnumChatFormatting.YELLOW + this.product.getDisplayName());

			// input label + items
			list.add(EnumChatFormatting.BOLD + I18nUtil.resolveKey("gui.recipe.input") + ":");
			if(cost != null) for(AStack stack : cost) {
				ItemStack display = stack.extractForCyclingDisplay(20);
				list.add("  " + EnumChatFormatting.GRAY + display.stackSize + "x " + display.getDisplayName());
			}
			// output label + items
			list.add(EnumChatFormatting.BOLD + I18nUtil.resolveKey("gui.recipe.output") + ":");
			list.add("  " + EnumChatFormatting.GRAY + this.product.stackSize + "x " + this.product.getDisplayName());

			return list;
		}

	}

	public enum Requirement {
		EARLY,
		OIL,
		NUCLEAR_EARLY,
		NUCLEAR_LATE,
	}


}
