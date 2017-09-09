package com.example.examplemod;

import com.example.examplemod.entities.mobs.common.RenderEmpty;
import com.example.examplemod.entities.mobs.swarm.*;
import com.example.examplemod.entities.particles.swarm.EntitySwarmPart;
import com.example.examplemod.entities.particles.swarm.RenderSwarmPart;
import com.example.examplemod.items.SpawnEgg;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;

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
        if(FMLCommonHandler.instance().getSide() == Side.CLIENT) {
//            RenderingRegistry.registerEntityRenderingHandler(EntitySwarm.class, new RenderSwarm());
//            RenderingRegistry.registerEntityRenderingHandler(EntitySwarmPart.class, new RenderSwarmPart());
            this
                    .invisible(EntityTestSwarm.class)
//                    .invisible(EntityTest2Swarm.class)
                    .invisible(EntityEnderSwarm.class)
                    .invisible(EntityWaterSwarm.class)
                    .invisible(EntityAirSwarm.class)
                    .invisible(EntityFireSwarm.class)
            ;
        }
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
        GameRegistry.registerItem(new SpawnEgg().setUnlocalizedName("ExampleSpawnEgg"), "ExampleSpawnEgg", MODID);

        int swarmBaseEggColor = new Color(255, 150, 0).getRGB();
        int id = 0;
        EntityRegistry.registerModEntity(EntityTest2Swarm.class,
                SpawnEgg.addMapping("Test2Swarm", swarmBaseEggColor, Color.PINK.getRGB()),
                id++, this, 64, 2, true);
        EntityRegistry.registerModEntity(EntityTestSwarm.class,
                SpawnEgg.addMapping("TestSwarm", swarmBaseEggColor, Color.GREEN.getRGB()),
                id++, this, 64, 2, true);
        EntityRegistry.registerModEntity(EntityEnderSwarm.class,
                SpawnEgg.addMapping("EnderSwarm", swarmBaseEggColor, new Color(0, 0, 0).getRGB()),
                id++, this, 64, 2, true);
        EntityRegistry.registerModEntity(EntityWaterSwarm.class,
                SpawnEgg.addMapping("WaterSwarm", swarmBaseEggColor, new Color(100, 140, 255).getRGB()),
                id++, this, 64, 2, true);
        EntityRegistry.registerModEntity(EntityAirSwarm.class,
                SpawnEgg.addMapping("AirSwarm", swarmBaseEggColor, new Color(255, 220, 100).getRGB()),
                id++, this, 64, 2, true);
        EntityRegistry.registerModEntity(EntityFireSwarm.class,
                SpawnEgg.addMapping("FireSwarm", swarmBaseEggColor, new Color(255, 70, 70).getRGB()),
                id++, this, 64, 2, true);
        EntityRegistry.registerModEntity(EntitySwarmPart.class,
                SpawnEgg.addMapping("EntitySwarmPart", swarmBaseEggColor, new Color(255, 50, 200).getRGB()),
                id++, this, 64, 2, true);
        System.out.println("entities count : " + id);
        EntityRegistry.addSpawn(EntityWaterSwarm.class, 20, 0, 1, EnumCreatureType.waterCreature, BiomeGenBase.deepOcean);
    }

    private ExampleMod invisible(Class<? extends Entity> cls){
        RenderingRegistry.registerEntityRenderingHandler(cls, new RenderEmpty());
        return this;
    }
}
