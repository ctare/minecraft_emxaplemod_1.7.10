package com.example.examplemod.entities.mobs.testmob;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

/**
 * Created by ctare on 2017/08/19.
 */
public class TestMobRender extends RenderLiving{
    public TestMobRender() {
        super(new TestMobModel(), 0.6f);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return null;
//        return new ResourceLocation("samctare", "misc/white.png");
//        return AbstractClientPlayer.locationStevePng;
    }
}