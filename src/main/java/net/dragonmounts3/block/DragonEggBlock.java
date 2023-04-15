package net.dragonmounts3.block;

import net.dragonmounts3.entity.dragon.DragonEggEntity;
import net.dragonmounts3.objects.DragonType;
import net.dragonmounts3.objects.IDragonTypified;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.List;

import static net.dragonmounts3.DragonMounts.getBlockTranslationKey;

public class DragonEggBlock extends net.minecraft.block.DragonEggBlock implements IDragonTypified {
    private static final String TRANSLATION_KEY = getBlockTranslationKey("dragon_egg");
    protected DragonType type;

    public DragonEggBlock(DragonType type) {
        super(Properties.of(Material.EGG, MaterialColor.COLOR_BLACK).strength(3.0F, 9.0F));
        this.type = type;
    }

    @Nonnull
    @Override
    public ActionResultType use(@Nonnull BlockState state, World level, @Nonnull BlockPos pos, @Nonnull PlayerEntity player, @Nonnull Hand hand, @Nonnull BlockRayTraceResult hit) {
        if (level.isClientSide) {
            return ActionResultType.SUCCESS;
        } else {
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            DragonEggEntity entity = new DragonEggEntity(level);
            entity.setDragonType(this.getDragonType(), true);
            entity.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            level.addFreshEntity(entity);
            return ActionResultType.CONSUME;
        }
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
