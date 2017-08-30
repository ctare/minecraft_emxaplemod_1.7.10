package com.example.examplemod.entities.mobs.swarm;

import com.example.examplemod.entities.particles.SwarmParticleManager;
import com.example.examplemod.utils.Swarm;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.List;

import static com.example.examplemod.utils.Calc.randint;

/**
 * Created by ctare on 2017/08/30.
 */
public class EntityEnderSwarm extends EntityMob {
    private SwarmParticleManager particleManager = new SwarmParticleManager(50).changeSpeedAndTurn(1.4f);
    public EntityEnderSwarm(World p_i1738_1_) {
        super(p_i1738_1_);
        setSize(1.0f, 1.0f);
    }

    @Override
    protected int getExperiencePoints(EntityPlayer p_70693_1_) {
        return Swarm.EXP;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        int gray = randint(0, 50);
        particleManager
                .setColor(gray, gray, gray)
                .addParticle(this);
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
}
