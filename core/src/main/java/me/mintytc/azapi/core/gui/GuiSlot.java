package me.mintytc.azapi.core.gui;

public final class GuiSlot {

    private final int index;

    private GuiSlot(int index) {
        this.index = index;
    }

    public static GuiSlot of(int index) {
        return new GuiSlot(index);
    }

    public int index() {
        return index;
    }
}
