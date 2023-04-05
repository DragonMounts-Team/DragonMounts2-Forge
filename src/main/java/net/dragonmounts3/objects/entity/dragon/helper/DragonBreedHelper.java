package net.dragonmounts3.objects.entity.dragon.helper;

import net.dragonmounts3.objects.entity.dragon.EntityTameableDragon;
import net.minecraft.network.datasync.DataParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DragonBreedHelper extends DragonHelper {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final int BLOCK_RANGE = 2;
    private static final int POINTS_BLOCK = 1;
    private static final int POINTS_BIOMES = 1;
    private static final int POINTS_INITIAL = 1000;
    private static final int POINTS_INHERIT = 1800;
    private static final int POINTS_ENV = 3;
    private static final int TICK_RATE_PARTICLES = 2;
    private static final int TICK_RATE_BLOCK = 20;
    private static final String NBT_BREED = "Breed";
    private static final String NBT_BREED_POINTS = "breedPoints";
    private final DataParameter<String> DATA_BREED;

    public DragonBreedHelper(EntityTameableDragon dragon, DataParameter<String> DATA_BREED) {
        super(dragon);
        this.DATA_BREED = DATA_BREED;
        if (!dragon.level.isClientSide) {

        }
    }

}