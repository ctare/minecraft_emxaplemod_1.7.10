package com.example.examplemod.entities.mobs.swarm;

import com.example.examplemod.entities.mobs.common.EntityLiquidMob;
import com.example.examplemod.entities.particles.SwarmParticleManager;
import com.example.examplemod.utils.Calc;
import cpw.mods.fml.common.Mod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import static com.example.examplemod.utils.Calc.randint;

/**
 * Created by ctare on 2017/08/23.
 */
public class EntityTest2Swarm extends EntityMob implements ISwarm{
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

    @Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        Calc.spawnLava(worldObj, posX, posY, posZ);
        return super.attackEntityFrom(par1DamageSource, par2);
    }

    @Override
    protected void updateEntityActionState() {
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
