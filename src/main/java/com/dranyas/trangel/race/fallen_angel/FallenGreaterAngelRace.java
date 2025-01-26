package com.dranyas.trangel.race.fallen_angel;

import com.dranyas.trangel.config.TensuraAngelConfig;
import com.dranyas.trangel.race.angel.LesserAngelRace;
import com.dranyas.trangel.registry.race.TensuraAngelRaces;
import com.github.manasmods.manascore.api.skills.SkillAPI;
import com.github.manasmods.tensura.ability.TensuraSkill;
import com.github.manasmods.tensura.capability.ep.TensuraEPCapability;
import com.github.manasmods.tensura.capability.race.TensuraPlayerCapability;
import com.github.manasmods.tensura.race.Race;
import com.github.manasmods.tensura.registry.items.TensuraMobDropItems;
import com.github.manasmods.tensura.registry.race.TensuraRaces;
import com.github.manasmods.tensura.registry.skill.ExtraSkills;
import com.github.manasmods.tensura.registry.skill.IntrinsicSkills;
import com.github.manasmods.tensura.registry.skill.ResistanceSkills;
import com.github.manasmods.tensura.util.JumpPowerHelper;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FallenGreaterAngelRace extends Race{
    public FallenGreaterAngelRace() {
        super(Race.Difficulty.EASY);

    }
    public static Player getCurrentPlayer() {
        // Fetch the player from the game state, world, or manager
        return Minecraft.getInstance().player; // Example for Minecraft
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
        return JumpPowerHelper.defaultPlayer();
    }

    public double getMovementSpeed() {
        return 0.2;
    }

    public double getSprintSpeed() {
        return 0.5;
    }

    public double getAdditionalSpiritualHealth() {
        return (double)30.0F;
    }

    public Pair<Double, Double> getBaseMagiculeRange() {
        return Pair.of((double)35000.0F, (double)40000.0F);
    }

    public Pair<Double, Double> getBaseAuraRange() {
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
        return Level.OVERWORLD;
    }


    public  Race getDefaultEvolution() {
        return TensuraRaces.RACE_REGISTRY.get().getValue(TensuraAngelRaces.FALLEN_ARCHANGEL);
    }
    @Nullable
    public  Race getAwakeningEvolution() {
        return TensuraRaces.RACE_REGISTRY.get().getValue(TensuraAngelRaces.FALLEN_ARCHANGEL);

    }
    @Nullable
    public Race getHarvestFestivalEvolution() {
        return TensuraRaces.RACE_REGISTRY.get().getValue(TensuraAngelRaces.FALLEN_ARCHANGEL);

    }

    Race race = TensuraPlayerCapability.getRace(getCurrentPlayer());




    public List<Race> getNextEvolutions() {
        List<Race> list = new ArrayList();
        list.add((Race) TensuraRaces.RACE_REGISTRY.get().getValue(TensuraAngelRaces.FALLEN_ARCHANGEL));
        return list;
    }

    public List<Component> getRequirementsForRendering() {
        List<Component> list = new ArrayList();
        list.add(Component.translatable("tensura.evolution_menu.ep_requirement"));
        list.add(Component.translatable("+ if not a majin:"));
        list.add(Component.translatable(
                "tensura.evolution_menu.consume_requirement",
                TensuraMobDropItems.DEMON_ESSENCE.get().getDescription()
        ));
        return list;
    }

    public double getEvolutionPercentage(Player player) {

        double essence = 0.0;
        if (FMLEnvironment.dist == Dist.CLIENT && player instanceof LocalPlayer localPlayer) {
            essence =  (double)localPlayer.getStats().getValue(Stats.ITEM_USED.get((Item)TensuraMobDropItems.DEMON_ESSENCE.get()));
        } else if (player instanceof ServerPlayer serverPlayer) {
            essence =  (double)serverPlayer.getStats().getValue(Stats.ITEM_USED.get((Item)TensuraMobDropItems.DEMON_ESSENCE.get()));
        }
        Race race = TensuraPlayerCapability.getRace(player);

        double epPercentage = race != null && race.equals(TensuraAngelRaces.LESSER_ANGEL) & !TensuraEPCapability.isMajin(player) ? TensuraPlayerCapability.getBaseEP(player) * 50.0 / (double)TensuraAngelConfig.INSTANCE.racesConfig.epToFieldOfficer.get() : TensuraPlayerCapability.getBaseEP(player) * 100.0 / (double)TensuraAngelConfig.INSTANCE.racesConfig.epToFieldOfficer.get();

        if (TensuraPlayerCapability.getBaseEP(player) >= (double)TensuraAngelConfig.INSTANCE.racesConfig.epToFieldOfficer.get()){
            epPercentage = 50.0;

        }
        double essencePercentage = 0.0;
        if (race != null && race.getClass() != LesserAngelRace.class ){
            essencePercentage = 50.0;
        }else if (race != null && race.getClass() == LesserAngelRace.class && !TensuraEPCapability.isMajin(player)){
            essencePercentage = essence * 50.0 / (double)TensuraAngelConfig.INSTANCE.racesConfig.essenceForFieldOfficerasAngel.get();
            if (essence >= (double)TensuraAngelConfig.INSTANCE.racesConfig.essenceForFieldOfficerasAngel.get()){
                essencePercentage = 50.0;
            }
        }else{
            essencePercentage = 50.0;
        }

        return  epPercentage + essencePercentage;
    }



    public List<TensuraSkill> getIntrinsicSkills() {
        List<TensuraSkill> list = new ArrayList<>();
        return list;
    }
    public boolean canFly() {
        return true;
    }



    public void raceAbility(Player entity) {
        if (!entity.isCrouching() && !entity.isSprinting()) {
            Level level = entity.getLevel();
            if (entity.getAbilities().flying) {
                entity.getAbilities().flying = false;
                entity.getAbilities().mayfly = false;
                entity.onUpdateAbilities();
                level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 1.0F, 1.0F);
            } else {
                entity.getAbilities().flying = true;
                entity.getAbilities().mayfly = true;
                entity.onUpdateAbilities();
                level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 1.0F, 1.0F);
            }

        }
    }
    public void triggerEvolutionRewards(Player player) {
        TensuraPlayerCapability.getFrom(player).ifPresent((cap) -> {
            if (this.getManaEvolutionReward() > 0.0D) {
                cap.setBaseMagicule(cap.getBaseMagicule() + this.getManaEvolutionReward(), player);
            } else if (cap.getBaseMagicule() < this.getMinBaseMagicule()) {
                cap.setBaseMagicule(this.getMinBaseMagicule(), player);
            }

            if (this.getAuraEvolutionReward() > 0.0D) {
                cap.setBaseAura(cap.getBaseAura() + this.getAuraEvolutionReward(), player);
            } else if (cap.getBaseAura() < this.getMinBaseAura()) {
                cap.setBaseAura(this.getMinBaseAura(), player);
            }

            List<TensuraSkill> list = new ArrayList<>();
            list.add((TensuraSkill) IntrinsicSkills.POSSESSION.get());
            list.add((TensuraSkill) IntrinsicSkills.LIGHT_TRANSFORM.get());
            list.add((TensuraSkill) ExtraSkills.MAGIC_LIGHT_TRANSFORM.get());
            list.add((TensuraSkill) ResistanceSkills.HOLY_ATTACK_RESISTANCE.get());
            list.add((TensuraSkill) ResistanceSkills.HOLY_ATTACK_NULLIFICATION.get());
            list.forEach((skill) -> {
                SkillAPI.getSkillsFrom(player).forgetSkill(skill);
            });
        });
        TensuraPlayerCapability.sync(player);
    }
    public List<Race> getPreviousEvolutions() {
        List<Race> list = new ArrayList();
        list.add((Race) TensuraRaces.RACE_REGISTRY.get().getValue(TensuraAngelRaces.LESSER_ANGEL));
        return list;
    }

}
