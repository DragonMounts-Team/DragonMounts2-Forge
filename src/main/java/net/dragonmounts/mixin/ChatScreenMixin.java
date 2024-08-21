package net.dragonmounts.mixin;

import net.dragonmounts.client.gui.DMConfigScreen;
import net.dragonmounts.command.ConfigCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SleepInMultiplayerScreen;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin({ChatScreen.class, SleepInMultiplayerScreen.class})
public abstract class ChatScreenMixin extends Screen {
    @Inject(method = "keyPressed", at = @At(
            value = "INVOKE",
            target = "Ljava/lang/String;isEmpty()Z"
    ), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    public void handleClientCommand(int $, int $$, int __, CallbackInfoReturnable<Boolean> info, String text) {
        if (ConfigCommand.OPEN_CONFIG_SCREEN.equals(text)) {
            Minecraft minecraft = this.minecraft;
            //noinspection DataFlowIssue
            minecraft.gui.getChat().addRecentChat(text);
            minecraft.setScreen(new DMConfigScreen(minecraft, minecraft.screen));
            info.setReturnValue(true);
        }
    }

    private ChatScreenMixin(ITextComponent $) {super($);}
}
