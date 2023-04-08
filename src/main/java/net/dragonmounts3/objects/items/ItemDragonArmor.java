package net.dragonmounts3.objects.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static net.minecraft.entity.ai.attributes.Attributes.ARMOR;

/**
 * @see net.minecraft.item.HorseArmorItem
 */
public class ItemDragonArmor extends Item {
    private final int protection;

    public ItemDragonArmor(int protection, Properties properties) {
        super(properties);
        this.protection = protection;
    }

    public int getProtection() {
        return this.protection;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable World world, @Nonnull List<ITextComponent> components, @Nonnull ITooltipFlag flag) {
        components.add(StringTextComponent.EMPTY);
        components.add((new TranslationTextComponent("item.modifiers.equipped").withStyle(TextFormatting.GRAY)));
        components.add((new TranslationTextComponent("attribute.modifier.plus.0", this.protection, new TranslationTextComponent(ARMOR.getDescriptionId()))).withStyle(TextFormatting.BLUE));
    }
}
