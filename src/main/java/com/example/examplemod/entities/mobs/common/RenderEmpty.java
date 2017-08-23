package com.example.examplemod.entities.mobs.common;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

/**
 * Created by ctare on 2017/08/23.
 */
public class RenderEmpty extends RenderLiving{
    public RenderEmpty() {
        super(new ModelEmpty(), 0.6f);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return AbstractClientPlayer.locationStevePng;
    }
}
