package net.dragonmounts.mixin;

import net.dragonmounts.api.IDoubleEntryVisitor;
import net.dragonmounts.client.gui.DoubleRuleEntry;
import net.dragonmounts.util.DoubleRuleValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.EditGamerulesScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(targets = "net.minecraft.client.gui.screen.EditGamerulesScreen$GamerulesList$1")
public abstract class RuleEntryVisitorMixin implements IDoubleEntryVisitor {
    @Shadow
    private <T extends GameRules.RuleValue<T>> void addEntry(GameRules.RuleKey<T> key, EditGamerulesScreen.IRuleEntry<T> entry) {}

    @Override
    public void dragonmounts$visitDouble(GameRules.RuleKey<DoubleRuleValue> key, GameRules.RuleType<DoubleRuleValue> $) {
        Screen screen = Minecraft.getInstance().screen;
        if (screen instanceof EditGamerulesScreen) {
            this.addEntry(key, (label, tooltip, description, rule) -> new DoubleRuleEntry((EditGamerulesScreen) screen, label, tooltip, description, rule));
        }
    }

    private RuleEntryVisitorMixin() {}
}
