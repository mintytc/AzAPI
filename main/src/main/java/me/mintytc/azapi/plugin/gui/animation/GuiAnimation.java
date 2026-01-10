package me.mintytc.azapi.plugin.gui.animation;

public interface GuiAnimation {
    void tick();

    boolean isFinished();

    void reset();
}
