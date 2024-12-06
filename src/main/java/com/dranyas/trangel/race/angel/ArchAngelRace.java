package com.dranyas.trangel.race.angel;

import com.dranyas.trangel.config.TensuraAngelConfig;
import com.dranyas.trangel.registry.race.TensuraAngelRaces;
import com.github.manasmods.tensura.ability.TensuraSkill;
import com.github.manasmods.tensura.capability.race.TensuraPlayerCapability;
import com.github.manasmods.tensura.race.Race;
import com.github.manasmods.tensura.registry.dimensions.TensuraDimensions;
import com.github.manasmods.tensura.registry.race.TensuraRaces;
import com.github.manasmods.tensura.registry.skill.ExtraSkills;
import com.github.manasmods.tensura.registry.skill.IntrinsicSkills;
import com.github.manasmods.tensura.registry.skill.ResistanceSkills;
import com.github.manasmods.tensura.util.JumpPowerHelper;
import com.mojang.datafixers.util.Pair;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ArchAngelRace extends Race{
    public ArchAngelRace() {
        super(Race.Difficulty.EASY);
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

    public double getJumpHeight() {
        return JumpPowerHelper.defaultPlayer();
    }

    public double getMovementSpeed() {
        return 0.5;
    }

    public double getSprintSpeed() {
        return 0.8;
    }

    public double getAdditionalSpiritualHealth() {
        return (double)30.0F;
    }

    public Pair<Double, Double> getBaseAuraRange() {
        return Pair.of((double)115000.0F, (double)125000.0F);
    }

    public Pair<Double, Double> getBaseMagiculeRange() {
        return Pair.of((double)65000.0F, (double)80000.0F);
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

    public List<Race> getPreviousEvolutions() {
        List<Race> list = new ArrayList();
        list.add((Race) TensuraRaces.RACE_REGISTRY.get().getValue(TensuraAngelRaces.GREATER_ANGEL));

        return list;
    }

    @Nullable
    public List<Race> getNextEvolutions() {
        List<Race> list = new ArrayList();
        list.add((Race)TensuraRaces.RACE_REGISTRY.get().getValue(TensuraAngelRaces.CHERUB));
        list.add((Race)TensuraRaces.RACE_REGISTRY.get().getValue(TensuraAngelRaces.FALLEN_CHERUB));
        list.add((Race)TensuraRaces.RACE_REGISTRY.get().getValue(TensuraAngelRaces.STAFF_OFFICER));
        return list;
    }
    public List<Component> getRequirementsForRendering(Player player) {
        List<Component> list = new ArrayList<>();
        list.add(Component.translatable(
                "tensura.evolution_menu.ep_requirement"
        ));

        return list;
    }
    public double getEvolutionPercentage(Player player) {
        return TensuraPlayerCapability.getBaseEP(player) * 100.0D / ((Double) TensuraAngelConfig.INSTANCE.racesConfig.epToArchAngel.get()).doubleValue();

    }

    public List<TensuraSkill> getIntrinsicSkills() {
        List<TensuraSkill> list = new ArrayList<>();
        list.add((TensuraSkill) ResistanceSkills.PHYSICAL_ATTACK_RESISTANCE.get());
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
