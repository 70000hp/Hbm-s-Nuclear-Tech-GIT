package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.interfaces.IBulletImpactBehavior;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

public class GunVortexFactory {
       public static GunConfiguration getVortexConfig() {
		
		GunConfiguration config = new GunConfiguration();
		config.rateOfFire = 1;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_RELEASE;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.reloadDuration = 10;
		config.reloadSoundEnd = false;
		config.firingDuration = 1;
		config.durability = 5000;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.ammoCap = 10;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_ARROWS;
		config.firingSound = "hbm:weapon.tauShoot";
		config.reloadSound = "hbm:weapon.tauChargeLoop2";
		
		config.name = "Visual Operation Ranged Tactical Electromagnetic Xenoblaster";
		if(MainRegistry.polaroidID == 11){
			config.comment.add("You maybe should possibly consider the idea of obeying Xon");
		}
		
		else{
			config.comment.add("OBEY XON");
        }
		config.manufacturer = "Xon Corporation";
        
		
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.VORTEX_ENERGY);
		return config;
	}
       public static GunConfiguration getAltConfig() {
   		
   		GunConfiguration config = new GunConfiguration();
   		
   		config.rateOfFire = 15;
   		config.roundsPerCycle = 1;
   		config.gunMode = GunConfiguration.MODE_NORMAL;
   		config.firingMode = GunConfiguration.FIRE_MANUAL;
   		config.reloadDuration = 20;
   		config.firingDuration = 0;
   		config.ammoCap = 0;
   		config.reloadType = GunConfiguration.RELOAD_NONE;
   		config.allowsInfinity = true;
   		config.firingSound = "hbm:weapon.singFlyby";
   		
   		config.config = new ArrayList<Integer>();
   		config.config.add(BulletConfigSyncingUtil.VORTEX_ENERGY);
   		
   		return config;
   	}

        public static BulletConfiguration getEnergyConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.ammo = ModItems.ammo_cell;
		bullet.ammoCount = 1;
		bullet.wear = 50;
		bullet.velocity = 100F;
		bullet.spread = 0F;
		bullet.maxAge = 100;
		bullet.gravity = 0D;
		bullet.dmgMin = 240;
	    bullet.dmgMax = 300;
		bullet.bulletsMin = 1;
		bullet.bulletsMax = 1;
		bullet.style = bullet.STYLE_BOLT;
		bullet.trail = bullet.BOLT_LACUNAE;
		return bullet;
	}
        public static BulletConfiguration getAltEnergyConfig() {
    		
    		BulletConfiguration bullet = new BulletConfiguration();
    		
    		bullet.ammo = ModItems.ammo_cell;
    		bullet.ammoCount = 1;
    		bullet.wear = 50;
    		bullet.velocity = 100F;
    		bullet.spread = 0F;
    		bullet.maxAge = 100;
    		bullet.gravity = 0D;
    		bullet.dmgMin = 240;
    	    bullet.dmgMax = 300;
    		bullet.bulletsMin = 1;
    		bullet.bulletsMax = 1;
    		bullet.style = bullet.STYLE_ORB;
    		
    		bullet.liveAfterImpact = true;
    		
    		return bullet;
    	}    
        public static BulletConfiguration getOuchConfig() {
    		
    		BulletConfiguration bullet = getAltEnergyConfig();
    		bullet.dmgMin = 40;
    		bullet.dmgMax = 50;
    		bullet.velocity = 10F;
    		bullet.maxAge = 2;
    		
    		bullet.dmgBypass = true;
    		bullet.doesPenetrate = true;
    		bullet.liveAfterImpact = true;
    		
    		bullet.bImpact = new IBulletImpactBehavior() {

    			@Override
    			public void behaveBlockHit(EntityBulletBase bullet, int x, int y, int z) {
    				
    				if(!bullet.worldObj.isRemote) {
    					ExplosionChaos.explodeZOMG(bullet.worldObj, (int)Math.floor(bullet.posX), (int)Math.floor(bullet.posY), (int)Math.floor(bullet.posZ), 10);
    					bullet.worldObj.playSoundEffect(bullet.posX, bullet.posY, bullet.posZ, "hbm:entity.bombDet", 5.0F, 1.0F);
        				ExplosionLarge.spawnParticles(bullet.worldObj, bullet.posX, bullet.posY, bullet.posZ, 10);
    				}
    			}
    		};
    		return bullet;
    	}
        
}
