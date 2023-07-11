package net.dragonmounts3.item;

import net.dragonmounts3.api.DragonType;
import net.dragonmounts3.api.IArmorEffect;
import net.dragonmounts3.util.ArmorEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

import java.util.Random;

import static net.dragonmounts3.init.DMCapabilities.DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN;
import static net.dragonmounts3.util.EntityUtil.addOrMergeEffect;
import static net.dragonmounts3.util.EntityUtil.addOrResetEffect;


public abstract class DragonArmorEffect implements IArmorEffect {
    public static final String TRANSLATION_KEY_PREFIX = "tooltip.dragonmounts.armor_effect.";
    public static final DragonArmorEffect AETHER = new DragonArmorEffect(TRANSLATION_KEY_PREFIX + "aether", 300) {
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

    public static final DragonArmorEffect ENCHANT = new DragonArmorEffect(TRANSLATION_KEY_PREFIX + "enchant", 0) {
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

    public static final DragonArmorEffect ENDER = new DragonArmorEffect(TRANSLATION_KEY_PREFIX + "ender", 1200) {
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
            if (strength < 4 || player.getHealth() >= 5) return;
            player.getCapability(DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN).ifPresent(cooldown -> {
                //Trying to add these two effects in any case requires using `|` instead of `||`
                if (cooldown.get(DragonType.ENDER) <= 0 && (addOrMergeEffect(player, Effects.DAMAGE_RESISTANCE, 600, 2, true, true, true) | addOrMergeEffect(player, Effects.DAMAGE_BOOST, 300, 0, true, true, true))) {
                    player.level.levelEvent(2003, player.blockPosition(), 0);
                    player.level.playSound(null, player, SoundEvents.END_PORTAL_SPAWN, SoundCategory.HOSTILE, 0.05f, 1.0f);
                    cooldown.set(DragonType.ENDER, this.cooldown);
                }
            });
        }
    };

    public static final DragonArmorEffect FIRE = new DragonArmorEffect(TRANSLATION_KEY_PREFIX + "fire", 900) {
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

    public static final DragonArmorEffect MOONLIGHT = new DragonArmorEffect(TRANSLATION_KEY_PREFIX + "moonlight", 300) {
        @Override
        public void invoke(PlayerEntity player, int strength) {
            if (strength >= 4 && !player.level.isClientSide) {
                player.getCapability(DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN).ifPresent(cooldown -> {
                    if (cooldown.get(DragonType.MOONLIGHT) <= 0 && addOrResetEffect(player, Effects.NIGHT_VISION, 600, 0, true, true, true, 201)) {
                        cooldown.set(DragonType.MOONLIGHT, this.cooldown);
                    }
                });
            }
        }
    };

    public static final DragonArmorEffect STORM = new DragonArmorEffect(TRANSLATION_KEY_PREFIX + "storm", 160) {
        @Override
        public void invoke(PlayerEntity player, int strength) {
        }
    };

    public static final DragonArmorEffect TERRA = new DragonArmorEffect(TRANSLATION_KEY_PREFIX + "terra", 300) {
        @Override
        public void invoke(PlayerEntity player, int strength) {
            if (strength >= 4 && !player.level.isClientSide) {
                player.getCapability(DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN).ifPresent(cooldown -> {
                    if (cooldown.get(DragonType.TERRA) <= 0 && addOrResetEffect(player, Effects.DIG_SPEED, 600, 0, true, true, true, 201)) {
                        cooldown.set(DragonType.TERRA, this.cooldown);
                    }
                });
            }
        }
    };

    public static final DragonArmorEffect WATER = new DragonArmorEffect(TRANSLATION_KEY_PREFIX + "water", 300) {
        @Override
        public void invoke(PlayerEntity player, int strength) {
            if (strength >= 4 && !player.level.isClientSide && player.isEyeInFluid(FluidTags.WATER)) {
                player.getCapability(DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN).ifPresent(cooldown -> {
                    if (cooldown.get(DragonType.WATER) <= 0 && addOrResetEffect(player, Effects.WATER_BREATHING, 600, 0, true, true, true, 201)) {
                        cooldown.set(DragonType.WATER, this.cooldown);
                    }
                });
            }
        }
    };

    public static final DragonArmorEffect ZOMBIE = new DragonArmorEffect(TRANSLATION_KEY_PREFIX + "zombie", 400) {
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

    public final String description;
    public final int cooldown;

    public DragonArmorEffect(String description, int cooldown) {
        this.description = description;
        this.cooldown = cooldown;
    }
}
