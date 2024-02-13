package net.dragonmounts.init;

import net.dragonmounts.api.IDragonScaleArmorEffect;
import net.dragonmounts.capability.ArmorEffectManager;
import net.dragonmounts.capability.IArmorEffectManager;
import net.dragonmounts.network.SRiposteEffectPacket;
import net.dragonmounts.registry.CooldownCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static net.dragonmounts.DragonMounts.MOD_ID;
import static net.dragonmounts.network.DMPacketHandler.CHANNEL;
import static net.dragonmounts.util.EntityUtil.addOrMergeEffect;
import static net.dragonmounts.util.EntityUtil.addOrResetEffect;
import static net.minecraftforge.fml.network.PacketDistributor.TRACKING_ENTITY_AND_SELF;

public class DMArmorEffects {
    public static final String FISHING_LUCK = "tooltip.dragonmounts.armor_effect_fishing_luck";

    public static final IDragonScaleArmorEffect.Advanced AETHER = new IDragonScaleArmorEffect.Advanced(300) {
        @Override
        public boolean activate(IArmorEffectManager manager, PlayerEntity player, int level) {
            boolean flag = level > 3;
            if (flag && !player.level.isClientSide && manager.getCooldown(this) <= 0 && player.isSprinting() && addOrMergeEffect(player, Effects.MOVEMENT_SPEED, 100, 1, true, true, true)) {
                player.level.playSound(null, player, SoundEvents.GUARDIAN_HURT, SoundCategory.NEUTRAL, 1.0F, 1.0F);
                manager.setCooldown(this, this.cooldown);
            }
            return flag;
        }
    }.withRegistryName(MOD_ID + ":aether");

    public static final IDragonScaleArmorEffect ENCHANT = new IDragonScaleArmorEffect() {
        @Override
        public boolean activate(IArmorEffectManager manager, PlayerEntity player, int level) {
        if (player.level.isClientSide) {
            Random random = player.getRandom();
            for (int i = -2; i <= 2; ++i) {
                for (int j = -2; j <= 2; ++j) {
                    if (i > -2 && i < 2 && j == -1) j = 2;
                    if (random.nextInt(30) == 0) {
                        for (int k = 0; k <= 1; ++k) {
                            player.level.addParticle(
                                    ParticleTypes.ENCHANT,
                                    player.getX(),
                                    player.getY() + random.nextFloat() + 1.5,
                                    player.getZ(),
                                    i + random.nextFloat() - 0.5D,
                                    k - random.nextFloat() - 1.0F,
                                    j + random.nextFloat() - 0.5D
                            );
                        }
                    }
                }
            }
        }
        return level > 3;
        }

        @Override
        public void appendHoverText(@Nonnull ItemStack stack, World world, List<ITextComponent> tooltips) {
            tooltips.add(new TranslationTextComponent("tooltip.armor_effect.dragonmounts.water"));
        }
    };

    public static final IDragonScaleArmorEffect.Advanced ENDER = new IDragonScaleArmorEffect.Advanced(1200) {
        @Override
        public boolean activate(IArmorEffectManager manager, PlayerEntity player, int level) {
            if (player.level.isClientSide) {
                Random random = player.getRandom();
                player.level.addParticle(
                        ParticleTypes.PORTAL,
                        player.getX() + random.nextFloat() - 0.3,
                        player.getY() + random.nextFloat() - 0.3,
                        player.getZ() + random.nextFloat() - 0.3,
                        random.nextFloat() * 2 - 0.15,
                        random.nextFloat() * 2 - 0.15,
                        random.nextFloat() * 2 - 0.15
                );
                return level > 3;
            }
            // use `|` instead of `||` to avoid short-circuit evaluation when trying to add both of these two effects
            if (level > 3 && manager.getCooldown(this) <= 0 && player.getHealth() < 10 && (
                    addOrMergeEffect(player, Effects.DAMAGE_RESISTANCE, 600, 2, true, true, true)
                            | addOrMergeEffect(player, Effects.DAMAGE_BOOST, 300, 1, true, true, true)
            )) {
                player.level.levelEvent(2003, player.blockPosition(), 0);
                player.level.playSound(null, player, SoundEvents.END_PORTAL_SPAWN, SoundCategory.HOSTILE, 0.05F, 1.0F);
                manager.setCooldown(this, this.cooldown);
                return true;
            }
            return false;
        }
    }.withRegistryName(MOD_ID + ":ender");

