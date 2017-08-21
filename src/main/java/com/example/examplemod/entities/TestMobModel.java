package com.example.examplemod.entities;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * Created by ctare on 2017/08/19.
 */
public class TestMobModel extends ModelBase{
    ModelRenderer top;
    ModelRenderer bottom;
    ModelRenderer base;

    public TestMobModel() {
        super();

        textureWidth = 32;
        textureHeight = 64;
        base = new ModelRenderer(this, 0, 14);
        base.addBox(0f, 0f, 0f, 10, 8, 10);

        bottom = new ModelRenderer(this, 32, 2);
        bottom.addBox(1f, 18f, 1f, 8, 8, 8);

        top = new ModelRenderer(this, 40, 18);
        top.addBox(2f, 16f, 2f, 6, 8, 6);
    }

    @Override
    public void render(Entity f1, float f2, float f3, float f4, float f5, float f6, float f7) {
        base.render(f7);
        bottom.render(f7);
        top.render(f7);
    }
}
