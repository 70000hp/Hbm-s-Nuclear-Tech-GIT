package com.hbm.render.block;

import com.hbm.blocks.BlockCoral;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public class RenderCoral implements ISimpleBlockRenderingHandler {

	private static final ForgeDirection[] directions = new ForgeDirection[] {
		ForgeDirection.NORTH,
		ForgeDirection.SOUTH,
		ForgeDirection.WEST,
		ForgeDirection.EAST,
		ForgeDirection.UP,
		ForgeDirection.DOWN,
	};
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		Tessellator tess = Tessellator.instance;
		int colorMult = block.colorMultiplier(world, x, y, z);
		float r = (float) (colorMult >> 16 & 255) / 255.0F;
		float g = (float) (colorMult >> 8 & 255) / 255.0F;
		float b = (float) (colorMult & 255) / 255.0F;
		
		tess.setColorOpaque_F(r, g, b);
		
		int brightness = block.getMixedBrightnessForBlock(world, x, y, z);
		tess.setBrightness(brightness);
		
		IIcon icon = block.getIcon(world, x, y, z, 0);
		renderCrossedSquares(icon, x, y, z, 1.0D, getAttachment(world, x, y, z));
		
		return true;
	}

	// find a block to connect to, biased towards sideways connections
	private ForgeDirection getAttachment(IBlockAccess world, int x, int y, int z) {
		for(ForgeDirection dir : directions) {
			int ox = x + dir.offsetX;
			int oy = y + dir.offsetY;
			int oz = z + dir.offsetZ;

			if(world.getBlock(ox, oy, oz).isBlockSolid(world, ox, oy, oz, dir.ordinal())) {
				return dir;
			}
		}

		return ForgeDirection.DOWN;
	}
	
	// drawCrossedSquares but with a direction
	private void renderCrossedSquares(IIcon icon, double x, double y, double z, double height, ForgeDirection dir) {
		Tessellator tess = Tessellator.instance;
		
		double minU = icon.getMinU();
		double minV = icon.getMinV();
		double maxU = icon.getMaxU();
		double maxV = icon.getMaxV();
		
		double factor = 0.45D * height;
		double minY = 0;
		double maxY = height;
		double minX = 0.5D - factor;
		double maxX = 0.5D + factor;
		double minZ = 0.5D - factor;
		double maxZ = 0.5D + factor;

		double x0, x1, x2, x3, x4, x5, x6, x7;
		double y0, y1, y2, y3, y4, y5, y6, y7;
		double z0, z1, z2, z3, z4, z5, z6, z7;
		
		x0 = minX; x1 = minX; x2 = maxX; x3 = maxX;
		y0 = minY; y1 = maxY; y2 = maxY; y3 = minY;
		z0 = minZ; z1 = minZ; z2 = maxZ; z3 = maxZ;

		x4 = maxX; x5 = maxX; x6 = minX; x7 = minX;
		y4 = minY; y5 = maxY; y6 = maxY; y7 = minY;
		z4 = minZ; z5 = minZ; z6 = maxZ; z7 = maxZ;

		switch(dir) {
		case UP:
			y0 = 1 - y0; y1 = 1 - y1; y2 = 1 - y2; y3 = 1 - y3;
			y4 = 1 - y4; y5 = 1 - y5; y6 = 1 - y6; y7 = 1 - y7;
			break;
		case NORTH:
			y0 = minZ; y1 = minZ; y2 = maxZ; y3 = maxZ;
			z0 = minY; z1 = maxY; z2 = maxY; z3 = minY;
			y4 = minZ; y5 = minZ; y6 = maxZ; y7 = maxZ;
			z4 = minY; z5 = maxY; z6 = maxY; z7 = minY;
			break;
		case SOUTH:
			y0 = minZ; y1 = minZ; y2 = maxZ; y3 = maxZ;
			z0 = 1 - minY; z1 = 1 - maxY; z2 = 1 - maxY; z3 = 1 - minY;
			y4 = minZ; y5 = minZ; y6 = maxZ; y7 = maxZ;
			z4 = 1 - minY; z5 = 1 - maxY; z6 = 1 - maxY; z7 = 1 - minY;
			break;
		case WEST:
			y0 = minX; y1 = minX; y2 = maxX; y3 = maxX;
			x0 = minY; x1 = maxY; x2 = maxY; x3 = minY;
			y4 = maxX; y5 = maxX; y6 = minX; y7 = minX;
			x4 = minY; x5 = maxY; x6 = maxY; x7 = minY;
			break;
		case EAST:
			y0 = minX; y1 = minX; y2 = maxX; y3 = maxX;
			x0 = 1 - minY; x1 = 1 - maxY; x2 = 1 - maxY; x3 = 1 - minY;
			y4 = maxX; y5 = maxX; y6 = minX; y7 = minX;
			x4 = 1 - minY; x5 = 1 - maxY; x6 = 1 - maxY; x7 = 1 - minY;
			break;
		default:
			break;
		}

		tess.addVertexWithUV(x + x0, y + y0, z + z0, maxU, maxV); 
		tess.addVertexWithUV(x + x1, y + y1, z + z1, maxU, minV);
		tess.addVertexWithUV(x + x2, y + y2, z + z2, minU, minV);
		tess.addVertexWithUV(x + x3, y + y3, z + z3, minU, maxV);

		tess.addVertexWithUV(x + x3, y + y3, z + z3, maxU, maxV);
		tess.addVertexWithUV(x + x2, y + y2, z + z2, maxU, minV);
		tess.addVertexWithUV(x + x1, y + y1, z + z1, minU, minV);
		tess.addVertexWithUV(x + x0, y + y0, z + z0, minU, maxV); 

		tess.addVertexWithUV(x + x4, y + y4, z + z4, maxU, maxV); 
		tess.addVertexWithUV(x + x5, y + y5, z + z5, maxU, minV);
		tess.addVertexWithUV(x + x6, y + y6, z + z6, minU, minV);
		tess.addVertexWithUV(x + x7, y + y7, z + z7, minU, maxV);

		tess.addVertexWithUV(x + x7, y + y7, z + z7, maxU, maxV);
		tess.addVertexWithUV(x + x6, y + y6, z + z6, maxU, minV);
		tess.addVertexWithUV(x + x5, y + y5, z + z5, minU, minV);
		tess.addVertexWithUV(x + x4, y + y4, z + z4, minU, maxV); 
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return false;
	}

	@Override
	public int getRenderId() {
		return BlockCoral.renderID;
	}
	
}
