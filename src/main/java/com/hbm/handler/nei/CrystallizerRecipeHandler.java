package com.hbm.handler.nei;

import java.awt.Rectangle;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.gui.GUICrystallizer;
import com.hbm.inventory.recipes.CrystallizerRecipes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class CrystallizerRecipeHandler extends NEIGenericRecipeHandler {

	public CrystallizerRecipeHandler() {super(ModBlocks.machine_crystallizer.getLocalizedName(), CrystallizerRecipes.INSTANCE, ModBlocks.machine_crystallizer);}

	@Override public String getRecipeID() { return "ntmCrystallizer"; }
	/*
	@Override
	public void drawExtras(int recipe) {

		RecipeSet rec = (RecipeSet) this.arecipes.get(recipe);

		CrystallizerRecipe cRecipe = CrystallizerRecipes.getOutput(rec.input[1].item, Fluids.fromID(rec.input[0].item.getItemDamage()));

		if(cRecipe != null && cRecipe.productivity > 0) {
			FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
			String momentum = "Effectiveness: +" + Math.min((int) (cRecipe.productivity * 100), 99) + "% per level";
			int side = 8;
			fontRenderer.drawString(momentum, side, 52, 0x404040);
		}
	}*/
}
