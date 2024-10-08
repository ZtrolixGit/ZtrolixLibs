package dev.xdpxi.xdlib.mixin.client;

import dev.xdpxi.xdlib.XDsLibraryClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.message.MessageHandler;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(MessageHandler.class)
public class MessageHandlerMixin {
    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(method = "onGameMessage", at = @At(value = "INVOKE", shift = At.Shift.BEFORE, target = "Lnet/minecraft/client/gui/hud/InGameHud;setOverlayMessage(Lnet/minecraft/text/Text;Z)V"))
    private void interceptDangerousSleepMessage(Text message, boolean overlay, CallbackInfo ci) {
        if (client.player == null || client.world == null ||
                !(message.getContent() instanceof TranslatableTextContent && ((TranslatableTextContent) message.getContent()).getKey().equals("block.minecraft.bed.not_safe")))
            return;

        Vec3d vec3d = Vec3d.ofBottomCenter(client.player.getBlockPos());
        List<HostileEntity> list = client.world.getEntitiesByClass(
                HostileEntity.class,
                new Box(vec3d.getX() - 8.0D, vec3d.getY() - 5.0D, vec3d.getZ() - 8.0D, vec3d.getX() + 8.0D, vec3d.getY() + 5.0D,
                        vec3d.getZ() + 8.0D),
                hostileEntity -> hostileEntity.isAngryAt(client.player));

        if (!list.isEmpty()) {
            XDsLibraryClient.duration = 60;
            XDsLibraryClient.list = list;
        }
    }
}