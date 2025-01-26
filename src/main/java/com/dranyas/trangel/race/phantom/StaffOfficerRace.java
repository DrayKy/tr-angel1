package com.dranyas.trangel.race.phantom;

import com.dranyas.trangel.config.TensuraAngelConfig;
import com.dranyas.trangel.race.angel.ArchAngelRace;
import com.dranyas.trangel.race.angel.CherubRace;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class StaffOfficerRace extends Race{

    public StaffOfficerRace() {
        super(Difficulty.INTERMEDIATE);
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

    public Pair<Double, Double> getBaseAuraRange() {
        return Pair.of((double)255000.0F, (double)305000.0F);
    }

    public Pair<Double, Double> getBaseMagiculeRange() {
        return Pair.of((double)455000.0F, (double)505000.0F);
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

    public boolean canFly() {
        return true;
    }

    public double getAdditionalSpiritualHealth() {
        return (double)50.0F;
    }

    public ResourceKey<Level> getRespawnDimension() {
        return Level.OVERWORLD;
    }


    @Nullable
    public  Race getAwakeningEvolution() {
        return TensuraRaces.RACE_REGISTRY.get().getValue(TensuraAngelRaces.MYSTIC_ANGEL);
    }


    public List<Race> getNextEvolutions() {
        List<Race> list = new ArrayList();
        list.add((Race)TensuraRaces.RACE_REGISTRY.get().getValue(TensuraAngelRaces.MYSTIC_ANGEL));
        return list;
    }

    public List<Race> getPreviousEvolutions() {
        List<Race> list = new ArrayList();
        list.add((Race)TensuraRaces.RACE_REGISTRY.get().getValue(TensuraAngelRaces.GENERAL));
        list.add((Race)TensuraRaces.RACE_REGISTRY.get().getValue(TensuraAngelRaces.ARCHANGEL));


        return list;
    }


    public double getEvolutionPercentage(Player player) {
        double percentage = (double)0.0F;

        Race race = TensuraPlayerCapability.getRace(player);
        double essence = 0.0;
        if (FMLEnvironment.dist == Dist.CLIENT && player instanceof LocalPlayer localPlayer) {
            essence =  (double)localPlayer.getStats().getValue(Stats.ITEM_USED.get((Item)TensuraMobDropItems.DRAGON_ESSENCE.get()));
        } else if (player instanceof ServerPlayer serverPlayer) {
            essence =  (double)serverPlayer.getStats().getValue(Stats.ITEM_USED.get((Item)TensuraMobDropItems.DRAGON_ESSENCE.get()));
        }
        double reqPercentage = 0.0;
        double essencePercentage = 0.0;
        if (race != null && race.getClass() != ArchAngelRace.class){
            essencePercentage = 0.0;
            if (TensuraEPCapability.getName(player) != null) {
                essencePercentage += (double)100.0F;
            }

            if (TensuraPlayerCapability.isTrueDemonLord(player) || TensuraPlayerCapability.isTrueHero(player)) {
                essencePercentage += (double)100.0F;
            }
        }else if (race != null && (race.getClass() == ArchAngelRace.class || race.getClass() == CherubRace.class)){

            essencePercentage = essence * 50.0 / (double)TensuraAngelConfig.INSTANCE.racesConfig.essenceForStaffOfficerasAngel.get();
            if (TensuraEPCapability.getName(player) != null) {
                reqPercentage += (double)50.0F;
            }

            if (TensuraPlayerCapability.isTrueDemonLord(player) || TensuraPlayerCapability.isTrueHero(player)) {
                reqPercentage += (double)50.0F;
            }
            if (essence >= (double)TensuraAngelConfig.INSTANCE.racesConfig.essenceForStaffOfficerasAngel.get()){
                essencePercentage = 50.0;
            }
        }

        return reqPercentage + essencePercentage;
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


    public List<TensuraSkill> getIntrinsicSkills() {
        List<TensuraSkill> list = new ArrayList<>();
        list.add((TensuraSkill) ResistanceSkills.PHYSICAL_ATTACK_RESISTANCE.get());
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
