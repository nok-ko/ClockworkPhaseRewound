package me.nokko.cpr.data;

import me.nokko.cpr.item.ClockworkAttr;
import net.minecraft.world.item.Item;

import java.util.Map;

/**
 * Indicates that the implementing class can accept a modified Map of {@link ClockworkAttr}s and update itself
 * accordingly. Currently implemented on {@link Item}s exclusively.
 */
public interface ClockworkAttributeReloadable {
    /**
     * Update the default {@link ClockworkAttr} values to new values.
     * Called by ReloadListeners to update the gear item's default {@link ClockworkAttr} values according to data pack
     * values.
     * <p>
     * Also can be called by subclasses for other reasons, but that's probably not very useful given that it's a global
     * change in the base attributes – maybe stick to data packs.
     */
    void updateAttributes(Map<ClockworkAttr, Integer> newAttributes);
}
