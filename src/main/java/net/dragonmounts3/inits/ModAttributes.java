package net.dragonmounts3.inits;

import net.dragonmounts3.DragonMounts;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DragonMounts.create(ForgeRegistries.ATTRIBUTES);
    public static final RegistryObject<Attribute> MOVEMENT_SPEED_AIR = ATTRIBUTES.register("dragon.movement_speed_air", () -> new RangedAttribute("attribute.name.dragon.movement_speed_air", 0.9, 0.0, Double.MAX_VALUE).setSyncable(true));

}
