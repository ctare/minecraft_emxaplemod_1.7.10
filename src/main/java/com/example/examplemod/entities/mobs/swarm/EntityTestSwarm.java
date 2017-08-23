package com.example.examplemod.entities.mobs.swarm;

import com.example.examplemod.entities.particles.SwarmParticleManager;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;

import static com.example.examplemod.utils.Calc.randint;

/**
 * Created by ctare on 2017/08/23.
 */
public class EntityTestSwarm extends EntityMob {
    private SwarmParticleManager particle = new SwarmParticleManager(50).changeSpeedAndTurn(2);

    public EntityTestSwarm(World p_i1738_1_) {
        super(p_i1738_1_);
        setSize(2.0f, 2.0f);

//        // うろうろ移動するAIの追加
//        this.tasks.addTask(1, new EntityAIWander(this, 1.0D));
//        // 見回すAIの追加
//        this.tasks.addTask(2, new EntityAILookIdle(this));
    }

//    @Override
//    protected void entityInit() {
//        super.entityInit();
////        dataWatcher.addObject(16, (byte) 0);
//    }

//    @Override
//    protected float getSoundVolume() {
//        return 0.1f;
//    }

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
        int rgb = randint(155);
        int r = randint(255);
        int g = randint(255);
        int b = randint(255);
        particle
                .setColor(rgb, rgb, rgb)
                .setColor(r, g, b)
                .addParticle(this);
    }
}
