package com.example.examplemod.entities.particles;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import static com.example.examplemod.utils.Calc.random;

/**
 * Created by ctare on 2017/08/27.
 */
public class FXSwarmWithParticle extends FXSwarm{
    public FXSwarmWithParticle(World par1World, double x, double y, double z, Entity target, float r, float g, float b) {
        super(par1World, x, y, z, target, r, g, b);
    }

    public FXSwarmWithParticle(World par1World, double x, double y, double z, Entity target, float r, float g, float b, float sp, float ts, float pg) {
        super(par1World, x, y, z, target, r, g, b, sp, ts, pg);
    }

    @Override
    public void renderParticle(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5) {
        super.renderParticle(tessellator, f, f1, f2, f3, f4, f5);

        if(this.worldObj.isRemote && random() < 0.01) {
            this.worldObj.spawnParticle("smoke", this.prevPosX + (double)((this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F), this.prevPosY + (double)(this.height / 2.0F) + (double)((this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F), this.prevPosZ + (double)((this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F), 0.0D, 0.0D, 0.0D);
            this.worldObj.spawnParticle("flame", this.prevPosX + (double)((this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F), this.prevPosY + (double)(this.height / 2.0F) + (double)((this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F), this.prevPosZ + (double)((this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F), 0.0D, 0.0D, 0.0D);
        }
    }
}
