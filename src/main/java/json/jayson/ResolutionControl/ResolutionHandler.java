package json.jayson.ResolutionControl;

import json.jayson.util.LMConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.WindowFramebuffer;
import net.minecraft.client.util.Window;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;


/*
CODE USED FROM:
https://github.com/UltimateBoomer/Resolution-Control/tree/1.20.2?tab=License-2-ov-file
IF YOU ARE THE AUTHOR(S) OF THIS MOD, PLEASE GIVE US A PM, WE WILL REMOVE IT THEN! :)
 */
@Environment(EnvType.CLIENT)
public class ResolutionHandler {

    public ResolutionHandler() {
        updateFramebufferSize();
    }

    private boolean shouldScale = false;

    @Nullable
    private Framebuffer framebuffer;
    @Nullable
    private Framebuffer clientFramebuffer;
    private final MinecraftClient client = MinecraftClient.getInstance();


    private Window getWindow() {
        return client.getWindow();
    }

    private Set<Framebuffer> minecraftFramebuffers;


    private void setClientFramebuffer(Framebuffer framebuffer) {
        client.framebuffer = framebuffer;
    }

    public void setShouldScale(boolean shouldScale) {
        if (shouldScale == this.shouldScale) return;

        Window window = getWindow();
        if (framebuffer == null) {
            this.shouldScale = true;
            framebuffer = new WindowFramebuffer(
                    window.getFramebufferWidth(),
                    window.getFramebufferHeight()
            );
        }
        this.shouldScale = shouldScale;
        client.getProfiler().swap(shouldScale ? "startScaling" : "finishScaling");
        if (shouldScale) {
            clientFramebuffer = client.getFramebuffer();
            setClientFramebuffer(framebuffer);
            framebuffer.beginWrite(true);
        } else {
            setClientFramebuffer(clientFramebuffer);
            client.getFramebuffer().beginWrite(true);
                framebuffer.draw(
                        window.getFramebufferWidth(),
                        window.getFramebufferHeight()
                );
        }
        client.getProfiler().swap("level");
    }

    public void onResolutionChanged() {
        if (getWindow() == null)
            return;
        updateFramebufferSize();
    }

    public void updateFramebufferSize() {
        if (framebuffer == null)
            return;

        resize(framebuffer);
        resize(client.worldRenderer.getEntityOutlinesFramebuffer());
    }
    public void resize(@Nullable Framebuffer framebuffer) {
        if (framebuffer == null) return;

        boolean prev = shouldScale;
        shouldScale = true;
            framebuffer.resize(
                    getWindow().getFramebufferWidth(),
                    getWindow().getFramebufferHeight(),
                    MinecraftClient.IS_SYSTEM_MAC
            );
        shouldScale = prev;
    }

    public void initMinecraftFramebuffers() {
        if (minecraftFramebuffers != null) {
            minecraftFramebuffers.clear();
        } else {
            minecraftFramebuffers = new HashSet<>();
        }
        minecraftFramebuffers.add(client.worldRenderer.getEntityOutlinesFramebuffer());
        minecraftFramebuffers.add(client.worldRenderer.getTranslucentFramebuffer());
        minecraftFramebuffers.add(client.worldRenderer.getEntityFramebuffer());
        minecraftFramebuffers.add(client.worldRenderer.getParticlesFramebuffer());
        minecraftFramebuffers.add(client.worldRenderer.getWeatherFramebuffer());
        minecraftFramebuffers.add(client.worldRenderer.getCloudsFramebuffer());
        minecraftFramebuffers.remove(null);
    }

    public void resizeMinecraftFramebuffers() {
        initMinecraftFramebuffers();
        minecraftFramebuffers.forEach(this::resize);
    }

    public double getCurrentScaleFactor() {
        return shouldScale ? LMConfig.SCALE_FACTOR : 1;
    }
}
