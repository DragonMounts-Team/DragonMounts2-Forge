package net.dragonmounts.util;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.dragonmounts.api.IDoubleEntryVisitor;
import net.minecraft.command.CommandSource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.GameRules;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.BiConsumer;

import static net.minecraft.util.math.MathHelper.clamp;

@ParametersAreNonnullByDefault
public class DoubleRuleValue extends GameRules.RuleValue<DoubleRuleValue> {
    private static final GameRules.IRule<DoubleRuleValue> HANDLER = (visitor, key, type) -> {
        if (visitor instanceof IDoubleEntryVisitor) {
            ((IDoubleEntryVisitor) visitor).dragonmounts$visitDouble(key, type);
        }
    };

    public static GameRules.RuleType<DoubleRuleValue> create(double init, BiConsumer<MinecraftServer, DoubleRuleValue> listener) {
        return new GameRules.RuleType<>(DoubleArgumentType::doubleArg, type -> new DoubleRuleValue(type, init), listener, HANDLER);
    }

    public static GameRules.RuleType<DoubleRuleValue> create(double init) {
        return create(init, (t, u) -> {});
    }

    public static GameRules.RuleType<DoubleRuleValue> create(double init, double min, double max, BiConsumer<MinecraftServer, DoubleRuleValue> listener) {
        return new GameRules.RuleType<>(DoubleArgumentType::doubleArg, type -> new Limited(type, init, min, max), listener, HANDLER);
    }

    public static GameRules.RuleType<DoubleRuleValue> create(double init, double min, double max) {
        return create(init, min, max, (t, u) -> {});
    }

    public final double defaultValue;
    double value;//package-private

    public DoubleRuleValue(GameRules.RuleType<DoubleRuleValue> type, double value) {
        super(type);
        this.defaultValue = this.value = value;//do NOT use setter!
    }

    public final double get() {
        return this.value;
    }

    public void set(double value) {
        this.value = value;
    }

    protected void updateFromArgument(CommandContext<CommandSource> context, String name) {
        this.set(DoubleArgumentType.getDouble(context, name));
    }

    @Nonnull
    public String serialize() {
        return Double.toString(this.value);
    }

    protected void deserialize(String value) {
        if (!value.isEmpty()) {
            try {
                this.set(Double.parseDouble(value));
            } catch (NumberFormatException numberformatexception) {
                this.set(this.defaultValue);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public boolean tryDeserialize(String value) {
        try {
            this.set(Double.parseDouble(value));
            return true;
        } catch (NumberFormatException numberformatexception) {
            return false;
        }
    }

    public int getCommandResult() {
        return (int) Math.round(this.value);
    }

    @Nonnull
    protected DoubleRuleValue getSelf() {
        return this;
    }

    @Nonnull
    protected DoubleRuleValue copy() {
        return new DoubleRuleValue(this.type, this.value);
    }

    @OnlyIn(Dist.CLIENT)
    public void setFrom(DoubleRuleValue other, @Nullable MinecraftServer server) {
        this.set(other.value);
        this.onChanged(server);
    }

    public static class Limited extends DoubleRuleValue {
        public final double min;
        public final double max;

        public Limited(GameRules.RuleType<DoubleRuleValue> type, double value, double min, double max) {
            super(type, clamp(value, min, max));
            this.min = min;
            this.max = max;
        }

        @Override
        public void set(double value) {
            this.value = clamp(value, this.min, this.max);
        }

        @Nonnull
        @Override
        protected DoubleRuleValue copy() {
            return new Limited(this.type, this.value, this.min, this.max);
        }
    }
}
