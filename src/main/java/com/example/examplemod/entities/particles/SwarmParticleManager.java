package com.example.examplemod.entities.particles;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;

import java.util.ArrayList;

import static com.example.examplemod.utils.Calc.random;

/**
 * Created by ctare on 2017/08/23.
 */
public class SwarmParticleManager {
    private ArrayList<EntityFX> particles = new ArrayList<EntityFX>();
    private final int PARTICLE_NUM;
    private float r = 0.8f + random() * 0.2f;
    private float g = random() * 0.4f;
    private float b = 1.0f - random() * 0.2f;
    private float speed = 0.22f;
    private float turnSpeed = 15f;
    private float particleGravity = 0.08f;

    public SwarmParticleManager(int particleNum) {
        PARTICLE_NUM = particleNum;
    }

    public SwarmParticleManager(){
        PARTICLE_NUM = 25;
    }

    public void addParticle(Entity target) {
        if (target.worldObj.isRemote) {
            int a = 0;
            do {
                if (a >= particles.size())
                    break;
                if (particles.get(a) == null || particles.get(a).isDead) {
                    particles.remove(a);
                    break;
                }
                a++;
            } while (true);
            if (particles.size() < Math.max(SwarmParticleManager.particleCount(PARTICLE_NUM), 10)) {
                System.out.printf("%f %f %f%n", r, g, b);
                EntityFX fx = new FXSwarm(target.worldObj,
                        target.posX + (random() - random()) * 2.0,
                        target.posY + (random() - random()) * 2.0,
                        target.posZ + (random() - random()) * 2.0,
                        target, r, g, b, speed, turnSpeed, particleGravity);
                ParticleEngine.instance.addEffect(target.worldObj, fx);
                particles.add(fx);
            }
        }
    }

    private static int particleCount(int n) {
        if(FMLClientHandler.instance().getClient().gameSettings.particleSetting == 2)
            return 0;
        else
            return FMLClientHandler.instance().getClient().gameSettings.particleSetting != 1 ? n * 2 : n * 1;
    }

    public SwarmParticleManager changeSpeed(float n){
        this.speed *= n;
        return this;
    }

    public SwarmParticleManager setSpeed(float speed) {
        this.speed = speed;
        return this;
    }

    public SwarmParticleManager changeTurnSpeed(float n){
        this.turnSpeed *= n;
        return this;
    }

    public SwarmParticleManager setTurnSpeed(float turnSpeed) {
        this.turnSpeed = turnSpeed;
        return this;
    }

    public SwarmParticleManager changeParticleGravity(float n){
        this.particleGravity *= n;
        return this;
    }

    public SwarmParticleManager setParticleGravity(float particleGravity) {
        this.particleGravity = particleGravity;
        return this;
    }

    public SwarmParticleManager changeSpeedAndTurn(float n){
        return changeSpeed(n).changeTurnSpeed(n);
    }

    public  SwarmParticleManager setColor(int r, int g, int b){
        this.r = r / 255.0f;
        this.g = g / 255.0f;
        this.b = b / 255.0f;
        return this;
    }
}
