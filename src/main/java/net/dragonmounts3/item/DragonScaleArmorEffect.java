package net.dragonmounts3.item;

import net.dragonmounts3.api.DragonType;
import net.dragonmounts3.api.IArmorEffect;
import net.dragonmounts3.capability.IDragonTypifiedCooldown;
import net.dragonmounts3.network.SRiposteEffectPacket;
import net.dragonmounts3.util.ArmorEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

import java.util.List;
import java.util.Map;
import java.util.Random;

import static net.dragonmounts3.init.DMCapabilities.DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN;
import static net.dragonmounts3.network.DMPacketHandler.CHANNEL;
import static net.dragonmounts3.util.EntityUtil.addOrMergeEffect;
import static net.dragonmounts3.util.EntityUtil.addOrResetEffect;
import static net.minecraftforge.fml.network.PacketDistributor.TRACKING_ENTITY_AND_SELF;


public abstract class DragonScaleArmorEffect implements IArmorEffect {
    private static DragonScaleArmorEffect placeholder(String description, int cooldown) {
        return new DragonScaleArmorEffect(description, cooldown, false) {
            @Override
            public void invoke(PlayerEntity player, int strength) {
            }
        };
    }

    public static final String TRANSLATION_KEY_PREFIX = "tooltip.dragonmounts.armor_effect.";
    public static final DragonScaleArmorEffect AETHER = new DragonScaleArmorEffect(TRANSLATION_KEY_PREFIX + "aether", 300, false) {
        @Override
        public void invoke(PlayerEntity player, int strength) {
            if (strength >= 4 && !player.level.isClientSide && player.isSprinting()) {
                player.getCapability(DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN).ifPresent(cooldown -> {
                    if (cooldown.get(DragonType.AETHER) <= 0 && addOrMergeEffect(player, Effects.MOVEMENT_SPEED, 100, 1, true, true, true)) {
                        player.level.playSound(null, player, SoundEvents.GUARDIAN_HURT, SoundCategory.NEUTRAL, 1.0f, 1.0f);
                        cooldown.set(DragonType.AETHER, this.cooldown);
                    }
                });
            }
        }
    };

    public static final DragonScaleArmorEffect ENCHANT = new DragonScaleArmorEffect(TRANSLATION_KEY_PREFIX + "enchant", 0, false) {
        @Override
        public void invoke(PlayerEntity player, int strength) {
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
        }
    };

