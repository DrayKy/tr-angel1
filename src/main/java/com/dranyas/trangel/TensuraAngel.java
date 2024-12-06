package com.dranyas.trangel;
import com.dranyas.trangel.config.TensuraAngelConfig;
import com.dranyas.trangel.registry.TensuraAngelRegistry;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.IConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod("trangel")
public class TensuraAngel {
    public static final String MOD_ID = "trangel";
    public static final Logger LOGGER = LogManager.getLogger("tensura:angel");
    @SuppressWarnings("removal")
    public TensuraAngel() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::onCommonSetup);
        TensuraAngelRegistry.register(modEventBus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, (IConfigSpec) TensuraAngelConfig.SPEC, getConfigFileName("trangel-config"));
        LOGGER.info("Tensura:Angels");
    }
    public static Logger getLogger() {
        return LOGGER;
    }
    private String getConfigFileName(String name) {
        return String.format("%s/%s.toml", new Object[] { "tensura-reincarnated", name });
    }
    @SubscribeEvent
    public void onCommonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("Common Setup");
        editTOMLFile();
        LOGGER.info("Common setup works and TOML file was edited.");
    }

    public void editTOMLFile() {
        File tomlFile = new File("defaultconfigs/tensura-reincarnated/common.toml");
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(tomlFile));
            try {
                String line;
                while ((line = reader.readLine()) != null)
                    contentBuilder.append(line).append(System.lineSeparator());
                reader.close();
            } catch (Throwable throwable) {
                try {
                    reader.close();
                } catch (Throwable throwable1) {
                    throwable.addSuppressed(throwable1);
                }
                throw throwable;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading the TOML file: " + e.getMessage());
            return;
        }
        String content = contentBuilder.toString();
        String[] newStarting = {"trangel:phantom", "trangel:lesser_angel" };
        String[] newRandom = { "trangel:true_dragon", "trangel:phantom" };
        String startingRacesKey = "startingRaces = [";
        String randomRacesKey = "possibleRandomRaces = [";
        content = this.addItemsToTOMLList(content, startingRacesKey, newStarting);
        content = this.addItemsToTOMLList(content, randomRacesKey, newRandom);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(tomlFile));
            try {
                writer.write(content);
                writer.close();
            } catch (Throwable throwable) {
                try {
                    writer.close();
                } catch (Throwable throwable1) {
                    throwable.addSuppressed(throwable1);
                }
                throw throwable;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error writing to the TOML file: " + e.getMessage());
        }
        System.out.println("Items added to TOML lists successfully.");
    }
    private String addItemsToTOMLList(String content, String listKey, String[] newItems) {
        int index = content.indexOf(listKey);
        if (index == -1) {
            System.out.println("List identifier '" + listKey + "' not found.");
            return content;
        }
        int endIndex = content.indexOf("]", index) + 1;
        if (endIndex == 0) {
            System.out.println("Closing bracket not found for list: " + listKey);
            return content;
        }
        String listContent = content.substring(index, endIndex);
        for (String newItem : newItems) {
            if (!listContent.contains(newItem))
                listContent = listContent.replace("]", ", \"" + newItem + "\"]");
        }
        return content.replace(content.substring(index, endIndex), listContent);
    }


}