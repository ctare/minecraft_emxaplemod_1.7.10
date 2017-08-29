package com.example.examplemod.entities.mobs.swarm;

import com.example.examplemod.entities.mobs.common.EntityLiquidMob;
import com.example.examplemod.entities.particles.SwarmParticleManager;
import com.example.examplemod.utils.Swarm;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.List;

import static com.example.examplemod.utils.Calc.randint;

/**
 * Created by ctare on 2017/08/23.
 */
public class EntityWaterSwarm extends EntityLiquidMob implements ISwarm {
    private SwarmParticleManager particleManager = new SwarmParticleManager().changeSpeedAndTurn(1.4f);

    public EntityWaterSwarm(World p_i1738_1_) {
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
        particleManager
                .setColor(90, randint(90, 150), randint(170, 255))
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
