package com.example.examplemod.entities.particles;

import net.minecraft.client.particle.EntityAuraFX;
import net.minecraft.world.World;

/**
 * Created by ctare on 2017/08/30.
 */
public class OriginFX extends EntityAuraFX{
    public OriginFX(World p_i1232_1_, double p_i1232_2_, double p_i1232_4_, double p_i1232_6_, double p_i1232_8_, double p_i1232_10_, double p_i1232_12_) {
        super(p_i1232_1_, p_i1232_2_, p_i1232_4_, p_i1232_6_, p_i1232_8_, p_i1232_10_, p_i1232_12_);
        setParticleTextureIndex(82);
        particleScale = 2f;
        setRBGColorF(0x11, 0x88, 0x11);
    }
}
