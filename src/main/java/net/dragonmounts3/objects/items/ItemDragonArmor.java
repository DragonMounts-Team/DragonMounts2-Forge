package net.dragonmounts3.objects.items;

import net.dragonmounts3.objects.EnumDragonType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static net.dragonmounts3.DragonMounts.MOD_ID;

public class ItemDragonArmor extends ArmorItem {
    private static String[] TRANSLATION_KEY_SUFFIX = new String[]{"_boots", "_leggings", "_chestplate", "_helmet"};
    private final String translationKey;

    public EnumDragonType type;

    public ItemDragonArmor(
            EnumDragonType type,
            EquipmentSlotType slot,
            Properties properties
    ) {
        super(type.material, slot, properties);
        this.type = type;
        this.translationKey = "item." + MOD_ID + ".dragonscale" + TRANSLATION_KEY_SUFFIX[slot.getIndex()];
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable World world, List<ITextComponent> components, @Nullable ITooltipFlag flag) {
        components.add(type.getText());
    }

    @Nonnull
    @Override
    public ITextComponent getName(@Nonnull ItemStack pStack) {
        return new TranslationTextComponent(translationKey);
    }
}
