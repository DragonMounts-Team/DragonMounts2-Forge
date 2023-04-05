package net.dragonmounts3.inits;

import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import javax.annotation.Nonnull;

import static net.dragonmounts3.DragonMounts.MOD_ID;

public class ModItemGroups {
    public static final ItemGroup BLOCK_TAB = new ItemGroup(MOD_ID + ".blocks") {
        @Nonnull
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.BARRIER);
        }
    };

    public static final ItemGroup ITEM_TAB = new ItemGroup(MOD_ID + ".items") {
        @Nonnull
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.BARRIER);
        }
    };

    public static final ItemGroup EQUIPMENT_TAB = new ItemGroup(MOD_ID + ".equipments") {
        @Nonnull
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.BARRIER);
        }
    };

    public static Properties none() {
        return new Properties().tab(ITEM_TAB);
    }

    public static Properties block() {
        return new Properties().tab(BLOCK_TAB);
    }

    public static Properties item() {
        return new Properties().tab(ITEM_TAB);
    }

    public static Properties equipment() {
        return new Properties().tab(EQUIPMENT_TAB);
    }
}
