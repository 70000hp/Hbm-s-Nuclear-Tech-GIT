package com.hbm.util;

import net.minecraft.client.renderer.Tessellator;

public class RenderUtil {
	
	public static void renderBlock(Tessellator tessellator) {
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(-0.5, +0.5, -0.5, 1, 1);
		tessellator.addVertexWithUV(+0.5, +0.5, -0.5, 0, 1);
		tessellator.addVertexWithUV(+0.5, -0.5, -0.5, 0, 0);
		tessellator.addVertexWithUV(-0.5, -0.5, -0.5, 1, 0);
		
		tessellator.addVertexWithUV(-0.5, +0.5, +0.5, 1, 1);
		tessellator.addVertexWithUV(-0.5, +0.5, -0.5, 0, 1);
		tessellator.addVertexWithUV(-0.5, -0.5, -0.5, 0, 0);
		tessellator.addVertexWithUV(-0.5, -0.5, +0.5, 1, 0);
		
		tessellator.addVertexWithUV(+0.5, +0.5, +0.5, 1, 1);
		tessellator.addVertexWithUV(-0.5, +0.5, +0.5, 0, 1);
		tessellator.addVertexWithUV(-0.5, -0.5, +0.5, 0, 0);
		tessellator.addVertexWithUV(+0.5, -0.5, +0.5, 1, 0);
		
		tessellator.addVertexWithUV(+0.5, +0.5, -0.5, 1, 1);
		tessellator.addVertexWithUV(+0.5, +0.5, +0.5, 0, 1);
		tessellator.addVertexWithUV(+0.5, -0.5, +0.5, 0, 0);
		tessellator.addVertexWithUV(+0.5, -0.5, -0.5, 1, 0);
		
		tessellator.addVertexWithUV(-0.5, -0.5, -0.5, 1, 1);
		tessellator.addVertexWithUV(+0.5, -0.5, -0.5, 0, 1);
		tessellator.addVertexWithUV(+0.5, -0.5, +0.5, 0, 0);
		tessellator.addVertexWithUV(-0.5, -0.5, +0.5, 1, 0);
		
		tessellator.addVertexWithUV(+0.5, +0.5, -0.5, 1, 1);
		tessellator.addVertexWithUV(-0.5, +0.5, -0.5, 0, 1);
		tessellator.addVertexWithUV(-0.5, +0.5, +0.5, 0, 0);
		tessellator.addVertexWithUV(+0.5, +0.5, +0.5, 1, 0);
		tessellator.draw();
	}

}
