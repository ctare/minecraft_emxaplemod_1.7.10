package com.example.examplemod.entities.mobs.swarm;

import com.example.examplemod.entities.mobs.common.EntityFlyingMob;
import com.example.examplemod.entities.mobs.common.EntityWaterMob;
import com.example.examplemod.entities.particles.SwarmParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

import static com.example.examplemod.utils.Calc.randint;

/**
 * Created by ctare on 2017/08/23.
 */
public class EntityTest2Swarm extends EntityWaterMob implements ISwarm{
    SwarmParticleManager particleManager = new SwarmParticleManager();

    public EntityTest2Swarm(World p_i1738_1_) {
        super(p_i1738_1_);
        setSize(1.0f, 1.0f);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        int rdm = randint(150, 255);
        particleManager
                .setColor(90, randint(90, 150), randint(170, 255))
                .addParticle(this);
    }

//
//    protected float getSoundVolume() {
//        return 0.1F;
//    }
//
//    protected float getSoundPitch() {
//        return super.getSoundPitch() * 0.95F;
//    }
//
//    protected String getLivingSound() {
//        return this.getIsBatHanging() && this.rand.nextInt(4) != 0?null:"mob.bat.idle";
//    }
//
//    protected String getHurtSound() {
//        return "mob.bat.hurt";
//    }
//
//    protected String getDeathSound() {
//        return "mob.bat.death";
//    }
}
