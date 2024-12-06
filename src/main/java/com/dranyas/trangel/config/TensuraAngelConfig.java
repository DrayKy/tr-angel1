package com.dranyas.trangel.config;


import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class TensuraAngelConfig {
    public static final TensuraAngelConfig INSTANCE;

    public static final ForgeConfigSpec SPEC;

    public final TensuraAngelRacesConfig racesConfig;

    private TensuraAngelConfig(ForgeConfigSpec.Builder builder) {
        builder.push("races");
        this.racesConfig = new TensuraAngelRacesConfig(builder);
        builder.pop();
    }

    static {
        Pair<TensuraAngelConfig, ForgeConfigSpec> pair = (new ForgeConfigSpec.Builder()).configure(TensuraAngelConfig::new);
        INSTANCE = (TensuraAngelConfig)pair.getKey();
        SPEC = (ForgeConfigSpec)pair.getValue();
    }
}
