package me.mintytc.azapi.plugin.gui.animation;

import org.bukkit.inventory.Inventory;

public class GuiAnimator implements GuiAnimation {

    private final Inventory inventory;
    private final Timeline timeline;

    private int frameIndex = 0;
    private int tickCounter = 0;

    public GuiAnimator(Inventory inventory, Timeline timeline) {
        this.inventory = inventory;
        this.timeline = timeline;
    }

    @Override
    public void tick() {
        if (isFinished()) return;

        Frame frame = timeline.getFrames().get(frameIndex);
        tickCounter++;

        if (tickCounter >= frame.getDurationTicks()) {
            frame.getItems().forEach(inventory::setItem);
            tickCounter = 0;
            frameIndex++;
        }
    }

    @Override
    public boolean isFinished() {
        return frameIndex >= timeline.getFrames().size();
    }

    @Override
    public void reset() {
        frameIndex = 0;
        tickCounter = 0;
    }
}
