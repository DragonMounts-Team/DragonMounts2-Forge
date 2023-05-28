package net.dragonmounts3.init;

import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

import static net.dragonmounts3.DragonMounts.MOD_ID;

public class DMItemGroups {
    public static final ItemGroup BLOCK_TAB = new ItemGroup(MOD_ID + ".blocks") {
        @Nonnull
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(DMBlocks.ENDER_DRAGON_EGG.get());
        }
    };

    public static final ItemGroup ITEM_TAB = new ItemGroup(MOD_ID + ".items") {
        @Nonnull
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(DMItems.ENDER_DRAGON_SCALES.get());
        }
    };

    public static final ItemGroup TOOL_TAB = new ItemGroup(MOD_ID + ".tools") {
        @Nonnull
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(DMItems.ENDER_DRAGON_SCALE_SWORD.get());
        }
    };

    static {
        BLOCK_TAB.setRecipeFolderName(MOD_ID + "/blocks");
        ITEM_TAB.setRecipeFolderName(MOD_ID + "/items");
        TOOL_TAB.setRecipeFolderName(MOD_ID + "/tools");
    }

    public static Properties none() {
        return new Properties();
    }

    public static Properties block() {
        return new Properties().tab(BLOCK_TAB);
    }

    public static Properties item() {
        return new Properties().tab(ITEM_TAB);
    }

    public static Properties tool() {
        return new Properties().tab(TOOL_TAB);
    }
}
