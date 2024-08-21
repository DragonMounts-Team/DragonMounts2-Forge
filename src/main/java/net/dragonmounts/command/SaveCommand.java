package net.dragonmounts.command;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.dragonmounts.entity.dragon.TameableDragonEntity;
import net.dragonmounts.init.DMItems;
import net.dragonmounts.item.DragonAmuletItem;
import net.dragonmounts.item.DragonEssenceItem;
import net.dragonmounts.item.IEntityContainer;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.ItemArgument;
import net.minecraft.command.arguments.ItemInput;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Predicate;

import static net.dragonmounts.command.DMCommand.createClassCastException;
import static net.dragonmounts.entity.dragon.TameableDragonEntity.FLYING_DATA_PARAMETER_KEY;

public class SaveCommand {
    public static ArgumentBuilder<CommandSource, ?> register(Predicate<CommandSource> permission) {
        return Commands.literal("save").requires(permission).then(Commands.argument("target", EntityArgument.entity())
                .then(Commands.literal("amulet").executes(context -> saveAmulet(context, EntityArgument.getEntity(context, "target"))))
                .then(Commands.literal("essence").executes(context -> saveEssence(context, EntityArgument.getEntity(context, "target"))))
                .then(Commands.literal("container").then(Commands.argument("item", ItemArgument.item()).executes(context ->
                        save(context, ItemArgument.getItem(context, "item"), EntityArgument.getEntity(context, "target"))
                )))
        );
    }


    public static int saveAmulet(CommandContext<CommandSource> context, Entity target) throws CommandSyntaxException {
        CommandSource source = context.getSource();
        if (target instanceof TameableDragonEntity) {
            TameableDragonEntity dragon = (TameableDragonEntity) target;
            DragonAmuletItem amulet = dragon.getDragonType().getInstance(DragonAmuletItem.class, null);
            if (amulet != null) {
                give(source, amulet.saveEntity(dragon));
                return 1;
            }
        }
        ItemStack stack = DMItems.AMULET.saveEntity(target);
        if (stack.isEmpty()) {
            //source.sendFailure();
            return 0;
        }
        give(source, stack);
        return 1;
    }

    public static int saveEssence(CommandContext<CommandSource> context, Entity target) throws CommandSyntaxException {
        CommandSource source = context.getSource();
        if (target instanceof TameableDragonEntity) {
            TameableDragonEntity dragon = (TameableDragonEntity) target;
            DragonEssenceItem essence = dragon.getDragonType().getInstance(DragonEssenceItem.class, null);
            if (essence != null) {
                give(source, essence.saveEntity(dragon));
                return 1;
            }
        }
        source.sendFailure(createClassCastException(target, TameableDragonEntity.class));
        return 0;
    }

    public static int save(CommandContext<CommandSource> context, ItemInput input, Entity target) throws CommandSyntaxException {
        CommandSource source = context.getSource();
        Item item = input.getItem();
        if (item instanceof IEntityContainer<?> && ((IEntityContainer<?>) item).getContentType().isInstance(target)) {
            ItemStack stack;
            try {
                stack = (ItemStack) IEntityContainer.class
                        .getDeclaredMethod("saveEntity", Entity.class)
                        .invoke(item, target);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                source.sendFailure(new StringTextComponent(e.getMessage()));
                return 0;
            }
            if (stack.isEmpty()) {
                //source.sendFailure();
                return 0;
            }
            give(source, stack);
            return 1;
        }
        EntityType<?> type = target.getType();
        if (type.canSerialize()) {
            ItemStack stack = input.createItemStack(1, false);
            CompoundNBT tag = target.saveWithoutId(new CompoundNBT());
            tag.putString("id", EntityType.getKey(type).toString());
            tag.remove(FLYING_DATA_PARAMETER_KEY);
            tag.remove("UUID");
            CompoundNBT root = new CompoundNBT();
            root.put("EntityTag", IEntityContainer.simplifyData(tag));
            stack.setTag(root);
            give(source, stack);
            return 1;
        }
        //source.sendFailure();
        return 0;
    }

    public static void give(CommandSource source, ItemStack stack) throws CommandSyntaxException {
        ServerPlayerEntity player = source.getPlayerOrException();
        ITextComponent name = stack.getDisplayName();
        int count = stack.getCount();
        if (!player.inventory.add(stack)) {
            ItemEntity item = player.drop(stack, false);
            if (item != null) {
                item.setNoPickUpDelay();
                item.setOwner(player.getUUID());
            }
        }
        source.sendSuccess(new TranslationTextComponent("commands.give.success.single", count, name, player.getDisplayName()), true);
    }
}
