package net.dragonmounts.mixin;

import net.dragonmounts.item.AmuletItem;
import net.dragonmounts.item.IEntityContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
    @Shadow
    private int age;

    @Shadow
    public abstract ItemStack getItem();

    @Shadow
    public abstract void setItem(ItemStack pStack);

    @Inject(method = "setItem", at = @At("TAIL"))
    public void persistent(ItemStack stack, CallbackInfo info) {
        Item item = stack.getItem();
        if (item instanceof IEntityContainer && !((IEntityContainer<?>) item).isEmpty(stack.getTag())) {
            this.age = Short.MIN_VALUE;
        }
    }

    @SuppressWarnings("DataFlowIssue")
    @Inject(method = "hurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/item/ItemEntity;remove()V"))
    public void onDestroy(DamageSource source, float amount, CallbackInfoReturnable<Boolean> info) {
        ItemStack stack = this.getItem();
        Item item = stack.getItem();
        if (item instanceof AmuletItem) {
            AmuletItem<?> amulet = (AmuletItem<?>) item;
            CompoundNBT tag = stack.getTag();
            if (amulet.isEmpty(tag)) return;
            ServerWorld level = (ServerWorld) this.level;
            Entity entity = amulet.loadEntity(level, null, tag, this.getOnPos(), SpawnReason.BUCKET, null, true, false);
            if (entity != null) {
                level.addFreshEntityWithPassengers(entity);
            }
        }
    }

    private ItemEntityMixin(EntityType<?> a, World b) {super(a, b);}
}
