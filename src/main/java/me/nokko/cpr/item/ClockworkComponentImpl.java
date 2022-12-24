package me.nokko.cpr.item;

import me.nokko.cpr.data.ClockworkAttributeReloadable;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/**
 * Simple implementation of a ClockworkComponent. Note that it is immutable.
 * Here for cases where we need a quick implementation, mostly when a class can *be converted to* a
 * ClockworkComponent, but *isn't* a ClockworkComponent.
 */
public class ClockworkComponentImpl implements ClockworkComponent, ClockworkAttributeReloadable {
    public static ClockworkComponent EMPTY = new ClockworkComponentImpl();

    @NotNull
    private final Map<ClockworkAttr, Integer> attributes;

    public ClockworkComponentImpl() {
        attributes = ClockworkComponent.defaultAttributes();
    }

    public ClockworkComponentImpl(int speed, int quality, int memory) {
        attributes = new EnumMap<>(ClockworkAttr.class);
        attributes.put(ClockworkAttr.SPEED, speed);
        attributes.put(ClockworkAttr.QUALITY, quality);
        attributes.put(ClockworkAttr.MEMORY, memory);
    }

    public ClockworkComponentImpl(Map<ClockworkAttr, Integer> replacementMap) {
        attributes = Objects.requireNonNullElseGet(replacementMap, ClockworkComponent::defaultAttributes);
    }

    public void updateAttributes(Map<ClockworkAttr, Integer> newAttributes) {
        attributes.putAll(newAttributes);
    }

    @Override
    public Map<ClockworkAttr, Integer> getClockworkAttributes() {
        return attributes;
    }
}
