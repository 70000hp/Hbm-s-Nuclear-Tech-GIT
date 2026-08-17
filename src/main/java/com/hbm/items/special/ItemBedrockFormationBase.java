package com.hbm.items.special;

import java.util.List;
import java.util.Random;

import com.hbm.items.special.ItemBedrockOreNew.BedrockOreType;
import com.hbm.items.tool.ItemOreDensityScanner;
import com.hbm.main.MainRegistry;

import com.hbm.util.Tuple.*;
import com.hbm.world.noise.VoronoiNoiseGen;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

import static com.hbm.items.special.ItemBedrockOreNew.BedrockOreType.*;
public class ItemBedrockFormationBase extends Item {


	public ItemBedrockFormationBase() {
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		ItemStack ore = new ItemStack(item);
		EntityPlayer player = MainRegistry.proxy.me();
		if (player != null){
			int x = (int) Math.floor(player.posX);
			int z = (int) Math.floor(player.posZ);

			int formationID = getFormation(x, z);
			BedrockFormationType type = BedrockFormationType.values()[formationID];
			Pair<BedrockOreType, Double>[] composition = type.composition;

			setFormationType(ore, formationID);
			setOreAmount(ore, (int) Math.floor(player.posX), (int) Math.floor(player.posZ), 1D, composition);
		}
		list.add(ore);

	}

	public static BedrockFormationType getFormationType(ItemStack stack){
		if(!stack.hasTagCompound()) stack.stackTagCompound = new NBTTagCompound();
		NBTTagCompound data = stack.getTagCompound();

		return BedrockFormationType.values()[data.getInteger("formationID")];
	}

	public static void setFormationType(ItemStack stack, int formationID) {
		if(!stack.hasTagCompound()) stack.stackTagCompound = new NBTTagCompound();
		NBTTagCompound data = stack.getTagCompound();

		data.setInteger("formation", formationID);
	}

	public static double getOreAmount(ItemStack stack, BedrockOreType type) {
		if(!stack.hasTagCompound()) return 0;
		NBTTagCompound data = stack.getTagCompound();
		return data.getDouble(type.suffix);
	}

	public static void setOreAmount(ItemStack stack, int x, int z, double mult, Pair<BedrockOreType, Double>[] composition) {
		if(!stack.hasTagCompound()) stack.stackTagCompound = new NBTTagCompound();
		NBTTagCompound data = stack.getTagCompound();

		for(Pair<BedrockOreType, Double> type : composition) {
			double oreLevel = (getOreLevel(x, z, type.getKey()) * mult) - type.getValue();
			if(oreLevel > 0.2)
				data.setDouble(type.getKey().suffix, oreLevel);
		}
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {

		for(BedrockOreType type : BedrockOreType.values()) {
			double amount = getOreAmount(stack, type);
			String typeName = StatCollector.translateToLocalFormatted("item.bedrock_ore.type." + type.suffix + ".name");
			list.add(typeName + ": " + ((int) (amount * 100)) / 100D + " (" + ItemOreDensityScanner.getColor(amount) + StatCollector.translateToLocalFormatted(ItemOreDensityScanner.translateDensity(amount)) + EnumChatFormatting.GRAY + ")");
		}
	}

	/**at most 8 distinct richness maps, different ore formations use the same map
	 * if there are ever more than 8 ores per single formation feel free to bonk me in the head**/
	private static final int max_bedrock_ores = 8;
	private static VoronoiNoiseGen formations;
	private static NoiseGeneratorPerlin[] ores = new NoiseGeneratorPerlin[max_bedrock_ores];
	private static NoiseGeneratorPerlin level;

	/**takes the ore level from the ground, not the ores, this is the ore level they should have - adjustments like drill tier*/
	public static double getOreLevel(int x, int z, BedrockOreType type) {

		if(level == null) level = new NoiseGeneratorPerlin(new Random(2114043), 4);
		if(ores[type.ordinal()] == null) ores[type.ordinal()] = new NoiseGeneratorPerlin(new Random(2082127 + type.ordinal()), 4);

		double scale = 0.01D;

		return MathHelper.clamp_double(Math.abs(level.func_151601_a(x * scale, z * scale) * ores[type.ordinal()].func_151601_a(x * scale, z * scale)) * 0.05, 0, 2);
	}

	public static int getFormation(int x, int z) {

		if(formations == null) formations  = new VoronoiNoiseGen(8, 2114043);
		double[] noiseInfo = formations.sampleVoronoi(x,z);
		return (int) (Math.abs(noiseInfo[0] + noiseInfo[1] * 4) % (BedrockFormationType.values().length - 1));
	}

	public static double getOres(ItemStack stack, BedrockOreType type) {
		if(!stack.hasTagCompound()) return 0;
		NBTTagCompound data = stack.getTagCompound();
		return data.getDouble(type.suffix);
	}

	public enum BedrockFormationType {
		//												primary									sulfuric															solvent																		radsolvent
		OXIDE(	     0xFFFFFF,
			"form.oxide",
			new Pair<>(HEMATITE, 0.2),
			new Pair<>(MALACHITE, 0.0),
			new Pair<>(BAUXITE, -0.5)),

		HYDROTHERMAL(	 0xFFFFFF, "form.hydrothermal",   new Pair<>(CHALCOPYRITE, 0.0),  new Pair<>(PYRITE, 0.0),  new Pair<>(GALENA, 0.5)),
		SKARN(       0x868686, "form.skarn",    new Pair<>(WOLFRAMITE, 0.5),  new Pair<>(PITCHBLENDE, -1.0)),
		SEDIMENTARY( 0x868686, "form.sedimentary",    new Pair<>(COAL, 1.5), new Pair<>(FLUORITE, -1.5), new Pair<>(LIMESTONE, 0.0));

		public final int color;
		public final String suffix;
		public final Pair<BedrockOreType, Double>[] composition;

		/**the pair's integer is for adjusting ore rates, number is added to richness value**/
		@SafeVarargs
		BedrockFormationType(int color, String suffix, Pair<BedrockOreType, Double>... composition) {
			this.color = color;
			this.suffix = suffix;
			this.composition = composition;

		}
	}
}
