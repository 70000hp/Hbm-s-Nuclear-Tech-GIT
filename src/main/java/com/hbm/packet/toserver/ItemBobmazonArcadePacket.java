package com.hbm.packet.toserver;

import com.hbm.entity.missile.EntityBobmazon;
import com.hbm.inventory.recipes.BobmazonArcadeOffers;
import static com.hbm.inventory.recipes.BobmazonArcadeOffers.*;
import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemBobmazonArcade;
import com.hbm.lib.ModDamageSource;
import com.hbm.util.InventoryUtil;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.Random;

public class ItemBobmazonArcadePacket implements IMessage {

	int offer;

	public ItemBobmazonArcadePacket() { }

	public ItemBobmazonArcadePacket(EntityPlayer player, BobmazonArcadeOffers.ArcadeOffer offer) {
		if(player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.bobmazon_arcade)
			this.offer = BobmazonArcadeOffers.recipes.indexOf(offer);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		offer = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(offer);
	}

	public static class Handler implements IMessageHandler<ItemBobmazonArcadePacket, IMessage> {

		@Override
		public IMessage onMessage(ItemBobmazonArcadePacket m, MessageContext ctx) {

			EntityPlayerMP p = ctx.getServerHandler().playerEntity;
			World world = p.worldObj;

			BobmazonArcadeOffers.ArcadeOffer offer = null;
			if(p.getHeldItem() != null && p.getHeldItem().getItem() == ModItems.bobmazon_arcade) offer = BobmazonArcadeOffers.recipes.get(m.offer);
			if(offer == null) {
				p.addChatMessage(new ChatComponentText("[BOBMAZON] There appears to be a mismatch between the offer you have requested and the offers that exist."));
				p.addChatMessage(new ChatComponentText("[BOBMAZON] Engaging fail-safe..."));
				p.attackEntityFrom(ModDamageSource.nuclearBlast, 1000);
				p.motionY = 2.0D;
				return null;
			}

			ItemStack stack = BobmazonArcadeOffers.recipes.get(m.offer).product;

			if(p.capabilities.isCreativeMode || InventoryUtil.doesPlayerHaveAStacks(p, Arrays.asList(offer.cost), true)) {
				Random rand = world.rand;
				EntityBobmazon bob = new EntityBobmazon(world);
				bob.posX = p.posX + rand.nextGaussian() * 5;
				bob.posY = 300;
				bob.posZ = p.posZ + rand.nextGaussian() * 5;
				bob.payload = stack.copy();

				world.spawnEntityInWorld(bob);

			} else {
				p.addChatMessage(new ChatComponentText("[BOBMAZON] Not enough resources!"));
			}

			return null;
		}

	}
}
