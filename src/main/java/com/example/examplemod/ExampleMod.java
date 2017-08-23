package com.example.examplemod;

import com.example.examplemod.entities.mobs.swarm.EntitySwarm;
import com.example.examplemod.entities.mobs.swarm.EntityTest2Swarm;
import com.example.examplemod.entities.mobs.swarm.EntityTestSwarm;
import com.example.examplemod.entities.mobs.swarm.RenderSwarm;
import com.example.examplemod.entities.particles.ParticleEngine;
import com.example.examplemod.items.SpawnEgg;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.EnumCreatureType;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.MinecraftForge;

import java.awt.*;

@Mod(modid = ExampleMod.MODID, version = ExampleMod.VERSION)
public class ExampleMod {
    public static final String MODID = "examplemod";
    public static final String VERSION = "1.0";

    @Mod.Instance("examplemod")
    public static ExampleMod instance;

    @EventHandler
    public void init(FMLInitializationEvent event) {
		// some example code
        FMLCommonHandler.instance().bus().register(ParticleEngine.instance);
        MinecraftForge.EVENT_BUS.register(ParticleEngine.instance);
        if(FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            RenderingRegistry.registerEntityRenderingHandler(EntitySwarm.class, new RenderSwarm());
        }
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
        GameRegistry.registerItem(new SpawnEgg().setUnlocalizedName("ExampleSpawnEgg"), "ExampleSpawnEgg", MODID);

        int swarmBaseEggColor = new Color(255, 150, 0).getRGB();
        int id = 0;
        EntityRegistry.registerModEntity(EntityTest2Swarm.class,
                SpawnEgg.addMapping("Test2Swarm", swarmBaseEggColor, Color.RED.getRGB()),
                id++, this, 64, 2, true);
        EntityRegistry.registerModEntity(EntityTestSwarm.class,
                SpawnEgg.addMapping("TestSwarm", swarmBaseEggColor, Color.GREEN.getRGB()),
                id++, this, 64, 2, true);
        System.out.println("entities count : " + id);
//        EntityRegistry.addSpawn(TestMob.class, 20, 1, 4, EnumCreatureType.creature, BiomeGenBase.plains);
    }
}
