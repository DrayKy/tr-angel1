package com.dranyas.trangel.race.phantom;

import com.dranyas.trangel.config.TensuraAngelConfig;
import com.dranyas.trangel.race.angel.GreaterAngelRace;
import com.dranyas.trangel.registry.race.TensuraAngelRaces;
import com.github.manasmods.tensura.ability.TensuraSkill;
import com.github.manasmods.tensura.capability.race.TensuraPlayerCapability;
import com.github.manasmods.tensura.race.Race;
import com.github.manasmods.tensura.registry.dimensions.TensuraDimensions;
import com.github.manasmods.tensura.registry.items.TensuraMobDropItems;
import com.github.manasmods.tensura.registry.race.TensuraRaces;
import com.github.manasmods.tensura.registry.skill.ExtraSkills;
import com.github.manasmods.tensura.registry.skill.IntrinsicSkills;
import com.github.manasmods.tensura.registry.skill.ResistanceSkills;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class GeneralRace extends Race{

    public GeneralRace() {
        super(Difficulty.INTERMEDIATE);
    }

    public double getBaseHealth() {
        return 200.0D;
    }

    public float getPlayerSize() {
        return 2.0F;
    }

    public double getBaseAttackDamage() {
        return 3.0;
    }

    public double getBaseAttackSpeed() {
        return (double)4.2F;
    }

    public double getKnockbackResistance() {
        return 0.4;
    }

    @Override
    public double getJumpHeight() {
        return 1.0;
    }

    @Override
    public double getMovementSpeed() {
        return 0.5;
    }

    public double getSprintSpeed() {
        return (double)0.8F;
    }

    public Pair<Double, Double> getBaseAuraRange() {
        return Pair.of((double)45000.0F, (double)45000.0F);
    }

    public Pair<Double, Double> getBaseMagiculeRange() {
        return Pair.of((double)105000.0F, (double)105000.0F);
    }

    public boolean isSpiritual() {
        return false;
    }

    public boolean isMajin() {
        return true;
    }

    public boolean isDivine() {
        return false;
    }

    public double getAdditionalSpiritualHealth() {
        return (double)40.0F;
    }

    public double getSpiritualHealthMultiplier() {
        return (double)3.0F;
    }

    public ResourceKey<Level> getRespawnDimension() {
        return TensuraDimensions.HELL;
    }


    public List<Race> getNextEvolutions() {
        List<Race> list = new ArrayList();
        list.add((Race)TensuraRaces.RACE_REGISTRY.get().getValue(TensuraAngelRaces.STAFF_OFFICER));
        return list;
    }

    public List<Race> getPreviousEvolutions() {
        List<Race> list = new ArrayList();
        list.add((Race)TensuraRaces.RACE_REGISTRY.get().getValue(TensuraAngelRaces.FIELD_OFFICER));
        list.add((Race)TensuraRaces.RACE_REGISTRY.get().getValue(TensuraAngelRaces.GREATER_ANGEL));
        return list;
    }
    private static final Logger LOGGER = LogManager.getLogger("tensuraangel");


    public double getEvolutionPercentage(Player player) {

        double essence = 0.0;
        if (FMLEnvironment.dist == Dist.CLIENT && player instanceof LocalPlayer localPlayer) {
            essence =  (double)localPlayer.getStats().getValue(Stats.ITEM_USED.get((Item)TensuraMobDropItems.DRAGON_ESSENCE.get()));
        } else if (player instanceof ServerPlayer serverPlayer) {
            essence =  (double)serverPlayer.getStats().getValue(Stats.ITEM_USED.get((Item)TensuraMobDropItems.DRAGON_ESSENCE.get()));
        }
        Race race = TensuraPlayerCapability.getRace(player);

        double epPercentage = race != null && race.equals(TensuraAngelRaces.GREATER_ANGEL) ? TensuraPlayerCapability.getBaseEP(player) * 50.0 / (double)TensuraAngelConfig.INSTANCE.racesConfig.epToGeneral.get() : TensuraPlayerCapability.getBaseEP(player) * 100.0 / (double)TensuraAngelConfig.INSTANCE.racesConfig.epToGeneral.get();

        if (TensuraPlayerCapability.getBaseEP(player) >= (double)TensuraAngelConfig.INSTANCE.racesConfig.epToGeneral.get()){
            epPercentage = 50.0;

        }
        double essencePercentage = 0.0;
        if (race != null && race.getClass() != GreaterAngelRace.class){
            essencePercentage = 50.0;
        }else if (race != null && race.getClass() == GreaterAngelRace.class){
            essencePercentage = essence * 50.0 / (double)TensuraAngelConfig.INSTANCE.racesConfig.essenceForGeneralasAngel.get();
            if (essence >= (double)TensuraAngelConfig.INSTANCE.racesConfig.essenceForGeneralasAngel.get()){
                essencePercentage = 50.0;
            }
        }

        return  epPercentage + essencePercentage;
    }

    public List<Component> getRequirementsForRendering() {
        List<Component> list = new ArrayList<>();

        list.add(Component.translatable(
                "tensura.evolution_menu.ep_requirement"
        ));
        list.add(Component.translatable("+ if you are an angel:"));
        list.add(Component.translatable(
                "tensura.evolution_menu.consume_requirement",
                TensuraMobDropItems.DRAGON_ESSENCE.get().getDescription()
        ));

        return list;
    }



    public List<TensuraSkill> getIntrinsicSkills() {
        List<TensuraSkill> list = new ArrayList<>();
        return list;
    }
}
