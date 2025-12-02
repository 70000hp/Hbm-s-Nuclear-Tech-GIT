package com.hbm.main;

import com.google.common.collect.ImmutableList;
import com.hbm.blocks.BlockEnums.EnumStoneType;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockToolConversion;
import com.hbm.commands.*;
import com.hbm.config.*;
import com.hbm.crafting.RodRecipes;
import com.hbm.creativetabs.*;
import com.hbm.entity.EntityMappings;
import com.hbm.entity.logic.IChunkLoader;
import com.hbm.entity.mob.siege.SiegeTier;
import com.hbm.handler.*;
import com.hbm.handler.ae2.AE2CompatHandler;
import com.hbm.handler.imc.*;
import com.hbm.handler.microblocks.MicroBlocksCompatHandler;
import com.hbm.handler.neutron.NeutronHandler;
import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.hazard.HazardRegistry;
import com.hbm.inventory.FluidContainerRegistry;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.recipes.*;
import com.hbm.inventory.recipes.anvil.AnvilRecipes;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ItemEnums.EnumAchievementType;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.mods.XWeaponModManager;
import com.hbm.lib.HbmWorld;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.potion.HbmPotion;
import com.hbm.saveddata.satellites.Satellite;
import com.hbm.tileentity.TileMappings;
import com.hbm.tileentity.bomb.TileEntityLaunchPadBase;
import com.hbm.tileentity.bomb.TileEntityNukeCustom;
import com.hbm.tileentity.machine.rbmk.RBMKDials;
import com.hbm.util.*;
import com.hbm.world.biome.BiomeGenCraterBase;
import com.hbm.world.feature.BedrockOre;
import com.hbm.world.feature.OreCave;
import com.hbm.world.feature.OreLayer3D;
import com.hbm.world.feature.SchistStratum;
import com.hbm.world.generator.CellularDungeonFactory;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.Metadata;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.event.FMLInterModComms.IMCEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;
import cpw.mods.fml.common.event.FMLMissingMappingsEvent.MissingMapping;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatBasic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.LoadingCallback;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

@Mod(modid = RefStrings.MODID, name = RefStrings.NAME, version = RefStrings.VERSION)
public class MainRegistry {

	@Instance(RefStrings.MODID)
	public static MainRegistry instance;

	@SidedProxy(clientSide = RefStrings.CLIENTSIDE, serverSide = RefStrings.SERVERSIDE)
	public static ServerProxy proxy;

	@Metadata
	public static ModMetadata meta;

	public static Logger logger = LogManager.getLogger("HBM");

	// Tool Materials
	public static ToolMaterial tMatSchrab = EnumHelper.addToolMaterial("SCHRABIDIUM", 3, 10000, 50.0F, 100.0F, 200);
	public static ToolMaterial tMatHammmer = EnumHelper.addToolMaterial("SCHRABIDIUMHAMMER", 3, 0, 50.0F, 999999996F, 200);
	public static ToolMaterial tMatChainsaw = EnumHelper.addToolMaterial("CHAINSAW", 3, 1500, 50.0F, 22.0F, 0);
	public static ToolMaterial tMatSteel = EnumHelper.addToolMaterial("HBM_STEEL", 2, 500, 7.5F, 2.0F, 10);
	public static ToolMaterial tMatTitan = EnumHelper.addToolMaterial("HBM_TITANIUM", 3, 750, 9.0F, 2.5F, 15);
	public static ToolMaterial tMatAlloy = EnumHelper.addToolMaterial("HBM_ALLOY", 3, 2000, 15.0F, 5.0F, 5);
	public static ToolMaterial tMatCMB = EnumHelper.addToolMaterial("HBM_CMB", 3, 8500, 40.0F, 55F, 100);
	public static ToolMaterial tMatElec = EnumHelper.addToolMaterial("HBM_ELEC", 3, 0, 30.0F, 12.0F, 2);
	public static ToolMaterial tMatDesh = EnumHelper.addToolMaterial("HBM_DESH", 2, 0, 7.5F, 2.0F, 10);
	public static ToolMaterial tMatCobalt = EnumHelper.addToolMaterial("HBM_COBALT", 3, 750, 9.0F, 2.5F, 60);

	public static ToolMaterial enumToolMaterialSaw = EnumHelper.addToolMaterial("SAW", 2, 750, 2.0F, 3.5F, 25);
	public static ToolMaterial enumToolMaterialBat = EnumHelper.addToolMaterial("BAT", 0, 500, 1.5F, 3F, 25);
	public static ToolMaterial enumToolMaterialBatNail = EnumHelper.addToolMaterial("BATNAIL", 0, 450, 1.0F, 4F, 25);
	public static ToolMaterial enumToolMaterialGolfClub = EnumHelper.addToolMaterial("GOLFCLUB", 1, 1000, 2.0F, 5F, 25);
	public static ToolMaterial enumToolMaterialPipeRusty = EnumHelper.addToolMaterial("PIPERUSTY", 1, 350, 1.5F, 4.5F, 25);
	public static ToolMaterial enumToolMaterialPipeLead = EnumHelper.addToolMaterial("PIPELEAD", 1, 250, 1.5F, 3F, 25);

	public static ToolMaterial enumToolMaterialBottleOpener = EnumHelper.addToolMaterial("OPENER", 1, 250, 1.5F, 0.5F, 200);
	public static ToolMaterial enumToolMaterialSledge = EnumHelper.addToolMaterial("SHIMMERSLEDGE", 1, 0, 25.0F, 26F, 200);

