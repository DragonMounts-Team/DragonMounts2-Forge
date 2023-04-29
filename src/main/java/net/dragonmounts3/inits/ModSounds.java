package net.dragonmounts3.inits;

import net.dragonmounts3.DragonMounts;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static net.dragonmounts3.DragonMounts.prefix;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DragonMounts.create(ForgeRegistries.SOUND_EVENTS);
    public static final RegistryObject<SoundEvent> ENTITY_DRAGON_STEP = createSoundEvent("mob.dragon.step");
    public static final RegistryObject<SoundEvent> ENTITY_DRAGON_BREATHE = createSoundEvent("mob.dragon.breathe");
    public static final RegistryObject<SoundEvent> ENTITY_DRAGON_DEATH = createSoundEvent("mob.dragon.death");
    public static final RegistryObject<SoundEvent> ENTITY_DRAGON_GROWL = createSoundEvent("mob.dragon.growl");
    public static final RegistryObject<SoundEvent> ENTITY_DRAGON_HATCHLING_GROWL = createSoundEvent("mob.dragon.hatchlinggrowl");
    public static final RegistryObject<SoundEvent> ENTITY_NETHER_DRAGON_GROWL = createSoundEvent("mob.dragon.nethergrowl");
    public static final RegistryObject<SoundEvent> ENTITY_HATCHLING_NETHER_DRAGON_GROWL = createSoundEvent("mob.dragon.hatchlingnethergrowl");
    public static final RegistryObject<SoundEvent> ENTITY_SKELETON_DRAGON_GROWL = createSoundEvent("mob.dragon.skeletongrowl");
    public static final RegistryObject<SoundEvent> ENTITY_HATCHLING_SKELETON_DRAGON_GROWL = createSoundEvent("mob.dragon.hatchlingskeletongrowl");
    public static final RegistryObject<SoundEvent> ZOMBIE_DRAGON_DEATH = createSoundEvent("mob.dragon.zombiedeath");
    public static final RegistryObject<SoundEvent> ZOMBIE_DRAGON_GROWL = createSoundEvent("mob.dragon.zombiegrowl");
    public static final RegistryObject<SoundEvent> DRAGON_SNEEZE = createSoundEvent("mob.dragon.sneeze");
    public static final RegistryObject<SoundEvent> DRAGON_HATCHED = createSoundEvent("mob.dragon.hatched");
    public static final RegistryObject<SoundEvent> DRAGON_HATCHING = createSoundEvent("mob.dragon.hatching");
    public static final RegistryObject<SoundEvent> DRAGON_WHISTLE = createSoundEvent("item.whistle");
    public static final RegistryObject<SoundEvent> DRAGON_WHISTLE1 = createSoundEvent("item.whistle1");
    public static final RegistryObject<SoundEvent> DRAGON_ROAR = createSoundEvent("mob.dragon.roar");
    public static final RegistryObject<SoundEvent> HATCHLING_DRAGON_ROAR = createSoundEvent("mob.dragon.hatchlingroar");

    private static RegistryObject<SoundEvent> createSoundEvent(final String name) {
        return SOUNDS.register(name, () -> new SoundEvent(prefix(name)));
    }
}