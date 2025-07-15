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
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.plushie, 1, BlockPlushie.PlushieType.YOMI.ordinal()),
			new AStack[] {
				new ComparableStack(ModItems.arcade_parts, 16, EnumComponentType.MACHINE_PARTS),
				new OreDictStack(OreDictManager.KEY_CIRCUIT_T1, 2),
				new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MILITARY_PACKAGE)
			}));

		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_arc_welder),
			new AStack[] {
				new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MACHINE_PARTS),
			}));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_crystallizer),
			new AStack[] {
				new ComparableStack(ModItems.arcade_parts, 12, EnumComponentType.MACHINE_PARTS),
				new OreDictStack(OreDictManager.KEY_CIRCUIT_T2, 4),
				new ComparableStack(ModItems.arcade_parts, 4, EnumComponentType.MILITARY_PACKAGE)
			}));
		recipes.add(new ArcadeOffer(new ItemStack(ModBlocks.machine_catalytic_reformer),
			new AStack[] {
				new ComparableStack(ModItems.arcade_parts, 48, EnumComponentType.MACHINE_PARTS),
				new OreDictStack(OreDictManager.KEY_CIRCUIT_T3, 12),
				new ComparableStack(ModItems.arcade_parts, 16, EnumComponentType.MILITARY_PACKAGE),
				new ComparableStack(ModItems.part_generic, 8, ItemGenericPart.EnumPartType.LDE)
			}));
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

		recipes.add(new ArcadeOffer(product, cost, extraReq));
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

		public ArcadeOffer(ItemStack product, AStack[] cost){
			this(product, cost, null);
		}

		public ArcadeOffer(ItemStack product, AStack[] cost, Requirement extraReq){
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
