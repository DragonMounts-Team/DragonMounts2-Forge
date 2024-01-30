package net.dragonmounts.item;

import net.dragonmounts.api.DragonScaleMaterial;
import net.dragonmounts.api.IArmorEffectSource;
import net.dragonmounts.api.IDragonScaleArmorEffect;
import net.dragonmounts.api.IDragonTypified;
import net.dragonmounts.capability.IArmorEffectManager;
import net.dragonmounts.registry.DragonType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static net.dragonmounts.DragonMounts.ITEM_TRANSLATION_KEY_PREFIX;

public class DragonScaleArmorItem extends ArmorItem implements IDragonTypified, IArmorEffectSource {
    private static final String[] TRANSLATION_KEYS = new String[]{
            ITEM_TRANSLATION_KEY_PREFIX + "dragon_scale_boots",
            ITEM_TRANSLATION_KEY_PREFIX + "dragon_scale_leggings",
            ITEM_TRANSLATION_KEY_PREFIX + "dragon_scale_chestplate",
            ITEM_TRANSLATION_KEY_PREFIX + "dragon_scale_helmet"
    };

    protected DragonType type;
    public final IDragonScaleArmorEffect effect;

    public DragonScaleArmorItem(DragonScaleMaterial material, EquipmentSlotType slot, IDragonScaleArmorEffect effect, Properties properties) {
        super(material, slot, properties);
        this.type = material.getDragonType();
        this.effect = effect;
    }

    @Override
    public void affect(IArmorEffectManager manager, PlayerEntity player, ItemStack stack) {
        if (this.effect != null) manager.stackLevel(this.effect);
    }

    @Nonnull
    @Override
    public String getDescriptionId() {
        return TRANSLATION_KEYS[this.slot.getIndex()];
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable World world, List<ITextComponent> components, @Nonnull ITooltipFlag flag) {
        components.add(this.type.getName());
        if (this.effect == null) return;
        this.effect.appendHoverText(stack, world, components);
    }

    @Override
    public DragonType getDragonType() {
        return this.type;
    }
}
