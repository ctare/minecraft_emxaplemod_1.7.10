package com.example.examplemod.entities.mobs.swarm;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

/**
 * Created by ctare on 2017/08/23.
 */
public class EntityTestSwarm extends EntitySwarm{
    {
        particleNum = 10;
    }

    public EntityTestSwarm(World p_i1738_1_) {
        super(p_i1738_1_);
        System.out.println("ok test swarm");
    }

    @Override
    public EntityFX getParticle() {
        int rgb = random(155);
        return createParticle(rgb, rgb, rgb, BASE_SPEED * 2, BASE_TURN_SPEED * 2, BASE_PARTICLE_GRAVITY);
    }
}
