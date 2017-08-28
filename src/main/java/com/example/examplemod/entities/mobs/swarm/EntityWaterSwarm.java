package com.example.examplemod.entities.mobs.swarm;

import com.example.examplemod.entities.mobs.common.EntityWaterMob;
import com.example.examplemod.entities.particles.SwarmParticleManager;
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
public class EntityWaterSwarm extends EntityWaterMob implements ISwarm {
    private SwarmParticleManager particleManager = new SwarmParticleManager().changeSpeedAndTurn(1.4f);

    public EntityWaterSwarm(World p_i1738_1_) {
        super(p_i1738_1_);
        setSize(1.0f, 1.0f);
    }

    @Override
    protected int getExperiencePoints(EntityPlayer p_70693_1_) {
        return 7;
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
    public boolean attackEntityFrom(DamageSource damageSource, float p_70097_2_) {
//        Entity from = damageSource.getEntity();
//        if(!worldObj.isRemote && from != null && from instanceof EntityLivingBase){
//            EntityLivingBase elb = (EntityLivingBase) from;
//            elb.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 1000, 10));
//        }
        return super.attackEntityFrom(damageSource, p_70097_2_);
    }

    protected Entity findPlayerToAttack() {
        double range = 12.0D;
        AxisAlignedBB box = this.boundingBox.expand(range, range * 1.2, range);

        List list = worldObj.getEntitiesWithinAABB(IMob.class, box);
        Entity target = null;
        double d0 = Double.MAX_VALUE;

        for (Object entity : list) {
            Entity entity2 = (Entity) entity;

            if (!(entity2 instanceof ISwarm) && this.canEntityBeSeen(entity2)) {
                double d1 = this.getDistanceSqToEntity(entity2);

                if (d1 <= d0) {
                    target = entity2;
                    d0 = d1;
                }
            }
        }

        return target;
    }
}
