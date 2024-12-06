package com.dranyas.trangel.handler;


import com.dranyas.trangel.config.TensuraAngelConfig;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.config.IConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;

@EventBusSubscriber(modid = "tensuraangel", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ConfigHandler {
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, (IConfigSpec) TensuraAngelConfig.SPEC);
    }

    @SubscribeEvent
    public static void clientConfig(FMLClientSetupEvent event) {}

    @SubscribeEvent
    public static void serverConfig(FMLDedicatedServerSetupEvent event) {}
}
