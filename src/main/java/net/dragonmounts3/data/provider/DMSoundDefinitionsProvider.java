package net.dragonmounts3.data.provider;

import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

import static net.dragonmounts3.DragonMounts.MOD_ID;
import static net.dragonmounts3.DragonMounts.prefix;
import static net.dragonmounts3.init.DMSounds.*;

public class DMSoundDefinitionsProvider extends SoundDefinitionsProvider {
    public static final String TITLE_PREFIX = "subtitles." + MOD_ID + '.';

    public DMSoundDefinitionsProvider(DataGenerator generator, ExistingFileHelper helper) {
        super(generator, MOD_ID, helper);
    }

    @Override
    public void registerSounds() {
        this.add(DRAGON_GROWL, definition()
                .subtitle(TITLE_PREFIX + "dragon.growl")
                .with(sound(prefix("mob/dragon/growl")),
                        sound(prefix("mob/dragon/growl1"))
                ));
        this.add(HATCHLING_DRAGON_ROAR, definition()
                .subtitle(TITLE_PREFIX + "dragon.hatchling_roar")
                .with(sound(prefix("mob/dragon/hatchlingroar1")),
                        sound(prefix("mob/dragon/hatchlingroar2"))
                ));
        this.add(DRAGON_HATCHLING_GROWL, definition()
                .subtitle(TITLE_PREFIX + "dragon.hatchling_growl")
                .with(sound(prefix("mob/dragon/hatchlinggrowl1")),
                        sound(prefix("mob/dragon/hatchlinggrowl2"))
                ));
        this.add(DRAGON_ROAR, definition()
                .subtitle(TITLE_PREFIX + "dragon.roar")
                .with(
                        sound(prefix("mob/dragon/roar")),
                        sound(prefix("mob/dragon/roar1")),
                        sound(prefix("mob/dragon/roar2")),
                        sound(prefix("mob/dragon/roar3"))
                ));
        this.add(NETHER_DRAGON_GROWL, definition()
                .subtitle(TITLE_PREFIX + "dragon.growl.nether")
                .with(
                        sound(prefix("mob/dragon/nethergrowl")),
                        sound(prefix("mob/dragon/nethergrowl1")),
                        sound(prefix("mob/dragon/nethergrowl2")),
                        sound(prefix("mob/dragon/nethergrowl3"))
                ));
        this.add(ZOMBIE_DRAGON_GROWL, definition()
                .subtitle(TITLE_PREFIX + "dragon.growl.zombie")
                .with(
                        sound(prefix("mob/dragon/zombiegrowl")),
                        sound(prefix("mob/dragon/zombiegrowl1")),
                        sound(prefix("mob/dragon/zombiegrowl2"))
                ));
        this.add(SKELETON_DRAGON_GROWL, definition()
                .subtitle(TITLE_PREFIX + "dragon.growl.skeleton")
                .with(sound(prefix("mob/dragon/skeletongrowl1"))));
        this.add(DRAGON_BREATHE, definition()
                .subtitle(TITLE_PREFIX + "dragon.breathe")
                .with(
                        sound(new ResourceLocation("minecraft", "mob/cow/say2")).volume(0.5f).pitch(0.5f),
                        sound(new ResourceLocation("minecraft", "mob/cow/say4")).volume(0.5f).pitch(0.5f)
                ));
        this.add(DRAGON_SNEEZE, definition()
                .subtitle(TITLE_PREFIX + "dragon.sneeze")
                .with(sound(prefix("mob/dragon/sneeze"))));
        this.add(DRAGON_STEP, definition()
                .subtitle(TITLE_PREFIX + "dragon.step")
                .with(
                        sound(prefix("mob/dragon/step1")),
                        sound(prefix("mob/dragon/step2")),
                        sound(prefix("mob/dragon/step3")),
                        sound(prefix("mob/dragon/step4"))
                ));
        this.add(DRAGON_DEATH, definition()
                .subtitle(TITLE_PREFIX + "dragon.death")
                .with(sound(prefix("mob/dragon/death"))));
        this.add(ZOMBIE_DRAGON_DEATH, definition()
                .subtitle(TITLE_PREFIX + "dragon.death.zombie")
                .with(sound(prefix("mob/dragon/zombiedeath"))));
        this.add(DRAGON_HATCHED, definition()
                .subtitle(TITLE_PREFIX + "dragon_egg.hatched")
                .with(sound(prefix("mob/dragon/hatched"))));
        this.add(DRAGON_HATCHING, definition()
                .subtitle(TITLE_PREFIX + "dragon_egg.hatching")
                .with(
                        sound(prefix("mob/dragon/hatching1")),
                        sound(prefix("mob/dragon/hatching2"))
                ));
        this.add(DRAGON_WHISTLE, definition()
                .subtitle(TITLE_PREFIX + "dragon_whistle")
                .with(
                        sound(prefix("item/whistle")),
                        sound(prefix("item/whistle1"))
                ));
    }
}
