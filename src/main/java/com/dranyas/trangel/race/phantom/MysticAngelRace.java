package com.dranyas.trangel.race.phantom;

import com.dranyas.trangel.config.TensuraAngelConfig;
import com.dranyas.trangel.race.angel.SeraphimRace;
import com.dranyas.trangel.registry.race.TensuraAngelRaces;
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

import java.util.ArrayList;
import java.util.List;

public class MysticAngelRace extends Race{

    public MysticAngelRace() {
        super(Difficulty.INTERMEDIATE);
    }

    public double getBaseHealth() {
        return 7777.0D;
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
        return JumpPowerHelper.defaultPlayer((double)3.0F);
    }

    public double getMovementSpeed() {
        return 0.3;
    }

    public double getSprintSpeed() {
        return 1.4;
    }

    public Pair<Double, Double> getBaseAuraRange() {
        return Pair.of((double)1005000.0F, (double)1205000.0F);
    }

    public Pair<Double, Double> getBaseMagiculeRange() {
        return Pair.of((double)1005000.0F, (double)1205000.0F);
    }

    public boolean isSpiritual() {
        return false;
    }

    public boolean isMajin() {
        return false;
    }

    public boolean isDivine() {
        return true;
    }

    public double getSpiritualHealthMultiplier() {
        return (double)6.0F;
    }


    public ResourceKey<Level> getRespawnDimension() {
        return Level.OVERWORLD;
    }
    public List<Race> getPreviousEvolutions() {
        List<Race> list = new ArrayList();
        list.add((Race)TensuraRaces.RACE_REGISTRY.get().getValue(TensuraAngelRaces.STAFF_OFFICER));
        list.add((Race)TensuraRaces.RACE_REGISTRY.get().getValue(TensuraAngelRaces.SERAPHIM));

        return list;
    }


    public List<TensuraSkill> getIntrinsicSkills() {
        List<TensuraSkill> list = new ArrayList<>();
        list.add((TensuraSkill) IntrinsicSkills.DIVINE_KI_RELEASE.get());
        list.add((TensuraSkill) ResistanceSkills.HOLY_ATTACK_RESISTANCE.get());
        list.add((TensuraSkill) ExtraSkills.MAGIC_LIGHT_TRANSFORM.get());
        return list;
    }

    public double getEvolutionPercentage(Player player) {
        double percentage = (double)0.0F;

        Race race = TensuraPlayerCapability.getRace(player);
        double essence = 0.0;
        if (player instanceof LocalPlayer localPlayer) {
            essence =  (double)localPlayer.getStats().getValue(Stats.ITEM_USED.get((Item)TensuraMobDropItems.DRAGON_ESSENCE.get()));
        } else if (player instanceof ServerPlayer serverPlayer) {
            essence =  (double)serverPlayer.getStats().getValue(Stats.ITEM_USED.get((Item)TensuraMobDropItems.DRAGON_ESSENCE.get()));
        }
        double essencePercentage = 0.0;
        if (race != null && race.getClass() != SeraphimRace.class){
            essencePercentage = 0.0;
            if (TensuraEPCapability.getName(player) != null) {
                essencePercentage += (double)25.0F;
            }

            if (TensuraPlayerCapability.isTrueDemonLord(player) || TensuraPlayerCapability.isTrueHero(player)) {
                essencePercentage += (double)75.0F;
            }
        }else if (race != null && race.getClass() == SeraphimRace.class){
            essencePercentage = essence * 100.0 / (double)TensuraAngelConfig.INSTANCE.racesConfig.essenceForMysticAngelasAngel.get();
            if (essence >= (double)TensuraAngelConfig.INSTANCE.racesConfig.essenceForMysticAngelasAngel.get()){
                essencePercentage = 100.0;
            }
        }
        return essencePercentage;
    }

    public List<Component> getRequirementsForRendering() {
        List<Component> list = new ArrayList();
        list.add(Component.translatable("tensura.evolution_menu.name_requirement"));
        list.add(Component.translatable("tensura.evolution_menu.awaken_requirement", new Object[]{Component.translatable("tensura.attribute.true_demon_lord.name").withStyle(ChatFormatting.DARK_PURPLE), Component.translatable("tensura.attribute.true_hero.name").withStyle(ChatFormatting.GOLD)}));
        list.add(Component.translatable("+ if you are an angel:"));
        list.add(Component.translatable(
                "tensura.evolution_menu.consume_requirement",
                TensuraMobDropItems.DRAGON_ESSENCE.get().getDescription()
        ));
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
