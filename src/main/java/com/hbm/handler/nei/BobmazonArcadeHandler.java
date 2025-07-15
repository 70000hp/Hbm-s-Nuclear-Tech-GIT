package com.hbm.handler.nei;

import com.hbm.inventory.recipes.BobmazonArcadeOffers;
import com.hbm.items.ModItems;
import com.hbm.util.i18n.I18nUtil;

public class BobmazonArcadeHandler extends NEIUniversalHandler {

	public BobmazonArcadeHandler() {
		super(I18nUtil.format(ModItems.bobmazon_arcade.getUnlocalizedName() + ".name"), ModItems.bobmazon_arcade, BobmazonArcadeOffers.getRecipes());
	}

	@Override
	public String getKey() {
		return "ntmBobmazon";
	}
}
