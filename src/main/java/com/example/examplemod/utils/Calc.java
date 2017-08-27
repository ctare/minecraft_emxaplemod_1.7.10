package com.example.examplemod.utils;

import com.example.examplemod.entities.particles.FXGeneric;
import com.example.examplemod.entities.particles.ParticleEngine;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by ctare on 2017/08/23.
 */
public final class Calc {
    private Calc(){}

    public static Random random = new Random();
    public static int randint(int max){
       return random.nextInt(max);
    }

    public static int randint(int min, int max){
        return random.nextInt(max) + min;
    }

    public static float random(){
        return random.nextFloat();
    }

    public static boolean getBit(int value, int bit) {
        return (value & 1 << bit) != 0;
    }

    public static int setBit(int value, int bit) {
        return value | 1 << bit;
    }

    public static int clearBit(int value, int bit) {
        return value & ~(1 << bit);
    }

    public static abstract class Counter {
        private int count;
        private final int INTERVAL;

        public Counter(int INTERVAL) {
            this.INTERVAL = INTERVAL;
        }

        public final void update(){
            if(count++ >= INTERVAL){
                action();
                count = 0;
            }
        }

        public final void restart(){
            count = 0;
        }

        public abstract void action();
    }

    public static void drawGenericParticles(World worldObj, double x, double y, double z, double x2, double y2, double z2, float r, float g, float b, float alpha, boolean loop, int start, int num, int inc, int age, int delay, float scale) {
        FXGeneric fb = new FXGeneric(worldObj, x, y, z, x2, y2, z2);
        fb.setMaxAge(age, delay);
        fb.setRBGColorF(r, g, b);
        fb.setAlphaF(alpha);
        fb.setLoop(loop);
        fb.setParticles(start, num, inc);
        fb.setScale(scale);
        ParticleEngine.instance.addEffect(worldObj, fb);
    }

    public static void setRecentlyHit(EntityLivingBase ent, int hit) {
        try {
            ObfuscationReflectionHelper.setPrivateValue(EntityLivingBase.class, ent, hit, "recentlyHit", "field_70718_bc");
        } catch (Exception ignored) {
        }

    }
}
