package net.dragonmounts3.block;

import net.dragonmounts3.DragonMountsConfig;
import net.dragonmounts3.api.DragonType;
import net.dragonmounts3.api.IDragonTypified;
import net.dragonmounts3.entity.dragon.HatchableDragonEggEntity;
import net.dragonmounts3.util.EntityUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DragonEggBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static net.dragonmounts3.DragonMounts.BLOCK_TRANSLATION_KEY_PREFIX;

public class HatchableDragonEggBlock extends DragonEggBlock implements IDragonTypified {
    protected static void spawn(World level, BlockPos pos, DragonType type) {
        level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        HatchableDragonEggEntity entity = new HatchableDragonEggEntity(level);
        entity.setDragonType(type, true, true);
        EntityUtil.setPos(entity, pos);
        level.addFreshEntity(entity);
    }

    public static void interact(PlayerInteractEvent.RightClickBlock event) {
        World level = event.getWorld();
        if (DragonMountsConfig.SERVER.block_override.get() && !level.isClientSide) {
            BlockPos pos = event.getPos();
            Block block = level.getBlockState(pos).getBlock();
            if (block == Blocks.DRAGON_EGG && !level.dimension().equals(World.END)) {
                event.setUseBlock(Event.Result.DENY);
                spawn(level, pos, DragonType.ENDER);
            }
        }
    }

    private static final String TRANSLATION_KEY = BLOCK_TRANSLATION_KEY_PREFIX + "dragon_egg";
    protected DragonType type;

    public HatchableDragonEggBlock(DragonType type, Properties properties) {
        super(properties);
        this.type = type;
    }

    @Override
    public void attack(@Nonnull BlockState state, World level, @Nonnull BlockPos pos, @Nonnull PlayerEntity player) {
        if (level.dimension().equals(World.END)) {
            super.attack(state, level, pos, player);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public float getDestroyProgress(@Nonnull BlockState state, @Nonnull PlayerEntity player, @Nonnull IBlockReader level, @Nonnull BlockPos pos) {
        if (player.level.dimension().equals(World.END)) return 0.0F;
        return super.getDestroyProgress(state, player, level, pos);
    }

    @Nonnull
    @Override
    public ActionResultType use(@Nonnull BlockState state, World level, @Nonnull BlockPos pos, @Nonnull PlayerEntity player, @Nonnull Hand hand, @Nonnull BlockRayTraceResult hit) {
        if (level.isClientSide) {
            return ActionResultType.SUCCESS;
        } else if (level.dimension().equals(World.END)) {
            return super.use(state, level, pos, player, hand, hit);
        }
        spawn(level, pos, this.type);
        return ActionResultType.CONSUME;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable IBlockReader world, List<ITextComponent> components, @Nonnull ITooltipFlag flag) {
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
