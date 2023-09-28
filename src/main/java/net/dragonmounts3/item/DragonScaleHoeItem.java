package net.dragonmounts3.item;

import net.dragonmounts3.api.DragonScaleTier;
import net.dragonmounts3.api.IDragonTypified;
import net.dragonmounts3.registry.DragonType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static net.dragonmounts3.DragonMounts.ITEM_TRANSLATION_KEY_PREFIX;

public class DragonScaleHoeItem extends HoeItem implements IDragonTypified {
    private static final String TRANSLATION_KEY = ITEM_TRANSLATION_KEY_PREFIX + "dragon_scale_hoe";

    protected DragonType type;

    public DragonScaleHoeItem(
            DragonScaleTier tier,
            int attackDamageModifier,
            float attackSpeedModifier,
            Properties properties
    ) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
        this.type = tier.getDragonType();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable World world, List<ITextComponent> components, @Nonnull ITooltipFlag flag) {
        components.add(this.type.getName());
    }

    @Nonnull
    @Override
    public String getDescriptionId() {
        return TRANSLATION_KEY;
    }

    @Override
    public DragonType getDragonType() {
        return this.type;
    }
}