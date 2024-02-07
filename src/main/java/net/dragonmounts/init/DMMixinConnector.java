package net.dragonmounts.init;

import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.connect.IMixinConnector;

public final class DMMixinConnector implements IMixinConnector {
    @Override
    public void connect() {
        Mixins.addConfigurations("dragonmounts.mixins.json");
    }
}
