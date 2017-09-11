package com.example.examplemod.entities.mobs.swarm;

import com.example.examplemod.entities.particles.SwarmParticleManager;
import com.example.examplemod.utils.Swarm;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

import static com.example.examplemod.utils.Calc.randint;

/**
 * Created by ctare on 2017/09/09.
 */
public class EntityMudSwarm extends EntityMob implements ISwarm{
    private SwarmParticleManager particleManager = new SwarmParticleManager();

    public EntityMudSwarm(World p_i1745_1_) {
        super(p_i1745_1_);
        setSize(1.0f, 1.0f);
        this.getNavigator().setAvoidsWater(true);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, (byte)0);
    }

    @Override
    protected int getExperiencePoints(EntityPlayer p_70693_1_) {
        return Swarm.EXP;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(7.0);
    }

    @Override
    public int getTotalArmorValue() {
        return super.getTotalArmorValue() + 10;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        particleManager
                .setColor(110 + randint(50), 100 + randint(30), 0)
                .addParticle(this);

        if (!this.worldObj.isRemote) {
            this.setBesideClimbableBlock(this.isCollidedHorizontally);
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

    public boolean isBesideClimbableBlock() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
    }

    public void setBesideClimbableBlock(boolean p_70839_1_) {
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);

        if (p_70839_1_) {
            b0 = (byte)(b0 | 1);
        } else {
            b0 &= -2;
        }

        this.dataWatcher.updateObject(16, b0);
    }

    public boolean isOnLadder() {
        return this.isBesideClimbableBlock();
    }
}
