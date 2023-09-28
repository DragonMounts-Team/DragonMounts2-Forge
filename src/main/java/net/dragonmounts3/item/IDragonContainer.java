package net.dragonmounts3.item;

import net.dragonmounts3.api.IDragonTypified;
import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.dragonmounts3.registry.DragonType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static net.dragonmounts3.util.EntityUtil.saveScoreboard;
import static net.dragonmounts3.util.EntityUtil.simplifyDragonData;

public interface IDragonContainer {
    default TameableDragonEntity release(CompoundNBT compound, World level, @Nullable DragonType type) {
        TameableDragonEntity dragon = new TameableDragonEntity(level);
        if (compound != null) {
            dragon.load(compound);
            if (type != null) {
                dragon.setDragonType(type, false);
            }
        } else if (type != null) {
            dragon.setDragonType(type, true);
        }
        return dragon;
    }

    static ItemStack fill(TameableDragonEntity dragon, Item item, boolean reborn) {
        ItemStack stack = new ItemStack(item);
        CompoundNBT compound = simplifyDragonData(dragon.saveWithoutId(new CompoundNBT()), reborn);
        compound.remove("UUID");
        if (item instanceof IDragonTypified) {
            compound.remove(DragonType.DATA_PARAMETER_KEY);
        }
        LivingEntity entity = dragon.getOwner();
        if (entity != null) {
            compound.putString("OwnerName", ITextComponent.Serializer.toJson(entity.getName()));
        }
        stack.setTag(reborn ? compound : saveScoreboard(dragon, compound));
        return stack;
    }
}