	public static ToolMaterial enumToolMaterialMultitool = EnumHelper.addToolMaterial("MULTITOOL", 3, 5000, 25F, 5.5F, 25);

	// Armor Materials
	public static ArmorMaterial aMatSchrab = EnumHelper.addArmorMaterial("HBM_SCHRABIDIUM", 100, new int[] { 3, 8, 6, 3 }, 50);
	public static ArmorMaterial aMatEuph = EnumHelper.addArmorMaterial("HBM_EUPHEMIUM", 15000000, new int[] { 3, 8, 6, 3 }, 100);
	public static ArmorMaterial aMatHaz = EnumHelper.addArmorMaterial("HBM_HAZMAT", 60, new int[] { 2, 5, 4, 1 }, 5);
	public static ArmorMaterial aMatHaz2 = EnumHelper.addArmorMaterial("HBM_HAZMAT2", 60, new int[] { 2, 5, 4, 1 }, 5);
	public static ArmorMaterial aMatHaz3 = EnumHelper.addArmorMaterial("HBM_HAZMAT3", 60, new int[] { 2, 5, 4, 1 }, 5);
	public static ArmorMaterial aMatSteel = EnumHelper.addArmorMaterial("HBM_STEEL", 20, new int[] { 2, 6, 5, 2 }, 5);
	public static ArmorMaterial aMatAsbestos = EnumHelper.addArmorMaterial("HBM_ASBESTOS", 20, new int[] { 1, 4, 3, 1 }, 5);
	public static ArmorMaterial aMatTitan = EnumHelper.addArmorMaterial("HBM_TITANIUM", 25, new int[] { 3, 8, 6, 3 }, 9);
	public static ArmorMaterial aMatAlloy = EnumHelper.addArmorMaterial("HBM_ALLOY", 40, new int[] { 3, 8, 6, 3 }, 12);
	public static ArmorMaterial aMatPaa = EnumHelper.addArmorMaterial("HBM_PAA", 75, new int[] { 3, 8, 6, 3 }, 25);
	public static ArmorMaterial aMatCMB = EnumHelper.addArmorMaterial("HBM_CMB", 60, new int[] { 3, 8, 6, 3 }, 50);
	public static ArmorMaterial aMatAus3 = EnumHelper.addArmorMaterial("HBM_AUSIII", 375, new int[] { 2, 6, 5, 2 }, 0);
	public static ArmorMaterial aMatSecurity = EnumHelper.addArmorMaterial("HBM_SECURITY", 100, new int[] { 3, 8, 6, 3 }, 15);
	public static ArmorMaterial aMatCobalt = EnumHelper.addArmorMaterial("HBM_COBALT", 70, new int[] { 3, 8, 6, 3 }, 60);
	public static ArmorMaterial aMatStarmetal = EnumHelper.addArmorMaterial("HBM_STARMETAL", 150, new int[] { 3, 8, 6, 3 }, 100);
	public static ArmorMaterial aMatBismuth = EnumHelper.addArmorMaterial("HBM_BISMUTH", 100, new int[] { 3, 8, 6, 3 }, 100);

	// Creative Tabs

	public static CreativeTabs partsTab = new PartsTab(CreativeTabs.getNextID(), "tabParts");					// ingots, nuggets, wires, machine parts
	public static CreativeTabs controlTab = new ControlTab(CreativeTabs.getNextID(), "tabControl");				// items that belong in machines, fuels, etc
	public static CreativeTabs templateTab = new TemplateTab(CreativeTabs.getNextID(), "tabTemplate");			// templates, siren tracks
	public static CreativeTabs blockTab = new BlockTab(CreativeTabs.getNextID(), "tabBlocks");					// ore and mineral blocks
	public static CreativeTabs machineTab = new MachineTab(CreativeTabs.getNextID(), "tabMachine");				// machines, structure parts
	public static CreativeTabs nukeTab = new NukeTab(CreativeTabs.getNextID(), "tabNuke");						// bombs
	public static CreativeTabs missileTab = new MissileTab(CreativeTabs.getNextID(), "tabMissile");				// missiles, satellites
	public static CreativeTabs weaponTab = new WeaponTab(CreativeTabs.getNextID(), "tabWeapon");				// turrets, weapons, ammo
	public static CreativeTabs consumableTab = new ConsumableTab(CreativeTabs.getNextID(), "tabConsumable");	// drinks, kits, tools

	// Statistics
	public static StatBase statLegendary;
	public static StatBase statMines;
	public static StatBase statBullets;

	// Achievements
	public static Achievement achSacrifice;
	public static Achievement achImpossible;
	public static Achievement achTOB;
	public static Achievement achPotato;
	public static Achievement achC20_5;
	public static Achievement achFiend;
	public static Achievement achFiend2;
	public static Achievement achRadPoison;
	public static Achievement achRadDeath;
	public static Achievement achStratum;
	public static Achievement achOmega12;
	public static Achievement achSomeWounds;
	public static Achievement achSlimeball;
	public static Achievement achSulfuric;
	public static Achievement achGoFish;
	public static Achievement achNo9;
	public static Achievement achInferno;
	public static Achievement achRedRoom;
	public static Achievement bobHidden;
	public static Achievement horizonsStart;
	public static Achievement horizonsEnd;
	public static Achievement horizonsBonus;
	public static Achievement bossCreeper;
	public static Achievement bossMeltdown;
	public static Achievement bossMaskman;
	public static Achievement bossWorm;
	public static Achievement bossUFO;
	public static Achievement digammaSee;
	public static Achievement digammaFeel;
	public static Achievement digammaKnow;
	public static Achievement digammaKauaiMoho;
	public static Achievement digammaUpOnTop;

