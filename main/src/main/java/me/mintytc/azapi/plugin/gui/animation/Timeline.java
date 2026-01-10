package me.mintytc.azapi.plugin.gui.animation;

import java.util.ArrayList;
import java.util.List;

public class Timeline {

    private final List<Frame> frames = new ArrayList<>();

    public Timeline addFrame(Frame frame) {
        frames.add(frame);
        return this;
    }

    public List<Frame> getFrames() {
        return frames;
    }

    public boolean isEmpty() {
        return frames.isEmpty();
    }
}
