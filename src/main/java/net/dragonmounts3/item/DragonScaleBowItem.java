package net.dragonmounts3.item;

import net.dragonmounts3.objects.DragonType;
import net.dragonmounts3.objects.IDragonTypified;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static net.dragonmounts3.DragonMounts.getItemTranslationKey;

public class DragonScaleBowItem extends BowItem implements IDragonTypified {
    private static final String TRANSLATION_KEY = getItemTranslationKey("dragon_bow");

    protected DragonType type;

    protected final DragonScaleTier tier;

    public DragonScaleBowItem(
            DragonScaleTier tier,
            Item.Properties properties
    ) {
        super(properties.defaultDurability((int) (tier.getUses() * 0.25)));
        this.tier = tier;
        this.type = tier.getDragonType();
    }

    public DragonScaleTier getTier() {
        return this.tier;
    }

    @Override
    public int getEnchantmentValue() {
        return this.tier.getEnchantmentValue();
    }

    @Override
    public boolean isValidRepairItem(@Nonnull ItemStack toRepair, @Nonnull ItemStack repair) {
        return this.tier.getRepairIngredient().test(repair);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable World world, List<ITextComponent> components, @Nonnull ITooltipFlag flag) {
        components.add(this.type.getText());
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
