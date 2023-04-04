package net.dragonmounts3.objects.items;

import net.minecraft.block.BlockState;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.item.ShearsItem;

import javax.annotation.Nonnull;

/**
 * @see net.minecraft.item.TieredItem
 */
public class ItemTieredShears extends ShearsItem {
    private final IItemTier tier;
    private final float speedFactor;

    public ItemTieredShears(IItemTier tier, Properties properties) {
        super(properties.defaultDurability(tier.getUses() / 10 + 213));
        this.tier = tier;
        this.speedFactor = tier.getSpeed() / ItemTier.IRON.getSpeed();
    }

    public IItemTier getTier() {
        return this.tier;
    }

    @Override
    public int getEnchantmentValue() {
        return this.tier.getEnchantmentValue();
    }

    @Override
    public boolean isValidRepairItem(@Nonnull ItemStack toRepair, @Nonnull ItemStack repair) {
        return this.tier.getRepairIngredient().test(repair) || super.isValidRepairItem(toRepair, repair);
    }

    @Override
    public float getDestroySpeed(@Nonnull ItemStack stack, @Nonnull BlockState state) {
        float speed = super.getDestroySpeed(stack, state);
        if (speed > 1.0F) {
            return speed * speedFactor;
        }
        return speed;
    }
}
