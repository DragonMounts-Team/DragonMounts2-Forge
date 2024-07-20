package net.dragonmounts.mixin;

import net.dragonmounts.capability.ArmorEffectManager;
import net.dragonmounts.capability.IArmorEffectManager.Provider;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements Provider {
    @Unique
    protected final ArmorEffectManager dragonmounts$manager = new ArmorEffectManager((PlayerEntity) (Object) this);
    @Inject(method = "tick", at = @At("HEAD"))
    public void tickManager(CallbackInfo info) {
        this.dragonmounts$manager.tick();
    }

    @Override
    public ArmorEffectManager dragonmounts$getManager() {
        return this.dragonmounts$manager;
    }

    private PlayerEntityMixin(EntityType<? extends LivingEntity> a, World b) {
        super(a, b);
    }
}
