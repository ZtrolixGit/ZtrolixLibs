package dev.xdpxi.xdlib.mixin.client;

import dev.xdpxi.xdlib.sodium.CustomOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OptionsGUIMixin.class)
public class OptionsGUIMixin {
    @Inject(at = @At("RETURN"))
    private void onInit(CallbackInfo ci) {
        CustomOptions.integrate();
    }
}