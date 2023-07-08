package net.dragonmounts3.item;

import net.dragonmounts3.api.DragonType;
import net.dragonmounts3.api.IArmorEffect;
import net.dragonmounts3.util.ArmorEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;

import java.util.Random;

import static net.dragonmounts3.init.DMCapabilities.DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN;


public abstract class DragonArmorEffect implements IArmorEffect {
    public static final String TRANSLATION_KEY_PREFIX = "tooltip.dragonmounts.armor_effect.";
    public static final DragonArmorEffect AETHER = new DragonArmorEffect(TRANSLATION_KEY_PREFIX + "aether", 300) {
        @Override
        public void invoke(PlayerEntity player, int strength) {
            if (strength >= 4 && !player.level.isClientSide && player.isSprinting()) {
                player.getCapability(DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN).ifPresent(cooldown -> {
                    if (cooldown.get(DragonType.AETHER) <= 0) {
                        player.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 100, 1, true, true));
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
                if (cooldown.get(DragonType.ENDER) <= 0) {
                    player.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 600, 2, true, true));
                    player.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 300, 0, true, true));
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
                        player.addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 600, 0, true, true));
                        cooldown.set(DragonType.FIRE, this.cooldown);
                    }
                });
            }
        }
    };

    public static final DragonArmorEffect MOONLIGHT = new DragonArmorEffect(TRANSLATION_KEY_PREFIX + "moonlight", 400) {
        @Override
        public void invoke(PlayerEntity player, int strength) {
            if (strength >= 4 && !player.level.isClientSide) {
                player.getCapability(DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN).ifPresent(cooldown -> {
                    if (cooldown.get(DragonType.MOONLIGHT) <= 0) {
                        player.addEffect(new EffectInstance(Effects.NIGHT_VISION, 600, 0, true, true));
                        cooldown.set(DragonType.MOONLIGHT, this.cooldown);
                    }
                });
            }
        }
    };

    public static final DragonArmorEffect TERRA = new DragonArmorEffect(TRANSLATION_KEY_PREFIX + "terra", 400) {
        @Override
        public void invoke(PlayerEntity player, int strength) {
            if (strength >= 4 && !player.level.isClientSide) {
                player.getCapability(DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN).ifPresent(cooldown -> {
                    if (cooldown.get(DragonType.TERRA) <= 0) {
                        player.addEffect(new EffectInstance(Effects.DIG_SPEED, 600, 0, true, true));
                        cooldown.set(DragonType.TERRA, this.cooldown);
                    }
                });
            }
        }
    };

    public static final DragonArmorEffect WATER = new DragonArmorEffect(TRANSLATION_KEY_PREFIX + "water", 400) {
        @Override
        public void invoke(PlayerEntity player, int strength) {
            if (strength >= 4 && !player.level.isClientSide && player.isEyeInFluid(FluidTags.WATER)) {
                player.getCapability(DRAGON_SCALE_ARMOR_EFFECT_COOLDOWN).ifPresent(cooldown -> {
                    if (cooldown.get(DragonType.WATER) <= 0) {
                        player.addEffect(new EffectInstance(Effects.WATER_BREATHING, 600, 0, true, true));
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
                    if (cooldown.get(DragonType.ZOMBIE) <= 0) {
                        player.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 300, 0, true, true));
                        cooldown.set(DragonType.ZOMBIE, this.cooldown);
                    }
                });
            }
        }
    };

    public static void xpBonus(LivingExperienceDropEvent event) {
        PlayerEntity player = event.getAttackingPlayer();
        if (!player.level.isClientSide && ArmorEffect.getCache(player).getOrDefault(ENCHANT, 0) >= 4) {
            event.setDroppedExperience((int) Math.ceil(event.getOriginalExperience() * 1.2));
        }
    }

    public final String description;
    public final int cooldown;

    public DragonArmorEffect(String description, int cooldown) {
        this.description = description;
        this.cooldown = cooldown;
    }
}