    public static final IDragonScaleArmorEffect.Advanced FIRE = new IDragonScaleArmorEffect.Advanced(900) {
        @Override
        public boolean activate(IArmorEffectManager manager, PlayerEntity player, int level) {
            boolean flag = level > 3;
            if (flag && !player.level.isClientSide && manager.getCooldown(this) <= 0 && player.isOnFire()) {
                if (addOrMergeEffect(player, Effects.FIRE_RESISTANCE, 600, 0, true, true, true)) {
                    manager.setCooldown(this, this.cooldown);
                }
                player.clearFire();
            }
            return flag;
        }
    }.withRegistryName(MOD_ID + ":fire");

    public static final IDragonScaleArmorEffect.Advanced FOREST = new IDragonScaleArmorEffect.Advanced(1200) {
        @Override
        public boolean activate(IArmorEffectManager manager, PlayerEntity player, int level) {
            boolean flag = level > 3;
            if (flag && !player.level.isClientSide) {
                if (player.fishing != null) {
                    addOrResetEffect(player, Effects.LUCK, 200, 0, true, true, true, 21);
                }
                if (player.getHealth() < 10 && manager.getCooldown(this) <= 0) {
                    if (addOrMergeEffect(player, Effects.REGENERATION, 200, 1, true, true, true)) {
                        manager.setCooldown(this, this.cooldown);
                    }
                }
            }
            return flag;
        }

        @Override
        public void appendHoverText(@Nonnull ItemStack stack, @Nullable World world, List<ITextComponent> tooltips) {
            tooltips.add(StringTextComponent.EMPTY);
            this.appendTriggerInfo(stack, world, tooltips);
            tooltips.add(new TranslationTextComponent(FISHING_LUCK));
            this.appendCooldownInfo(stack, world, tooltips);
        }
    }.withRegistryName(MOD_ID + ":forest");

    public static final IDragonScaleArmorEffect.Advanced ICE = new IDragonScaleArmorEffect.Advanced(1200).withRegistryName(MOD_ID + ":ice");

    public static final IDragonScaleArmorEffect MOONLIGHT = new IDragonScaleArmorEffect() {
        @Override
        public boolean activate(IArmorEffectManager manager, PlayerEntity player, int level) {
        boolean flag = level > 3;
        if (flag && !player.level.isClientSide) {
            addOrResetEffect(player, Effects.NIGHT_VISION, 600, 0, true, true, true, 201);
        }
        return flag;
        }

        @Override
        public void appendHoverText(@Nonnull ItemStack stack, World world, List<ITextComponent> tooltips) {
            tooltips.add(new TranslationTextComponent("tooltip.armor_effect.dragonmounts.water"));
        }
    };

    public static final IDragonScaleArmorEffect.Advanced NETHER = new IDragonScaleArmorEffect.Advanced(1200).withRegistryName(MOD_ID + ":nether");

    public static final IDragonScaleArmorEffect.Advanced STORM = new IDragonScaleArmorEffect.Advanced(160).withRegistryName(MOD_ID + ":storm");

    public static final IDragonScaleArmorEffect.Advanced SUNLIGHT = new IDragonScaleArmorEffect.Advanced(1200) {
        @Override
        public boolean activate(IArmorEffectManager manager, PlayerEntity player, int level) {
            boolean flag = level > 3;
            if (flag && !player.level.isClientSide) {
                if (player.fishing != null) {
                    addOrResetEffect(player, Effects.LUCK, 200, 0, true, true, true, 21);
                }
                if (manager.getCooldown(this) <= 0 && player.getFoodData().getFoodLevel() < 6 && addOrMergeEffect(player, Effects.SATURATION, 200, 0, true, true, true)) {
                    manager.setCooldown(this, this.cooldown);
                }
            }
            return flag;
        }

        @Override
        public void appendHoverText(@Nonnull ItemStack stack, @Nullable World world, List<ITextComponent> tooltips) {
            tooltips.add(StringTextComponent.EMPTY);
            this.appendTriggerInfo(stack, world, tooltips);
            tooltips.add(new TranslationTextComponent(FISHING_LUCK));
            this.appendCooldownInfo(stack, world, tooltips);
        }
    }.withRegistryName(MOD_ID + ":sunlight");

    public static final IDragonScaleArmorEffect TERRA = new IDragonScaleArmorEffect() {
        @Override
        public boolean activate(IArmorEffectManager manager, PlayerEntity player, int level) {
        boolean flag = level > 3;
            if (flag && !player.level.isClientSide)
                addOrResetEffect(player, Effects.DIG_SPEED, 600, 0, true, true, true, 201);
            return flag;
        }

        @Override
        public void appendHoverText(@Nonnull ItemStack stack, World world, List<ITextComponent> tooltips) {
            tooltips.add(new TranslationTextComponent("tooltip.armor_effect.dragonmounts.water"));
        }
    };

