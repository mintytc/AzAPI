package me.mintytc.azapi.core.gui;

public enum GuiType {

    CHEST(54),
    DROPPER(9),
    HOPPER(5),
    ANVIL(3),
    CRAFTING(4),
    CARTOGRAPHY(3);

    private final int defaultSize;

    GuiType(int defaultSize) {
        this.defaultSize = defaultSize;
    }

    public int defaultSize() {
        return defaultSize;
    }
}
