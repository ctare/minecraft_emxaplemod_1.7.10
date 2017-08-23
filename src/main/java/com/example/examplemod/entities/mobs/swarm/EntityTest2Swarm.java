package com.example.examplemod.entities.mobs.swarm;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

/**
 * Created by ctare on 2017/08/23.
 */
public class EntityTest2Swarm extends EntitySwarm{
    public EntityTest2Swarm(World p_i1738_1_) {
        super(p_i1738_1_);
        System.out.println("no test 2 swarm");
    }

    @Override
    public EntityFX getParticle() {
        return createParticle(0, 0, random(255), BASE_SPEED, BASE_TURN_SPEED, BASE_PARTICLE_GRAVITY);
    }
}
