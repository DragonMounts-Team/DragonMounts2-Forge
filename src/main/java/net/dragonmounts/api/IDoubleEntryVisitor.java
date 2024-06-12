package net.dragonmounts.api;

import net.dragonmounts.util.DoubleRuleValue;
import net.minecraft.world.GameRules;

public interface IDoubleEntryVisitor extends GameRules.IRuleEntryVisitor {
    default void dragonmounts$visitDouble(GameRules.RuleKey<DoubleRuleValue> key, GameRules.RuleType<DoubleRuleValue> type) {}
}
