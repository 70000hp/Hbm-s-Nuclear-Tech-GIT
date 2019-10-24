package com.hbm.explosion;

import java.util.Random;

import com.hbm.entity.particle.EntityDSmokeFX;

import net.minecraft.world.World;

public class ExplosionLarge {

	static Random rand = new Random();

	public static void spawnParticles(World world, double x, double y, double z, int count) {
		
		for(int i = 0; i < count; i++) {
			EntityDSmokeFX fx = new EntityDSmokeFX(world, x, y, z, 0.0, 0.0, 0.0);
			//fx.posX = x;
			//fx.posY = y;
			//fx.posZ = z;
			fx.motionY = rand.nextGaussian() * (1 + (count / 50));
			fx.motionX = rand.nextGaussian() * (1 + (count / 150));
			fx.motionZ = rand.nextGaussian() * (1 + (count / 150));
			world.spawnEntity(fx);
		}
	}
	
	public static void spawnRubble(World world, double x, double y, double z, int count) {
		
		for(int i = 0; i < count; i++) {
			EntityRubble rubble = new EntityRubble(world);
			rubble.posX = x;
			rubble.posY = y;
			rubble.posZ = z;
			rubble.motionY = 0.75						* (1 + ((count + rand.nextInt(count * 5))) / 25);
			rubble.motionX = rand.nextGaussian() * 0.75	* (1 + (count / 50));
			rubble.motionZ = rand.nextGaussian() * 0.75	* (1 + (count / 50));
			rubble.setMetaBasedOnBlock(Blocks.stone, 0);
			world.spawnEntityInWorld(rubble);
		}
	}

	public static void spawnShrapnels(World world, double x, double y, double z, int count) {
	
	for(int i = 0; i < count; i++) {
		EntityShrapnel shrapnel = new EntityShrapnel(world);
		shrapnel.posX = x;
		shrapnel.posY = y;
		shrapnel.posZ = z;
		shrapnel.motionY = ((rand.nextFloat() * 0.5) + 0.5) * (1 + (count / (15 + rand.nextInt(21)))) + (rand.nextFloat() / 50 * count);
		shrapnel.motionX = rand.nextGaussian() * 1	* (1 + (count / 50));
		shrapnel.motionZ = rand.nextGaussian() * 1	* (1 + (count / 50));
		shrapnel.setTrail(rand.nextInt(3) == 0);
		world.spawnEntityInWorld(shrapnel);
	}
}
	
	public static void explode(World world, double x, double y, double z, float strength, boolean cloud, boolean rubble, boolean shrapnel) {
		world.createExplosion(null, x, y, z, strength, true);
		if(cloud)
			spawnParticles(world, x, y, z, cloudFunction((int)strength));
		if(rubble)
			spawnRubble(world, x, y, z, rubbleFunction((int)strength));
		if(shrapnel)
			spawnShrapnels(world, x, y, z, shrapnelFunction((int)strength));
	}
	
	public static int cloudFunction(int i) {
		//return (int)(345 * (1 - Math.pow(Math.E, -i/15)) + 15);
		return (int)(545 * (1 - Math.pow(Math.E, -i/15)) + 15);
	}
	
	public static int rubbleFunction(int i) {
		return i/10;
	}
	
	public static int shrapnelFunction(int i) {
		return i/3;
	}
}
