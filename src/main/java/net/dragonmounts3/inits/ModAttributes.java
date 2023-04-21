package net.dragonmounts3.inits;

import net.dragonmounts3.DragonMounts;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DragonMounts.create(ForgeRegistries.ATTRIBUTES);
    public static final RegistryObject<Attribute> FLIGHT_SPEED = ATTRIBUTES.register("dragon.flight_speed", () -> new RangedAttribute("attribute.name.dragon.flight_speed", 0.9, 0.0, Double.MAX_VALUE).setSyncable(true));

}
