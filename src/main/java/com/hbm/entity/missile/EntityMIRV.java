package com.hbm.entity.missile;

import java.util.ArrayList;
import java.util.List;

import api.hbm.entity.IRadarDetectable;
import api.hbm.entity.IRadarDetectable.RadarTargetType;

import api.hbm.entity.IRadarDetectableNT;
import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.entity.logic.IChunkLoader;

import com.hbm.entity.particle.EntitySmokeFX;
import com.hbm.entity.projectile.EntityThrowableNT;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;

public class EntityMIRV extends EntityThrowableNT implements IChunkLoader, IRadarDetectableNT {
	
	private Ticket loaderTicket;
	public int health = 25;
	
	public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_) {
		if(this.isEntityInvulnerable()) {
			return false;
		} else {
			if(!this.isDead && !this.worldObj.isRemote) {
				health -= p_70097_2_;

				if(this.health <= 0) {
					this.setDead();
					this.killMissile();
				}
			}

			return true;
		}
	}

	private void killMissile() {
		ExplosionLarge.explode(worldObj, posX, posY, posZ, 5, true, false, true);
		ExplosionLarge.spawnShrapnelShower(worldObj, posX, posY, posZ, motionX, motionY, motionZ, 15, 0.075);
	}
	public EntityMIRV(World p_i1582_1_) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
	}
	
	@Override
	protected void entityInit() {
		init(ForgeChunkManager.requestTicket(MainRegistry.instance, worldObj, Type.ENTITY));
		this.dataWatcher.addObject(8, Integer.valueOf(this.health));
	}
	
	@Override
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;

		this.motionY -= 0.03;

		this.rotation();

		if(this.worldObj.getBlock((int) this.posX, (int) this.posY, (int) this.posZ) != Blocks.air) {
			if(!this.worldObj.isRemote) {
				worldObj.spawnEntityInWorld(EntityNukeExplosionMK5.statFac(worldObj, BombConfig.mirvRadius, posX, posY, posZ));
				EntityNukeTorex.statFac(worldObj, posX, posY, posZ, BombConfig.mirvRadius);
			}
			this.setDead();
		}

		//this.worldObj.spawnEntityInWorld(new EntitySmokeFX(this.worldObj, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0));
		loadNeighboringChunks((int)(posX / 16), (int)(posZ / 16));
	}

	protected void rotation() {
		float f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
		this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

		for(this.rotationPitch = (float) (Math.atan2(this.motionY, f2) * 180.0D / Math.PI) - 90; this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
			;
		}

		while(this.rotationPitch - this.prevRotationPitch >= 180.0F) {
			this.prevRotationPitch += 360.0F;
		}

		while(this.rotationYaw - this.prevRotationYaw < -180.0F) {
			this.prevRotationYaw -= 360.0F;
		}

		while(this.rotationYaw - this.prevRotationYaw >= 180.0F) {
			this.prevRotationYaw += 360.0F;
		}
	}

	@Override
	protected void onImpact(MovingObjectPosition p_70184_1_) {

	}
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		return distance < 500000;
	}

	@Override public String getUnlocalizedName() { return "radar.target.tier4"; }
	@Override public int getBlipLevel() { return IRadarDetectableNT.TIER4; }
	@Override
	public boolean canBeSeenBy(Object radar) {
		return true;
	}

	@Override
	public boolean paramsApplicable(RadarScanParams params) {
		return params.scanMissiles;
	}

	@Override
	public boolean suppliesRedstone(RadarScanParams params) {
		return true;
	}

	@Override
    public void init(Ticket ticket) {
	   if(!worldObj.isRemote) {

          if(ticket != null) {

              if(loaderTicket == null) {

            	loaderTicket = ticket;
            	loaderTicket.bindEntity(this);
            	loaderTicket.getModData();
              }

            ForgeChunkManager.forceChunk(loaderTicket, new ChunkCoordIntPair(chunkCoordX, chunkCoordZ));
        }
     }
   }

   List<ChunkCoordIntPair> loadedChunks = new ArrayList<ChunkCoordIntPair>();
	public void loadNeighboringChunks(int newChunkX, int newChunkZ) {
		if(!worldObj.isRemote && loaderTicket != null) {

			clearChunkLoader();

			loadedChunks.clear();
			loadedChunks.add(new ChunkCoordIntPair(newChunkX, newChunkZ));
			//loadedChunks.add(new ChunkCoordIntPair(newChunkX + (int) Math.floor((this.posX + this.motionX * this.motionMult()) / 16D), newChunkZ + (int) Math.floor((this.posZ + this.motionZ * this.motionMult()) / 16D)));

			for(ChunkCoordIntPair chunk : loadedChunks) {
				ForgeChunkManager.forceChunk(loaderTicket, chunk);
			}
		}
	}

	public void clearChunkLoader() {
		if(!worldObj.isRemote && loaderTicket != null) {
			for(ChunkCoordIntPair chunk : loadedChunks) {
				ForgeChunkManager.unforceChunk(loaderTicket, chunk);
			}
		}
	}
}
