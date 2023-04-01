package net.dragonmounts3.objects.items;

import net.dragonmounts3.objects.DragonType;
import net.dragonmounts3.objects.IDragonTypified;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static net.dragonmounts3.DragonMounts.getItemTranslationKey;

public class ItemDragonPickaxe extends PickaxeItem implements IDragonTypified {
    private static final String TRANSLATION_KEY = getItemTranslationKey("dragon_pickaxe");

    protected DragonType type;

    public ItemDragonPickaxe(
            DragonScaleTier tier,
            int attackDamageModifier,
            float attackSpeedModifier,
            Properties properties
    ) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
        this.type = tier.getDragonType();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable World world, List<ITextComponent> components, @Nullable ITooltipFlag flag) {
        components.add(this.type.getText());
    }

    @Nonnull
    @Override
    public String getOrCreateDescriptionId() {
        return TRANSLATION_KEY;
    }

    @Override
    public DragonType getDragonType() {
        return this.type;
    }
}