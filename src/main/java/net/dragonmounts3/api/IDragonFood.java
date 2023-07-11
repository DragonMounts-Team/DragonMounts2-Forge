package net.dragonmounts3.api;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.Random;

@FunctionalInterface
public interface IDragonFood {
    void eat(TameableDragonEntity dragon, PlayerEntity player, ItemStack stack, Hand hand);

    default boolean isEatable(TameableDragonEntity dragon, PlayerEntity player, ItemStack stack, Hand hand) {
        return true;
    }

    default void act(TameableDragonEntity dragon, Item item) {
        if (item == Items.AIR) return;
        Minecraft minecraft = Minecraft.getInstance();
        PlayerEntity player = minecraft.player;
        World level = dragon.level;
        dragon.refreshForcedAgeTimer();
        level.playSound(player, dragon, item.getEatingSound(), SoundCategory.NEUTRAL, 1f, 0.75f);
        if (item == Items.HONEY_BOTTLE) return;
        if (item instanceof BucketItem) {
            level.playSound(player, dragon, item.getDrinkingSound(), SoundCategory.NEUTRAL, 0.25f, 0.75f);
            if (item == Items.COD_BUCKET) {
                item = Items.COD;
            } else if (item == Items.SALMON_BUCKET) {
                item = Items.SALMON;
            } else {
                item = Items.TROPICAL_FISH;
            }
        }
        ItemStack stack = new ItemStack(item);
        Random random = dragon.getRandom();
        for (int i = 0; i < 8; ++i) {
            //TODO: Create particles around the mouth of the dragon
            Vector3d vector3d = new Vector3d((random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D).xRot((float) (-dragon.xRot * Math.PI / 180F)).yRot((float) (-dragon.yRot * Math.PI / 180F));
            dragon.level.addParticle(new ItemParticleData(ParticleTypes.ITEM, stack), dragon.getX(), dragon.getY(), dragon.getZ(), vector3d.x, vector3d.y + 0.05D, vector3d.z);
        }
    }
}
