package net.dragonmounts3.util;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.dragonmounts3.api.IArmorEffect;
import net.dragonmounts3.api.IArmorEffectSource;
import net.dragonmounts3.capability.IDragonTypifiedCooldown;
import net.dragonmounts3.network.SSyncCooldownPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.NonNullConsumer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

import static net.dragonmounts3.init.DMCapabilities.DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN;
import static net.dragonmounts3.network.DMPacketHandler.CHANNEL;
import static net.minecraftforge.fml.network.PacketDistributor.PLAYER;

public class ArmorEffect {
    private static final WeakHashMap<PlayerEntity, Object2IntOpenHashMap<IArmorEffect>> EFFECT_CACHE = new WeakHashMap<>();
    private static final NonNullConsumer<IDragonTypifiedCooldown> TICK_COOLDOWN = IDragonTypifiedCooldown::tick;

    /**
     * Do not modify the Map returned by this!
     */
    public static Map<IArmorEffect, Integer> getCache(PlayerEntity player) {
        Map<IArmorEffect, Integer> map = EFFECT_CACHE.get(player);
        if (map == null) return Collections.emptyMap();
        return map;
    }

    private static void checkSlot(Map<IArmorEffect, Integer> map, PlayerEntity player, EquipmentSlotType slot) {
        ItemStack stack = player.getItemBySlot(slot);
        Item item = stack.getItem();
        if (item instanceof IArmorEffectSource) {
            ((IArmorEffectSource) item).putEffect(map, player, stack);
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) return;
        event.player.getCapability(DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN).ifPresent(TICK_COOLDOWN);
        Object2IntOpenHashMap<IArmorEffect> map = EFFECT_CACHE.get(event.player);
        if (map == null) {
            map = new Object2IntOpenHashMap<>();
            EFFECT_CACHE.put(event.player, map);
        } else {
            map.clear();
        }
        checkSlot(map, event.player, EquipmentSlotType.HEAD);
        checkSlot(map, event.player, EquipmentSlotType.CHEST);
        checkSlot(map, event.player, EquipmentSlotType.LEGS);
        checkSlot(map, event.player, EquipmentSlotType.FEET);
        for (Map.Entry<IArmorEffect, Integer> entry : map.object2IntEntrySet()) {
            entry.getKey().invoke(event.player, entry.getValue());
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        PlayerEntity player = event.getPlayer();
        if (player instanceof ServerPlayerEntity) {
            player.getCapability(DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN).ifPresent(cooldown -> {
                SSyncCooldownPacket packet = cooldown.createPacket();
                if (packet != null) {
                    CHANNEL.send(PLAYER.with(() -> (ServerPlayerEntity) player), packet);
                }
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getOriginal().getCapability(DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN).ifPresent(cooldown -> {
                Capability.IStorage<IDragonTypifiedCooldown> storage = DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN.getStorage();
                storage.readNBT(DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN, cooldown, null, storage.writeNBT(DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN, cooldown, null));
            });
        }
    }

    private ArmorEffect() {
    }
}
