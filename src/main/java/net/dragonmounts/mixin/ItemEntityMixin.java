package net.dragonmounts.mixin;

import net.dragonmounts.item.AmuletItem;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {
    @Shadow
    public abstract ItemStack getItem();

    @Shadow
    private int age;

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    public void persistent(CallbackInfo info) {
        if (this.getItem().getItem() instanceof AmuletItem) {
            this.age = Short.MIN_VALUE;
        }
    }

    private ItemEntityMixin() {}
}
