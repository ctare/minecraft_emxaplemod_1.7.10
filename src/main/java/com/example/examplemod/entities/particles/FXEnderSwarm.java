package com.example.examplemod.entities.particles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import static com.example.examplemod.utils.Calc.random;

/**
 * Created by ctare on 2017/08/30.
 */
public class FXEnderSwarm extends FXSwarm{
    private Entity target;
    public FXEnderSwarm(World par1World, double x, double y, double z, Entity target, float r, float g, float b) {
        super(par1World, x, y, z, target, r, g, b);
    }

    public FXEnderSwarm(World par1World, double x, double y, double z, Entity target, float r, float g, float b, float sp, float ts, float pg) {
        super(par1World, x, y, z, target, r, g, b, sp, ts, pg);
        this.target = target;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (target.worldObj == null) {
            this.setDead();
        }
//        System.out.println(String.format("%f %f %f", this.posX, this.posY, this.posZ));
//        System.out.println(String.format("%f %f %f", target.posX, target.posY, target.posZ));
        if (target.getDistanceSq(this.posX, this.posY, this.posZ) > 300.0) {
//            System.out.println("------------------");
//            System.out.println(String.format("%f %f %f", this.posX, this.posY, this.posZ));
//            System.out.println(String.format("%f %f %f", target.posX, target.posY, target.posZ));
//            System.out.println(target.getDistanceSq(this.posX, this.posY, this.posZ));
            setPosition(target.posX + (random() - random()) * 2.0, target.posY + (random() - random()) * 2.0, target.posZ + (random() - random()) * 2.0);
//            this.worldObj.spawnParticle("smoke", this.prevPosX + (double)((this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F), this.prevPosY + (double)(this.height / 2.0F) + (double)((this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F), this.prevPosZ + (double)((this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F), 0.0D, 0.0D, 0.0D);
//            this.worldObj.spawnParticle("flame",  0.0D, 0.0D, 0.0D);
            if(worldObj.isRemote){
                Minecraft.getMinecraft().effectRenderer.addEffect(new OriginFX(worldObj, this.prevPosX + (double)((this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F), this.prevPosY + (double)(this.height / 2.0F) + (double)((this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F), this.prevPosZ + (double)((this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F), 0.0D, 0.0D, 0.0D));
//                FXSwarm fx = new FXSwarm(worldObj, target.posX, target.posY, target.posZ, target, 1f, 1f, 1f);
//                fx.setParticleTextureIndex(82);
//                Minecraft.getMinecraft().effectRenderer.addEffect(fx);
            }
//            setPosition(-110, 73, 120);
//            target.setPosition(-110, 73, 120);
        }
    }
}
