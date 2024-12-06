package com.dranyas.trangel.race;

import com.github.manasmods.manascore.api.skills.ManasSkill;
import com.github.manasmods.manascore.api.skills.ManasSkillInstance;
import com.github.manasmods.manascore.api.skills.event.RemoveSkillEvent;
import com.github.manasmods.tensura.ability.ISpatialStorage;
import com.github.manasmods.tensura.ability.SkillUtils;
import com.github.manasmods.tensura.ability.TensuraSkill;
import com.github.manasmods.tensura.capability.skill.TensuraSkillCapability;
import com.github.manasmods.tensura.network.TensuraNetwork;
import com.github.manasmods.tensura.network.play2client.RequestClientSkillRemovePacket;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;

public class AngelRace {

    public static Player getCurrentPlayer() {
        // Fetch the player from the game state, world, or manager
        return Minecraft.getInstance().player; // Example for Minecraft
    }
    public static void onSkillRemove(RemoveSkillEvent event) {
        // Get the skill instance from the event
        Player player = getCurrentPlayer();
        ManasSkillInstance skillInstance = event.getSkillInstance();

        // Check if the entity is in a certain state, and return early if so
        if (event.getEntity().isAlive()) {
            return;
        }

        // Get the entity that triggered the event

        // If the entity is a player, handle the skill removal
        if (player instanceof ServerPlayer) {
            ServerPlayer player1 = (ServerPlayer) player;

            // Toggle off the skill for the player
            skillInstance.onToggleOff(player);

            // Remove the skill from the player's slots
            TensuraSkillCapability.removeSkillFromSlots(player, skillInstance.getSkill());

            // If the skill has spatial storage, drop all items associated with the skill
            ManasSkill skill = skillInstance.getSkill();
            if (skill instanceof ISpatialStorage) {
                ISpatialStorage storage = (ISpatialStorage) skill;
                storage.dropAllItems(skillInstance, player);
            }

            // Send a packet to the player to inform them the skill has been removed
            TensuraNetwork.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player1),
                    new RequestClientSkillRemovePacket(
                            SkillUtils.getSkillId(skillInstance.getSkill()),
                            player.getId()
                    )
            );

            // If the skill should be removed temporarily, send a message to the player
            if (skillInstance.shouldRemove()) {
                player.sendSystemMessage(Component.translatable(
                        "tensura.skill.temporary.remove",
                        skillInstance.getSkill().getName()
                ).setStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
            }
        }
    }
    public List<TensuraSkill> skillRemoval() {
        return new ArrayList();
    }

    public boolean isNotIntrinsicSkill(ManasSkill skill) {
        return this.skillRemoval().contains(skill);
    }

}
