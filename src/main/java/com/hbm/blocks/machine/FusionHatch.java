package com.hbm.blocks.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityFusionHatch;
import com.hbm.tileentity.machine.TileEntityFusionMultiblock;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class FusionHatch extends BlockContainer {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	public FusionHatch(Material materialIn, String s) {
		super(materialIn);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityFusionHatch();
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()));
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
			EnumFacing e = state.getValue(FACING);
			if(e == EnumFacing.NORTH)
			{
				if(world.getTileEntity(pos.add(0, 0, 8)) instanceof TileEntityFusionMultiblock)
				{
					if(((TileEntityFusionMultiblock)world.getTileEntity(pos.add(0, 0, 8))).isStructureValid(world))
					{
						player.openGui(MainRegistry.instance, ModBlocks.guiID_fusion_multiblock, world, pos.getX(), pos.getY(), pos.getZ() + 8);
					} else {
						player.sendMessage(new TextComponentTranslation("[Fusion Reactor] Error: Reactor Structure not valid!"));
					}
				} else {
					player.sendMessage(new TextComponentTranslation("[Fusion Reactor] Error: Reactor Core not found!"));
				}
			}
			if(e == EnumFacing.SOUTH)
			{
				if(world.getTileEntity(pos.add(0, 0, -8)) instanceof TileEntityFusionMultiblock)
				{
					if(((TileEntityFusionMultiblock)world.getTileEntity(pos.add(0, 0, -8))).isStructureValid(world))
					{
						player.openGui(MainRegistry.instance, ModBlocks.guiID_fusion_multiblock, world, pos.getX(), pos.getY(), pos.getZ() - 8);
					} else {
						player.sendMessage(new TextComponentTranslation("[Fusion Reactor] Error: Reactor Structure not valid!"));
					}
				} else {
					player.sendMessage(new TextComponentTranslation("[Fusion Reactor] Error: Reactor Core not found!"));
				}
			}
			if(e == EnumFacing.WEST)
			{
				if(world.getTileEntity(pos.add(8, 0, 0)) instanceof TileEntityFusionMultiblock)
				{
					if(((TileEntityFusionMultiblock)world.getTileEntity(pos.add(8, 0, 0))).isStructureValid(world))
					{
						player.openGui(MainRegistry.instance, ModBlocks.guiID_fusion_multiblock, world, pos.getX() + 8, pos.getY(), pos.getZ());
					} else {
						player.sendMessage(new TextComponentTranslation("[Fusion Reactor] Error: Reactor Structure not valid!"));
					}
				} else {
					player.sendMessage(new TextComponentTranslation("[Fusion Reactor] Error: Reactor Core not found!"));
				}
			}
			if(e == EnumFacing.EAST)
			{
				if(world.getTileEntity(pos.add(-8, 0, 0)) instanceof TileEntityFusionMultiblock)
				{
					if(((TileEntityFusionMultiblock)world.getTileEntity(pos.add(-8, 0, 0))).isStructureValid(world))
					{
						player.openGui(MainRegistry.instance, ModBlocks.guiID_fusion_multiblock, world, pos.getX() - 8, pos.getY(), pos.getZ());
					} else {
						player.sendMessage(new TextComponentTranslation("[Fusion Reactor] Error: Reactor Structure not valid!"));
					}
				} else {
					player.sendMessage(new TextComponentTranslation("[Fusion Reactor] Error: Reactor Core not found!"));
				}
			}
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getIndex();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
	}
	
	
	
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
	}
	
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
	{
	   return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
	}

}
