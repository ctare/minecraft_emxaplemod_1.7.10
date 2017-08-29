package com.example.examplemod.entities.mobs.swarm;

import com.example.examplemod.entities.mobs.common.EntityLiquidMob;
import com.example.examplemod.entities.particles.SwarmParticleManager;
import com.example.examplemod.utils.Swarm;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import static com.example.examplemod.utils.Calc.randint;

/**
 * Created by ctare on 2017/08/29.
 */
public class EntityFireSwarm extends EntityLiquidMob implements ISwarm{
    private SwarmParticleManager particleManager = new SwarmParticleManager().changeSpeedAndTurn(1.4f);

    { this.targetLiquid = Material.lava; }

    public EntityFireSwarm(World p_i1738_1_) {
        super(p_i1738_1_);
        this.setSize(1f, 1f);
        this.isImmuneToFire = true;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        particleManager
                .setColor(randint(200, 255), randint(50, 100), 50)
                .addParticle(this);
    }

    @Override
    protected Entity findPlayerToAttack() {
        return Swarm.attackTarget(this);
    }
}
