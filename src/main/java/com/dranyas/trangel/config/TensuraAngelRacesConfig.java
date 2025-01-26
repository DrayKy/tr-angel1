package com.dranyas.trangel.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public class TensuraAngelRacesConfig {

    private static final Logger LOGGER = LogManager.getLogger("trangel");
    public final ForgeConfigSpec.DoubleValue TrueDragonMinEP;

    public final ForgeConfigSpec.DoubleValue TrueDragonMaxEP;


    public final ForgeConfigSpec.DoubleValue epToFieldOfficer;
    public final ForgeConfigSpec.DoubleValue epToGreaterAngel;
    public final ForgeConfigSpec.DoubleValue epToArchAngel;


    public final ForgeConfigSpec.IntValue essenceForFieldOfficerasAngel;
    public final ForgeConfigSpec.IntValue essenceForFieldOfficer;
    public final ForgeConfigSpec.IntValue essenceForGeneral;
    public final ForgeConfigSpec.IntValue essenceForGeneralasAngel;
    public final ForgeConfigSpec.IntValue essenceForStaffOfficer;
    public final ForgeConfigSpec.IntValue essenceForStaffOfficerasAngel;
    public final ForgeConfigSpec.IntValue essenceForMysticAngel;
    public final ForgeConfigSpec.IntValue essenceForMysticAngelasAngel;



    public final ForgeConfigSpec.DoubleValue epToGeneral;




    public TensuraAngelRacesConfig(ForgeConfigSpec.Builder builder) {
        builder.push("TrueDragon");
        this.TrueDragonMinEP = builder.comment("Minimum Possible EP for True Dragon").defineInRange("TrueDragonMinEP", 20000000.0D, 10000000.0D, 1.0E9D);
        this.TrueDragonMaxEP = builder.comment("Maximum Possible EP for True Dragon").defineInRange("TrueDragonMaxEP", 20000000.0D, 100000000.0D, 1.0E9D);
        builder.pop();
        builder.push("evolutionRequirements");
        this.epToFieldOfficer = builder.comment("The amount of EP needed to evolve into a Field Officer").defineInRange("epToFieldOfficer", 100000.0D, 0.0D, 1.0E9D);
        this.epToGeneral = builder.comment("The amount of EP needed to evolve into a General").defineInRange("epToGeneral", 400000.0D, 0.0D, 1.0E9D);
        this.epToGreaterAngel = builder.comment("The amount of EP needed to evolve into a Field Officer").defineInRange("epToGreaterAngel", 100000.0D, 0.0D, 1.0E9D);
        this.epToArchAngel = builder.comment("The amount of EP needed to evolve into a General").defineInRange("epToArchAngel", 400000.0D, 0.0D, 1.0E9D);
        this.essenceForFieldOfficer = builder.comment("The number of Dragon Essences needed to evolve into a Field Officer as a Phantom").defineInRange("essenceForFieldOfficer", 1, 0, 10000);
        this.essenceForFieldOfficerasAngel = builder.comment("The number of Dragon Essences needed to evolve into a Field Officer as a Lesser Angel").defineInRange("essenceForFieldOfficerasAngel", 1, 0, 10000);
        this.essenceForGeneral = builder.comment("The number of Dragon Essences needed to evolve into a General as a Field Officer").defineInRange("essenceForGeneral", 1, 0, 10000);
        this.essenceForGeneralasAngel = builder.comment("The number of Dragon Essences needed to evolve into a General as a Greater Angel").defineInRange("essenceForGeneralasAngel", 3, 0, 10000);
        this.essenceForStaffOfficer = builder.comment("The number of Dragon Essences needed to evolve into a Staff Officer as a General").defineInRange("essenceForStaffOfficer", 1, 0, 10000);
        this.essenceForStaffOfficerasAngel = builder.comment("The number of Dragon Essences needed to evolve into a Staff Officer as an Archangel").defineInRange("essenceForStaffOfficerasAngel", 7, 0, 10000);
        this.essenceForMysticAngel = builder.comment("The number of Dragon Essences needed to evolve into a Mystic Angel as a Staff Officer").defineInRange("essenceForMysticAngel", 1, 0, 10000);
        this.essenceForMysticAngelasAngel = builder.comment("The number of Dragon Essences needed to evolve into a Mystic Angel as a Seraphim").defineInRange("essenceForMysticAngelasAngel", 10, 0, 10000);

        builder.pop();
        LOGGER.info("TestRacesConfig() called too");  // Log the event


    }
}
