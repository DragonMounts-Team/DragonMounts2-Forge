package net.dragonmounts3.entity.dragon.config;

import net.dragonmounts3.DragonMountsConfig;
import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.dragonmounts3.entity.dragon.sound.SoundEffectNames;
import net.dragonmounts3.inits.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.biome.Biome;

import java.util.HashSet;
import java.util.Set;

public abstract class DragonConfig {
    public static SoundEffectNames[] soundEffectNames;
    private final Set<String> immunities = new HashSet<>();
    private final Set<Block> breedBlocks = new HashSet<>();
    private final Set<RegistryKey<Biome>> biomes = new HashSet<>();

    DragonConfig() {
        // ignore suffocation damage
        this.setImmunity(DamageSource.DROWN);
        this.setImmunity(DamageSource.IN_WALL);
        this.setImmunity(DamageSource.ON_FIRE);
        this.setImmunity(DamageSource.IN_FIRE);
        this.setImmunity(DamageSource.LAVA);
        this.setImmunity(DamageSource.HOT_FLOOR);
        this.setImmunity(DamageSource.CACTUS); // assume that cactus needles don't do much damage to animals with horned scales
        this.setImmunity(DamageSource.DRAGON_BREATH); // ignore damage from vanilla ender dragon. I kinda disabled this because it wouldn't make any sense, feel free to re enable
    }

    protected final void setImmunity(DamageSource dmg) {
        immunities.add(dmg.msgId);
    }

    public boolean isImmuneToDamage(DamageSource dmg) {
        if (immunities.isEmpty()) {
            return false;
        }

        return immunities.contains(dmg.msgId);
    }

    protected final void setHabitatBlock(Block block) {
        breedBlocks.add(block);
    }

    public boolean isHabitatBlock(Block block) {
        return breedBlocks.contains(block);
    }

    protected final void setHabitatBiome(RegistryKey<Biome> biome) {
        biomes.add(biome);
    }

    public boolean isHabitatBiome(RegistryKey<Biome> biome) {
        return biomes.contains(biome);
    }

    public boolean isHabitatEnvironment(TameableDragonEntity dragon) {
        return false;
    }

    public Item getShrinkingFood() {
        return Items.POISONOUS_POTATO;
    }

    public Item getGrowingFood() {
        return Items.CARROT;
    }

    protected float getFootprintChance() {
        return 1;
    }

    public abstract void onEnable(TameableDragonEntity dragon);

    public abstract void onDisable(TameableDragonEntity dragon);

    public abstract void onDeath(TameableDragonEntity dragon);

    public SoundEvent getLivingSound(TameableDragonEntity dragon) {
        if (dragon.isBaby()) {
            return ModSounds.ENTITY_DRAGON_HATCHLING_GROWL;
        } else {
            if (dragon.getRandom().nextInt(3) == 0) {
                return ModSounds.ENTITY_DRAGON_GROWL;
            } else {
                return ModSounds.ENTITY_DRAGON_BREATHE;
            }
        }
    }

    public SoundEvent getRoarSoundEvent(TameableDragonEntity dragon) {
        return dragon.isBaby() ? ModSounds.HATCHLING_DRAGON_ROAR : ModSounds.DRAGON_ROAR;
    }

    public SoundEvent getHurtSound() {
        return SoundEvents.ENDER_DRAGON_HURT;
    }

    public SoundEvent getDeathSound() {
        return ModSounds.ENTITY_DRAGON_DEATH;
    }

    public SoundEvent getWingsSound() {
        return SoundEvents.ENDER_DRAGON_FLAP;
    }

    public SoundEvent getStepSound() {
        return ModSounds.ENTITY_DRAGON_STEP;
    }

    public SoundEvent getEatSound() {
        return SoundEvents.GENERIC_EAT;
    }

    public SoundEvent getAttackSound() {
        return SoundEvents.GENERIC_EAT;
    }

    public boolean canChangeBreed() {
        return true;
    }

    public boolean canUseBreathWeapon() {
        return true;
    }
    /*
    public void continueAndUpdateBreathing(World world, Vector3d origin, Vector3d endOfLook, BreathNode.Power power, EntityTameableDragon dragon) {
        dragon.getBreathHelper().getBreathAffectedArea().continueBreathing(world, origin, endOfLook, power, dragon);
        dragon.getBreathHelper().getBreathAffectedArea().updateTick(world);
    }

    public void spawnBreathParticles(World world, BreathNode.Power power, int tickCounter, Vec3d origin, Vec3d endOfLook, EntityTameableDragon dragon) {
        dragon.getBreathHelper().getEmitter().setBeamEndpoints(origin, endOfLook);
        dragon.getBreathHelper().getEmitter().spawnBreathParticles(world, power, tickCounter);
    }*/

    public double getMaxHealth() {
        return DragonMountsConfig.BASE_HEALTH.get();
    }

    public BasicParticleType getSneezeParticle() {
        return ParticleTypes.LARGE_SMOKE;
    }

    public BasicParticleType getEggParticle() {
        return ParticleTypes.MYCELIUM;
    }
}