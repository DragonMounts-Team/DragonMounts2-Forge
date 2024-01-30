package net.dragonmounts3.registry;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.*;

import javax.annotation.Nullable;

import static net.dragonmounts3.DragonMounts.MOD_ID;

public class CooldownCategory extends ForgeRegistryEntry<CooldownCategory> {
    public static final ResourceLocation DEFAULT_KEY = new ResourceLocation(MOD_ID, "ender");
    public static final Registry REGISTRY = new Registry(MOD_ID, "cooldown_category", new RegistryBuilder<CooldownCategory>().setDefaultKey(DEFAULT_KEY));

    private int id = -1;

    public final int getId() {
        return id;
    }

    public final ResourceLocation getSerializedName() {
        ResourceLocation key = this.getRegistryName();
        return key == null ? DEFAULT_KEY : key;
    }

    public static class Registry extends DeferredRegistry<CooldownCategory> implements IForgeRegistry.AddCallback<CooldownCategory>, IForgeRegistry.ClearCallback<CooldownCategory> {
        public Registry(String namespace, String name, RegistryBuilder<CooldownCategory> builder) {
            super(namespace, name, CooldownCategory.class, builder);
        }

        @Override
        public void onAdd(IForgeRegistryInternal<CooldownCategory> owner, RegistryManager stage, int id, CooldownCategory obj, @Nullable CooldownCategory oldObj) {
            if (owner == this.registry) {//public -> protected
                if (oldObj != null) {
                    oldObj.id = -1;
                }
                obj.id = id;
            }
        }

        @Override
        public void onClear(IForgeRegistryInternal<CooldownCategory> owner, RegistryManager stage) {
            if (owner == this.registry) {//public -> protected
                for (CooldownCategory category : owner) {
                    category.id = -1;
                }
            }
        }
    }
}
