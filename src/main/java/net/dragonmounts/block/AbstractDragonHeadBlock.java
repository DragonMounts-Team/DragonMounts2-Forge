package net.dragonmounts.block;

import net.dragonmounts.api.IDragonTypified;
import net.dragonmounts.block.entity.DragonHeadBlockEntity;
import net.dragonmounts.registry.DragonType;
import net.dragonmounts.registry.DragonVariant;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.IArmorVanishable;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static net.dragonmounts.DragonMounts.BLOCK_TRANSLATION_KEY_PREFIX;

public abstract class AbstractDragonHeadBlock extends ContainerBlock implements IArmorVanishable, IDragonTypified {
    public static final String TRANSLATION_KEY = BLOCK_TRANSLATION_KEY_PREFIX + "dragon_head";
    public final DragonVariant variant;
    public final boolean isOnWall;

    public AbstractDragonHeadBlock(DragonVariant variant, Properties props, boolean isOnWall) {
        super(props);
        this.variant = variant;
        this.isOnWall = isOnWall;
    }

    public abstract float getYRotation(BlockState state);

    @SuppressWarnings("deprecation")
    public boolean isPathfindable(BlockState state, IBlockReader level, BlockPos pos, PathType type) {
        return false;
    }

    @Override
    public final boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    public final DragonHeadBlockEntity createTileEntity(BlockState state, IBlockReader world) {
        return this.newBlockEntity(world);
    }

    @Nullable
    @Override
    public DragonHeadBlockEntity newBlockEntity(IBlockReader level) {
        return new DragonHeadBlockEntity();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable IBlockReader world, List<ITextComponent> tooltips, ITooltipFlag flag) {
        tooltips.add(this.variant.type.getName());
    }

    @Nonnull
    @Override
    public String getDescriptionId() {
        return TRANSLATION_KEY;
    }

    @Override
    public DragonType getDragonType() {
        return this.variant.type;
    }
}
