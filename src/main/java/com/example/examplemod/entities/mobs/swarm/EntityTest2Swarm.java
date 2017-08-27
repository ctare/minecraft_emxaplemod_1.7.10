package com.example.examplemod.entities.mobs.swarm;

import com.example.examplemod.entities.particles.SwarmParticleManager;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Calendar;

import static com.example.examplemod.utils.Calc.randint;
import static com.example.examplemod.utils.Calc.random;

/**
 * Created by ctare on 2017/08/23.
 */
public class EntityTest2Swarm extends EntityFlying implements IMob{
    SwarmParticleManager particleManager = new SwarmParticleManager();

    public EntityTest2Swarm(World p_i1738_1_) {
        super(p_i1738_1_);
        setSize(2.0f, 2.0f);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5d);
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(5d);
//        getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2 + 4);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        motionY *= 0.60000002384185791D;
        particleManager
                .setColor(0, 0, randint(255))
                .addParticle(this);
    }

    @Override
    public boolean getCanSpawnHere() {
        int i = MathHelper.floor_double(this.boundingBox.minY);

        if (i >= 63) {
            return false;
        } else {
            int j = MathHelper.floor_double(this.posX);
            int k = MathHelper.floor_double(this.posZ);
            int l = this.worldObj.getBlockLightValue(j, i, k);
            byte b0 = 4;
            Calendar calendar = this.worldObj.getCurrentDate();

            if ((calendar.get(2) + 1 != 10 || calendar.get(5) < 20) && (calendar.get(2) + 1 != 11 || calendar.get(5) > 3)) {
                if (this.rand.nextBoolean()) {
                    return false;
                }
            } else {
                b0 = 7;
            }

            return l > this.rand.nextInt(b0) ? false : super.getCanSpawnHere();
        }
    }
}
