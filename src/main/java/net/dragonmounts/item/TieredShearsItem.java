package net.dragonmounts.item;

import net.dragonmounts.entity.dragon.TameableDragonEntity;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.item.ShearsItem;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * @see net.minecraft.item.TieredItem
 */
public class TieredShearsItem extends ShearsItem {
    protected final IItemTier tier;
    private final float speedFactor;

    public TieredShearsItem(IItemTier tier, Properties properties) {
        super(properties.defaultDurability((int) (tier.getUses() * 0.952F)));
        this.tier = tier;
        this.speedFactor = tier.getSpeed() / ItemTier.IRON.getSpeed();
    }

    public IItemTier getTier() {
        return this.tier;
    }

    @Nonnull
    @Override
    public ActionResultType interactLivingEntity(@Nonnull ItemStack stack, @Nonnull PlayerEntity player, @Nonnull LivingEntity entity, @Nonnull Hand hand) {
        if (player.level.isClientSide) return super.interactLivingEntity(stack, player, entity, hand);
        if (entity instanceof TameableDragonEntity) {
            TameableDragonEntity dragon = (TameableDragonEntity) entity;
            BlockPos pos = dragon.blockPosition();
            if (dragon.isShearable(stack, dragon.level, pos)) {
                if (dragon.isOwnedBy(player)) {
                    Random random = dragon.getRandom();
                    boolean flag = false;
                    for (ItemStack drop : dragon.onSheared(player, stack, dragon.level, pos, EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, stack))) {
                        ItemEntity item = entity.spawnAtLocation(drop, 1.0F);
                        if (item != null) {
                            flag = true;
                            item.setDeltaMovement(item.getDeltaMovement().add((random.nextFloat() - random.nextFloat()) * 0.1D, random.nextFloat() * 0.05D, (random.nextFloat() - random.nextFloat()) * 0.1D));
                        }
                    }
                    if (flag) {
                        stack.hurtAndBreak(20, dragon, e -> e.broadcastBreakEvent(hand));
                        return ActionResultType.SUCCESS;
                    }
                } else
                    player.displayClientMessage(new TranslationTextComponent("message.dragonmounts.not_owner"), true);
                return ActionResultType.FAIL;
            }
            return ActionResultType.PASS;
        }
        return super.interactLivingEntity(stack, player, entity, hand);
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
        return speed > 1.0F ? speed * this.speedFactor : speed;
    }
}