    public static final DragonScaleArmorEffect ENDER = new DragonScaleArmorEffect(TRANSLATION_KEY_PREFIX + "ender", 1200, false) {
        @Override
        public void invoke(PlayerEntity player, int strength) {
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
                return;
            }
            if (strength < 4 || player.getHealth() >= 10) return;
            player.getCapability(DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN).ifPresent(cooldown -> {
                //Trying to add these two effects in any case requires using `|` instead of `||`
                if (cooldown.get(DragonType.ENDER) <= 0 && (addOrMergeEffect(player, Effects.DAMAGE_RESISTANCE, 600, 2, true, true, true) | addOrMergeEffect(player, Effects.DAMAGE_BOOST, 300, 1, true, true, true))) {
                    player.level.levelEvent(2003, player.blockPosition(), 0);
                    player.level.playSound(null, player, SoundEvents.END_PORTAL_SPAWN, SoundCategory.HOSTILE, 0.05f, 1.0f);
                    cooldown.set(DragonType.ENDER, this.cooldown);
                }
            });
        }
    };

    public static final DragonScaleArmorEffect FIRE = new DragonScaleArmorEffect(TRANSLATION_KEY_PREFIX + "fire", 900, false) {
        @Override
        public void invoke(PlayerEntity player, int strength) {
            if (strength >= 4 && !player.level.isClientSide && player.isOnFire()) {
                player.getCapability(DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN).ifPresent(cooldown -> {
                    if (cooldown.get(DragonType.FIRE) <= 0) {
                        if (addOrMergeEffect(player, Effects.FIRE_RESISTANCE, 600, 0, true, true, true)) {
                            cooldown.set(DragonType.FIRE, this.cooldown);
                        }
                        player.clearFire();
                    }
                });
            }
        }
    };

    public static final DragonScaleArmorEffect FOREST = new DragonScaleArmorEffect(TRANSLATION_KEY_PREFIX + "forest", 1200, true) {
        @Override
        public void invoke(PlayerEntity player, int strength) {
            if (strength >= 4 && !player.level.isClientSide) {
                if (player.fishing != null) {
                    addOrResetEffect(player, Effects.LUCK, 200, 0, true, true, true, 21);
                }
                if (player.getHealth() >= 10) return;
                player.getCapability(DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN).ifPresent(cooldown -> {
                    if (cooldown.get(DragonType.FOREST) <= 0) {
                        if (addOrMergeEffect(player, Effects.REGENERATION, 200, 1, true, true, true)) {
                            cooldown.set(DragonType.FOREST, this.cooldown);
                        }
                    }
                });
            }
        }
    };

    public static final DragonScaleArmorEffect ICE = placeholder(TRANSLATION_KEY_PREFIX + "ice", 1200);

    public static final DragonScaleArmorEffect MOONLIGHT = new DragonScaleArmorEffect(TRANSLATION_KEY_PREFIX + "moonlight", 0, false) {
        @Override
        public void invoke(PlayerEntity player, int strength) {
            if (strength >= 4 && !player.level.isClientSide) {
                addOrResetEffect(player, Effects.NIGHT_VISION, 600, 0, true, true, true, 201);
            }
        }
    };

    public static final DragonScaleArmorEffect NETHER = placeholder(TRANSLATION_KEY_PREFIX + "nether", 1200);

    public static final DragonScaleArmorEffect STORM = placeholder(TRANSLATION_KEY_PREFIX + "storm", 160);

    public static final DragonScaleArmorEffect SUNLIGHT = new DragonScaleArmorEffect(TRANSLATION_KEY_PREFIX + "sunlight", 1200, true) {
        @Override
        public void invoke(PlayerEntity player, int strength) {
            if (strength >= 4 && !player.level.isClientSide) {
                if (player.fishing != null) {
                    addOrResetEffect(player, Effects.LUCK, 200, 0, true, true, true, 21);
                }
                if (player.getFoodData().getFoodLevel() >= 6) return;
                player.getCapability(DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN).ifPresent(cooldown -> {
                    if (cooldown.get(DragonType.SUNLIGHT) <= 0) {
                        if (addOrMergeEffect(player, Effects.SATURATION, 200, 0, true, true, true)) {
                            cooldown.set(DragonType.SUNLIGHT, this.cooldown);
                        }
                    }
                });
            }
        }
    };

    public static final DragonScaleArmorEffect TERRA = new DragonScaleArmorEffect(TRANSLATION_KEY_PREFIX + "terra", 0, false) {
        @Override
        public void invoke(PlayerEntity player, int strength) {
            if (strength >= 4 && !player.level.isClientSide) {
                addOrResetEffect(player, Effects.DIG_SPEED, 600, 0, true, true, true, 201);
            }
        }
    };

    public static final DragonScaleArmorEffect WATER = new DragonScaleArmorEffect(TRANSLATION_KEY_PREFIX + "water", 0, false) {
        @Override
        public void invoke(PlayerEntity player, int strength) {
            if (strength >= 4 && !player.level.isClientSide && player.isEyeInFluid(FluidTags.WATER)) {
                addOrResetEffect(player, Effects.WATER_BREATHING, 600, 0, true, true, true, 201);
            }
        }
    };

    public static final DragonScaleArmorEffect ZOMBIE = new DragonScaleArmorEffect(TRANSLATION_KEY_PREFIX + "zombie", 400, false) {
        @Override
        public void invoke(PlayerEntity player, int strength) {
            if (strength >= 4 && !player.level.isClientSide && !player.level.isDay()) {
                player.getCapability(DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN).ifPresent(cooldown -> {
                    if (cooldown.get(DragonType.ZOMBIE) <= 0 && addOrMergeEffect(player, Effects.DAMAGE_BOOST, 300, 0, true, true, true)) {
                        cooldown.set(DragonType.ZOMBIE, this.cooldown);
                    }
                });
            }
        }
    };

    public static void xpBonus(LivingExperienceDropEvent event) {
        PlayerEntity player = event.getAttackingPlayer();
        if (player != null && !player.level.isClientSide && ArmorEffect.getCache(player).getOrDefault(ENCHANT, 0) >= 4) {
            int original = event.getOriginalExperience();
            event.setDroppedExperience(original + (original + 1) >> 1);//Math.ceil(original * 1.5)
        }
    }

    public static void meleeChanneling(AttackEntityEvent event) {
        PlayerEntity player = event.getPlayer();
        if (player.level.isClientSide) return;
        Entity entity = event.getTarget();
        if (ArmorEffect.getCache(player).getOrDefault(STORM, 0) >= 4 && player.getRandom().nextBoolean()) {
            player.getCapability(DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN).ifPresent(cooldown -> {
                if (cooldown.get(DragonType.STORM) <= 0) {
                    BlockPos pos = entity.blockPosition();
                    if (entity.level.canSeeSky(pos)) {
                        LightningBoltEntity bolt = EntityType.LIGHTNING_BOLT.create(entity.level);
                        if (bolt == null) return;
                        bolt.moveTo(Vector3d.atBottomCenterOf(pos));
                        bolt.setCause((ServerPlayerEntity) player);
                        entity.level.addFreshEntity(bolt);
                    }
                    cooldown.set(DragonType.STORM, STORM.cooldown);
                }
            });
        }
    }

    public static void riposte(LivingHurtEvent event) {
        Entity entity = event.getEntity();
        //In fact, entity.level.isClientSide -> false
        if (entity.level.isClientSide || !(entity instanceof PlayerEntity)) return;
        PlayerEntity player = (PlayerEntity) entity;
        List<Entity> targets = player.level.getEntities(player, player.getBoundingBox().inflate(5.0D), EntityPredicates.ATTACK_ALLOWED);
        if (targets.isEmpty()) return;
        Map<IArmorEffect, Integer> strength = ArmorEffect.getCache(player);
        LazyOptional<IDragonTypifiedCooldown> capability = player.getCapability(DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN);
        SRiposteEffectPacket packet = new SRiposteEffectPacket(player.getId());
        if (strength.getOrDefault(ICE, 0) >= 4) {
            capability.ifPresent(cooldown -> {
                if (cooldown.get(DragonType.ICE) <= 0) {
                    packet.flag |= 0B01;
                    for (Entity target : targets) {
                        target.hurt(DamageSource.GENERIC, 1);
                        if (target instanceof LivingEntity) {
                            ((LivingEntity) target).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 200, 1));
                            ((LivingEntity) target).knockback(0.4f, 1, 1);
                        }
                    }
                    cooldown.set(DragonType.ICE, ICE.cooldown);
                }
            });
        }
        if (strength.getOrDefault(NETHER, 0) >= 4) {
            capability.ifPresent(cooldown -> {
                if (cooldown.get(DragonType.NETHER) <= 0) {
                    packet.flag |= 0B10;
                    for (Entity target : targets) {
                        target.setSecondsOnFire(10);
                        if (target instanceof LivingEntity) {
                            ((LivingEntity) target).knockback(0.4f, 1, 1);
                        }
                    }
                    cooldown.set(DragonType.NETHER, NETHER.cooldown);
                }
            });
        }
        if (packet.flag != 0) {
            CHANNEL.send(TRACKING_ENTITY_AND_SELF.with(() -> player), packet);
        }
    }

    public final String description;
    public final int cooldown;
    public final boolean luck;

    public DragonScaleArmorEffect(String description, int cooldown, boolean luck) {
        this.description = description;
        this.cooldown = cooldown;
        this.luck = luck;
    }
}
