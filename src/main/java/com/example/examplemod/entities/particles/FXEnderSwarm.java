package com.example.examplemod.entities.particles;

import com.example.examplemod.entities.mobs.swarm.EntityEnderSwarm;
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
        if (target.getDistanceSq(this.posX, this.posY, this.posZ) > 25.0) {
            double preX = posX, preY = posY, preZ = posZ;
            setPosition(target.posX + (random() - random()) * 2.0, target.posY + (random() - random()) * 2.0, target.posZ + (random() - random()) * 2.0);
//            this.worldObj.spawnParticle("smoke", this.prevPosX + (double)((this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F), this.prevPosY + (double)(this.height / 2.0F) + (double)((this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F), this.prevPosZ + (double)((this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F), 0.0D, 0.0D, 0.0D);
//            this.worldObj.spawnParticle("flame",  0.0D, 0.0D, 0.0D);
            if(worldObj.isRemote){
                EntityEnderSwarm.enderParticle(worldObj, (short) 1, preX, preY, preZ, 0, 0, 0);
            }
        }
    }
}
