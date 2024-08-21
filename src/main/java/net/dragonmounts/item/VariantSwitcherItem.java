package net.dragonmounts.item;

import net.dragonmounts.block.AbstractDragonHeadBlock;
import net.dragonmounts.entity.dragon.TameableDragonEntity;
import net.dragonmounts.init.DMSounds;
import net.dragonmounts.init.DragonVariants;
import net.dragonmounts.registry.DragonVariant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;
import static net.minecraft.state.properties.BlockStateProperties.ROTATION_16;

public class VariantSwitcherItem extends Item {
    private static DragonVariant draw(DragonVariant variant) {
        return variant.type.variants.draw(random, variant);
    }

    public VariantSwitcherItem(Properties props) {
        super(props);
    }

    @Nonnull
    @Override
    public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity player, LivingEntity entity, Hand hand) {
        if (entity instanceof TameableDragonEntity) {
            World level = entity.level;
            if (level.isClientSide) return ActionResultType.SUCCESS;
            TameableDragonEntity dragon = (TameableDragonEntity) entity;
            if (dragon.isOwnedBy(player)) {
                dragon.setVariant(draw(dragon.getVariant()));
                level.playSound(player, dragon, DMSounds.VARIANT_SWITCHER.get(), SoundCategory.PLAYERS, 1.0F, 1.0F);
                if (!player.abilities.instabuild) {
                    stack.shrink(1);
                }
                player.awardStat(Stats.ITEM_USED.get(this));
                return ActionResultType.CONSUME;
            } else {
                player.displayClientMessage(new TranslationTextComponent("message.dragonmounts.not_owner"), true);
                return ActionResultType.FAIL;
            }
        }
        return ActionResultType.PASS;
    }

    @Nonnull
    @Override
    public ActionResultType useOn(ItemUseContext context) {
        World level = context.getLevel();
        PlayerEntity player = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        BlockState old = level.getBlockState(pos);
        Block block = old.getBlock();
        BlockState neo;
        if (block == Blocks.DRAGON_HEAD) {
            if (level.isClientSide) return ActionResultType.SUCCESS;
            neo = draw(DragonVariants.ENDER_FEMALE).headBlock.defaultBlockState().setValue(ROTATION_16, old.getValue(ROTATION_16));
        } else if (block == Blocks.DRAGON_WALL_HEAD) {
            if (level.isClientSide) return ActionResultType.SUCCESS;
            neo = draw(DragonVariants.ENDER_FEMALE).headWallBlock.defaultBlockState().setValue(HORIZONTAL_FACING, old.getValue(HORIZONTAL_FACING));
        } else if (block instanceof AbstractDragonHeadBlock) {
            if (level.isClientSide) return ActionResultType.SUCCESS;
            AbstractDragonHeadBlock head = (AbstractDragonHeadBlock) block;
            neo = head.isOnWall
                    ? draw(head.variant).headWallBlock.defaultBlockState().setValue(HORIZONTAL_FACING, old.getValue(HORIZONTAL_FACING))
                    : draw(head.variant).headBlock.defaultBlockState().setValue(ROTATION_16, old.getValue(ROTATION_16));
        } else return ActionResultType.PASS;
        level.setBlock(pos, neo, 0b1011);
        if (player != null) {
            if (!player.abilities.instabuild) {
                context.getItemInHand().shrink(1);
            }
            player.awardStat(Stats.ITEM_USED.get(this));
        }
        return ActionResultType.CONSUME;
    }
}
