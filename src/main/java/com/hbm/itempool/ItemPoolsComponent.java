package com.hbm.itempool;

import static com.hbm.lib.HbmChestContents.weighted;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.material.Mats;
import com.hbm.items.ItemEnums.EnumCokeType;
import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemBlowtorch;

import net.minecraft.init.Items;
import net.minecraft.util.WeightedRandomChestContent;

public class ItemPoolsComponent {

	public static final String POOL_MACHINE_PARTS = "POOL_MACHINE_PARTS";
	public static final String POOL_NUKE_FUEL = "POOL_NUKE_FUEL";
	public static final String POOL_SILO = "POOL_SILO";
	public static final String POOL_OFFICE_TRASH = "POOL_OFFICE_TRASH";
	public static final String POOL_FILING_CABINET = "POOL_FILING_CABINET";
	public static final String POOL_SOLID_FUEL = "POOL_SOLID_FUEL";
	public static final String POOL_VAULT_LAB = "POOL_VAULT_LAB";
	public static final String POOL_VAULT_LOCKERS = "POOL_VAULT_LOCKERS";
	
	public static void init() {
		
		//machine parts
		new ItemPool(POOL_MACHINE_PARTS) {{
			this.pool = new WeightedRandomChestContent[] {
					weighted(ModItems.plate_steel, 0, 1, 5, 5),
					weighted(ModItems.hull_big_steel, 0, 1, 2, 2),
					weighted(ModItems.hull_small_steel, 0, 1, 3, 3),
					weighted(ModItems.plate_polymer, 0, 1, 6, 5),
					weighted(ModItems.bolt, Mats.MAT_STEEL.id, 4, 16, 3),
					weighted(ModItems.bolt, Mats.MAT_TUNGSTEN.id, 4, 16, 3),
					weighted(ModItems.plate_copper, 0, 4, 8, 4),
					weighted(ModItems.coil_tungsten, 0, 1, 2, 5),
					weighted(ModItems.motor, 0, 1, 2, 4),
					weighted(ModItems.tank_steel, 0, 1, 2, 3),
					weighted(ModItems.coil_copper, 0, 1, 3, 4),
					weighted(ModItems.coil_copper_torus, 0, 1, 2, 3),
					weighted(ModItems.wire_red_copper, 0, 1, 8, 5),
					weighted(ModItems.piston_selenium, 0, 1, 1, 3),
					weighted(ModItems.battery_advanced_cell, 0, 1, 1, 3),
					weighted(ModItems.circuit_raw, 0, 1, 3, 5),
					weighted(ModItems.circuit_aluminium, 0, 1, 2, 4),
					weighted(ModItems.circuit_copper, 0, 1, 1, 3),
					weighted(ModItems.circuit_red_copper, 0, 1, 1, 2),
					weighted(ModItems.blade_titanium, 0, 1, 8, 1)
			};
		}};
		
		//fuel isotopes found in bunkers and labs
		new ItemPool(POOL_NUKE_FUEL) {{
			this.pool = new WeightedRandomChestContent[] {
					weighted(ModItems.billet_uranium, 0, 1, 4, 4),
					weighted(ModItems.billet_th232, 0, 1, 3, 3),
					weighted(ModItems.billet_uranium_fuel, 0, 1, 3, 5),
					weighted(ModItems.billet_mox_fuel, 0, 1, 3, 5),
					weighted(ModItems.billet_thorium_fuel, 0, 1, 3, 3),
					weighted(ModItems.billet_ra226be, 0, 1, 2, 2),
					weighted(ModItems.billet_beryllium, 0, 1, 1, 1),
					weighted(ModItems.nugget_u233, 0, 1, 1, 1),
					weighted(ModItems.nugget_uranium_fuel, 0, 1, 1, 1),
					weighted(ModItems.rod_zirnox_empty, 0, 1, 3, 3),
					weighted(ModItems.ingot_graphite, 0, 1, 4, 3),
					weighted(ModItems.pile_rod_uranium, 0, 2, 5, 3),
					weighted(ModItems.pile_rod_source, 0, 1, 2, 2),
					weighted(ModItems.reacher, 0, 1, 1, 3),
					weighted(ModItems.screwdriver, 0, 1, 1, 2)
			};
		}};
		
		//missile parts found in silos
		new ItemPool(POOL_SILO) {{
			this.pool = new WeightedRandomChestContent[] {
					weighted(ModItems.missile_generic, 0, 1, 1, 4),
					weighted(ModItems.missile_incendiary, 0, 1, 1, 4),
					weighted(ModItems.gas_mask_m65, 0, 1, 1, 5),
					weighted(ModItems.battery_advanced, 0, 1, 1, 5),
					weighted(ModItems.designator, 0, 1, 1, 5),
					weighted(ModItems.crate_caller, 0, 1, 1, 1),
					weighted(ModItems.thruster_small, 0, 1, 1, 5),
					weighted(ModItems.thruster_medium, 0, 1, 1, 4),
					weighted(ModItems.fuel_tank_small, 0, 1, 1, 5),
					weighted(ModItems.fuel_tank_medium, 0, 1, 1, 4),
					weighted(ModItems.bomb_caller, 0, 1, 1, 1),
					weighted(ModItems.bomb_caller, 3, 1, 1, 1),
					weighted(ModItems.bottle_nuka, 0, 1, 3, 10)
			};
		}};
		
		//low quality items from offices in chests
		new ItemPool(POOL_OFFICE_TRASH) {{
			this.pool = new WeightedRandomChestContent[] {
					weighted(Items.paper, 0, 1, 12, 10),
					weighted(Items.book, 0, 1, 3, 4),
					weighted(ModItems.twinkie, 0, 1, 2, 6),
					weighted(ModItems.coffee, 0, 1, 1, 4),
					weighted(ModItems.flame_politics, 0, 1, 1, 2),
					weighted(ModItems.ring_pull, 0, 1, 1, 4),
					weighted(ModItems.can_empty, 0, 1, 1, 2),
					weighted(ModItems.can_creature, 0, 1, 2, 2),
					weighted(ModItems.can_smart, 0, 1, 3, 2),
					weighted(ModItems.can_mrsugar, 0, 1, 2, 2),
					weighted(ModItems.cap_nuka, 0, 1, 16, 2),
					weighted(ModItems.book_guide, 3, 1, 1, 1),
					weighted(ModBlocks.deco_computer, 0, 1, 1, 1)
			};
		}};
		
		//things found in various filing cabinets, paper, books, etc
		new ItemPool(POOL_FILING_CABINET) {{
			this.pool = new WeightedRandomChestContent[] {
					weighted(Items.paper, 0, 1, 12, 240),
					weighted(Items.book, 0, 1, 3, 90),
					weighted(Items.map, 0, 1, 1, 50),
					weighted(Items.writable_book, 0, 1, 1, 30),
					weighted(ModItems.cigarette, 0, 1, 16, 20),
					weighted(ModItems.toothpicks, 0, 1, 16, 10),
					weighted(ModItems.dust, 0, 1, 1, 40),
					weighted(ModItems.dust_tiny, 0, 1, 3, 75),
					weighted(ModItems.ink, 0, 1, 1, 1)
			};
		}};
		
		//solid fuels from bunker power rooms
		new ItemPool(POOL_SOLID_FUEL) {{
			this.pool = new WeightedRandomChestContent[] {
					weighted(ModItems.solid_fuel, 0, 1, 5, 1),
					weighted(ModItems.solid_fuel_presto, 0, 1, 2, 2),
					weighted(ModItems.ball_dynamite, 0, 1, 4, 2),
					weighted(ModItems.coke, EnumCokeType.PETROLEUM.ordinal(), 1, 3, 1),
					weighted(Items.redstone, 0, 1, 3, 1),
					weighted(ModItems.niter, 0, 1, 3, 1)
			};
		}};
		
		//various lab related items from bunkers
		new ItemPool(POOL_VAULT_LAB) {{
			this.pool = new WeightedRandomChestContent[] {
					weighted(ItemBlowtorch.getEmptyTool(ModItems.blowtorch), 1, 1, 4),
					weighted(ModItems.chemistry_set, 0, 1, 1, 15),
					weighted(ModItems.screwdriver, 0, 1, 1, 10),
					weighted(ModItems.nugget_mercury, 0, 1, 1, 3),
					weighted(ModItems.morning_glory, 0, 1, 1, 1),
					weighted(ModItems.filter_coal, 0, 1, 1, 5),
					weighted(ModItems.dust, 0, 1, 3, 25),
					weighted(Items.paper, 0, 1, 2, 15),
					weighted(ModItems.cell_empty, 0, 1, 1, 5),
					weighted(Items.glass_bottle, 0, 1, 1, 5),
					weighted(ModItems.powder_iodine, 0, 1, 1, 1),
					weighted(ModItems.powder_bromine, 0, 1, 1, 1),
					weighted(ModItems.powder_cobalt, 0, 1, 1, 1),
					weighted(ModItems.powder_neodymium, 0, 1, 1, 1),
					weighted(ModItems.powder_boron, 0, 1, 1, 1)
			};
		}};
		
		//personal items and gear from vaults
		new ItemPool(POOL_VAULT_LOCKERS) {{
			this.pool = new WeightedRandomChestContent[] {
					weighted(ModItems.robes_helmet, 0, 1, 1, 1),
					weighted(ModItems.robes_plate, 0, 1, 1, 1),
					weighted(ModItems.robes_legs, 0, 1, 1, 1),
					weighted(ModItems.robes_boots, 0, 1, 1, 1),
					weighted(ModItems.jackt, 0, 1, 1, 1),
					weighted(ModItems.jackt2, 0, 1, 1, 1),
					weighted(ModItems.gas_mask_m65, 0, 1, 1, 2),
					weighted(ModItems.gas_mask_mono, 0, 1, 1, 2),
					weighted(ModItems.goggles, 0, 1, 1, 2),
					weighted(ModItems.gas_mask_filter, 0, 1, 1, 4),
					weighted(ModItems.flame_opinion, 0, 1, 3, 5),
					weighted(ModItems.flame_conspiracy, 0, 1, 3, 5),
					weighted(ModItems.flame_politics, 0, 1, 3, 5),
					weighted(ModItems.definitelyfood, 0, 2, 7, 5),
					weighted(ModItems.cigarette, 0, 1, 8, 5),
					weighted(ModItems.armor_polish, 0, 1, 1, 3),
					weighted(ModItems.gun_kit_1, 0, 1, 1, 3),
					weighted(ModItems.rag, 0, 1, 3, 5),
					weighted(Items.paper, 0, 1, 6, 7),
					weighted(Items.clock, 0, 1, 1, 3),
					weighted(Items.book, 0, 1, 5, 10),
					weighted(Items.experience_bottle, 0, 1, 3, 1)
			};
		}};
	}
}
