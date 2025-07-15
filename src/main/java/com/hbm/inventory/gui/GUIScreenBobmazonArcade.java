package com.hbm.inventory.gui;

import com.hbm.inventory.recipes.BobmazonArcadeOffers.*;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.ItemBobmazonArcadePacket;
import com.hbm.packet.toserver.ItemBobmazonPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GUIScreenBobmazonArcade extends GuiScreen {

	protected static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/arcade/gui_bobmazon_arcade.png");
	protected int xSize = 176 + 41;
	protected int ySize = 229;
	protected int guiLeft;
	protected int guiTop;
	int currentPage = 0;
	List<ArcadeOffer> offers;
	List<FolderButton> buttons = new ArrayList<FolderButton>();
	private final EntityPlayer player;

	public GUIScreenBobmazonArcade(EntityPlayer player, List<ArcadeOffer> offers) {
		this.player = player;
		this.offers = offers;
	}

	int getPageCount() {
		return (int) Math.ceil((offers.size() - 1) / 3);
	}

	@Override
	public void updateScreen() {
		if(currentPage < 0)
			currentPage = 0;
		if(currentPage > getPageCount())
			currentPage = getPageCount();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		this.drawDefaultBackground();
		this.drawGuiContainerBackgroundLayer(f, mouseX, mouseY);
		GL11.glDisable(GL11.GL_LIGHTING);
		this.drawGuiContainerForegroundLayer(mouseX, mouseY);
		GL11.glEnable(GL11.GL_LIGHTING);
	}

	@Override
	public void initGui() {
		super.initGui();
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;

		updateButtons();
	}

	protected void updateButtons() {

		if(!buttons.isEmpty())
			buttons.clear();

		for(int i = currentPage * 3; i < Math.min(currentPage * 3 + 3, offers.size()); i++) {
			buttons.add(new FolderButton(guiLeft + 34, guiTop + 35 + (54 * (int) Math.floor(i)) - currentPage * 3 * 54, offers.get(i)));
		}

		if(currentPage != 0)
			buttons.add(new FolderButton(guiLeft + 25 - 18, guiTop + 26 + (27 * 3), 1, "Previous"));
		if(currentPage != getPageCount())
			buttons.add(new FolderButton(guiLeft + 25 + (27 * 4) + 18 + 41, guiTop + 26 + (27 * 3), 2, "Next"));
	}

	@Override
	protected void mouseClicked(int i, int j, int k) {
		try {
			for(FolderButton b : buttons)
				if(b.isMouseOnButton(i, j))
					b.executeAction();
		} catch(Exception ex) {
			updateButtons();
		}
	}

	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {

		this.fontRendererObj.drawString(I18n.format((currentPage + 1) + "/" + (getPageCount() + 1)),
				guiLeft + this.xSize / 2 - this.fontRendererObj.getStringWidth(I18n.format((currentPage + 1) + "/" + (getPageCount() + 1))) / 2, guiTop + 215, 4210752);

		for(FolderButton b : buttons)
			if(b.isMouseOnButton(mouseX, mouseY))
				b.drawString(mouseX, mouseY);

		for(int d = currentPage * 3; d < Math.min(currentPage * 3 + 3, offers.size()); d++) {

			int x = guiLeft + 34;
			int y = guiTop + 53 + (54 * (int) Math.floor(d)) - currentPage * 3 * 54;
			ArcadeOffer offer = offers.get(d);

			for(int i = 0; i < offer.cost.length; i++) {
				if (x + i * 18 < mouseX && x + i * 18 + 18 > mouseX && y < mouseY && y + 18 > mouseY) {
					renderToolTip(offer.cost[i].extractForCyclingDisplay(20), mouseX, mouseY);
				}
			}
		}
	}

	protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		for(FolderButton b : buttons)
			b.drawButton(b.isMouseOnButton(mouseX, mouseY));
		for(FolderButton b : buttons)
			b.drawIcon(b.isMouseOnButton(mouseX, mouseY));

		for(int d = currentPage * 3; d < Math.min(currentPage * 3 + 3, offers.size()); d++) {
			drawRequirement(offers.get(d), guiLeft + 34, guiTop + 53 + (54 * (int) Math.floor(d)) - currentPage * 3 * 54);
		}
	}

	@Override
	protected void keyTyped(char p_73869_1_, int p_73869_2_) {
		if(p_73869_2_ == 1 || p_73869_2_ == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
			this.mc.thePlayer.closeScreen();
		}
	}

	protected void drawRequirement(ArcadeOffer offer, int x, int y) {
		ItemStack product = offer.product;

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);

		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		this.drawTexturedModalRect(x + 19, y - 12, 176 + 41, 62, 39, 8);
		this.drawTexturedModalRect(x + 19, y - 12, 176 + 41, 54, 4, 8);
		for(int i = 1; i < offer.cost.length; i++)
			this.drawTexturedModalRect(x + i * 18, y, 34, 53, 18, 18);

		String count = "";
		if(product.stackSize > 1)
			count = " x" + product.stackSize;

		GL11.glPushMatrix();
		float scale = 0.5F;
		GL11.glScalef(scale, scale, scale);
		this.fontRendererObj.drawString(I18n.format(product.getDisplayName()) + count, (int) ((x + 20) / scale), (int) ((y - 16) / scale), 4210752);
		GL11.glPopMatrix();

		GL11.glEnable(GL11.GL_LIGHTING);
		for(int i = 0; i < offer.cost.length; i++) {
			ItemStack stack = offer.cost[i].extractForCyclingDisplay(20);

			itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), stack, x + 1 + i * 18, y + 1);
			itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), stack, x + 1 + i * 18, y + 1, stack.stackSize > 1 ? (stack.stackSize + "") : null);

		}

	}

	class FolderButton {

		int xPos;
		int yPos;
		// 0: regular, 1: prev, 2: next
		int type;
		String info;
		ArcadeOffer offer;

		public FolderButton(int x, int y, int t, String i) {
			xPos = x;
			yPos = y;
			type = t;
			info = i;
		}

		public FolderButton(int x, int y, ArcadeOffer offer) {
			xPos = x;
			yPos = y;
			type = 0;
			this.offer = offer;
		}

		public void updateButton(int mouseX, int mouseY) {
		}

		public boolean isMouseOnButton(int mouseX, int mouseY) {
			return xPos <= mouseX && xPos + 18 > mouseX && yPos < mouseY && yPos + 18 >= mouseY;
		}

		public void drawButton(boolean b) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
			drawTexturedModalRect(xPos, yPos, b ? 176 + 41 + 18 : 176 + 41, type == 1 ? 18 : (type == 2 ? 36 : 0), 18, 18);
		}

		public void drawIcon(boolean b) {
			try {
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glEnable(GL12.GL_RESCALE_NORMAL);
				if(offer != null) {
					RenderHelper.enableGUIStandardItemLighting();
					ItemStack stack = offer.product;

					itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), stack, xPos + 1, yPos + 1);
					itemRender.renderItemOverlayIntoGUI(fontRendererObj, mc.getTextureManager(), stack, xPos + 1, yPos + 1, stack.stackSize > 1 ? (stack.stackSize + "") : null);
				}
			} catch(Exception x) {
			}
		}

		public void drawString(int x, int y) {
			if(info == null || info.isEmpty())
				return;

			func_146283_a(Arrays.asList(new String[] { info }), x, y);
		}

		public void executeAction() {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			if(type == 0) {
				PacketDispatcher.wrapper.sendToServer(new ItemBobmazonArcadePacket(player, offer));
			} else if(type == 1) {
				if(currentPage > 0)
					currentPage--;
				updateButtons();
			} else if(type == 2) {
				if(currentPage < getPageCount())
					currentPage++;
				updateButtons();
			}
		}
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	public enum Requirement {

		NONE(AchievementList.openInventory),
		STEEL(MainRegistry.achBlastFurnace),
		ASSEMBLY(MainRegistry.achAssembly),
		CHEMICS(MainRegistry.achChemplant),
		OIL(MainRegistry.achDesh),
		NUCLEAR(MainRegistry.achTechnetium),
		HIDDEN(MainRegistry.bobHidden);

		private Requirement(Achievement achievement) {
			this.achievement = achievement;
		}

		public boolean fullfills(EntityPlayerMP player) {

			return player.func_147099_x().hasAchievementUnlocked(achievement);
		}

		public Achievement achievement;
	}

}