    public static final IDragonScaleArmorEffect WATER = new IDragonScaleArmorEffect() {
        @Override
        public boolean activate(IArmorEffectManager manager, PlayerEntity player, int level) {
            boolean flag = level > 3;
            if (flag && !player.level.isClientSide && player.isEyeInFluid(FluidTags.WATER))
                addOrResetEffect(player, Effects.WATER_BREATHING, 600, 0, true, true, true, 201);
            return flag;
        }

        @Override
        public void appendHoverText(@Nonnull ItemStack stack, World world, List<ITextComponent> tooltips) {
            tooltips.add(new TranslationTextComponent("tooltip.armor_effect.dragonmounts.water"));
        }
    };

    public static final IDragonScaleArmorEffect.Advanced ZOMBIE = new IDragonScaleArmorEffect.Advanced(400) {
        @Override
        public boolean activate(IArmorEffectManager manager, PlayerEntity player, int level) {
            boolean flag = level > 3;
            if (flag && !player.level.isClientSide && !player.level.isDay() && manager.getCooldown(this) <= 0 && addOrMergeEffect(player, Effects.DAMAGE_BOOST, 300, 0, true, true, true))
                manager.setCooldown(this, this.cooldown);
            return flag;
        }
    }.withRegistryName(MOD_ID + ":zombie");

    public static void xpBonus(LivingExperienceDropEvent event) {
        PlayerEntity player = event.getAttackingPlayer();
        if (player != null && ((IArmorEffectManager.Provider) player).dragonmounts$getManager().isActive(ENCHANT)) {
            event.setDroppedExperience((int) (event.getOriginalExperience() * 1.5F + 0.5F));//Math.ceil(original * 1.5)
        }
    }

    public static void meleeChanneling(AttackEntityEvent event) {
        PlayerEntity player = event.getPlayer();
        if (player.level.isClientSide || player.getRandom().nextBoolean()) return;
        ArmorEffectManager manager = ((IArmorEffectManager.Provider) player).dragonmounts$getManager();
        if (manager.isActive(STORM) && manager.getCooldown(STORM) <= 0) {
            Entity entity = event.getTarget();
            BlockPos pos = entity.blockPosition();
            if (entity.level.canSeeSky(pos)) {
                LightningBoltEntity bolt = EntityType.LIGHTNING_BOLT.create(entity.level);
                if (bolt == null) return;
                bolt.moveTo(Vector3d.atBottomCenterOf(pos));
                bolt.setCause((ServerPlayerEntity) player);
                entity.level.addFreshEntity(bolt);
            }
            manager.setCooldown(STORM, STORM.cooldown);
        }
    }

    public static void riposte(LivingHurtEvent event) {
        final Entity self = event.getEntity();
        //In fact, self.world.isClientSide -> false
        if (self.level.isClientSide || !(self instanceof PlayerEntity)) return;
        ArmorEffectManager manager = ((IArmorEffectManager.Provider) self).dragonmounts$getManager();
        final boolean ice = manager.isActive(DMArmorEffects.ICE) && manager.getCooldown(DMArmorEffects.ICE) <= 0;
        final boolean nether = manager.isActive(DMArmorEffects.NETHER) && manager.getCooldown(DMArmorEffects.NETHER) <= 0;
        if (!ice && !nether) return;
        final List<Entity> entities = self.level.getEntities(self, self.getBoundingBox().inflate(5.0D), EntityPredicates.NO_CREATIVE_OR_SPECTATOR);
        if (entities.isEmpty()) return;
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity) {
                LivingEntity target = (LivingEntity) entity;
                target.knockback(0.4F, 1, 1);
                if (ice) {
                    addOrMergeEffect(target, Effects.MOVEMENT_SLOWDOWN, 200, 1, false, true, true);
                    entity.hurt(DamageSource.GENERIC, 1F);
                }
            } else if (ice) {
                entity.hurt(DamageSource.GENERIC, 1F);
            }
            if (nether) {
                entity.setSecondsOnFire(10);
            }
        }
        if (ice) manager.setCooldown(DMArmorEffects.ICE, DMArmorEffects.ICE.cooldown);
        if (nether) manager.setCooldown(DMArmorEffects.NETHER, DMArmorEffects.NETHER.cooldown);
        CHANNEL.send(TRACKING_ENTITY_AND_SELF.with(() -> self), new SRiposteEffectPacket(self.getId(), (ice ? 0b01 : 0b00) | (nether ? 0b10 : 0b00)));
    }

    public static void register(RegistryEvent.Register<CooldownCategory> event) {
        IForgeRegistry<CooldownCategory> registry = event.getRegistry();
        registry.register(AETHER);
        registry.register(ENDER);
        registry.register(FIRE);
        registry.register(FOREST);
        registry.register(ICE);
        registry.register(NETHER);
        registry.register(STORM);
        registry.register(SUNLIGHT);
        registry.register(ZOMBIE);
    }
}
