package net.dragonmounts3.item;

import net.dragonmounts3.registry.DragonType;
import net.dragonmounts3.registry.IDragonTypified;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static net.dragonmounts3.DragonMounts.getItemTranslationKey;

public class DragonEssenceItem extends Item implements IDragonTypified {
    private static final String TRANSLATION_KEY = getItemTranslationKey("dragon_essence");

    protected DragonType type;

    public DragonEssenceItem(DragonType type, Properties properties) {
        super(properties.stacksTo(1));
        this.type = type;
    }

    @Nonnull
    @Override
    @SuppressWarnings("CommentedOutCode")
    public ActionResultType useOn(ItemUseContext context) {
        PlayerEntity player = context.getPlayer();
        if (player != null) {
            /*
            BlockPos pos = context.getClickedPos();
            World world = context.getLevel();

        if (world.isRemote || !player.isServerWorld()) return EnumActionResult.FAIL;
        ItemStack stack = player.getHeldItem(hand);
        if (!stack.hasTagCompound()) return EnumActionResult.FAIL;

        EntityTameableDragon dragon = new EntityTameableDragon(world);
        dragon.readFromNBT(stack.getTagCompound());

        if (dragon.isAllowed(player)) {
            dragon.setPosition(pos.getX(), pos.getY() + 1, pos.getZ());
            world.spawnEntity(dragon);
            //debug
            System.out.println(dragon.getUniqueID());
            player.setHeldItem(hand, ItemStack.EMPTY);
            dragon.world.playSound(x, y, z, SoundEvents.ITEM_SHIELD_BREAK, SoundCategory.PLAYERS, 1, 1, false);
            dragon.world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, x + dragon.getRNG().nextInt(5), y + dragon.getRNG().nextInt(5), z + dragon.getRNG().nextInt(5), 1, 1, 1, 0);
        } else player.sendStatusMessage(new TextComponentTranslation("dragon.notOwned"), true);
        */
            player.sendMessage(new StringTextComponent("Uncompleted"), Util.NIL_UUID);
        }
        return super.useOn(context);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable World world, List<ITextComponent> components, @Nonnull ITooltipFlag flag) {
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
