package net.dragonmounts3.inits;

import net.dragonmounts3.DragonMounts;
import net.dragonmounts3.entity.carriage.CarriageEntity;
import net.dragonmounts3.entity.dragon.DragonEggEntity;
import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DragonMounts.create(ForgeRegistries.ENTITIES);
    public static final RegistryObject<EntityType<CarriageEntity>> ENTITY_CARRIAGE = register("carriage", CarriageEntity::new, EntityClassification.MISC, 0.8F, 0.8F);
    public static final RegistryObject<EntityType<DragonEggEntity>> ENTITY_DRAGON_EGG = register("dragon_egg", DragonEggEntity::new, EntityClassification.MISC, 0.8F, 0.8F);
    public static final RegistryObject<EntityType<TameableDragonEntity>> ENTITY_TAMEABLE_DRAGON = register("dragon", TameableDragonEntity::new, EntityClassification.MISC, 0.8F, 0.8F);

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String name, EntityType.IFactory<T> factory, EntityClassification category, float width, float height) {
        return ENTITY_TYPES.register(name, () -> EntityType.Builder.of(factory, category).sized(width, height).build(name));
    }

}