package com.dranyas.trangel.race.angel;

import com.dranyas.trangel.registry.race.TensuraAngelRaces;
import com.github.manasmods.tensura.ability.TensuraSkill;
import com.github.manasmods.tensura.capability.ep.TensuraEPCapability;
import com.github.manasmods.tensura.capability.race.TensuraPlayerCapability;
import com.github.manasmods.tensura.race.Race;
import com.github.manasmods.tensura.registry.race.TensuraRaces;
import com.github.manasmods.tensura.registry.skill.ExtraSkills;
import com.github.manasmods.tensura.registry.skill.IntrinsicSkills;
import com.github.manasmods.tensura.registry.skill.ResistanceSkills;
import com.github.manasmods.tensura.util.JumpPowerHelper;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CherubRace extends Race{

    public CherubRace() {
        super(Race.Difficulty.EASY);
    }

    public double getBaseHealth() {
        return 800.0D;
    }

    public float getPlayerSize() {
        return 2.0F;
    }

    public double getBaseAttackDamage() {
        return 4.0;
    }

    public double getBaseAttackSpeed() {
        return (double)4.5F;
    }

    public double getKnockbackResistance() {
        return 0.8;
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

    public Pair<Double, Double> getBaseAuraRange() {
        return Pair.of((double)505000.0F, (double)550000.0F);
    }

    public Pair<Double, Double> getBaseMagiculeRange() {
        return Pair.of((double)205000.0F, (double)355000.0F);
    }

    public boolean isSpiritual() {
        return false;
    }

    public boolean isMajin() {
        return false;
    }

    public boolean isDivine() {
        return false;
    }

    public ResourceKey<Level> getRespawnDimension() {
        return Level.OVERWORLD;
    }

    public List<Component> getRequirementsForRendering() {
        List<Component> list = new ArrayList();
        list.add(Component.translatable("tensura.evolution_menu.name_requirement"));
        list.add(Component.translatable("tensura.evolution_menu.physical_body_requirement"));
        list.add(Component.translatable("tensura.evolution_menu.awaken_requirement", new Object[]{Component.translatable("tensura.attribute.true_demon_lord.name").withStyle(ChatFormatting.DARK_PURPLE), Component.translatable("tensura.attribute.true_hero.name").withStyle(ChatFormatting.GOLD)}));
        return list;
    }
    public List<Race> getPreviousEvolutions() {
        List<Race> list = new ArrayList();
        list.add((Race) TensuraRaces.RACE_REGISTRY.get().getValue(TensuraAngelRaces.ARCHANGEL));

        return list;
    }

    public double getEvolutionPercentage(Player player) {
        double percentage = (double)0.0F;
        if (TensuraEPCapability.getName(player) != null) {
            percentage += (double)50.0F;
        }

        if (!TensuraPlayerCapability.isSpiritualForm(player)) {
            percentage += (double)50.0F;
        }

        if (TensuraPlayerCapability.isTrueDemonLord(player) || TensuraPlayerCapability.isTrueHero(player)) {
            percentage += (double)50.0F;
        }

        return percentage;
    }

    @Nullable
    public List<Race> getNextEvolutions() {
        List<Race> list = new ArrayList();
        list.add((Race) TensuraRaces.RACE_REGISTRY.get().getValue(TensuraAngelRaces.SERAPHIM));
        list.add((Race) TensuraRaces.RACE_REGISTRY.get().getValue(TensuraAngelRaces.FALLEN_CHERUB));
        list.add((Race)TensuraRaces.RACE_REGISTRY.get().getValue(TensuraAngelRaces.STAFF_OFFICER));
        return list;
    }

    public List<TensuraSkill> getIntrinsicSkills() {
        List<TensuraSkill> list = new ArrayList<>();
        list.add((TensuraSkill) ResistanceSkills.HOLY_ATTACK_RESISTANCE.get());
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
