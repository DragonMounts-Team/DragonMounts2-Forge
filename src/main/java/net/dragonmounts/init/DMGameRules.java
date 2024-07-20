package net.dragonmounts.init;

import net.dragonmounts.util.DoubleRuleValue;
import net.minecraft.world.GameRules;

import static net.minecraft.world.GameRules.register;

public class DMGameRules {
    public static final double DEFAULT_DRAGON_BASE_HEALTH = 90D;
    public static final double DEFAULT_DRAGON_BASE_DAMAGE = 12D;
    public static final double DEFAULT_DRAGON_BASE_ARMOR = 8D;
    public static final GameRules.RuleKey<DoubleRuleValue> DRAGON_BASE_HEALTH = register("dragonmounts.dragonBaseHealth", GameRules.Category.MOBS, DoubleRuleValue.create(DEFAULT_DRAGON_BASE_HEALTH, 1D, 1024D));
    public static final GameRules.RuleKey<DoubleRuleValue> DRAGON_BASE_DAMAGE = register("dragonmounts.dragonBaseDamage", GameRules.Category.MOBS, DoubleRuleValue.create(DEFAULT_DRAGON_BASE_DAMAGE, 0D, 2048D));
    public static final GameRules.RuleKey<DoubleRuleValue> DRAGON_BASE_ARMOR = register("dragonmounts.dragonBaseArmor", GameRules.Category.MOBS, DoubleRuleValue.create(DEFAULT_DRAGON_BASE_ARMOR, 0D, 30D));
    public static final GameRules.RuleKey<GameRules.BooleanValue> IS_EGG_PUSHABLE = register("dragonmounts.isEggPushable", GameRules.Category.MISC, GameRules.BooleanValue.create(false));
    public static final GameRules.RuleKey<GameRules.BooleanValue> IS_EGG_OVERRIDDEN = register("dragonmounts.isEggOverridden", GameRules.Category.MISC, GameRules.BooleanValue.create(true));

    public static void init() {}
}
