package com.dranyas.trangel;


import com.dranyas.trangel.config.TensuraAngelConfig;
import com.dranyas.trangel.registry.TensuraAngelRegistry;
import com.github.manasmods.tensura.Tensura;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.loading.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("trangel")
public class TensuraAngel {
    public static final String MOD_ID = "trangel";
    public static final Logger LOGGER = LogManager.getLogger("Tensura:Angels");

    public TensuraAngel() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::onCommonSetup);
        TensuraAngelRegistry.register(modEventBus);
        LOGGER.info("Tensura:Angels has been loaded!");
        FileUtils.getOrCreateDirectory(FMLPaths.CONFIGDIR.get().resolve("tensura-reincarnated"), "tensura-reincarnated");
        ModLoadingContext.get().registerConfig(Type.CLIENT, TensuraAngelConfig.SPEC, getConfigFileName("trangel-config"));
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    @SubscribeEvent
    public void onCommonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("Common Setup");
        if (this.isFirstLaunch()) {
            this.editTOMLFile();
            this.leaveATrace();
            LOGGER.info("Common setup is working, TOML file was edited.");
        } else {
            LOGGER.info("Common setup is working, but the TOML file was previously edited.");
        }

    }

    private boolean isFirstLaunch() {
        File footprintFile = new File("defaultconfigs/tensura-reincarnated/trangel_injector_footprint");
        return !footprintFile.exists();
    }

    private void leaveATrace() {
        File footprintFile = new File("defaultconfigs/tensura-reincarnated/trangel_injector_footprint");

        try {
            if (footprintFile.createNewFile()) {
                System.out.println("Tensura:Angels's config injector left a footprint file: " + footprintFile.getAbsolutePath());
            } else {
                System.out.println("Tensura:Angels already left a footprint file.");
            }
        } catch (IOException var3) {
            var3.printStackTrace();
            System.out.println("Error leaving a footprint file: " + var3.getMessage());
        }

    }

    public void editTOMLFile() {
        File tomlFile = new File("defaultconfigs/tensura-reincarnated/common.toml");
        StringBuilder contentBuilder = new StringBuilder();

        String[] line;
        try (BufferedReader reader = new BufferedReader(new FileReader(tomlFile))) {
            while((line = new String[]{reader.readLine()}) != null) {
                contentBuilder.append(line).append(System.lineSeparator());
            }
        } catch (IOException var16) {
            var16.printStackTrace();
            System.out.println("Error reading the TOML file: " + var16.getMessage());
            return;
        }

        String content = contentBuilder.toString();
        line = new String[]{"trangel:phantom", "trangel:lesser_angel"};
        String[] newRandom = new String[]{"trangel:phantom", "trangel:lesser_angel", "trangel:true_dragon"};
        String startingRacesKey = "startingRaces = [";
        String randomRacesKey = "possibleRandomRaces = [";
        content = this.addItemsToTOMLList(content, startingRacesKey, line);
        content = this.addItemsToTOMLList(content, randomRacesKey, newRandom);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tomlFile))) {
            writer.write(content);
        } catch (IOException var14) {
            var14.printStackTrace();
            System.out.println("Error writing to the TOML file: " + var14.getMessage());
        }

        System.out.println("Items added to TOML lists successfully.");
    }


    private String addItemsToTOMLList(String content, String listKey, String[] newItems) {
        int index = content.indexOf(listKey);
        if (index == -1) {
            System.out.println("List identifier '" + listKey + "' not found.");
            return content;
        } else {
            int endIndex = content.indexOf("]", index) + 1;
            if (endIndex == 0) {
                System.out.println("Closing bracket not found for list: " + listKey);
                return content;
            } else {
                String listContent = content.substring(index, endIndex);

                for(String newItem : newItems) {
                    if (!listContent.contains(newItem)) {
                        listContent = listContent.replace("]", ", \"" + newItem + "\"]");
                    }
                }

                return content.replace(content.substring(index, endIndex), listContent);
            }
        }
    }

    public static void copyDefaultConfig(String path, Path destination) {
        String resourcePath = String.format("%s/%s/%s.toml", "defaultconfigs", "tensura-reincarnated", path);

        try (InputStream inputStream = Tensura.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Resource not found: " + resourcePath);
            }

            if (Files.exists(FMLPaths.GAMEDIR.get().resolve("defaultconfigs/tensura-reincarnated/trangel-config"), new LinkOption[0])) {
                throw new IllegalArgumentException("No need, file already exist: " + resourcePath);
            }

            Path replace = FileUtils.getOrCreateDirectory(FMLPaths.GAMEDIR.get().resolve("defaultconfigs/" + getConfigFileName(path)), "tensura-reincarnated");
            Files.copy(inputStream, replace, new CopyOption[]{StandardCopyOption.REPLACE_EXISTING});
        } catch (Exception var8) {
            var8.printStackTrace();
        }

    }

    private static String getConfigFileName(String name) {
        return String.format("%s/%s.toml", "tensura-reincarnated", name);
    }
}
