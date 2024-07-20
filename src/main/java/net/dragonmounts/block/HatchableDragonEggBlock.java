package net.dragonmounts.block;

import net.dragonmounts.api.IDragonTypified;
import net.dragonmounts.entity.dragon.HatchableDragonEggEntity;
import net.dragonmounts.init.DMGameRules;
import net.dragonmounts.init.DragonTypes;
import net.dragonmounts.registry.DragonType;
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

import static net.dragonmounts.DragonMounts.BLOCK_TRANSLATION_KEY_PREFIX;

public class HatchableDragonEggBlock extends DragonEggBlock implements IDragonTypified {
    //public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 10);
    protected static ActionResultType spawn(World level, BlockPos pos, DragonType type) {
        level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        HatchableDragonEggEntity entity = new HatchableDragonEggEntity(level);
        entity.setDragonType(type, true);
        entity.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
        level.addFreshEntity(entity);
        return ActionResultType.CONSUME;
    }

    public static void interact(PlayerInteractEvent.RightClickBlock event) {
        World level = event.getWorld();
        if (!level.isClientSide && !level.dimension().equals(World.END) && level.getGameRules().getBoolean(DMGameRules.IS_EGG_OVERRIDDEN)) {
            BlockPos pos = event.getPos();
            if (level.getBlockState(pos).getBlock() == Blocks.DRAGON_EGG) {
                event.setUseBlock(Event.Result.DENY);
                spawn(level, pos, DragonTypes.ENDER);
            }
        }
    }

    private static final String TRANSLATION_KEY = BLOCK_TRANSLATION_KEY_PREFIX + "dragon_egg";
    public final DragonType type;

    public HatchableDragonEggBlock(DragonType type, Properties props) {
        super(props);
        this.type = type;
        //this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
    }

    @Override
    public void attack(BlockState state, World level, BlockPos pos, PlayerEntity player) {
        if (level.dimension().equals(World.END)) super.attack(state, level, pos, player);
    }

    @Override
    @SuppressWarnings("deprecation")
    public float getDestroyProgress(BlockState state, PlayerEntity player, IBlockReader level, BlockPos pos) {
        if (player.level.dimension().equals(World.END)) return 0.0F;
        return super.getDestroyProgress(state, player, level, pos);
    }

    @Nonnull
    @Override
    public ActionResultType use(BlockState state, World level, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (level.isClientSide) return ActionResultType.SUCCESS;
        else if (level.dimension().equals(World.END)) return super.use(state, level, pos, player, hand, hit);
        return spawn(level, pos, this.type);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable IBlockReader world, List<ITextComponent> tooltips, ITooltipFlag flag) {
        tooltips.add(this.type.getName());
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
