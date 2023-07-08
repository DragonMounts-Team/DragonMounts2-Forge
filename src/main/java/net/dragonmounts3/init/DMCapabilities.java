package net.dragonmounts3.init;

import net.dragonmounts3.capability.DragonTypifiedCooldown;
import net.dragonmounts3.capability.IDragonTypifiedCooldown;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;

import static net.dragonmounts3.DragonMounts.prefix;

public class DMCapabilities {
    public static final String DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN_ID = "dragon_scale_armor_cd";

    @CapabilityInject(IDragonTypifiedCooldown.class)
    public static Capability<IDragonTypifiedCooldown> DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IDragonTypifiedCooldown.class, new DragonTypifiedCooldown.Storage(), DragonTypifiedCooldown::new);
    }

    public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();
        if (entity instanceof PlayerEntity) {
            event.addCapability(prefix(DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN_ID), new DragonTypifiedCooldown.Provider((PlayerEntity) entity));
        }
    }
}
