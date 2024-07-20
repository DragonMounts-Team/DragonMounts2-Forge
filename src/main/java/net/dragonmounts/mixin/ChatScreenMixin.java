package net.dragonmounts.mixin;

import net.dragonmounts.client.gui.DMConfigScreen;
import net.dragonmounts.command.ConfigCommand;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ChatScreen.class)
public abstract class ChatScreenMixin extends Screen {
    @Inject(method = "keyPressed", at = @At(
            value = "INVOKE",
            target = "Ljava/lang/String;isEmpty()Z"
    ), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    public void handleClientCommand(int $, int $$, int __, CallbackInfoReturnable<Boolean> info, String text) {
        if (text.equals(ConfigCommand.OPEN_CONFIG_SCREEN)) {
            //noinspection DataFlowIssue
            this.minecraft.gui.getChat().addRecentChat(text);
            this.minecraft.setScreen(new DMConfigScreen(this.minecraft, this.minecraft.screen));
            info.setReturnValue(true);
        }
    }

    private ChatScreenMixin(ITextComponent $) {super($);}
}
