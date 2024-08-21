package net.dragonmounts.init;

import net.dragonmounts.DragonMounts;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static net.dragonmounts.DragonMounts.makeId;

public class DMSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DragonMounts.create(ForgeRegistries.SOUND_EVENTS);
    public static final RegistryObject<SoundEvent> DRAGON_STEP = createSoundEvent("mob.dragon.step");
    public static final RegistryObject<SoundEvent> DRAGON_BREATHE = createSoundEvent("mob.dragon.breathe");
    public static final RegistryObject<SoundEvent> DRAGON_DEATH = createSoundEvent("mob.dragon.death");
    public static final RegistryObject<SoundEvent> DRAGON_GROWL = createSoundEvent("mob.dragon.growl");
    public static final RegistryObject<SoundEvent> DRAGON_HATCHLING_GROWL = createSoundEvent("mob.dragon.hatchlinggrowl");
    public static final RegistryObject<SoundEvent> NETHER_DRAGON_GROWL = createSoundEvent("mob.dragon.nethergrowl");
    public static final RegistryObject<SoundEvent> SKELETON_DRAGON_GROWL = createSoundEvent("mob.dragon.skeletongrowl");
    public static final RegistryObject<SoundEvent> ZOMBIE_DRAGON_DEATH = createSoundEvent("mob.dragon.zombiedeath");
    public static final RegistryObject<SoundEvent> ZOMBIE_DRAGON_GROWL = createSoundEvent("mob.dragon.zombiegrowl");
    public static final RegistryObject<SoundEvent> DRAGON_SNEEZE = createSoundEvent("mob.dragon.sneeze");
    public static final RegistryObject<SoundEvent> DRAGON_HATCHED = createSoundEvent("mob.dragon.hatched");
    public static final RegistryObject<SoundEvent> DRAGON_HATCHING = createSoundEvent("mob.dragon.hatching");
    public static final RegistryObject<SoundEvent> DRAGON_WHISTLE = createSoundEvent("item.whistle");
    public static final RegistryObject<SoundEvent> DRAGON_ROAR = createSoundEvent("mob.dragon.roar");
    public static final RegistryObject<SoundEvent> HATCHLING_DRAGON_ROAR = createSoundEvent("mob.dragon.hatchlingroar");
    public static final RegistryObject<SoundEvent> VARIANT_SWITCHER = createSoundEvent("item.variant_switcher");

    private static RegistryObject<SoundEvent> createSoundEvent(final String name) {
        return SOUNDS.register(name, () -> new SoundEvent(makeId(name)));
    }
}