	public static Achievement achBurnerPress;
	public static Achievement achBlastFurnace;
	public static Achievement achAssembly;
	public static Achievement achSelenium;
	public static Achievement achChemplant;
	public static Achievement achConcrete;
	public static Achievement achPolymer;
	public static Achievement achDesh;
	public static Achievement achTantalum;
	public static Achievement achRedBalloons;
	public static Achievement achManhattan;
	public static Achievement achGasCent;
	public static Achievement achCentrifuge;
	public static Achievement achFOEQ;
	public static Achievement achSoyuz;
	public static Achievement achSpace;
	public static Achievement achSchrab;
	public static Achievement achAcidizer;
	public static Achievement achRadium;
	public static Achievement achTechnetium;
	public static Achievement achZIRNOXBoom;
	public static Achievement achChicagoPile;
	public static Achievement achSILEX;
	public static Achievement achWatz;
	public static Achievement achWatzBoom;
	public static Achievement achRBMK;
	public static Achievement achRBMKBoom;
	public static Achievement achBismuth;
	public static Achievement achBreeding;
	public static Achievement achFusion;

	public static int generalOverride = 0;
	public static int polaroidID = 1;

	public static long startupTime = 0;
	public static File configDir;
	public static File configHbmDir;

	public Random rand = new Random();

	@EventHandler
	public void PreLoad(FMLPreInitializationEvent PreEvent) {
		CrashHelper.init();

		startupTime = System.currentTimeMillis();
		configDir = PreEvent.getModConfigurationDirectory();
		configHbmDir = new File(configDir.getAbsolutePath() + File.separatorChar + "hbmConfig");

		if(!configHbmDir.exists()) configHbmDir.mkdir();
		Identity.init(configDir);

		logger.info("Let us celebrate the fact that the logger finally works again!");

		// Reroll Polaroid
		if(generalOverride > 0 && generalOverride < 19) {
			polaroidID = generalOverride;
		} else {
			polaroidID = rand.nextInt(18) + 1;
			while(polaroidID == 4 || polaroidID == 9)
				polaroidID = rand.nextInt(18) + 1;
		}

		ShadyUtil.test();
		loadConfig(PreEvent);
		HbmPotion.init();

		/* For whichever fucking reason, replacing the bolt items with a bolt autogen broke all autogen items, most likely due to the load order.
		 * This "fix" just makes sure that the material system is loaded first no matter what. */
		Mats.MAT_STONE.getUnlocalizedName();
		Fluids.init();
		proxy.registerPreRenderInfo();
		ModBlocks.mainRegistry();
		ModItems.mainRegistry();
		proxy.registerRenderInfo();
		HbmWorld.mainRegistry();
		GameRegistry.registerFuelHandler(new FuelHandler());
		BulletConfigSyncingUtil.loadConfigsForSync();
		CellularDungeonFactory.init();
		Satellite.register();
		HTTPHandler.loadStats();
		CraftingManager.mainRegistry();
		SiegeTier.registerTiers();
		HazardRegistry.registerItems();
		HazardRegistry.registerTrafos();
		XWeaponModManager.init();

		OreDictManager oreMan = new OreDictManager();
		MinecraftForge.EVENT_BUS.register(oreMan); //OreRegisterEvent
		OreDictManager.registerGroups(); //important to run first
		OreDictManager.registerOres();

		if(WorldConfig.enableCraterBiomes) BiomeGenCraterBase.initDictionary();
		//BiomeGenNoMansLand.initDictionary();

		aMatSchrab.customCraftingMaterial = ModItems.ingot_schrabidium;
		aMatHaz.customCraftingMaterial = ModItems.hazmat_cloth;
		aMatHaz2.customCraftingMaterial = ModItems.hazmat_cloth_red;
		aMatHaz3.customCraftingMaterial = ModItems.hazmat_cloth_grey;
		aMatTitan.customCraftingMaterial = ModItems.ingot_titanium;
		aMatSteel.customCraftingMaterial = ModItems.ingot_steel;
		aMatAsbestos.customCraftingMaterial = ModItems.asbestos_cloth;
		aMatAlloy.customCraftingMaterial = ModItems.ingot_advanced_alloy;
		aMatPaa.customCraftingMaterial = ModItems.plate_paa;
		aMatCMB.customCraftingMaterial = ModItems.ingot_combine_steel;
		aMatAus3.customCraftingMaterial = ModItems.ingot_australium;
		aMatSecurity.customCraftingMaterial = ModItems.plate_kevlar;
		aMatCobalt.customCraftingMaterial = ModItems.ingot_cobalt;
		aMatStarmetal.customCraftingMaterial = ModItems.ingot_starmetal;
		aMatBismuth.customCraftingMaterial = ModItems.plate_bismuth;
		tMatSchrab.setRepairItem(new ItemStack(ModItems.ingot_schrabidium));
		tMatHammmer.setRepairItem(new ItemStack(Item.getItemFromBlock(ModBlocks.block_schrabidium)));
		tMatChainsaw.setRepairItem(new ItemStack(ModItems.ingot_steel));
		tMatTitan.setRepairItem(new ItemStack(ModItems.ingot_titanium));
		tMatSteel.setRepairItem(new ItemStack(ModItems.ingot_steel));
		tMatAlloy.setRepairItem(new ItemStack(ModItems.ingot_advanced_alloy));
		tMatCMB.setRepairItem(new ItemStack(ModItems.ingot_combine_steel));
		enumToolMaterialBottleOpener.setRepairItem(new ItemStack(ModItems.plate_steel));
		tMatDesh.setRepairItem(new ItemStack(ModItems.ingot_desh));

		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GUIHandler());

