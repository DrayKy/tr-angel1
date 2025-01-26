package com.dranyas.trangel.registry.race;

import com.dranyas.trangel.race.angel.*;
import com.dranyas.trangel.race.fallen_angel.*;
import com.dranyas.trangel.race.phantom.*;
import com.dranyas.trangel.race.true_dragon.TrueDragonRace;
import com.github.manasmods.tensura.race.Race;
import com.github.manasmods.tensura.registry.race.TensuraRaces;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegisterEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@EventBusSubscriber(modid = "trangel", bus = EventBusSubscriber.Bus.MOD)
public class TensuraAngelRaces {
    public static final ResourceLocation TRUE_DRAGON = new ResourceLocation("trangel", "true_dragon");
    public static ResourceLocation LESSER_ANGEL = new ResourceLocation("trangel", "lesser_angel");
    public static final ResourceLocation GREATER_ANGEL = new ResourceLocation("trangel", "greater_angel");
    public static final ResourceLocation ARCHANGEL = new ResourceLocation("trangel", "archangel");
    public static final ResourceLocation CHERUB = new ResourceLocation("trangel", "cherub");
    public static final ResourceLocation SERAPHIM = new ResourceLocation("trangel", "seraphim");
    public static final ResourceLocation FALLEN_GREATER_ANGEL = new ResourceLocation("trangel", "fallen_greater_angel");
    public static final ResourceLocation FALLEN_ARCHANGEL = new ResourceLocation("trangel", "fallen_archangel");
    public static final ResourceLocation FALLEN_CHERUB = new ResourceLocation("trangel", "fallen_cherub");
    public static final ResourceLocation FALLEN_SERAPHIM = new ResourceLocation("trangel", "fallen_seraphim");
    public static final ResourceLocation PHANTOM = new ResourceLocation("trangel", "phantom");
    public static final ResourceLocation FIELD_OFFICER = new ResourceLocation("trangel", "field_officer");
    public static final ResourceLocation GENERAL = new ResourceLocation("trangel", "general");
    public static final ResourceLocation STAFF_OFFICER = new ResourceLocation("trangel", "staff_officer");
    public static final ResourceLocation MYSTIC_ANGEL = new ResourceLocation("trangel", "mystic_angel");



    public TensuraAngelRaces() {
    }
    @SubscribeEvent
    public static void register(RegisterEvent event) {
        LOGGER.info("TensuraAngelRaces.register() called");  // Log the event
        event.register(((IForgeRegistry)TensuraRaces.RACE_REGISTRY.get()).getRegistryKey(), helper -> {
            helper.register("true_dragon", new TrueDragonRace());
            helper.register("lesser_angel", new LesserAngelRace());
            helper.register("greater_angel", new GreaterAngelRace());
            helper.register("archangel", new ArchAngelRace());
            helper.register("cherub", new CherubRace());
            helper.register("seraphim", new SeraphimRace());
            helper.register("fallen_greater_angel", new FallenGreaterAngelRace());
            helper.register("fallen_archangel", new FallenArchAngelRace());
            helper.register("fallen_cherub", new FallenCherubRace());
            helper.register("fallen_seraphim", new FallenSeraphimRace());
            helper.register("phantom", new PhantomRace());
            helper.register("field_officer", new FieldOfficerRace());
            helper.register("general", new GeneralRace());
            helper.register("staff_officer", new StaffOfficerRace());
            helper.register("mystic_angel", new MysticAngelRace());
        });
    }

    // Create the logger
    private static final Logger LOGGER = LogManager.getLogger("trangel");

}

