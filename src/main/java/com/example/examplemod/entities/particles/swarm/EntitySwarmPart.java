package com.example.examplemod.entities.particles.swarm;

import com.example.examplemod.utils.Calc;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.awt.*;

import static com.example.examplemod.utils.Calc.randint;

/**
 * Created by ctare on 2017/08/31.
 */
public class EntitySwarmPart extends EntityMob {
    private Entity owner;
    public EntitySwarmPart(World p_i1738_1_) {
        super(p_i1738_1_);
        setSize(0.2f, 0.2f);
        this.yOffset = 0.5f;
    }

    public void setOwner(Entity owner){
        this.owner = owner;
    }
    /** A constantly increasing value that RenderXPOrb uses to control the colour shifting (Green / yellow) */
    public int xpColor;
    /** The age of the XP orb in ticks. */
    public int xpOrbAge;
    public int field_70532_c;
    /** The health of this XP orb. */
    private int xpOrbHealth = 5;
    /** This is how much XP this orb has. */
    /** The closest EntityPlayer to this orb. */
    /** Threshold color for tracking players */
    private int xpTargetColor;

    private Calc.Counter deathcounter = new Calc.Counter(0, 1, 50);
    private float particleGravity = 0.1f;

    Calc.Counter counter = new Calc.IterCounter(randint(12), 0.5, 11);
    Color color = new Color(0.2f, 0.8f, 0, 0.5f);

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float p_70070_1_) {
        float f1 = 0.5F;

        int i = super.getBrightnessForRender(p_70070_1_);
        int j = i & 255;
        int k = i >> 16 & 255;
        j += (int)(f1 * 15.0F * 16.0F);

        if (j > 240) {
            j = 240;
        }

        return j | k << 16;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate() {
//        super.onUpdate();
        if(owner == null || owner.isDead || (owner instanceof EntityLivingBase) && ((EntityLivingBase)owner).deathTime > 0) {
            deathcounter.nextCount();
            motionY -= particleGravity / 2.0F;
            if(deathcounter.getFloor() == 50)
                setDead();
        } else {
            motionY += particleGravity;
        }

//        motionX *= 0.98499999999999999D;
//        motionY *= 0.98499999999999999D;
//        motionZ *= 0.98499999999999999D;
//
//        float turnSpeed = 0.5f;
//        if(owner != null && !owner.isDead && (!(owner instanceof EntityLivingBase) || ((EntityLivingBase)owner).deathTime <= 0)) {
//            boolean hurt = false;
//            if(owner instanceof EntityLivingBase)
//                hurt = ((EntityLivingBase)owner).hurtTime > 0;
//            if(getDistanceSqToEntity(owner) > (double)owner.width && !hurt)
//                faceEntity(owner, turnSpeed / 2.0F + (float)rand.nextInt((int)(turnSpeed / 2.0F)), turnSpeed / 2.0F + (float)rand.nextInt((int)(turnSpeed / 2.0F)));
//            else
//                faceEntity(owner, -(turnSpeed / 2.0F + (float)rand.nextInt((int)(turnSpeed / 2.0F))), -(turnSpeed / 2.0F + (float)rand.nextInt((int)(turnSpeed / 2.0F))));
//            motionX = -MathHelper.sin((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F);
//            motionZ = MathHelper.cos((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F);
//            motionY = -MathHelper.sin((rotationPitch / 180F) * 3.141593F);
//        }
//
        if (this.field_70532_c > 0) {
            --this.field_70532_c;
        }

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= 0.029999999329447746D;

        if (this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)).getMaterial() == Material.lava) {
            this.motionY = 0.20000000298023224D;
            this.motionX = (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
            this.motionZ = (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
            this.playSound("random.fizz", 0.4F, 2.0F + this.rand.nextFloat() * 0.4F);
        }

//        this.func_145771_j(this.posX, (this.boundingBox.minY + this.boundingBox.maxY) / 2.0D, this.posZ);
        double d0 = 8.0D;

        if (this.xpTargetColor < this.xpColor - 20 + this.getEntityId() % 100) {
            if (this.owner == null) {
                this.owner = this.worldObj.getClosestPlayerToEntity(this, d0);
            }

            this.xpTargetColor = this.xpColor;
        }

        if (this.owner != null) {
            double d1 = (this.owner.posX - this.posX) / d0;
            double d2 = (this.owner.posY + (double)this.owner.getEyeHeight() - this.posY) / d0;
            double d3 = (this.owner.posZ - this.posZ) / d0;
            double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
            double d5 = 1.0D - d4;

//            if (d5 > 0.0D) {
                d5 *= d5;
                this.motionX += d1 / d4 * d5 * 0.1D;
                this.motionY += d2 / d4 * d5 * 0.1D;
                this.motionZ += d3 / d4 * d5 * 0.1D;
//            }
        }

        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        float f = 0.98F;

        if (this.onGround) {
            f = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ)).slipperiness * 0.98F;
        }

        this.motionX *= (double)f;
        this.motionY *= 0.9800000190734863D;
        this.motionZ *= (double)f;

        if (this.onGround) {
            this.motionY *= -0.8999999761581421D;
        }

        ++this.xpOrbAge;

        if (this.xpOrbAge >= 6000) {
            this.setDead();
        }
    }

    /**
     * Returns if this entity is in water and will end up adding the waters velocity to the entity
     */
    public boolean handleWaterMovement() {
        return this.worldObj.handleMaterialAcceleration(this.boundingBox, Material.water, this);
    }

    /**
     * Will deal the specified amount of damage to the entity if the entity isn't immune to fire damage. Args:
     * amountDamage
     */
    protected void dealFireDamage(int p_70081_1_) {
        this.attackEntityFrom(DamageSource.inFire, (float)p_70081_1_);
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_) {
        if (this.isEntityInvulnerable()) {
            return false;
        } else {
            this.setBeenAttacked();
            this.xpOrbHealth = (int)((float)this.xpOrbHealth - p_70097_2_);

            if (this.xpOrbHealth <= 0) {
                this.setDead();
            }

            return false;
        }
    }

    /**
     * Called by a player entity when they collide with an entity
     */
    public void onCollideWithPlayer(EntityPlayer player) {
        if (!this.worldObj.isRemote) {
            if (this.field_70532_c == 0 && player.xpCooldown == 0) {
//                this.setDead();
            }
        }
    }

    /**
     * If returns false, the item will not inflict any damage against entities.
     */
    public boolean canAttackWithItem() {
        return false;
    }
}
