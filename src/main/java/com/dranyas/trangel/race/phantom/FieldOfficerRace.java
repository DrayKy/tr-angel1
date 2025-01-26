package com.dranyas.trangel.race.phantom;

import com.dranyas.trangel.config.TensuraAngelConfig;
import com.dranyas.trangel.race.angel.LesserAngelRace;
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
import com.github.manasmods.tensura.util.JumpPowerHelper;
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
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.jetbrains.annotations.Nullable;
import com.github.manasmods.tensura.capability.race.*;

import java.util.ArrayList;
import java.util.List;

public class FieldOfficerRace extends Race{

    public FieldOfficerRace() {
        super(Difficulty.INTERMEDIATE);
    }

    public double getBaseHealth() {
        return 80.0D;
    }

    public float getPlayerSize() {
        return 2.5F;
    }

    public double getBaseAttackDamage() {
        return 2.0;
    }

    public double getBaseAttackSpeed() {
        return (double)4.0F;
    }

    public double getKnockbackResistance() {
        return 0.2;
    }

    public double getJumpHeight() {
        return JumpPowerHelper.defaultPlayer( 1.0);
    }

    @Override
    public double getMovementSpeed() {
        return 0.2;
    }

    public double getSprintSpeed() {
        return (double)0.5F;
    }

    public double getAdditionalSpiritualHealth() {
        return (double)30.0F;
    }

    public Pair<Double, Double> getBaseAuraRange() {
        return Pair.of((double)15000.0F, (double)25000.0F);
    }

    public Pair<Double, Double> getBaseMagiculeRange() {
        return Pair.of((double)15000.0F, (double)25000.0F);
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

    public ResourceKey<Level> getRespawnDimension() {
        return TensuraDimensions.HELL;
    }


    public static final Capability<ITensuraPlayerCapability> RACE_CAPABILITY = CapabilityManager.get(new CapabilityToken<ITensuraPlayerCapability>() {});




    @Nullable
    public  Race getDefaultEvolution() {
        return TensuraRaces.RACE_REGISTRY.get().getValue(TensuraAngelRaces.GENERAL);
    }
    @Nullable
    public  Race getAwakeningEvolution() {
        return TensuraRaces.RACE_REGISTRY.get().getValue(TensuraAngelRaces.STAFF_OFFICER);
    }
    @Nullable
    public Race getHarvestFestivalEvolution() {
        return TensuraRaces.RACE_REGISTRY.get().getValue(TensuraAngelRaces.STAFF_OFFICER);
    }

    public List<Race> getNextEvolutions() {
        List<Race> list = new ArrayList();
        list.add((Race)TensuraRaces.RACE_REGISTRY.get().getValue(TensuraAngelRaces.GENERAL));
        return list;
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

    public double getEvolutionPercentage(Player player) {

        double essence = 0.0;
        if (FMLEnvironment.dist == Dist.CLIENT && player instanceof LocalPlayer localPlayer) {
            essence =  (double)localPlayer.getStats().getValue(Stats.ITEM_USED.get((Item)TensuraMobDropItems.DRAGON_ESSENCE.get()));
        } else if (player instanceof ServerPlayer serverPlayer) {
            essence =  (double)serverPlayer.getStats().getValue(Stats.ITEM_USED.get((Item)TensuraMobDropItems.DRAGON_ESSENCE.get()));
        }
        Race race = TensuraPlayerCapability.getRace(player);

        double epPercentage = race != null && race.equals(TensuraAngelRaces.LESSER_ANGEL) ? TensuraPlayerCapability.getBaseEP(player) * 50.0 / (double)TensuraAngelConfig.INSTANCE.racesConfig.epToFieldOfficer.get() : TensuraPlayerCapability.getBaseEP(player) * 100.0 / (double)TensuraAngelConfig.INSTANCE.racesConfig.epToFieldOfficer.get();

        if (TensuraPlayerCapability.getBaseEP(player) >= (double)TensuraAngelConfig.INSTANCE.racesConfig.epToFieldOfficer.get()){
            epPercentage = 50.0;

        }
        double essencePercentage = 0.0;
        if (race != null && race.getClass() != LesserAngelRace.class){
            essencePercentage = 50.0;
        }else if (race != null && race.getClass() == LesserAngelRace.class){
            essencePercentage = essence * 50.0 / (double)TensuraAngelConfig.INSTANCE.racesConfig.essenceForFieldOfficerasAngel.get();
            if (essence >= (double)TensuraAngelConfig.INSTANCE.racesConfig.essenceForFieldOfficerasAngel.get()){
                essencePercentage = 50.0;
            }
        }

        return  epPercentage + essencePercentage;
    }







    public List<Race> getPreviousEvolutions() {
        List<Race> list = new ArrayList();
        list.add((Race)TensuraRaces.RACE_REGISTRY.get().getValue(TensuraAngelRaces.PHANTOM));
        list.add((Race)TensuraRaces.RACE_REGISTRY.get().getValue(TensuraAngelRaces.LESSER_ANGEL));
        return list;
    }




    public List<TensuraSkill> getIntrinsicSkills() {
        List<TensuraSkill> list = new ArrayList<>();
        list.add((TensuraSkill) ExtraSkills.BODY_DOUBLE.get());
        return list;
    }
}