		TileMappings.writeMappings();
		MachineDynConfig.initialize();
		TileEntityLaunchPadBase.registerLaunchables();

		for(Entry<Class<? extends TileEntity>, String[]> e : TileMappings.map.entrySet()) {

			if(e.getValue().length == 1)
				GameRegistry.registerTileEntity(e.getKey(), e.getValue()[0]);
			else
				GameRegistry.registerTileEntityWithAlternatives(e.getKey(), e.getValue()[0], e.getValue());
		}

		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(ModItems.armor_polish), 1, 1, 3));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(ModItems.bathwater), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(ModItems.bathwater), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(ModItems.serum), 1, 1, 5));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(ModItems.no9), 1, 1, 5));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(ModItems.key_red_cracked), 1, 1, 5));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(ModItems.heart_piece), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(ModItems.key_red_cracked), 1, 1, 5));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(ModItems.heart_piece), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST, new WeightedRandomChestContent(new ItemStack(ModItems.heart_piece), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(ModItems.scrumpy), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(ModItems.scrumpy), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(new ItemStack(ModItems.no9), 1, 1, 7));

		EntityMappings.writeMappings();
		//CompatNER.init();

		ForgeChunkManager.setForcedChunkLoadingCallback(this, new LoadingCallback() {

			@Override
			public void ticketsLoaded(List<Ticket> tickets, World world) {
				for(Ticket ticket : tickets) {

					if(ticket.getEntity() instanceof IChunkLoader) {
						((IChunkLoader) ticket.getEntity()).init(ticket);
					}
				}
			}
		});

		DispenserBehaviorHandler.init();
		MicroBlocksCompatHandler.preInit();
	}

	@EventHandler
	public static void load(FMLInitializationEvent event) {

		RodRecipes.registerInit();

		statLegendary = new StatBasic("stat.ntmLegendary", new ChatComponentTranslation("stat.ntmLegendary")).registerStat();
		statMines = new StatBasic("stat.ntmMines", new ChatComponentTranslation("stat.ntmMines")).registerStat();
		statBullets = new StatBasic("stat.ntmBullets", new ChatComponentTranslation("stat.ntmBullets")).registerStat();

		achSacrifice = new Achievement("achievement.sacrifice", "sacrifice", -3, 1, ModItems.burnt_bark, null).initIndependentStat().setSpecial().registerStat();
		achImpossible = new Achievement("achievement.impossible", "impossible", 18, 10, ModItems.nothing, null).initIndependentStat().setSpecial().registerStat();
		achTOB = new Achievement("achievement.tasteofblood", "tasteofblood", 3, 10, new ItemStack(ModItems.fluid_icon, 1, Fluids.ASCHRAB.getID()), null).initIndependentStat().setSpecial().registerStat();
		achGoFish = new Achievement("achievement.goFish", "goFish", 5, 10, DictFrame.fromOne(ModItems.achievement_icon, EnumAchievementType.GOFISH), null).initIndependentStat().setSpecial().registerStat();
		achPotato = new Achievement("achievement.potato", "potato", -2, -2, ModItems.battery_potatos, null).initIndependentStat().setSpecial().registerStat();
		achC20_5 = new Achievement("achievement.c20_5", "c20_5", 3, 6, DictFrame.fromOne(ModItems.achievement_icon, EnumAchievementType.QUESTIONMARK), null).initIndependentStat().setSpecial().registerStat();
		achFiend = new Achievement("achievement.fiend", "fiend", -6, 8, ModItems.shimmer_sledge, null).initIndependentStat().setSpecial().registerStat();
		achFiend2 = new Achievement("achievement.fiend2", "fiend2", -4, 9, ModItems.shimmer_axe, null).initIndependentStat().setSpecial().registerStat();
		achStratum = new Achievement("achievement.stratum", "stratum", -4, -2, new ItemStack(ModBlocks.stone_gneiss), null).initIndependentStat().setSpecial().registerStat();
		achOmega12 = new Achievement("achievement.omega12", "omega12", 17, -1, ModItems.particle_digamma, null).initIndependentStat().setSpecial().registerStat();

		achNo9 = new Achievement("achievement.no9", "no9", -8, 12, ModItems.no9, null).initIndependentStat().registerStat();
		achSlimeball = new Achievement("achievement.slimeball", "slimeball", -10, 6, DictFrame.fromOne(ModItems.achievement_icon, EnumAchievementType.ACID), null).initIndependentStat().registerStat();
		achSulfuric = new Achievement("achievement.sulfuric", "sulfuric", -10, 8, DictFrame.fromOne(ModItems.achievement_icon, EnumAchievementType.BALLS), achSlimeball).initIndependentStat().setSpecial().registerStat();
		achInferno = new Achievement("achievement.inferno", "inferno", -8, 10, ModItems.canister_napalm, null).initIndependentStat().setSpecial().registerStat();
		achRedRoom = new Achievement("achievement.redRoom", "redRoom", -10, 10, ModItems.key_red, null).initIndependentStat().setSpecial().registerStat();

		bobHidden = new Achievement("achievement.hidden", "hidden", 15, -4, DictFrame.fromOne(ModItems.achievement_icon, EnumAchievementType.QUESTIONMARK), null).initIndependentStat().registerStat();

		horizonsStart = new Achievement("achievement.horizonsStart", "horizonsStart", -5, 4, ModItems.sat_gerald, null).initIndependentStat().registerStat();
		horizonsEnd = new Achievement("achievement.horizonsEnd", "horizonsEnd", -3, 4, ModItems.sat_gerald, horizonsStart).initIndependentStat().registerStat();
		horizonsBonus = new Achievement("achievement.horizonsBonus", "horizonsBonus", -1, 4, ModItems.sat_gerald, horizonsEnd).initIndependentStat().registerStat().setSpecial();

		bossCreeper = new Achievement("achievement.bossCreeper", "bossCreeper", -7, 1, ModItems.coin_creeper, null).initIndependentStat().registerStat();
		bossMeltdown = new Achievement("achievement.bossMeltdown", "bossMeltdown", -8, 3, ModItems.coin_radiation, bossCreeper).initIndependentStat().registerStat();
		bossMaskman = new Achievement("achievement.bossMaskman", "bossMaskman", -8, -1, ModItems.coin_maskman, bossCreeper).initIndependentStat().registerStat();
		bossWorm = new Achievement("achievement.bossWorm", "bossWorm", -8, -3, ModItems.coin_worm, bossMaskman).initIndependentStat().registerStat().setSpecial();
		bossUFO = new Achievement("achievement.bossUFO", "bossUFO", -6, -3, ModItems.coin_ufo, bossWorm).initIndependentStat().registerStat().setSpecial();

		achRadPoison = new Achievement("achievement.radPoison", "radPoison", -2, 6, ModItems.geiger_counter, null).initIndependentStat().registerStat();
		achRadDeath = new Achievement("achievement.radDeath", "radDeath", 0, 6, Items.skull, achRadPoison).initIndependentStat().registerStat().setSpecial();

		achSomeWounds = new Achievement("achievement.someWounds", "someWounds", -2, 10, ModItems.injector_knife, null).initIndependentStat().registerStat();

		digammaSee = new Achievement("achievement.digammaSee", "digammaSee", -1, 8, DictFrame.fromOne(ModItems.achievement_icon, EnumAchievementType.DIGAMMASEE), null).initIndependentStat().registerStat();
		digammaFeel = new Achievement("achievement.digammaFeel", "digammaFeel", 1, 8, DictFrame.fromOne(ModItems.achievement_icon, EnumAchievementType.DIGAMMAFEEL), digammaSee).initIndependentStat().registerStat();
		digammaKnow = new Achievement("achievement.digammaKnow", "digammaKnow", 3, 8, DictFrame.fromOne(ModItems.achievement_icon, EnumAchievementType.DIGAMMAKNOW), digammaFeel).initIndependentStat().registerStat().setSpecial();
		digammaKauaiMoho = new Achievement("achievement.digammaKauaiMoho", "digammaKauaiMoho", 5, 8, DictFrame.fromOne(ModItems.achievement_icon, EnumAchievementType.DIGAMMAKAUAIMOHO), digammaKnow).initIndependentStat().registerStat().setSpecial();
		digammaUpOnTop = new Achievement("achievement.digammaUpOnTop", "digammaUpOnTop", 7, 8, DictFrame.fromOne(ModItems.achievement_icon, EnumAchievementType.DIGAMMAUPONTOP), digammaKauaiMoho).initIndependentStat().registerStat().setSpecial();

		//progression achieves
		achBurnerPress = new Achievement("achievement.burnerPress", "burnerPress", 0, 0, new ItemStack(ModBlocks.machine_press), null).initIndependentStat().registerStat();
		achBlastFurnace = new Achievement("achievement.blastFurnace", "blastFurnace", 1, 3, new ItemStack(ModBlocks.machine_difurnace_off), achBurnerPress).initIndependentStat().registerStat();
		achAssembly = new Achievement("achievement.assembly", "assembly", 3, -1, new ItemStack(ModBlocks.machine_assembly_machine), achBurnerPress).initIndependentStat().registerStat();
		achSelenium = new Achievement("achievement.selenium", "selenium", 3, 2, ModItems.ingot_starmetal, achBurnerPress).initIndependentStat().setSpecial().registerStat();
		achChemplant = new Achievement("achievement.chemplant", "chemplant", 6, -1, new ItemStack(ModBlocks.machine_chemical_plant), achAssembly).initIndependentStat().registerStat();
		achConcrete	= new Achievement("achievement.concrete", "concrete", 6, -4, new ItemStack(ModBlocks.concrete), achChemplant).initIndependentStat().registerStat();
		achPolymer = new Achievement("achievement.polymer", "polymer", 9, -1, ModItems.ingot_polymer, achChemplant).initIndependentStat().registerStat();
		achDesh = new Achievement("achievement.desh", "desh", 9, 2, ModItems.ingot_desh, achChemplant).initIndependentStat().registerStat();
		achTantalum = new Achievement("achievement.tantalum", "tantalum", 7, 3, ModItems.gem_tantalium, achChemplant).initIndependentStat().setSpecial().registerStat();
		achGasCent = new Achievement("achievement.gasCent", "gasCent", 13, 2, ModItems.ingot_uranium_fuel, achDesh).initIndependentStat().registerStat();
		achCentrifuge = new Achievement("achievement.centrifuge", "centrifuge", 12, -2, new ItemStack(ModBlocks.machine_centrifuge), achPolymer).initIndependentStat().registerStat();
		achFOEQ = new Achievement("achievement.FOEQ", "FOEQ", 5, 5, ModItems.sat_foeq, achDesh).initIndependentStat().setSpecial().registerStat();
		achSoyuz = new Achievement("achievement.soyuz", "soyuz", 7, 6, Items.baked_potato, achDesh).initIndependentStat().setSpecial().registerStat();
		achSpace = new Achievement("achievement.space", "space", 9, 7, ModItems.missile_soyuz, achDesh).initIndependentStat().setSpecial().registerStat();
		achSchrab = new Achievement("achievement.schrab", "schrab", 11, 3, ModItems.ingot_schrabidium, achDesh).initIndependentStat().registerStat();
		achAcidizer = new Achievement("achievement.acidizer", "acidizer", 11, 5, new ItemStack(ModBlocks.machine_crystallizer), achDesh).initIndependentStat().registerStat();
		achRadium = new Achievement("achievement.radium", "radium", 13, -4, ModItems.coffee_radium, achCentrifuge).initIndependentStat().setSpecial().registerStat();
		achTechnetium = new Achievement("achievement.technetium", "technetium", 15, -2, ModItems.ingot_tcalloy, achCentrifuge).initIndependentStat().registerStat();
		achZIRNOXBoom = new Achievement("achievement.ZIRNOXBoom", "ZIRNOXBoom", 14, -1, ModItems.debris_element, achCentrifuge).initIndependentStat().setSpecial().registerStat();
		achChicagoPile = new Achievement("achievement.chicagoPile", "chicagoPile", 13, 0, ModItems.pile_rod_plutonium, achCentrifuge).initIndependentStat().registerStat();
		achSILEX = new Achievement("achievement.SILEX", "SILEX", 12, 7, new ItemStack(ModBlocks.machine_silex), achAcidizer).initIndependentStat().registerStat();
		achWatz = new Achievement("achievement.watz", "watz", 14, 3, ModItems.watz_pellet, achSchrab).initIndependentStat().registerStat();
		achWatzBoom = new Achievement("achievement.watzBoom", "watzBoom", 14, 5, ModItems.bucket_mud, achWatz).initIndependentStat().setSpecial().registerStat();
		achRBMK = new Achievement("achievement.RBMK", "RBMK", 9, -5, ModItems.rbmk_fuel_ueu, achConcrete).initIndependentStat().registerStat();
		achRBMKBoom = new Achievement("achievement.RBMKBoom", "RBMKBoom", 9, -7, ModItems.debris_fuel, achRBMK).initIndependentStat().setSpecial().registerStat();
		achBismuth = new Achievement("achievement.bismuth", "bismuth", 11, -6, ModItems.ingot_bismuth, achRBMK).initIndependentStat().registerStat();
		achBreeding = new Achievement("achievement.breeding", "breeding", 7, -6, ModItems.ingot_am_mix, achRBMK).initIndependentStat().setSpecial().registerStat();
		achFusion = new Achievement("achievement.fusion", "fusion", 13, -7, new ItemStack(ModBlocks.iter), achBismuth).initIndependentStat().setSpecial().registerStat();
		achRedBalloons = new Achievement("achievement.redBalloons", "redBalloons", 11, 0, ModItems.missile_nuclear, achPolymer).initIndependentStat().setSpecial().registerStat();
		achManhattan = new Achievement("achievement.manhattan", "manhattan", 11, -4, new ItemStack(ModBlocks.nuke_boy), achPolymer).initIndependentStat().setSpecial().registerStat();

		AchievementPage.registerAchievementPage(new AchievementPage("Nuclear Tech", new Achievement[] {
			achSacrifice,
			achImpossible,
			achTOB,
			achGoFish,
			achPotato,
			achC20_5,
			achFiend,
			achFiend2,
			achStratum,
			achOmega12,
			bobHidden,
			horizonsStart,
			horizonsEnd,
			horizonsBonus,
			achRadPoison,
			achRadDeath,
			achNo9,
			achInferno,
			achRedRoom,
			achSlimeball,
			achSulfuric,
			bossCreeper,
			bossMeltdown,
			bossMaskman,
			bossWorm,
			bossUFO,
			achSomeWounds,
			digammaSee,
			digammaFeel,
			digammaKnow,
			digammaKauaiMoho,
			digammaUpOnTop,

			achBurnerPress,
			achBlastFurnace,
			achAssembly,
			achSelenium,
			achChemplant,
			achConcrete,
			achPolymer,
			achDesh,
			achTantalum,
			achGasCent,
			achCentrifuge,
			achFOEQ,
			achSoyuz,
			achSpace,
			achSchrab,
			achAcidizer,
			achRadium,
			achTechnetium,
			achZIRNOXBoom,
			achChicagoPile,
			achSILEX,
			achWatz,
			achWatzBoom,
			achRBMK,
			achRBMKBoom,
			achBismuth,
			achBreeding,
			achFusion,
			achRedBalloons,
			achManhattan
		}));

		// MUST be initialized AFTER achievements!!
		BobmazonOfferFactory.init();

		IMCHandler.registerHandler("blastfurnace", new IMCBlastFurnace());
		IMCHandler.registerHandler("centrifuge", new IMCCentrifuge());

		if (Loader.isModLoaded("NotEnoughItems")){
			if (Loader.instance().getIndexedModList().get("NotEnoughItems").getVersion().contains("GTNH")) {
				proxy.handleNHNEICompat();
			}
		}
	}

	@EventHandler
	public static void initIMC(IMCEvent event) {

		ImmutableList<IMCMessage> inbox = event.getMessages(); //tee-hee

		for(IMCMessage message : inbox) {
			IMCHandler handler = IMCHandler.getHandler(message.key);

			if(handler != null) {
				MainRegistry.logger.info("Received IMC of type >" + message.key + "< from " + message.getSender() + "!");
				handler.process(message);
			} else {
				MainRegistry.logger.error("Could not process unknown IMC type \"" + message.key + "\"");
			}
		}
	}

	@EventHandler
	public static void PostLoad(FMLPostInitializationEvent PostEvent) {
		// to make sure that foreign registered fluids are accounted for,
		// even when the reload listener is registered too late due to load order
		// IMPORTANT: fluids have to load before recipes. weird shit happens if not.
		Fluids.reloadFluids();
		FluidContainerRegistry.register();

		MagicRecipes.register();
		LemegetonRecipes.register();
		SILEXRecipes.register();
		RefineryRecipes.registerRefinery();
		GasCentrifugeRecipes.register();

		CustomMachineConfigJSON.initialize();

		//the good stuff
		SerializableRecipe.registerAllHandlers();
		SerializableRecipe.initialize();

		//Anvil has to come after serializables (i.e. anvil)
		AnvilRecipes.register();

		//has to register after cracking, and therefore after all serializable recipes
		RadiolysisRecipes.registerRadiolysis();

		FalloutConfigJSON.initialize();
		ItemPoolConfigJSON.initialize();

		ClientConfig.initConfig();
		ServerConfig.initConfig();

		TileEntityNukeCustom.registerBombItems();
		ArmorUtil.register();
		HazmatRegistry.registerHazmats();
		DamageResistanceHandler.init();
		BlockToolConversion.registerRecipes();
		AchievementHandler.register();

		MobUtil.intializeMobPools();

		proxy.registerMissileItems();

		// Load compatibility for OC.
		CompatHandler.init();

		// Load compatibility for AE2.
		AE2CompatHandler.init();

		//expand for the largest entity we have (currently Quackos who is 17.5m in diameter, that's one fat duck)
		World.MAX_ENTITY_RADIUS = Math.max(World.MAX_ENTITY_RADIUS, 8.75);

		MinecraftForge.EVENT_BUS.register(new SchistStratum()); //DecorateBiomeEvent.Pre
		//MinecraftForge.EVENT_BUS.register(new DeepLayer()); //DecorateBiomeEvent.Pre

		if(WorldConfig.enableSulfurCave) new OreCave(ModBlocks.stone_resource, 0).setThreshold(1.5D).setRangeMult(20).setYLevel(30).setMaxRange(20).withFluid(ModBlocks.sulfuric_acid_block);	//sulfur
		if(WorldConfig.enableAsbestosCave) new OreCave(ModBlocks.stone_resource, 1).setThreshold(1.75D).setRangeMult(20).setYLevel(25).setMaxRange(20);											//asbestos
		new OreCave(ModBlocks.stone_resource, EnumStoneType.LIMESTONE.ordinal()).setThreshold(1.2D).setRangeMult(30).setYLevel(30).setMaxRange(30).withFluid(Blocks.water);

		if(WorldConfig.enableHematite) new OreLayer3D(ModBlocks.stone_resource, EnumStoneType.HEMATITE.ordinal()).setScaleH(0.04D).setScaleV(0.25D).setThreshold(230);
		if(WorldConfig.enableBauxite) new OreLayer3D(ModBlocks.stone_resource, EnumStoneType.BAUXITE.ordinal()).setScaleH(0.03D).setScaleV(0.15D).setThreshold(300);
		if(WorldConfig.enableMalachite) new OreLayer3D(ModBlocks.stone_resource, EnumStoneType.MALACHITE.ordinal()).setScaleH(0.1D).setScaleV(0.15D).setThreshold(275);
		if(WorldConfig.enableMalachite) new OreLayer3D(ModBlocks.stone_resource, EnumStoneType.GALENA.ordinal()).setScaleH(0.1D).setScaleV(0.15D).setThreshold(275);


		//new BiomeCave().setThreshold(1.5D).setRangeMult(20).setYLevel(40).setMaxRange(20);
		//new OreLayer(Blocks.coal_ore, 0.2F).setThreshold(4).setRangeMult(3).setYLevel(70);
		BedrockOre.init();

		Compat.handleRailcraftNonsense();
		SuicideThreadDump.register();
		CommandReloadClient.register();

		//ExplosionTests.runTest();
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		if(logger == null)
			logger = event.getModLog();

		ModEventHandler commonHandler = new ModEventHandler();
		FMLCommonHandler.instance().bus().register(commonHandler);
		MinecraftForge.EVENT_BUS.register(commonHandler);
		MinecraftForge.TERRAIN_GEN_BUS.register(commonHandler);
		MinecraftForge.ORE_GEN_BUS.register(commonHandler);

		ModEventHandlerImpact impactHandler = new ModEventHandlerImpact();
		FMLCommonHandler.instance().bus().register(impactHandler);
		MinecraftForge.EVENT_BUS.register(impactHandler);
		MinecraftForge.TERRAIN_GEN_BUS.register(impactHandler);

		PacketDispatcher.registerPackets();

		ChunkRadiationManager radiationSystem = new ChunkRadiationManager();
		MinecraftForge.EVENT_BUS.register(radiationSystem);
		FMLCommonHandler.instance().bus().register(radiationSystem);

		PollutionHandler pollution = new PollutionHandler();
		MinecraftForge.EVENT_BUS.register(pollution);
		FMLCommonHandler.instance().bus().register(pollution);

		DamageResistanceHandler dmgHandler = new DamageResistanceHandler();
		MinecraftForge.EVENT_BUS.register(dmgHandler);

		NeutronHandler neutronHandler = new NeutronHandler();
		MinecraftForge.EVENT_BUS.register(neutronHandler);
		FMLCommonHandler.instance().bus().register(neutronHandler);

		if(event.getSide() == Side.CLIENT) {
			HbmKeybinds.register();
			HbmKeybinds keyHandler = new HbmKeybinds();
			FMLCommonHandler.instance().bus().register(keyHandler);
		}
	}

	//yes kids, this is where we would usually register commands
	@EventHandler
	public void serverStart(FMLServerStartingEvent event) {
		World world = event.getServer().getEntityWorld();
		RBMKDials.createDials(world);
		event.registerServerCommand(new CommandReloadRecipes());
		event.registerServerCommand(new CommandDebugChunkLoad());
		event.registerServerCommand(new CommandSatellites());
		event.registerServerCommand(new CommandRadiation());
		event.registerServerCommand(new CommandPacketInfo());
		event.registerServerCommand(new CommandReloadServer());
		event.registerServerCommand(new CommandLocate());
		event.registerServerCommand(new CommandCustomize());
		ArcFurnaceRecipes.registerFurnaceSmeltables(); // because we have to wait for other mods to take their merry ass time to register recipes
	}

	@EventHandler
	public void serverStart(FMLServerStartedEvent event) {

		if(GeneralConfig.enableStatReRegistering) {
			logger.info("Attempting to re-register item stats...");
			StatHelper.resetStatShitFuck(); //shit yourself
			logger.info("Item stats re-registered");
		}
	}

	private void loadConfig(FMLPreInitializationEvent event) {

		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();

		GeneralConfig.loadFromConfig(config);
		WorldConfig.loadFromConfig(config);
		MachineConfig.loadFromConfig(config);
		BombConfig.loadFromConfig(config);
		RadiationConfig.loadFromConfig(config);
		PotionConfig.loadFromConfig(config);
		ToolConfig.loadFromConfig(config);
		WeaponConfig.loadFromConfig(config);
		MobConfig.loadFromConfig(config);
		StructureConfig.loadFromConfig(config);

		config.save();

		try {
			if(GeneralConfig.enableThermosPreventer && Class.forName("thermos.ThermosClassTransformer") != null) {
				throw new IllegalStateException("The mod tried to start on a Thermos or its fork server and therefore stopped. To allow the server to start on Thermos, change the appropriate "
					+ "config entry (0.00 in hbm.cfg). This was done because, by default, Thermos "
					+ "uses a so-called \"optimization\" feature that reduces tile ticking a lot, which will inevitably break a lot of machines. Most people aren't even aware "
					+ "of this, and start blaming random mods for all their stuff breaking. In order to adjust or even disable this feature, edit \"tileentities.yml\" in your "
					+ "Thermos install folder. If you believe that crashing the server until a config option is changed is annoying, then I would agree, but it's still preferable "
					+ "over wasting hours trying to fix an issue that is really just an \"intended feature\" added by Thermos itself, and not a bug in the mod. You'll have to "
					+ "change Thermos' config anyway so that extra change in NTM's config can't be that big of a burden.");
			}
		} catch(ClassNotFoundException e) { }
	}

	private static HashSet<String> ignoreMappings = new HashSet();
	private static HashMap<String, Item> remapItems = new HashMap();


	@EventHandler
	public void handleMissingMappings(FMLMissingMappingsEvent event) {

		ignoreMappings.clear();
		remapItems.clear();

		/// IGNORE ///
		for(int i = 1; i <= 8; i++) ignoreMappings.add("hbm:item.gasflame" + i);

		for(MissingMapping mapping : event.get()) {

			// ignore all ammo prefixes because those are from the time we threw out all the ammo items
			if(mapping.name.startsWith("hbm:item.ammo_")) {
				mapping.ignore();
				continue;
			}

			if(ignoreMappings.contains(mapping.name)) {
				mapping.ignore();
				continue;
			}

			if(mapping.type == GameRegistry.Type.ITEM) {
				if(remapItems.get(mapping.name) != null) {
					mapping.remap(remapItems.get(mapping.name));
					continue;
				}
			}
		}
	}
}
