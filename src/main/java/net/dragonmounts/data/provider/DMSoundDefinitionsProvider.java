package net.dragonmounts.data.provider;

import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

import static net.dragonmounts.DragonMounts.MOD_ID;
import static net.dragonmounts.DragonMounts.makeId;
import static net.dragonmounts.init.DMSounds.*;

public class DMSoundDefinitionsProvider extends SoundDefinitionsProvider {
    public static final String TITLE_PREFIX = "subtitles." + MOD_ID + '.';

    public DMSoundDefinitionsProvider(DataGenerator generator, ExistingFileHelper helper) {
        super(generator, MOD_ID, helper);
    }

    @Override
    public void registerSounds() {
        this.add(DRAGON_GROWL, definition()
                .subtitle(TITLE_PREFIX + "dragon.growl")
                .with(sound(makeId("mob/dragon/growl")),
                        sound(makeId("mob/dragon/growl1"))
                ));
        this.add(HATCHLING_DRAGON_ROAR, definition()
                .subtitle(TITLE_PREFIX + "dragon.hatchling_roar")
                .with(sound(makeId("mob/dragon/hatchlingroar1")),
                        sound(makeId("mob/dragon/hatchlingroar2"))
                ));
        this.add(DRAGON_HATCHLING_GROWL, definition()
                .subtitle(TITLE_PREFIX + "dragon.hatchling_growl")
                .with(sound(makeId("mob/dragon/hatchlinggrowl1")),
                        sound(makeId("mob/dragon/hatchlinggrowl2"))
                ));
        this.add(DRAGON_ROAR, definition()
                .subtitle(TITLE_PREFIX + "dragon.roar")
                .with(
                        sound(makeId("mob/dragon/roar")),
                        sound(makeId("mob/dragon/roar1")),
                        sound(makeId("mob/dragon/roar2")),
                        sound(makeId("mob/dragon/roar3"))
                ));
        this.add(NETHER_DRAGON_GROWL, definition()
                .subtitle(TITLE_PREFIX + "dragon.growl.nether")
                .with(
                        sound(makeId("mob/dragon/nethergrowl")),
                        sound(makeId("mob/dragon/nethergrowl1")),
                        sound(makeId("mob/dragon/nethergrowl2")),
                        sound(makeId("mob/dragon/nethergrowl3"))
                ));
        this.add(ZOMBIE_DRAGON_GROWL, definition()
                .subtitle(TITLE_PREFIX + "dragon.growl.zombie")
                .with(
                        sound(makeId("mob/dragon/zombiegrowl")),
                        sound(makeId("mob/dragon/zombiegrowl1")),
                        sound(makeId("mob/dragon/zombiegrowl2"))
                ));
        this.add(SKELETON_DRAGON_GROWL, definition()
                .subtitle(TITLE_PREFIX + "dragon.growl.skeleton")
                .with(sound(makeId("mob/dragon/skeletongrowl1"))));
        this.add(DRAGON_BREATHE, definition()
                .subtitle(TITLE_PREFIX + "dragon.breathe")
                .with(
                        sound(new ResourceLocation("minecraft", "mob/cow/say2")).volume(0.5F).pitch(0.5F),
                        sound(new ResourceLocation("minecraft", "mob/cow/say4")).volume(0.5F).pitch(0.5F)
                ));
        this.add(DRAGON_SNEEZE, definition()
                .subtitle(TITLE_PREFIX + "dragon.sneeze")
                .with(sound(makeId("mob/dragon/sneeze"))));
        this.add(DRAGON_STEP, definition()
                .subtitle(TITLE_PREFIX + "dragon.step")
                .with(
                        sound(makeId("mob/dragon/step1")),
                        sound(makeId("mob/dragon/step2")),
                        sound(makeId("mob/dragon/step3")),
                        sound(makeId("mob/dragon/step4"))
                ));
        this.add(DRAGON_DEATH, definition()
                .subtitle(TITLE_PREFIX + "dragon.death")
                .with(sound(makeId("mob/dragon/death"))));
        this.add(ZOMBIE_DRAGON_DEATH, definition()
                .subtitle(TITLE_PREFIX + "dragon.death.zombie")
                .with(sound(makeId("mob/dragon/zombiedeath"))));
        this.add(DRAGON_HATCHED, definition()
                .subtitle(TITLE_PREFIX + "dragon_egg.hatched")
                .with(sound(makeId("mob/dragon/hatched"))));
        this.add(DRAGON_HATCHING, definition()
                .subtitle(TITLE_PREFIX + "dragon_egg.hatching")
                .with(
                        sound(makeId("mob/dragon/hatching1")),
                        sound(makeId("mob/dragon/hatching2"))
                ));
        this.add(DRAGON_WHISTLE, definition()
                .subtitle(TITLE_PREFIX + "dragon_whistle")
                .with(
                        sound(makeId("item/whistle")),
                        sound(makeId("item/whistle1"))
                ));
        this.add(VARIANT_SWITCHER, definition()
                .subtitle(TITLE_PREFIX + "variant_switcher")
                .with(sound(makeId("item/variant_switcher"))));
    }
}
