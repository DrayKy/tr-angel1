package com.dranyas.trangel.race.true_dragon;


import com.github.manasmods.tensura.ability.TensuraSkill;
import com.github.manasmods.tensura.race.Race;
import com.github.manasmods.tensura.registry.skill.ExtraSkills;
import com.github.manasmods.tensura.registry.skill.IntrinsicSkills;
import com.github.manasmods.tensura.util.JumpPowerHelper;
import com.mojang.datafixers.util.Pair;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class TrueDragonRace extends Race {

    public TrueDragonRace() {
        super(Race.Difficulty.EASY);
    }

    public double getBaseHealth() {
        return 10000.0D;
    }

    public double getBaseAttackDamage() {
        return 1000D;
    }

    public double getBaseAttackSpeed() {
        return 200D;
    }

    public double getKnockbackResistance() {
        return 100D;
    }

    public double getJumpHeight() {
        return JumpPowerHelper.defaultPlayer(10.0D);
    }

    public double getMovementSpeed() {
        return 0.5D;
    }

    public double getSprintSpeed() {
        return 1.4D;
    }

    public float getPlayerSize() {
        return 2.0F;
    }

    public Pair<Double, Double> getBaseAuraRange() {
        return Pair.of(Double.valueOf(20000000.0D), Double.valueOf(40000000.0D));
    }

    public Pair<Double, Double> getBaseMagiculeRange() {
        return Pair.of(Double.valueOf(20000000.0D), Double.valueOf(40000000.0D));
    }

    public boolean isSpiritual() {
        return true;
    }

    public boolean isMajin() {
        return false;
    }

    public boolean isDivine() {
        return true;
    }
    
    public List<TensuraSkill> getIntrinsicSkills() {
        List<TensuraSkill> list = new ArrayList<>();
        list.add((TensuraSkill) IntrinsicSkills.DIVINE_KI_RELEASE.get());
        list.add((TensuraSkill) IntrinsicSkills.POSSESSION.get());
        list.add((TensuraSkill) ExtraSkills.UNIVERSAL_PERCEPTION.get());
        return list;
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
