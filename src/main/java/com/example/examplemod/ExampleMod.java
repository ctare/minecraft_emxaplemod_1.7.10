package com.example.examplemod;

import com.example.examplemod.entities.TestMob;
import com.example.examplemod.entities.TestMobRender;
import com.example.examplemod.entities.TestMobSpawnEgg;
import com.google.common.eventbus.Subscribe;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.item.Item;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;

import java.awt.*;

@Mod(modid = ExampleMod.MODID, version = ExampleMod.VERSION)
public class ExampleMod
{
    public static final String MODID = "examplemod";
    public static final String VERSION = "1.0";

    @Mod.Instance("examplemod")
    public static ExampleMod instance;

    public static Item itemSpawnEgg = new TestMobSpawnEgg(Color.RED.getRGB(), Color.WHITE.getRGB())
            .setUnlocalizedName("sample:spawn_egg")
            .setTextureName("spawn_egg")
            .setCreativeTab(CreativeTabs.tabMisc);
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
		// some example code
        FMLCommonHandler.instance().bus().register(ParticleEngine.instance);
        MinecraftForge.EVENT_BUS.register(ParticleEngine.instance);
        EntityRegistry.registerModEntity(TestMob.class, "TestMob", 0, this, 250, 1, false);
        EntityRegistry.addSpawn(TestMob.class, 20, 1, 4, EnumCreatureType.creature, BiomeGenBase.plains);
        if(FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            RenderingRegistry.registerEntityRenderingHandler(TestMob.class, new TestMobRender());
        }
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
        // MOD独自のモブを追加。今回のサンプルには含まれないことに注意
        EntityRegistry.registerModEntity(TestMob.class, "TestMob", 0, ExampleMod.instance, 64, 2, true);
        // スポーンエッグを追加
        GameRegistry.registerItem(itemSpawnEgg, "itemSpawnEggExample");
    }
}
