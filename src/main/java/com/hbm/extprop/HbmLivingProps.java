package com.hbm.extprop;

import java.util.UUID;

import com.hbm.lib.ModDamageSource;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class HbmLivingProps implements IExtendedEntityProperties {
	
	public static final String key = "NTM_EXT_LIVING";
	public static final UUID digamma_UUID = UUID.fromString("2a3d8aec-5ab9-4218-9b8b-ca812bdf378b");
	public EntityLivingBase entity;
	
	/// VALS ///
	private float radiation;
	private float digamma;
	
	public HbmLivingProps(EntityLivingBase entity) {
		this.entity = entity;
	}
	
	/// DATA ///
	public static HbmLivingProps registerData(EntityLivingBase entity) {
		
		entity.registerExtendedProperties(key, new HbmLivingProps(entity));
		return (HbmLivingProps) entity.getExtendedProperties(key);
	}
	
	public static HbmLivingProps getData(EntityLivingBase entity) {
		
		HbmLivingProps props = (HbmLivingProps) entity.getExtendedProperties(key);
		return props != null ? props : registerData(entity);
	}
	
	/// RADIATION ///
	public static float getRadiation(EntityLivingBase entity) {
		return getData(entity).radiation;
	}
	
	public static void setRadiation(EntityLivingBase entity, float rad) {
		getData(entity).radiation = rad;
	}
	
	public static void incrementRadiation(EntityLivingBase entity, float rad) {
		HbmLivingProps data = getData(entity);
		float radiation = getData(entity).radiation + rad;
		
		if(radiation > 2500)
			radiation = 2500;
		if(radiation < 0)
			radiation = 0;
		
		data.setRadiation(entity, radiation);
	}
	
	/// DIGAMA ///
	public static float getDigamma(EntityLivingBase entity) {
		return getData(entity).digamma;
	}
	
	public static void setDigamma(EntityLivingBase entity, float digamma) {
		getData(entity).digamma = digamma;
		
		float healthMod = (float)Math.pow(0.5, digamma) - 1F;
		
		IAttributeInstance attributeinstance = entity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth);
		
		try {
			attributeinstance.removeModifier(attributeinstance.getModifier(digamma_UUID));
		} catch(Exception ex) { }
		
		attributeinstance.applyModifier(new AttributeModifier(digamma_UUID, "digamma", healthMod, 2));
		
		if(entity.getHealth() > entity.getMaxHealth()) {
			entity.setHealth(entity.getMaxHealth());
		}
		
		if((entity.getMaxHealth() <= 0 || digamma >= 10.0F) && entity.isEntityAlive()) {
			entity.setAbsorptionAmount(0);
			entity.attackEntityFrom(ModDamageSource.radiation, 500F);
			entity.setHealth(0);
			
			if(entity.isEntityAlive())
				entity.onDeath(ModDamageSource.radiation);
		}
	}
	
	public static void incrementDigamma(EntityLivingBase entity, float digamma) {
		HbmLivingProps data = getData(entity);
		float dRad = getDigamma(entity) + digamma;
		
		if(dRad > 10)
			dRad = 10;
		if(dRad < 0)
			dRad = 0;
		
		data.setDigamma(entity, dRad);
	}

	@Override
	public void init(Entity entity, World world) { }

	@Override
	public void saveNBTData(NBTTagCompound nbt) {
		
		NBTTagCompound props = new NBTTagCompound();
		
		props.setFloat("hfr_radiation", radiation);
		props.setFloat("hfr_digamma", digamma);
		
		nbt.setTag("HbmLivingProps", props);
	}

	@Override
	public void loadNBTData(NBTTagCompound nbt) {
		
		NBTTagCompound props = (NBTTagCompound) nbt.getTag("HbmLivingProps");
		
		if(props != null) {
			radiation = props.getFloat("hfr_radiation");
			digamma = props.getFloat("hfr_digamma");
		}
	}
}
