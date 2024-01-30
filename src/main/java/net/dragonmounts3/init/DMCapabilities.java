package net.dragonmounts3.init;

import net.dragonmounts3.capability.ArmorEffectManager;
import net.dragonmounts3.capability.IArmorEffectManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;

import static net.dragonmounts3.DragonMounts.prefix;

public class DMCapabilities {
    public static final ResourceLocation ARMOR_EFFECT_MANAGER_ID = prefix("armor_effect_manager");

    @SuppressWarnings("CanBeFinal")
    @CapabilityInject(IArmorEffectManager.class)
    public static Capability<IArmorEffectManager> ARMOR_EFFECT_MANAGER = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IArmorEffectManager.class, new ArmorEffectManager.Storage(), ArmorEffectManager::new);
    }

    public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();
        if (entity instanceof PlayerEntity) {
            event.addCapability(ARMOR_EFFECT_MANAGER_ID, new ArmorEffectManager.Provider((PlayerEntity) entity));
        }
    }
}
