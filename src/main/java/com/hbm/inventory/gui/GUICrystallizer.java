package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerCrystallizer;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineCrystallizer;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUICrystallizer extends GuiInfoContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_crystallizer_new.png");
	private TileEntityMachineCrystallizer acidomatic;

	public GUICrystallizer(InventoryPlayer invPlayer, TileEntityMachineCrystallizer acidomatic) {
		super(new ContainerCrystallizer(invPlayer, acidomatic));
		this.acidomatic = acidomatic;

		this.xSize = 176;
		this.ySize = 204;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 152, guiTop + 18, 16, 52, acidomatic.power, acidomatic.maxPower);
		acidomatic.inputTank.renderTankInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 18, 16, 52);
		acidomatic.outputTank.renderTankInfo(this, mouseX, mouseY, guiLeft + 116, guiTop + 18, 16, 52);

		String[] upgradeText = new String[4];
		upgradeText[0] = I18nUtil.resolveKey("desc.gui.upgrade");
		upgradeText[1] = I18nUtil.resolveKey("desc.gui.upgrade.speed");
		upgradeText[2] = I18nUtil.resolveKey("desc.gui.upgrade.effectiveness");
		upgradeText[3] = I18nUtil.resolveKey("desc.gui.upgrade.overdrive");
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 90, guiTop + 76, 8, 8, guiLeft + 200, guiTop + 45, upgradeText);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.acidomatic.hasCustomInventoryName() ? this.acidomatic.getInventoryName() : I18n.format(this.acidomatic.getInventoryName());
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		int i = (int)(acidomatic.power * 52 / acidomatic.maxPower);
		drawTexturedModalRect(guiLeft + 152, guiTop + 70 - i, 176, 64 - i, 16, i);

		if(acidomatic.module.progress > 0) {
			int j = (int) Math.ceil(28 * acidomatic.module.progress);
			drawTexturedModalRect(guiLeft + 57, guiTop + 47, 176, 0, j, 12);
		}

		this.drawInfoPanel(guiLeft + 90, guiTop + 76, 8, 8, 8);

		acidomatic.inputTank.renderTank(guiLeft + 8, guiTop + 70, this.zLevel, 16, 52);
		acidomatic.outputTank.renderTank(guiLeft + 116, guiTop + 70, this.zLevel, 16, 52);
	}
}
