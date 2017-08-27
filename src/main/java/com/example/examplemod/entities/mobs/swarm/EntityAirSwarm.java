package com.example.examplemod.entities.mobs.swarm;

import com.example.examplemod.entities.mobs.common.EntityFlyingMob;
import com.example.examplemod.entities.particles.SwarmParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

import static com.example.examplemod.utils.Calc.randint;

/**
 * Created by ctare on 2017/08/27.
 */
public class EntityAirSwarm extends EntityFlyingMob implements ISwarm{
    private SwarmParticleManager particleManager = new SwarmParticleManager().changeSpeedAndTurn(2);

    public EntityAirSwarm(World p_i1738_1_) {
        super(p_i1738_1_);
        setSize(1.0f, 1.0f);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        int rdm = randint(150, 255);
        particleManager
                .setColor(rdm, rdm - randint(20), randint(70, 120))
                .addParticle(this);
    }

    protected Entity findPlayerToAttack() {
        double range = 12.0D;
        AxisAlignedBB box = this.boundingBox.expand(range, range * 1.2, range);

        List list = worldObj.getEntitiesWithinAABB(IMob.class, box);
        Entity target = null;
        double d0 = Double.MAX_VALUE;

        for (Object entity : list) {
            Entity entity2 = (Entity) entity;

            if (!(entity2 instanceof ISwarm)) {
                double d1 = this.getDistanceSqToEntity(entity2);

                if (d1 <= d0) {
                    target = entity2;
                    d0 = d1;
                }
            }
        }

        return target;
//        return this.worldObj.findNearestEntityWithinAABB(IMob.class, , this);
//        return this.worldObj.getClosestVulnerablePlayerToEntity(this, var1);
    }
}
