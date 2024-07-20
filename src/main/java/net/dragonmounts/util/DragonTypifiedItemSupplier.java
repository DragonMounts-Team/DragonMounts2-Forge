package net.dragonmounts.util;

import com.google.gson.JsonObject;
import net.dragonmounts.api.IDragonTypified;
import net.dragonmounts.registry.DragonType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Supplier;

public class DragonTypifiedItemSupplier<T extends Item> implements Supplier<T>, Ingredient.IItemList, IDragonTypified {
    public final DragonType type;
    protected final Class<T> clazz;

    public DragonTypifiedItemSupplier(DragonType type, Class<T> clazz) {
        this.type = type;
        this.clazz = clazz;
    }

    @Override
    public T get() {
        return this.type.getInstance(this.clazz, null);
    }

    @Nonnull
    public Collection<ItemStack> getItems() {
        return Collections.singleton(new ItemStack(this.get()));
    }

    @Nonnull
    public JsonObject serialize() {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(this.get());
        JsonObject obj = new JsonObject();
        obj.addProperty("item", id == null ? "minecraft:air" : id.toString());
        return obj;
    }

    @Override
    public final DragonType getDragonType() {
        return this.type;
    }
}
