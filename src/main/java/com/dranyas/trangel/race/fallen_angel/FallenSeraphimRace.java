package com.dranyas.trangel.race.fallen_angel;

import com.dranyas.trangel.config.TensuraAngelConfig;
import com.dranyas.trangel.race.angel.SeraphimRace;
import com.dranyas.trangel.registry.race.TensuraAngelRaces;
import com.github.manasmods.tensura.ability.TensuraSkill;
import com.github.manasmods.tensura.capability.ep.TensuraEPCapability;
import com.github.manasmods.tensura.capability.race.TensuraPlayerCapability;
import com.github.manasmods.tensura.race.Race;
import com.github.manasmods.tensura.registry.items.TensuraMobDropItems;
import com.github.manasmods.tensura.registry.race.TensuraRaces;
import com.github.manasmods.tensura.registry.skill.IntrinsicSkills;
import com.github.manasmods.tensura.registry.skill.ResistanceSkills;
import com.github.manasmods.tensura.util.JumpPowerHelper;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
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

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class FallenSeraphimRace extends FallenGreaterAngelRace{

    public FallenSeraphimRace() {
        super(Race.Difficulty.EASY);
    }

    public double getBaseHealth() {
        return 6600.0D;
    }

    public float getPlayerSize() {
        return 2.0F;
    }

    public double getBaseAttackDamage() {
        return 5.0;
    }

    public double getBaseAttackSpeed() {
        return (double)5.0F;
    }

    public double getKnockbackResistance() {
        return 1.0;
    }

    public double getJumpHeight() {
        return JumpPowerHelper.defaultPlayer((double)2.0F);
    }

    public double getMovementSpeed() {
        return 0.5;
    }

    public double getSprintSpeed() {
        return 1.0;
    }

    public double getAdditionalSpiritualHealth() {
        return (double)30.0F;
    }

    public Pair<Double, Double> getBaseMagiculeRange() {
        return Pair.of((double)705000.0F, (double)950000.0F);
    }

    public Pair<Double, Double> getBaseAuraRange() {
        return Pair.of((double)405000.0F, (double)555000.0F);
    }

    public boolean isSpiritual() {
        return false;
    }

    public boolean isMajin() {
        return true;
    }

    public boolean isDivine() {
        return true;
    }

    public ResourceKey<Level> getRespawnDimension() {
        return Level.OVERWORLD;
    }


    public List<Component> getRequirementsForRendering() {
        List<Component> list = new ArrayList();
        list.add(Component.translatable("tensura.evolution_menu.name_requirement"));
        list.add(Component.translatable("tensura.evolution_menu.physical_body_requirement"));
        list.add(Component.translatable("tensura.evolution_menu.awaken_requirement", new Object[]{Component.translatable("tensura.attribute.true_demon_lord.name").withStyle(ChatFormatting.DARK_PURPLE), Component.translatable("tensura.attribute.true_hero.name").withStyle(ChatFormatting.GOLD)}));
        list.add(Component.translatable("+ if not a majin:"));
        list.add(Component.translatable(
                "tensura.evolution_menu.consume_requirement",
                TensuraMobDropItems.DEMON_ESSENCE.get().getDescription()
        ));
        return list;
    }
    public List<Race> getPreviousEvolutions() {
        List<Race> list = new ArrayList();
        list.add((Race) TensuraRaces.RACE_REGISTRY.get().getValue(TensuraAngelRaces.FALLEN_CHERUB));
        list.add((Race) TensuraRaces.RACE_REGISTRY.get().getValue(TensuraAngelRaces.SERAPHIM));

        return list;
    }

    public double getEvolutionPercentage(Player player) {
        double percentage = (double)0.0F;

        Race race = TensuraPlayerCapability.getRace(player);
        double essence = 0.0;
        if (player instanceof LocalPlayer localPlayer) {
            essence =  (double)localPlayer.getStats().getValue(Stats.ITEM_USED.get((Item)TensuraMobDropItems.DEMON_ESSENCE.get()));
        } else if (player instanceof ServerPlayer serverPlayer) {
            essence =  (double)serverPlayer.getStats().getValue(Stats.ITEM_USED.get((Item)TensuraMobDropItems.DEMON_ESSENCE.get()));
        }
        double reqPercentage = 0.0;
        double essencePercentage = 0.0;
        if (race != null && race.getClass() != SeraphimRace.class){
            essencePercentage = 0.0;
            if (TensuraEPCapability.getName(player) != null) {
                essencePercentage += (double)25.0F;
            }

            if (!TensuraPlayerCapability.isSpiritualForm(player)) {
                essencePercentage += (double)25.0F;
            }

            if (TensuraPlayerCapability.isTrueDemonLord(player) || TensuraPlayerCapability.isTrueHero(player)) {
                essencePercentage += (double)50.0F;
            }
        }else if (race != null && race.getClass() == SeraphimRace.class && !TensuraEPCapability.isMajin(player)){

            essencePercentage = essence * 50.0 / (double)TensuraAngelConfig.INSTANCE.racesConfig.essenceForStaffOfficerasAngel.get();
            if (TensuraEPCapability.getName(player) != null) {
                reqPercentage += (double)25.0F;
            }

            if (!TensuraPlayerCapability.isSpiritualForm(player)) {
                reqPercentage += (double)25.0F;
            }

            if (TensuraPlayerCapability.isTrueDemonLord(player) || TensuraPlayerCapability.isTrueHero(player)) {
                reqPercentage += (double)25.0F;
            }
            if (essence >= (double)TensuraAngelConfig.INSTANCE.racesConfig.essenceForMysticAngelasAngel.get()){
                essencePercentage = 50.0;
            }
        }else{
            if (TensuraEPCapability.getName(player) != null) {
                essencePercentage += (double)25.0F;
            }

            if (!TensuraPlayerCapability.isSpiritualForm(player)) {
                essencePercentage += (double)25.0F;
            }

            if (TensuraPlayerCapability.isTrueDemonLord(player) || TensuraPlayerCapability.isTrueHero(player)) {
                essencePercentage += (double)50.0F;
            }
        }

        return reqPercentage + essencePercentage;
    }
    public List<Race> getNextEvolutions() {
        List<Race> list = new ArrayList();
        return list;
    }
    @Nullable
    public Race getDefaultEvolution() {
        return null;
    }

    @Nullable
    public Race getAwakeningEvolution() {
        return null;
    }

    @Nullable
    public Race getHarvestFestivalEvolution() {
        return null;
    }


    public List<TensuraSkill> getIntrinsicSkills() {
        List<TensuraSkill> list = new ArrayList<>();
        list.add((TensuraSkill) IntrinsicSkills.DIVINE_KI_RELEASE.get());
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
}
