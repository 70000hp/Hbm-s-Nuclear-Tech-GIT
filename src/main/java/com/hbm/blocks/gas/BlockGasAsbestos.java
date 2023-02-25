package com.hbm.blocks.gas;

import java.util.Random;

import com.hbm.capability.HbmLivingProps;
import com.hbm.lib.ForgeDirection;
import com.hbm.util.ArmorRegistry;
import com.hbm.util.ArmorRegistry.HazardClass;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockGasAsbestos extends BlockGasBase {

	public BlockGasAsbestos(String s) {
		super(0.6F, 0.6F, 0.5F, s);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World world, BlockPos pos, Random rand){
		super.randomDisplayTick(stateIn, world, pos, rand);
		if(world.rand.nextInt(5) == 0)
			world.spawnParticle(EnumParticleTypes.TOWN_AURA, pos.getX() + rand.nextFloat(), pos.getY() + rand.nextFloat(), pos.getZ() + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
	}
	
	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entity){
		if(entity instanceof EntityLivingBase) {
			
			EntityLivingBase living = (EntityLivingBase) entity;
			
			if(!ArmorRegistry.hasProtection(living, EntityEquipmentSlot.HEAD, HazardClass.PARTICLE_FINE))
				HbmLivingProps.incrementAsbestos(living, 1);
		}
	}
	

	@Override
	public ForgeDirection getFirstDirection(World world, int x, int y, int z) {
		
		if(world.rand.nextInt(5) == 0)
			return ForgeDirection.DOWN;
		
		return ForgeDirection.getOrientation(world.rand.nextInt(6));
	}

	@Override
	public ForgeDirection getSecondDirection(World world, int x, int y, int z) {
		return this.randomHorizontal(world);
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {

		if(!world.isRemote && rand.nextInt(50) == 0) {
			world.setBlockToAir(pos);
			return;
		}
		
		super.updateTick(world, pos, state, rand);
	}
}