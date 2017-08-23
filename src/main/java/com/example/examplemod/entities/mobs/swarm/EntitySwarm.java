package com.example.examplemod.entities.mobs.swarm;

import com.example.examplemod.entities.particles.FXSwarm;
import com.example.examplemod.entities.particles.ParticleEngine;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ctare on 2017/08/23.
 */
public class EntitySwarm extends EntityMob{
    private int damBonus = 0;
    private ArrayList<Entity> particles = new ArrayList<Entity>();
    protected int particleNum = 25;
    protected final float BASE_SPEED = 0.22f;
    protected final float BASE_TURN_SPEED = 15f;
    protected final float BASE_PARTICLE_GRAVITY = 0.08f;
    public EntitySwarm(World p_i1738_1_) {
        super(p_i1738_1_);
        setSize(2.0f, 2.0f);


//        // うろうろ移動するAIの追加
//        this.tasks.addTask(1, new EntityAIWander(this, 1.0D));
//        // 見回すAIの追加
//        this.tasks.addTask(2, new EntityAILookIdle(this));
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        dataWatcher.addObject(16, (byte) 0);
    }

    @Override
    public float getBrightness(float p_70013_1_) {
        return 1.0f;
    }

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float par1) {
        return 15728880;
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
            if(particles.size() < Math.max(EntitySwarm.particleCount(particleNum), 10)){
                EntityFX fx = getParticle();
                ParticleEngine.instance.addEffect(worldObj, fx);
                particles.add(fx);
            }
        }
    }

    public FXSwarm createParticle(float r, float g, float b, float speed, float turnSpeed, float particleGravity){
        return new FXSwarm(
                worldObj,
                this.posX + (double)((rand.nextFloat() - rand.nextFloat()) * 2.0F),
                this.posY + (double)((rand.nextFloat() - rand.nextFloat()) * 2.0F),
                this.posZ + (double)((rand.nextFloat() - rand.nextFloat()) * 2.0F),
                this, r / 255.0f, g / 255.0f, b / 255.0f,
                speed, turnSpeed, particleGravity);
    }

    Random rand = worldObj.rand;
    final int random(int min, int max){
        return min + rand.nextInt(max);
    }

    final int random(int max){
        return rand.nextInt(max);
    }

    public EntityFX getParticle(){
        return createParticle(
                200 + rand.nextInt(55),
                rand.nextInt(100),
                255 - rand.nextInt(55),
                BASE_SPEED, BASE_TURN_SPEED, BASE_PARTICLE_GRAVITY);
    }

    private static int particleCount(int base) {
        if(FMLClientHandler.instance().getClient().gameSettings.particleSetting == 2)
            return 0;
        else
            return FMLClientHandler.instance().getClient().gameSettings.particleSetting != 1 ? base * 2 : base * 1;
    }
}
