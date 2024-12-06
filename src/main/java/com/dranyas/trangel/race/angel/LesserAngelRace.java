package com.dranyas.trangel.race.angel;

import com.dranyas.trangel.registry.race.TensuraAngelRaces;
import com.github.manasmods.tensura.ability.TensuraSkill;
import com.github.manasmods.tensura.capability.ep.TensuraEPCapability;
import com.github.manasmods.tensura.race.Race;
import com.github.manasmods.tensura.registry.dimensions.TensuraDimensions;
import com.github.manasmods.tensura.registry.race.TensuraRaces;
import com.github.manasmods.tensura.registry.skill.IntrinsicSkills;
import com.github.manasmods.tensura.registry.skill.ResistanceSkills;
import com.github.manasmods.tensura.util.JumpPowerHelper;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class LesserAngelRace extends Race{

    public LesserAngelRace() {
        super(Difficulty.EASY);

    }

    public double getBaseHealth() {
        return 40.0D;
    }

    public float getPlayerSize() {
        return 3.0F;
    }

    public double getBaseAttackDamage() {
        return 1.4;
    }

    public double getBaseAttackSpeed() {
        return (double)3.5F;
    }

    public double getKnockbackResistance() {
        return 0.1;
    }

    public double getJumpHeight() {
        return JumpPowerHelper.defaultPlayer();
    }

    public double getMovementSpeed() {
        return 0.1;
    }

    public double getSprintSpeed() {
        return 0.2;
    }

    public double getAdditionalSpiritualHealth() {
        return (double)20.0F;
    }

    public double getSpiritualHealthMultiplier() {
        return (double)2.5F;
    }

    public Pair<Double, Double> getBaseAuraRange() {
        return Pair.of((double)5500.0F, (double)6500.0F);
    }

    public Pair<Double, Double> getBaseMagiculeRange() {
        return Pair.of((double)1500.0F, (double)3500.0F);
    }

    public boolean isSpiritual() {
        return true;
    }

    public boolean isMajin() {
        return false;
    }

    public boolean isDivine() {
        return false;
    }

    public ResourceKey<Level> getRespawnDimension() {
        return TensuraDimensions.LABYRINTH;
    }
    public Player getCurrentPlayer() {
        // Fetch the player from the game state, world, or manager
        return Minecraft.getInstance().player; // Example for Minecraft
    }

    @Nullable
    public  Race getDefaultEvolution() {
        Player player = getCurrentPlayer();

        if (TensuraEPCapability.isMajin(player)){
            return TensuraRaces.RACE_REGISTRY.get().getValue(TensuraAngelRaces.FALLEN_GREATER_ANGEL);
        }else {
            return TensuraRaces.RACE_REGISTRY.get().getValue(TensuraAngelRaces.GREATER_ANGEL);
        }
    }
    @Nullable
    public  Race getAwakeningEvolution() {
        Player player = getCurrentPlayer();

        if (TensuraEPCapability.isMajin(player)){
            return TensuraRaces.RACE_REGISTRY.get().getValue(TensuraAngelRaces.FALLEN_CHERUB);
        }else {
            return TensuraRaces.RACE_REGISTRY.get().getValue(TensuraAngelRaces.CHERUB);
        }
    }
    @Nullable
    public Race getHarvestFestivalEvolution() {
        Player player = getCurrentPlayer();

        if (TensuraEPCapability.isMajin(player)){
            return TensuraRaces.RACE_REGISTRY.get().getValue(TensuraAngelRaces.FALLEN_ARCHANGEL);
        }else {
            return TensuraRaces.RACE_REGISTRY.get().getValue(TensuraAngelRaces.ARCHANGEL);
        }
    }


    public List<Race> getNextEvolutions() {
        Player player = getCurrentPlayer();

        List<Race> list = new ArrayList();
        if (TensuraEPCapability.isMajin(player)) {
            list.add((Race)TensuraRaces.RACE_REGISTRY.get().getValue(TensuraAngelRaces.FALLEN_GREATER_ANGEL));
        }else {
            list.add((Race)TensuraRaces.RACE_REGISTRY.get().getValue(TensuraAngelRaces.GREATER_ANGEL));
            list.add((Race)TensuraRaces.RACE_REGISTRY.get().getValue(TensuraAngelRaces.FALLEN_GREATER_ANGEL));
            list.add((Race)TensuraRaces.RACE_REGISTRY.get().getValue(TensuraAngelRaces.FIELD_OFFICER));
        }

        return list;
    }

    public List<TensuraSkill> getIntrinsicSkills() {
        List<TensuraSkill> list = new ArrayList<>();
        list.add((TensuraSkill) IntrinsicSkills.POSSESSION.get());
        list.add((TensuraSkill) IntrinsicSkills.LIGHT_TRANSFORM.get());
        list.add((TensuraSkill) ResistanceSkills.MAGIC_RESISTANCE.get());
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
