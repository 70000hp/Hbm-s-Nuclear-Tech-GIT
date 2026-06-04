package com.hbm.items.special;

import static com.hbm.items.special.ItemBedrockOreNew.ProcessingGrade.*;
import static com.hbm.items.special.ItemBedrockOreNew.ProcessingTrait.*;
import static com.hbm.items.special.ItemBedrockOreNew.OreRichness.*;


import java.util.List;
import java.util.Locale;

import com.hbm.items.ModItems;
import com.hbm.util.EnumUtil;
import com.hbm.util.i18n.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.NTMMaterial;
import com.hbm.inventory.material.NTMMaterial.SmeltingBehavior;
import com.hbm.lib.RefStrings;
import com.hbm.render.icon.RGBMutatorInterpolatedComponentRemap;
import com.hbm.render.icon.TextureAtlasSpriteMutatable;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

public class ItemBedrockOreNew extends Item {

	public IIcon[] icons = new IIcon[BedrockOreType.values().length * ProcessingGrade.values().length];
	public IIcon[] overlays = new IIcon[ProcessingTrait.values().length];

	public ItemBedrockOreNew() {
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {


		if(reg instanceof TextureMap) {
			TextureMap map = (TextureMap) reg;

			for(int i = 0; i < BedrockOreType.values().length; i++) {
				BedrockOreType type = BedrockOreType.values()[i];
				for(int j = 0; j < type.traits.length; j++) {
					ProcessingGrade grade = ProcessingGrade.values()[j];
					String placeholderName = RefStrings.MODID + ":bedrock_ore_new_" + grade.prefix + "_" + type.suffix + "-" + (i * BedrockOreType.values().length + j);
					TextureAtlasSpriteMutatable mutableIcon = new TextureAtlasSpriteMutatable(placeholderName, new RGBMutatorInterpolatedComponentRemap(0xFFFFFF, 0x505050, type.light, type.dark));
					map.setTextureEntry(placeholderName, mutableIcon);
					this.icons[i * BedrockOreType.values().length + j] = mutableIcon;
				}
			}
		}

		for(int i = 0; i < overlays.length; i++) {
			ProcessingTrait trait = ProcessingTrait.values()[i];
			overlays[i] = reg.registerIcon(RefStrings.MODID + ":bedrock_ore_overlay." + trait.name().toLowerCase(Locale.US));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {

		for(int i = 0; i < BedrockOreType.values().length; i++) {
			BedrockOreType type = BedrockOreType.values()[i];
			for(int j = 0; j < type.traits.length; j++) {
				ProcessingGrade grade = ProcessingGrade.values()[j];
				list.add(this.make(grade, type));
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public int getRenderPasses(int metadata) {
		return 1 + this.getGrade(metadata).traits.length;
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int meta, int pass) {
		if(pass == 0) return this.getIconFromDamage(meta);
		return this.overlays[this.getGrade(meta).traits[pass - 1].ordinal()];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		int icon = this.getGrade(meta).ordinal() * BedrockOreType.values().length + this.getType(meta).ordinal();
		return icons[Math.abs(icon % icons.length)];
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		int meta = stack.getItemDamage();
		String type = StatCollector.translateToLocalFormatted(this.getUnlocalizedNameInefficiently(stack) + ".type." + this.getType(meta).suffix + ".name");
		return StatCollector.translateToLocalFormatted(this.getUnlocalizedNameInefficiently(stack) + ".grade." + this.getGrade(meta).name().toLowerCase(Locale.US) + ".name", type);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {

		for(ProcessingTrait trait : this.getGrade(stack.getItemDamage()).traits) {
			list.add(I18nUtil.resolveKey(this.getUnlocalizedNameInefficiently(stack) + ".trait." + trait.name().toLowerCase(Locale.US)));
		}
	}

	public static class BedrockOreOutput {
		public NTMMaterial mat;
		public int amount;
		public BedrockOreOutput(NTMMaterial mat, int amount) {
			this.mat = mat;
			this.amount = amount;
		}
	}

	/*public static enum BedrockOreType {
		//												primary									sulfuric															solvent																		radsolvent
		HEMATITE(	0xFFFFFF, 0x353535, "light",	MAT_IRON, 9),
		CHALCOPYRITE(	0x868686, 0x000000, "heavy",	MAT_TUNGSTEN, 9),
		BAUXITE(		0xE6E6B6, 0x1C1C00, "rare",		,	),
		WOLFRAMITE(		0xC1C7BD, 0x2B3227, "actinide",	MAT_URANIUM, 4),
		PITCHBLENDE(		0xAFAFAF, 0x0F0F0F, "nonmetal",	MAT_COAL, 9),		,
		CRYSTALLINE(	0xE2FFFA, 0x1E8A77, "crystal",	MAT_REDSTONE, 9),	);
		//sediment

		public int light;
		public int dark;
		public String suffix;
		public BedrockOreOutput primary1, primary2;
		public BedrockOreOutput byproductAcid1, byproductAcid2, byproductAcid3;
		public BedrockOreOutput byproductSolvent1, byproductSolvent2, byproductSolvent3;
		public BedrockOreOutput byproductRad1, byproductRad2, byproductRad3;

		private BedrockOreType(int light, int dark, String suffix, BedrockOreOutput p1, BedrockOreOutput p2, BedrockOreOutput bA1, BedrockOreOutput bA2, BedrockOreOutput bA3, BedrockOreOutput bS1, BedrockOreOutput bS2, BedrockOreOutput bS3, BedrockOreOutput bR1, BedrockOreOutput bR2, BedrockOreOutput bR3) {
			this.light = light;
			this.dark = dark;
			this.suffix = suffix;
			this.primary1 = p1; this.primary2 = p2;
			this.byproductAcid1 = bA1; this.byproductAcid2 = bA2; this.byproductAcid3 = bA3;
			this.byproductSolvent1 = bS1; this.byproductSolvent2 = bS2; this.byproductSolvent3 = bS3;
			this.byproductRad1 = bR1; this.byproductRad2 = bR2; this.byproductRad3 = bR3;
		}
	}*/

	public enum BedrockOreType {
		//												primary									sulfuric															solvent																		radsolvent
		HEMATITE(	 0xFFFFFF, 0x353535, "HEMATITE",     CRUSHED, FINE, LEACHED_REGULAR, FROTHED, CONCENTRATE),
		PYRITE(	     0xFFFFFF, 0x353535, "PYRITE",       CRUSHED, FINE, LEACHED_REGULAR, FROTHED, ROASTED, CONCENTRATE),
		CHALCOPYRITE(0x868686, 0x000000, "CHALCOPYRITE", CRUSHED, FINE, LEACHED_REGULAR, FROTHED, ROASTED, CONCENTRATE),
		MALACHITE(   0x868686, 0x000000, "MALACHITE",    CRUSHED, FINE, LEACHED_REGULAR, FROTHED, ROASTED, CONCENTRATE),
		BAUXITE(	 0xE6E6B6, 0x1C1C00, "BAUXITE",      CRUSHED, FINE, LEACHED_NAOH, FROTHED, CONCENTRATE),
		GALENA( 	 0xE6E6B6, 0x1C1C00, "GALENA",       CRUSHED, FINE, LEACHED_REGULAR, FROTHED, ROASTED, CONCENTRATE),
		WOLFRAMITE(	 0xC1C7BD, 0x2B3227, "WOLFRAMITE",   CRUSHED, FINE, LEACHED_REGULAR, FROTHED, CONCENTRATE),
		PITCHBLENDE( 0xAFAFAF, 0x0F0F0F, "PITCHBLENDE",  CRUSHED, FINE, LEACHED_REGULAR, FROTHED, CONCENTRATE),
		FLUORITE(    0xE2FFFA, 0x1E8A77, "FLUORITE",     CRUSHED, FINE),
		COAL(        0xE2FFFA, 0x1E8A77, "COAL",         CRUSHED, FINE),
		LIMESTONE(   0xE2FFFA, 0x1E8A77, "LIMESTONE",    CRUSHED, FINE);
		//sediment

		public int light;
		public int dark;
		public String suffix;
		public ProcessingGrade[] traits;

		private BedrockOreType(int light, int dark, String suffix, ProcessingGrade... traits) {
			this.light = light;
			this.dark = dark;
			this.suffix = suffix;
			this.traits = traits;

		}
	}

	public static MaterialStack toFluid(BedrockOreOutput o, double amount) {
		if(o.mat != null && o.mat.smeltable == SmeltingBehavior.SMELTABLE) {
			return new MaterialStack(o.mat, (int) Math.ceil(MaterialShapes.FRAGMENT.q(o.amount) * amount));
		}
		return null;
	}

	public static ItemStack extract(BedrockOreOutput o, double amount) {
		return new ItemStack(ModItems.bedrock_ore_fragment, Math.min((int) Math.ceil(o.amount * amount), 64), o.mat.id);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass) {
		if(pass != 0) return 0xFFFFFF;
		ProcessingGrade grade = this.getGrade(stack.getItemDamage());
		return grade.tint;
	}

	public static final int none = 0xFFFFFF;
	public static final int roasted = 0xCFCFCF;
	public static final int leached = 0xC3A2A2;
	public static final int washed = 0xDBE2CB;

	public enum ProcessingTrait {
		CRUSH(RICH, MEDIUM, POOR),
		CENTRIFUGE(RICH, MEDIUM, POOR),
		FROTH(RICH, MEDIUM, POOR),
		MISC(RICH, MEDIUM, POOR),
		ROAST(MEDIUM, POOR),
		ELECTRO(RICH, MEDIUM, POOR),
		LEACH_SULF(MEDIUM),
		LEACH_AMMONIA(POOR),
		LEACH_NAOH(MEDIUM);

		public final OreRichness[] richness;

		ProcessingTrait(OreRichness... grades){
			richness = grades;
		}
	}

	public enum OreRichness {
		RICH,
		MEDIUM,
		POOR
	}

	public enum ProcessingGrade {
		NONE(none, "none", MISC),//from the slopper
		CRUSHED(none, "crush", CRUSH),
		FINE(none, "fine", CENTRIFUGE),
		FROTHED(none, "frothed", FROTH),
		ROASTED(roasted, "roasted", "fine", ROAST),
		LEACHED_REGULAR(leached, "leached", "fine", LEACH_SULF, LEACH_AMMONIA),
		LEACHED_NAOH(leached, "leached", "fine", LEACH_NAOH),
		CONCENTRATE(washed, "concentrate", MISC),
		SPECIAL(washed, "special", MISC);
						//endpoint for primary, recycling
				//rad endpoint

		public int tint;
		public String prefix;
		public String textureName;
		public ProcessingTrait[] traits;

		ProcessingGrade(int tint, String prefix, ProcessingTrait... traits) {
			this.tint = tint;
			this.prefix = prefix;
			this.textureName = prefix;
			this.traits = traits;
		}

		ProcessingGrade(int tint, String prefix, String textureName, ProcessingTrait... traits) {
			this.tint = tint;
			this.prefix = prefix;
			this.textureName = textureName;
			this.traits = traits;
		}
	}

	public static ItemStack make(ProcessingGrade grade, BedrockOreType type) {
		return make(grade, type, 1);
	}

	public static ItemStack make(ProcessingGrade grade, BedrockOreType type, int amount) {
		return new ItemStack(ModItems.bedrock_ore, amount, grade.ordinal() << 4 | type.ordinal());
	}

	public ProcessingGrade getGrade(int meta) {
		return EnumUtil.grabEnumSafely(ProcessingGrade.class, meta >> 4);
	}

	public BedrockOreType getType(int meta) {
		return EnumUtil.grabEnumSafely(BedrockOreType.class, meta & 15);
	}
}
