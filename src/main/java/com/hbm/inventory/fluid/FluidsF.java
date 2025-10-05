package com.hbm.inventory.fluid;

import com.hbm.render.util.EnumSymbol;

import static com.hbm.inventory.fluid.Fluids.*;
import static com.hbm.util.CompatFluidRegistry.*;

/** Register for fluids from this fork, kept in a seperate class to avoid merge pain */
public class FluidsF {

	public static int ID = 1000;

	public static FluidType BORIC_ACID;

	public static void init() {
		//please read the ATTENSHONE on Fluids.java

		BORIC_ACID =				new FluidType("BORIC_ACID", ID++,			0xC5ECF0, 1, 0, 0, EnumSymbol.NONE).addTraits(LIQUID);

		Fluids.metaOrder.add(BORIC_ACID);
	}

}
