package com.example.examplemod.entities.mobs.swarm;

import com.example.examplemod.entities.particles.SwarmParticleManager;
import com.example.examplemod.utils.Mob;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.List;

import static com.example.examplemod.utils.Calc.randint;

/**
 * Created by ctare on 2017/08/23.
 */
public class EntityWaterSwarm extends EntitySquid implements IMob {
    private SwarmParticleManager particleManager = new SwarmParticleManager().changeSpeedAndTurn(1.4f);
    private PathEntity pathToEntity;

    public EntityWaterSwarm(World p_i1738_1_) {
        super(p_i1738_1_);
        setSize(1.0f, 1.0f);
    }

    @Override
    protected int getExperiencePoints(EntityPlayer p_70693_1_) {
        return 7;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5d);
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(5d);
        getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
        getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        motionY *= 0.60000002384185791D;
        particleManager
                .setColor(90, randint(90, 150), randint(170, 255))
                .addParticle(this);
    }

    @Override
    protected Item getDropItem() {
        return null;
    }

    @Override
    protected void dropFewItems(boolean p_70628_1_, int p_70628_2_) {
//        super.dropFewItems(p_70628_1_, p_70628_2_);
    }

    @Override
    public boolean getCanSpawnHere() {
        List l = this.worldObj.getEntitiesWithinAABB(EntityWaterSwarm.class, this.boundingBox.expand(160.0D, 160.0D, 160.0D));
        return l.size() == 0 && super.getCanSpawnHere();
    }

    @Override
    protected Entity findPlayerToAttack() {
        EntityPlayer entityplayer = this.worldObj.getClosestVulnerablePlayerToEntity(this, 16.0D);
        return entityplayer != null && this.canEntityBeSeen(entityplayer) ? entityplayer : null;
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 1;
    }

    @Override
    protected void attackEntity(Entity p_70785_1_, float p_70785_2_) {
        Mob.attackEntity(this, p_70785_1_, p_70785_2_);
    }

    @Override
    public boolean attackEntityAsMob(Entity p_70652_1_) {
        return Mob.attackEntityAsMob(this, p_70652_1_);
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float p_70097_2_) {
//        System.out.println("been attacked");
//        System.out.println(String.valueOf(p_70097_2_));
//        System.out.println(damageSource);
//        System.out.println(damageSource.damageType);
//        System.out.println(damageSource.getEntity());
//        System.out.println(damageSource.getSourceOfDamage());
//        System.out.println(worldObj.findNearestEntityWithinAABB(IMob.class, this.boundingBox.expand(160, 160, 160), this));
//        System.out.println(worldObj.getClosestVulnerablePlayerToEntity(this, 5));

        Entity from = damageSource.getEntity();
        if(!worldObj.isRemote && from != null && from instanceof EntityLivingBase){
            EntityLivingBase elb = (EntityLivingBase) from;
            elb.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 1000, 10));
        }
        return super.attackEntityFrom(damageSource, p_70097_2_);
    }

