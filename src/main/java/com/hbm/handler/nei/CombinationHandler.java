package com.hbm.handler.nei;

import java.awt.Rectangle;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.gui.GUIFurnaceCombo;
import com.hbm.inventory.recipes.CombinationRecipes;

public class CombinationHandler extends NEIUniversalHandler {

	public CombinationHandler() {
		super(ModBlocks.furnace_combination.getLocalizedName(), ModBlocks.furnace_combination, CombinationRecipes.getRecipes());
	}

	@Override
	public String getKey() {
		return "ntmCombination";
	}

	@Override
	public void loadTransferRects() {
		super.loadTransferRects();
		//the offsets I only found via painful trial and error idfk how this works
		transferRectsGui.add(new RecipeTransferRect(new Rectangle(70 - 5, 47 - 11, 18, 18), "ntmCombination"));
		guiGui.add(GUIFurnaceCombo.class);
		RecipeTransferRectHandler.registerRectsToGuis(guiGui, transferRectsGui);
	}
}
