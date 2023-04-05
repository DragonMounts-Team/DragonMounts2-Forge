package net.dragonmounts3.objects.blocks;

import net.dragonmounts3.objects.DragonType;
import net.dragonmounts3.objects.IDragonTypified;
import net.minecraft.block.DragonEggBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.List;

import static net.dragonmounts3.DragonMounts.getBlockTranslationKey;

public class BlockDragonEgg extends DragonEggBlock implements IDragonTypified {
    private static final String TRANSLATION_KEY = getBlockTranslationKey("dragon_egg");
    protected DragonType type;

    public BlockDragonEgg(DragonType type) {
        super(Properties.of(Material.EGG, MaterialColor.COLOR_BLACK).strength(3.0F, 9.0F));
        this.type = type;
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