//    private void updateAttackAction(){
//        this.worldObj.theProfiler.startSection("ai");
//
//        if (this.fleeingTick > 0 && --this.fleeingTick == 0) {
//            IAttributeInstance iattributeinstance = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
//            iattributeinstance.removeModifier(field_110181_i);
//        }
//
//        this.hasAttacked = this.isMovementCeased();
//        float f4 = 16.0F;
//
//        if (this.entityToAttack == null) {
//            this.entityToAttack = this.findPlayerToAttack();
//
//            if (this.entityToAttack != null) {
//                this.pathToEntity = this.worldObj.getPathEntityToEntity(this, this.entityToAttack, f4, true, false, false, true);
//            }
//        } else if (this.entityToAttack.isEntityAlive()) {
//            float f = this.entityToAttack.getDistanceToEntity(this);
//
//            if (this.canEntityBeSeen(this.entityToAttack)) {
//                this.attackEntity(this.entityToAttack, f);
//            }
//        } else {
//            this.entityToAttack = null;
//        }
//
//        if (this.entityToAttack instanceof EntityPlayerMP && ((EntityPlayerMP)this.entityToAttack).theItemInWorldManager.isCreative()) {
//            this.entityToAttack = null;
//        }
//
//        this.worldObj.theProfiler.endSection();
//
//        if (!this.hasAttacked && this.entityToAttack != null && (this.pathToEntity == null || this.rand.nextInt(20) == 0)) {
//            this.pathToEntity = this.worldObj.getPathEntityToEntity(this, this.entityToAttack, f4, true, false, false, true);
//        } else if (!this.hasAttacked && (this.pathToEntity == null && this.rand.nextInt(180) == 0 || this.rand.nextInt(120) == 0 || this.fleeingTick > 0) && this.entityAge < 100) {
//            this.updateWanderPath();
//        }
//
//        int i = MathHelper.floor_double(this.boundingBox.minY + 0.5D);
//        boolean flag = this.isInWater();
//        boolean flag1 = this.handleLavaMovement();
//        this.rotationPitch = 0.0F;
//
//        if (this.pathToEntity != null && this.rand.nextInt(100) != 0) {
//            this.worldObj.theProfiler.startSection("followpath");
//            Vec3 vec3 = this.pathToEntity.getPosition(this);
//            double d0 = (double) (this.width * 2.0F);
//
//            while (vec3 != null && vec3.squareDistanceTo(this.posX, vec3.yCoord, this.posZ) < d0 * d0) {
//                this.pathToEntity.incrementPathIndex();
//
//                if (this.pathToEntity.isFinished()) {
//                    vec3 = null;
//                    this.pathToEntity = null;
//                } else {
//                    vec3 = this.pathToEntity.getPosition(this);
//                }
//            }
//
//            this.isJumping = false;
//
//            if (vec3 != null) {
//                double d1 = vec3.xCoord - this.posX;
//                double d2 = vec3.zCoord - this.posZ;
//                double d3 = vec3.yCoord - (double) i;
//                float f1 = (float) (Math.atan2(d2, d1) * 180.0D / Math.PI) - 90.0F;
//                float f2 = MathHelper.wrapAngleTo180_float(f1 - this.rotationYaw);
//                this.moveForward = (float) this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
//
//                if (f2 > 30.0F) {
//                    f2 = 30.0F;
//                }
//
//                if (f2 < -30.0F) {
//                    f2 = -30.0F;
//                }
//
//                this.rotationYaw += f2;
//
//                if (this.hasAttacked && this.entityToAttack != null) {
//                    double d4 = this.entityToAttack.posX - this.posX;
//                    double d5 = this.entityToAttack.posZ - this.posZ;
//                    float f3 = this.rotationYaw;
//                    this.rotationYaw = (float) (Math.atan2(d5, d4) * 180.0D / Math.PI) - 90.0F;
//                    f2 = (f3 - this.rotationYaw + 90.0F) * (float) Math.PI / 180.0F;
//                    this.moveStrafing = -MathHelper.sin(f2) * this.moveForward * 1.0F;
//                    this.moveForward = MathHelper.cos(f2) * this.moveForward * 1.0F;
//                }
//
//                if (d3 > 0.0D) {
//                    this.isJumping = true;
//                }
//            }
//            if (this.entityToAttack != null)
//            {
//                this.faceEntity(this.entityToAttack, 30.0F, 30.0F);
//            }
//
//            if (this.isCollidedHorizontally && !this.hasPath())
//            {
//                this.isJumping = true;
//            }
//
//            if (this.rand.nextFloat() < 0.8F && (flag || flag1))
//            {
//                this.isJumping = true;
//            }
//
//            this.worldObj.theProfiler.endSection();
//        } else {
////            super.updateEntityActionState();
//            this.pathToEntity = null;
//        }
//    }
//
//    @Override
//    protected void updateEntityActionState() {
//        super.updateEntityActionState();
//        updateAttackAction();
//    }
}
