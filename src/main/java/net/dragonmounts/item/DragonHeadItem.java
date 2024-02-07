package net.dragonmounts.item;

import net.dragonmounts.api.IDragonTypified;
import net.dragonmounts.block.AbstractDragonHeadBlock;
import net.dragonmounts.registry.DragonType;
import net.dragonmounts.registry.DragonVariant;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.IArmorVanishable;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.WallOrFloorItem;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class DragonHeadItem extends WallOrFloorItem implements IArmorVanishable, IDragonTypified {
    public final DragonVariant variant;

    public DragonHeadItem(DragonVariant variant, Block head, Block headWall, Properties properties) {
        super(head, headWall, properties);
        this.variant = variant;
    }

    @Nullable
    @Override
    public EquipmentSlotType getEquipmentSlot(ItemStack stack) {
        return EquipmentSlotType.HEAD;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltips, ITooltipFlag flag) {
        tooltips.add(this.variant.type.getName());
    }

    @Nonnull
    @Override
    public String getDescriptionId() {
        return AbstractDragonHeadBlock.TRANSLATION_KEY;
    }

    @Override
    public DragonType getDragonType() {
        return this.variant.type;
    }
}
