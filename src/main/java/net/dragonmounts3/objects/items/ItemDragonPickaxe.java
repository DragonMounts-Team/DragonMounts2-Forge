package net.dragonmounts3.objects.items;

import net.dragonmounts3.objects.EnumDragonType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static net.dragonmounts3.DragonMounts.MOD_ID;

public class ItemDragonPickaxe extends PickaxeItem {
    private static final String TRANSLATION_KEY = "item." + MOD_ID + ".dragon_pickaxe";

    public EnumDragonType type;

    public ItemDragonPickaxe(
            EnumDragonType type,
            int attackDamageModifier,
            float attackSpeedModifier,
            Properties properties
    ) {
        super(type.tier, attackDamageModifier, attackSpeedModifier, properties);
        this.type = type;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable World world, List<ITextComponent> components, @Nullable ITooltipFlag flag) {
        components.add(type.getText());
    }

    @Nonnull
    @Override
    public ITextComponent getName(@Nonnull ItemStack pStack) {
        return new TranslationTextComponent(TRANSLATION_KEY);
    }
}