package net.dragonmounts3.util;

import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Reference2IntMap;
import it.unimi.dsi.fastutil.objects.Reference2IntMaps;
import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
import net.dragonmounts3.api.IArmorEffect;
import net.dragonmounts3.api.IArmorEffectSource;
import net.dragonmounts3.capability.IDragonTypifiedCooldown;
import net.dragonmounts3.network.SInitCooldownPacket;
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
import java.util.WeakHashMap;

import static net.dragonmounts3.init.DMCapabilities.DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN;
import static net.dragonmounts3.network.DMPacketHandler.CHANNEL;
import static net.minecraftforge.fml.network.PacketDistributor.PLAYER;

public class ArmorEffect {
    private static final WeakHashMap<PlayerEntity, Reference2IntOpenHashMap<IArmorEffect>> EFFECT_CACHE = new WeakHashMap<>();
    private static final WeakHashMap<PlayerEntity, Reference2IntMap<IArmorEffect>> READONLY_EFFECT_CACHE = new WeakHashMap<>();
    private static final NonNullConsumer<IDragonTypifiedCooldown> TICK_COOLDOWN = IDragonTypifiedCooldown::tick;

    /**
     * Do not modify the Map returned by this!
     */
    @SuppressWarnings("unchecked")
    public static Reference2IntMap<IArmorEffect> getCache(PlayerEntity player) {
        Reference2IntMap<IArmorEffect> result = READONLY_EFFECT_CACHE.get(player);
        if (result == null) {
            Reference2IntMap<IArmorEffect> map = EFFECT_CACHE.get(player);
            if (map == null) return (Reference2IntMap<IArmorEffect>) Collections.EMPTY_MAP;
            result = Reference2IntMaps.unmodifiable(map);
            READONLY_EFFECT_CACHE.put(player, result);
        }
        return result;
    }

    private static void checkSlot(Reference2IntMap<IArmorEffect> map, PlayerEntity player, EquipmentSlotType slot) {
        ItemStack stack = player.getItemBySlot(slot);
        Item item = stack.getItem();
        if (item instanceof IArmorEffectSource) {
            ((IArmorEffectSource) item).attachEffect(map, player, stack);
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) return;
        Reference2IntOpenHashMap<IArmorEffect> map = EFFECT_CACHE.get(event.player);
        if (map == null) {
            map = new Reference2IntOpenHashMap<>();
            EFFECT_CACHE.put(event.player, map);
        } else {
            map.clear();
        }
        checkSlot(map, event.player, EquipmentSlotType.HEAD);
        checkSlot(map, event.player, EquipmentSlotType.CHEST);
        checkSlot(map, event.player, EquipmentSlotType.LEGS);
        checkSlot(map, event.player, EquipmentSlotType.FEET);
        for (ObjectIterator<Reference2IntMap.Entry<IArmorEffect>> it = map.reference2IntEntrySet().fastIterator(); it.hasNext(); ) {
            Reference2IntMap.Entry<IArmorEffect> entry = it.next();
            entry.getKey().apply(event.player, entry.getIntValue());
        }
        event.player.getCapability(DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN).ifPresent(TICK_COOLDOWN);
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        PlayerEntity player = event.getPlayer();
        if (player instanceof ServerPlayerEntity) {
            player.getCapability(DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN).ifPresent(cooldown -> {
                SInitCooldownPacket packet = cooldown.createPacket();
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

    private ArmorEffect() {}
}
