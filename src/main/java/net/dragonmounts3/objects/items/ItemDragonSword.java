package net.dragonmounts3.objects.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class ItemDragonSword extends SwordItem {

    public EnumDragonTypes type;

    public ItemDragonSword(
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
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> components, ITooltipFlag flag) {
        components.add(new TranslationTextComponent("dragon." + type.toString().toLowerCase()).withStyle(this.type.color));
    }
}