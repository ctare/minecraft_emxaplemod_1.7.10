package com.example.examplemod.utils;

import com.example.examplemod.entities.particles.FXGeneric;
import com.example.examplemod.entities.particles.ParticleEngine;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
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

    public static boolean isMatch(World world, int x, int y, int z, Material material){
        try{
            return world.getBlock(x, y, z).getMaterial().equals(material);
        } catch (Exception ignored){
            return false;
        }
    }

    public static boolean isIn(Material liquid, Entity entity) {
        return entity.worldObj.handleMaterialAcceleration(entity.boundingBox.expand(0.0D, -0.4000000238418579D, 0.0D).contract(0.001D, 0.001D, 0.001D), liquid, entity);
    }

    public static void spawnLava(World worldObj, double posX, double posY, double posZ){
        int x = (int) posX, y = (int) posY, z = (int) posZ;

        if (worldObj.isAirBlock(x, y, z)) {
            worldObj.setBlock(x, y, z, Blocks.lava);
            worldObj.setBlockMetadataWithNotify(x, y, z, 15, 2);
            worldObj.notifyBlockOfNeighborChange(x, y, z, Blocks.lava);
        }

        if (worldObj.isAirBlock(x + 1, y, z)) {
            worldObj.setBlock(x + 1, y, z, Blocks.lava);
            worldObj.setBlockMetadataWithNotify(x + 1, y, z, 15, 2);
            worldObj.notifyBlockOfNeighborChange(x + 1, y, z, Blocks.lava);
        }

        if (worldObj.isAirBlock(x - 1, y, z)) {
            worldObj.setBlock(x - 1, y, z, Blocks.lava);
            worldObj.setBlockMetadataWithNotify(x - 1, y, z, 15, 2);
            worldObj.notifyBlockOfNeighborChange(x - 1, y, z, Blocks.lava);
        }

        if (worldObj.isAirBlock(x, y, z + 1)) {
            worldObj.setBlock(x, y, z + 1, Blocks.lava);
            worldObj.setBlockMetadataWithNotify(x, y, z + 1, 15, 2);
            worldObj.notifyBlockOfNeighborChange(x, y, z + 1, Blocks.lava);
        }

        if (worldObj.isAirBlock(x, y, z - 1)) {
            worldObj.setBlock(x, y, z - 1, Blocks.lava);
            worldObj.setBlockMetadataWithNotify(x, y, z - 1, 15, 2);
            worldObj.notifyBlockOfNeighborChange(x, y, z - 1, Blocks.lava);
        }

        if (worldObj.isAirBlock(x, y + 1, z)) {
            worldObj.setBlock(x, y + 1, z, Blocks.lava);
            worldObj.setBlockMetadataWithNotify(x, y + 1, z, 15, 2);
            worldObj.notifyBlockOfNeighborChange(x, y + 1, z, Blocks.lava);
        }
    }

//    private static Map<String, ResourceLocation> boundTextures = new HashMap<String, ResourceLocation>();
//
//    public static void bindTexture(String texture) {
//        ResourceLocation rl = null;
//        if (boundTextures.containsKey(texture))
//            rl = (ResourceLocation)boundTextures.get(texture);
//        else {
//            rl = new ResourceLocation("artifacts", texture);
//        }
//        Minecraft.getMinecraft().renderEngine.bindTexture(rl);
//    }
//
//    public static ResourceLocation getParticleTexture() {
//        try {
//            return (ResourceLocation) ReflectionHelper.getPrivateValue(EffectRenderer.class, null, "particleTextures", "b", "field_110737_b");
//        } catch (Exception ignored) { }
//        return null;
//    }
}
