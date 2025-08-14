package com.hbm.inventory.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockPlushie;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.RecipesCommon.*;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
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
		//stick and stone stage shortcuts
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.pump_steam),
			new ComparableStack(ModItems.arcade_parts, 2, EnumComponentType.MACHINE_PARTS)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_ammo_press),
			new ComparableStack(ModItems.arcade_parts, 2, EnumComponentType.MACHINE_PARTS)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_boiler),
			new ComparableStack(ModItems.arcade_parts, 2, EnumComponentType.MACHINE_PARTS)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_crucible),
			new ComparableStack(ModItems.ingot_firebrick, 16),
			new ComparableStack(ModItems.arcade_parts, 2, EnumComponentType.MACHINE_PARTS)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.foundry_basin, 4),
			new ComparableStack(ModItems.ingot_firebrick, 4),
			new ComparableStack(ModItems.arcade_parts, 1, EnumComponentType.MACHINE_PARTS)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.foundry_mold, 4),
			new ComparableStack(ModItems.ingot_firebrick, 4),
			new ComparableStack(ModItems.arcade_parts, 1, EnumComponentType.MACHINE_PARTS)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.foundry_outlet, 8),
			new ComparableStack(ModItems.ingot_firebrick, 4),
			new ComparableStack(ModItems.arcade_parts, 1, EnumComponentType.MACHINE_PARTS)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.foundry_channel, 16),
			new ComparableStack(ModItems.ingot_firebrick, 4),
			new ComparableStack(ModItems.arcade_parts, 1, EnumComponentType.MACHINE_PARTS)));
		//electricity
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_arc_welder),
			new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_assembly_machine),
			new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_shredder),
			new ComparableStack(ModItems.arcade_parts, 2, EnumComponentType.MACHINE_PARTS),
			new OreDictStack(OreDictManager.KEY_CIRCUIT_T1, 1)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_crystallizer),
			new ComparableStack(ModItems.arcade_parts, 12, EnumComponentType.MACHINE_PARTS),
			new OreDictStack(OreDictManager.KEY_CIRCUIT_T2, 4),
			new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MILITARY_PACKAGE)));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_catalytic_reformer),
			new ComparableStack(ModItems.arcade_parts, 48, EnumComponentType.MACHINE_PARTS),
			new OreDictStack(OreDictManager.KEY_CIRCUIT_T3, 12),
			new ComparableStack(ModItems.arcade_parts, 16, EnumComponentType.MILITARY_PACKAGE),
			new ComparableStack(ModItems.part_generic, 8, ItemGenericPart.EnumPartType.LDE)));
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

		writer.name("extraReq").value(offer.extraReq.ordinal());
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
