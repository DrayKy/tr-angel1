package com.dranyas.trangel.race.phantom;

import com.dranyas.trangel.registry.race.TensuraAngelRaces;
import com.github.manasmods.tensura.ability.TensuraSkill;
import com.github.manasmods.tensura.race.Race;
import com.github.manasmods.tensura.registry.dimensions.TensuraDimensions;
import com.github.manasmods.tensura.registry.race.TensuraRaces;
import com.github.manasmods.tensura.registry.skill.IntrinsicSkills;
import com.github.manasmods.tensura.registry.skill.ResistanceSkills;
import com.github.manasmods.tensura.util.JumpPowerHelper;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PhantomRace extends Race{

    public PhantomRace() {
        super(Difficulty.INTERMEDIATE);
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
        return Pair.of((double)2500.0F, (double)3500.0F);
    }

    public Pair<Double, Double> getBaseMagiculeRange() {
        return Pair.of((double)5500.0F, (double)6500.0F);
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

    @Nullable
    public  Race getDefaultEvolution() {
        return TensuraRaces.RACE_REGISTRY.get().getValue(TensuraAngelRaces.FIELD_OFFICER);
    }
    @Nullable
    public  Race getAwakeningEvolution() {
        return TensuraRaces.RACE_REGISTRY.get().getValue(TensuraAngelRaces.STAFF_OFFICER);
    }
    @Nullable
    public Race getHarvestFestivalEvolution() {
        return TensuraRaces.RACE_REGISTRY.get().getValue(TensuraAngelRaces.GENERAL);
    }
    public List<Race> getNextEvolutions() {
        List<Race> list = new ArrayList();
        list.add((Race)TensuraRaces.RACE_REGISTRY.get().getValue(TensuraAngelRaces.FIELD_OFFICER));
        return list;
    }

    public List<TensuraSkill> getIntrinsicSkills() {
        List<TensuraSkill> list = new ArrayList<>();
        list.add((TensuraSkill) IntrinsicSkills.POSSESSION.get());
        list.add((TensuraSkill) ResistanceSkills.MAGIC_RESISTANCE.get());
        return list;
    }

}
