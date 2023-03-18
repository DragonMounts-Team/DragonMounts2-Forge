package net.dragonmounts3.objects.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

import static net.dragonmounts3.DragonMounts.MOD_ID;

public class ItemDragonHoe extends HoeItem {

    private static final String TRANSLATION_KEY = "item." + MOD_ID + ".dragon_hoe";

    public EnumDragonTypes type;

    public ItemDragonHoe(
            EnumDragonTypes type,
            int attackDamageModifier,
            float attackSpeedModifier,
            Properties properties
    ) {
        super(type, attackDamageModifier, attackSpeedModifier, properties);
        this.type = type;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@Nullable ItemStack stack, @Nullable World world, List<ITextComponent> components, @Nullable ITooltipFlag flag) {
        components.add(type.getName());
    }

    @Override
    public ITextComponent getName(@Nullable ItemStack pStack) {
        return new TranslationTextComponent(TRANSLATION_KEY);
    }
}