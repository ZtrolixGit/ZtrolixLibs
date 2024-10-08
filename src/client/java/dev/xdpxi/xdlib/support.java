package dev.xdpxi.xdlib;

import dev.xdpxi.xdlib.api.loader;
import dev.xdpxi.xdlib.config.configHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

public class support extends Screen {
    private static final Logger LOGGER = LoggerFactory.getLogger("xdlib");
    private static boolean shownToast = false;
    private final Screen parent;

    public support(Text title, Screen parent) {
        super(title);
        this.parent = parent;
    }

    @Override
    public void close() {
        this.client.setScreen(this.parent);
    }

    @Override
    protected void init() {
        if (loader.isModLoaded("cloth-config")) {
            if (!configHelper.isTitlePopupsDisabled()) {
                showPopups();
            }
        } else {
            showPopups();
        }

        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 0;
        }).thenAcceptAsync(result -> {
            close();
        }, MinecraftClient.getInstance());
    }

    private void showPopups() {
        if (!shownToast) {
            shownToast = true;
            if (loader.isModLoaded("iris") || loader.isModLoaded("optifine")) {
                LOGGER.warn("[XDLib] - Does not support shaders!");
                this.client.getToastManager().add(
                        new SystemToast(SystemToast.Type.NARRATOR_TOGGLE, Text.of("XD's Library"), Text.of("Does not support shaders!"))
                );
            }
            if (!loader.isModLoaded("cloth-config")) {
                LOGGER.warn("[XDLib] - Recommends the use of 'cloth-config'");
                this.client.getToastManager().add(
                        new SystemToast(SystemToast.Type.NARRATOR_TOGGLE, Text.of("XD's Library"), Text.of("Recommends the use of 'cloth-config'"))
                );
            }
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (this.client == null || this.textRenderer == null) {
            return;
        }

        super.render(context, mouseX, mouseY, delta);
    }
}