package com.example.examplemod.entities.mobs.swarm;

import com.example.examplemod.entities.particles.ParticleEngine;
import com.example.examplemod.entities.particles.SwarmParticleManager;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.world.World;

import static com.example.examplemod.utils.Calc.randint;

/**
 * Created by ctare on 2017/08/23.
 */
public class EntityWaterSwarm extends EntityWaterMob{
    SwarmParticleManager particleManager = new SwarmParticleManager()
            .setColor(90, randint(90, 150), randint(170, 255))
            .changeSpeedAndTurn(1.4f);

    public EntityWaterSwarm(World p_i1738_1_) {
        super(p_i1738_1_);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5d);
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(5d);
        getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2 + 4);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        motionY *= 0.60000002384185791D;
        particleManager.addParticle(this);
    }

    public boolean getCanSpawnHere() {
        return this.posY > 45.0D && this.posY < 63.0D && super.getCanSpawnHere();
    }
}
