package com.example.examplemod.entities.mobs.swarm;

import com.example.examplemod.entities.particles.FXEnderSwarm;
import com.example.examplemod.entities.particles.SwarmParticleManager;
import com.example.examplemod.utils.Swarm;
import net.minecraft.block.Block;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;

import java.util.List;

import static com.example.examplemod.utils.Calc.randint;
import static com.example.examplemod.utils.Calc.random;

/**
 * Created by ctare on 2017/08/30.
 */
public class EntityEnderSwarm extends EntityMob implements ISwarm{
    private SwarmParticleManager particleManager = new SwarmParticleManager(50).setParticleGenerator(new SwarmParticleManager.ParticleGenerator() {
        @Override
        public EntityFX generate(Entity target, float r, float g, float b, float speed, float turnSpeed, float particleGravity) {
            return new FXEnderSwarm(target.worldObj,
                    target.posX + (random() - random()) * 2.0,
                    target.posY + (random() - random()) * 2.0,
                    target.posZ + (random() - random()) * 2.0,
                    target, r, g, b, speed, turnSpeed, particleGravity);
        }
    });
    public EntityEnderSwarm(World p_i1738_1_) {
        super(p_i1738_1_);
        setSize(1.0f, 1.0f);
    }

    @Override
    protected int getExperiencePoints(EntityPlayer p_70693_1_) {
        return Swarm.EXP;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(7.0);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        int gray = randint(0, 50);
        particleManager
                .setColor(gray, gray, gray)
                .addParticle(this);
        if (entityToAttack != null && random() < 0.01 && this.getDistanceSqToEntity(entityToAttack) > 25) {
            teleportToEntity(entityToAttack);
        }
    }

    @Override
    public boolean getCanSpawnHere() {
        List l = this.worldObj.getEntitiesWithinAABB(EntityWaterSwarm.class, this.boundingBox.expand(160.0D, 160.0D, 160.0D));
        return l.size() == 0 && super.getCanSpawnHere();
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 1;
    }

    @Override
    protected Entity findPlayerToAttack() {
        return Swarm.attackTarget(this);
    }

    @Override
    public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_) {
        if (p_70097_1_.getEntity() != null && this.getDistanceSqToEntity(p_70097_1_.getEntity()) > 36) {
            teleportToEntity(p_70097_1_.getEntity());
        }
        if (p_70097_1_ == DamageSource.inWall || p_70097_1_ == DamageSource.drown) {
            teleportToRandom(this.posX, this.posY, this.posZ, 50);
        }
        return super.attackEntityFrom(p_70097_1_, p_70097_2_);
    }

    protected void teleportToEntity(Entity p_70816_1_) {
        this.teleportTo(p_70816_1_.posX, p_70816_1_.posY, p_70816_1_.posZ);
    }

    protected void teleportTo(double x, double y, double z) {
        double preX = this.posX;
        double preY = this.posY;
        double preZ = this.posZ;
        enderParticle(worldObj, (short)128, preX, preY, preZ);
        setPosition(x, y, z);

        this.worldObj.playSoundEffect(x, y, z, "mob.endermen.portal", 1.0F, 1.0F);
        this.playSound("mob.endermen.portal", 1.0F, 1.0F);
    }

    protected void teleportToRandom(double x, double y, double z, double range) {
        teleportTo(x + (random() - 0.5) * range, y + (random() - 0.5) * range, z + (random() - 0.5) * range);
    }

    public static void enderParticle(World world, short particleNum, double x, double y, double z){
        enderParticle(world, particleNum, x, y, z, 4, 4, 4);
    }

    public static void enderParticle(World world, short particleNum, double x, double y, double z, double sizeX, double sizeY, double sizeZ){
        for (int l = 0; l < particleNum; ++l) {
            float f = (random() - 0.5F) * 0.2F;
            float f1 = (random() - 0.5F) * 0.2F;
            float f2 = (random() - 0.5F) * 0.2F;
            double d7 = x + (random() - 0.5D) * sizeX;
            double d8 = y + random() * sizeY;
            double d9 = z + (random() - 0.5D) * sizeZ;
            world.spawnParticle("portal", d7, d8, d9, (double)f, (double)f1, (double)f2);
        }
    }

    public static void enderParticleTo(World world, short particleNum, double x, double y, double z, double targetX, double targetY, double targetZ){
        enderParticleTo(world, particleNum, x, y, z, targetX, targetY, targetZ, 4, 4, 4);
    }

    public static void enderParticleTo(World world, short particleNum, double x, double y, double z, double targetX, double targetY, double targetZ, double sizeX, double sizeY, double sizeZ){
        for (int l = 0; l < particleNum; ++l) {
            double d6 = (double)l / ((double)particleNum - 1.0D);
            float f = (random() - 0.5F) * 0.2F;
            float f1 = (random() - 0.5F) * 0.2F;
            float f2 = (random() - 0.5F) * 0.2F;
            double d7 = x + (targetX - x) * d6 + (random() - 0.5D) * sizeX;
            double d8 = y + (targetY - y) * d6 + random() * sizeY;
            double d9 = z + (targetZ - z) * d6 + (random() - 0.5D) * sizeZ;
            world.spawnParticle("portal", d7, d8, d9, (double)f, (double)f1, (double)f2);
        }

    }
}
