package com.example.examplemod.entities.mobs.testmob;

import com.example.examplemod.entities.particles.FXSwarm;
import com.example.examplemod.entities.particles.ParticleEngine;
import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.ArrayList;

/**
 * Created by ctare on 2017/08/19.
 */
public class TestMob extends EntityMob{
    private int damBonus = 3;
    private ArrayList<Entity> particles = new ArrayList<Entity>();
    public TestMob(World p_i1738_1_) {
        super(p_i1738_1_);
        setSize(2.0f, 2.0f);


        // うろうろ移動するAIの追加
        this.tasks.addTask(1, new EntityAIWander(this, 1.0D));
        // 見回すAIの追加
        this.tasks.addTask(2, new EntityAILookIdle(this));
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        dataWatcher.addObject(16, (byte) 0);
    }

    @Override
    public float getBrightness(float p_70013_1_) {
        return 10.0f;
    }

    @Override
    protected float getSoundVolume() {
        return 0.1f;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(5d);
        getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2 + damBonus);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        motionY *= 0.60000002384185791D;
        if(worldObj.isRemote) {
            int a = 0;
            do {
                if(a >= particles.size())
                    break;
                if(particles.get(a) == null || particles.get(a).isDead) {
                    particles.remove(a);
                    break;
                }
                a++;
            } while(true);
            if(particles.size() < Math.max(TestMob.particleCount(25), 10)){
                EntityFX fx = getParticle();
                ParticleEngine.instance.addEffect(worldObj, fx);
                particles.add(fx);
            }
        }
    }

    public EntityFX getParticle(){
        return new FXSwarm(
                worldObj,
                this.posX + (double)((worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 2.0F),
                this.posY + (double)((worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 2.0F),
                this.posZ + (double)((worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 2.0F),
                this, 0.8F + worldObj.rand.nextFloat() * 0.2F, worldObj.rand.nextFloat() * 0.4F,
                1.0F - worldObj.rand.nextFloat() * 0.2F,
                0.22f, 15f, 0.08f);
    }

    @Override
    protected void setBeenAttacked() {
        super.setBeenAttacked();
    }

    @Override
    public void onDeath(DamageSource p_70645_1_) {
        super.onDeath(p_70645_1_);
        System.out.println("on death");
    }

    private static int particleCount(int base) {
        if(FMLClientHandler.instance().getClient().gameSettings.particleSetting == 2)
            return 0;
        else
            return FMLClientHandler.instance().getClient().gameSettings.particleSetting != 1 ? base * 2 : base * 1;
    }
}
