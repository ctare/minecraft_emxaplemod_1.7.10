package com.example.examplemod.utils;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;

/**
 * Created by ctare on 2017/08/24.
 */
public final class Mob {
    private Mob() {
    }

    public static boolean attackEntityAsMob(EntityLivingBase org, Entity p_70652_1_) {
        float f = (float) org.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
        int i = 0;

        if (p_70652_1_ instanceof EntityLivingBase) {
            f += EnchantmentHelper.getEnchantmentModifierLiving(org, (EntityLivingBase) p_70652_1_);
            i += EnchantmentHelper.getKnockbackModifier(org, (EntityLivingBase) p_70652_1_);
        }

        boolean flag = p_70652_1_.attackEntityFrom(DamageSource.causeMobDamage(org), f);

        if (flag) {
            if (i > 0) {
                p_70652_1_.addVelocity((double) (-MathHelper.sin(org.rotationYaw * (float) Math.PI / 180.0F) * (float) i * 0.5F), 0.1D, (double) (MathHelper.cos(org.rotationYaw * (float) Math.PI / 180.0F) * (float) i * 0.5F));
                org.motionX *= 0.6D;
                org.motionZ *= 0.6D;
            }

            int j = EnchantmentHelper.getFireAspectModifier(org);

            if (j > 0) {
                p_70652_1_.setFire(j * 4);
            }

            if (p_70652_1_ instanceof EntityLivingBase) {
                EnchantmentHelper.func_151384_a((EntityLivingBase) p_70652_1_, org);
            }

            EnchantmentHelper.func_151385_b(org, p_70652_1_);
        }

        return flag;
    }

    public static void attackEntity(EntityLivingBase org, Entity p_70785_1_, float p_70785_2_) {
        if (org.attackTime <= 0 && p_70785_2_ < 2.0F && p_70785_1_.boundingBox.maxY > org.boundingBox.minY && p_70785_1_.boundingBox.minY < org.boundingBox.maxY) {
            org.attackTime = 20;
            org.attackEntityAsMob(p_70785_1_);
        }
    }
}
