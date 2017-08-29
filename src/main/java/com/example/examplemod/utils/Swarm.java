package com.example.examplemod.utils;

import com.example.examplemod.entities.mobs.swarm.ISwarm;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.IMob;
import net.minecraft.util.AxisAlignedBB;

import java.util.List;

/**
 * Created by ctare on 2017/08/29.
 */
public final class Swarm {
    public static final int EXP = 7;
    private Swarm(){}

    public static Entity attackTarget(EntityLiving self) {
        double range = 12.0D;
        AxisAlignedBB box = self.boundingBox.expand(range, range * 1.2, range);

        List list = self.worldObj.getEntitiesWithinAABB(IMob.class, box);
        Entity target = null;
        double d0 = Double.MAX_VALUE;

        for (Object entity : list) {
            Entity entity2 = (Entity) entity;

            if (!(entity2 instanceof ISwarm) && self.canEntityBeSeen(entity2)) {
                double d1 = self.getDistanceSqToEntity(entity2);

                if (d1 <= d0) {
                    target = entity2;
                    d0 = d1;
                }
            }
        }

        return target;
    }
}
