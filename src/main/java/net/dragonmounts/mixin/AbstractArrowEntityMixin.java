package net.dragonmounts.mixin;

import net.dragonmounts.api.IDMArrow;
import net.dragonmounts.capability.ArmorEffectManager;
import net.dragonmounts.capability.IArmorEffectManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import static net.dragonmounts.init.DMArmorEffects.CHANNELING;
import static net.dragonmounts.init.DMArmorEffects.STORM;

@Mixin(AbstractArrowEntity.class)
public abstract class AbstractArrowEntityMixin extends Entity implements IDMArrow {
    @Unique
    private boolean dragonmounts$hasChanneling = false;

    @Override
    public void dragonmounts$setChanneling(boolean flag) {
        this.dragonmounts$hasChanneling = flag;
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    public void saveData(CompoundNBT compound, CallbackInfo info) {
        if (this.dragonmounts$hasChanneling) {
            compound.putBoolean(CHANNELING, true);
        }
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    public void readData(CompoundNBT compound, CallbackInfo info) {
        this.dragonmounts$hasChanneling = compound.getBoolean(CHANNELING);
    }

    @Inject(
            method = "onHitEntity",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setLastHurtMob(Lnet/minecraft/entity/Entity;)V"),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    public void summonLightning(EntityRayTraceResult result, CallbackInfo info, Entity target, float f, int i, Entity owner, DamageSource source) {
        if (this.dragonmounts$hasChanneling && this.random.nextBoolean() && owner instanceof IArmorEffectManager.Provider) {
            ArmorEffectManager manager = ((IArmorEffectManager.Provider) owner).dragonmounts$getManager();
            if (manager.isActive(STORM) && manager.isAvailable(STORM)) {
                BlockPos pos = target.blockPosition();
                if (target.level.canSeeSky(pos)) {
                    LightningBoltEntity bolt = EntityType.LIGHTNING_BOLT.create(target.level);
                    if (bolt == null) return;
                    bolt.moveTo(Vector3d.atBottomCenterOf(pos));
                    bolt.setCause((ServerPlayerEntity) owner);
                    target.level.addFreshEntity(bolt);
                }
                manager.setCooldown(STORM, STORM.cooldown);
            }
        }
    }

    private AbstractArrowEntityMixin(EntityType<?> a, World b) {
        super(a, b);
    }
}
