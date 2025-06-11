package com.hbm.particle;

import com.hbm.main.ModEventHandlerClient;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class ParticleGlow extends EntityFX {

	private float peakScale;
	
	public ParticleGlow(World world, double x, double y, double z, double mX, double mY, double mZ, float scale) {
		super(world, x, y, z, mX, mY, mZ);
		this.particleIcon = ModEventHandlerClient.particleFlare;
		this.particleRed = 1.0F;
		this.particleGreen = 1.0F;
		this.particleBlue = 1.0F;
		this.particleScale = peakScale = scale;
		this.motionX = mX;
		this.motionY = mY;
		this.motionZ = mZ;
		this.particleAge = 1;
		this.particleMaxAge = 50 + world.rand.nextInt(50);
		this.particleAlpha = 0.8F;
		this.noClip = true;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();

		// Increases in size to maximum then returns to minimum
		float t = (float)particleAge / (float)particleMaxAge;
		t = Math.abs(0.5F - (t - 0.5F)) * 2;

		particleScale = peakScale * (float)Math.sin(t * Math.PI / 2);
	}

	@Override
	public int getFXLayer() {
		return 1;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float p_70070_1_) {
		return 15728880;
	}

}
