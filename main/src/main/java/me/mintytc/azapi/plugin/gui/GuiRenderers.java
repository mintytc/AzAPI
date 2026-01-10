package me.mintytc.azapi.plugin.gui;

import me.mintytc.azapi.core.ServerVersion;
import me.mintytc.azapi.core.gui.GuiRenderer;
import me.mintytc.azapi.core.gui.GuiType;
import me.mintytc.azapi.gui.ChestGuiRenderer_1_13;
import me.mintytc.azapi.gui.ChestGuiRenderer_1_8;

import java.util.EnumMap;
import java.util.Map;

public final class GuiRenderers {

    private static final Map<GuiType, GuiRenderer> RENDERERS = new EnumMap<>(GuiType.class);

    static {
        if (ServerVersion.atLeast(13, 0)) {
            RENDERERS.put(GuiType.CHEST, new ChestGuiRenderer_1_13());
        } else {
            RENDERERS.put(GuiType.CHEST, new ChestGuiRenderer_1_8());
        }
    }

    public static GuiRenderer get(GuiType type) {
        return RENDERERS.get(type);
    }
}
