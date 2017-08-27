package com.example.examplemod.entities.mobs.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

/**
 * Created by ctare on 2017/08/23.
 */
@SideOnly(Side.CLIENT)
public class RenderEmpty extends RenderLiving{
    public RenderEmpty() {
        super(null, 0.0f);
    }

    public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
    }

    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return AbstractClientPlayer.locationStevePng;
    }
}
