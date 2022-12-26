package me.nokko.cpr.item;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/*
 * From Lumaceon's original Clockwork Phase:
 * -General guide to making a base component-
 * The value of a mechanical component is judged by the sum of it's quality and speed.
 * Usually, the tension usage is a modified version of speed/quality, thus more quality will lower usage of tension.
 *
 * *Value of the component based on the sum of speed and quality*
 * -60 Low quality component such as stone or wood gears.
 * 60-80 Mid-tier component. Most of the more common metals fall into this category.
 * 80-120 High-tier component. Both steel and diamonds fall into this category.
 * 120+ God-tier component. Generally, only endgame mod items, such as Botania's Terrasteel, will reach this category.
 */

/**
 * Represents an item that modifies a clockwork tool.
 * Doesn't necessarily have to be attached to an actual Item.
 */
public interface ClockworkComponent extends ClockworkComponentLike {
    static String asString(ClockworkComponent component) {
        return component.getClockworkAttributes().toString();
    }
    static Map<ClockworkAttr, Integer> defaultAttributes() {
        var map = new EnumMap<ClockworkAttr, Integer>(ClockworkAttr.class);
        for (var attr : ClockworkAttr.values())
            map.put(attr, 0);
        return map;
    }

    /**
     * Get component values from an NBT-serialized ItemStack.
     * @param component
     * @return
     */
    static Map<ClockworkAttr, Integer> fromTag(CompoundTag component) {
        // The tag we receive is a serialized ItemStack. Let's reify it and see what it actually is:
        var virtualStack = ItemStack.of(component);
        if (ClockworkComponentLike.canMakeComponent(virtualStack)) {
            return ((ClockworkComponentLike) virtualStack.getItem()).makeComponent(virtualStack).getClockworkAttributes();
        }
        // If that stack isn't one of our ClockworkComponents, just return default attributes.
        return defaultAttributes();
    }

    static CompoundTag toTag(Map<ClockworkAttr, Integer> attrs) {
        var output = new CompoundTag();
        for (Map.Entry<ClockworkAttr, Integer> pair : attrs.entrySet()){
            output.putInt(pair.getKey().name(), pair.getValue());
        }
        return output;
    }

    /**
     * Helper function, called inside {@link Item#appendHoverText} to display the basic for this tool.
     * @param component - The ClockworkComponent summarizing this ClockworkTool's stats.
     * @param tooltipComponents - The tooltip components list that will be appended to.
     * @param flag - the TooltipFlag. If it is set to “advanced mode,” this method will leave a margin for the advanced
     *             tooltip below it.
     */
    static void addBasicTooltip(ClockworkComponent component, List<Component> tooltipComponents, TooltipFlag flag) {
        // Basic tooltip section, listing Speed/Quality/Memory.
        var summary = Component.empty();
        var clockworkAttributes = component.getClockworkAttributes();

        // Don't generate the basic section if the generated component has no attributes.
        if (clockworkAttributes.size() > 0) {
            int i;
            var attrs = clockworkAttributes.entrySet().iterator();
            for (i = 0; attrs.hasNext(); i++) {
                var attr = attrs.next();
                if (attr.getValue() < 1) continue;
                summary.append(
                        Component.translatable("cpr.tooltip_" + attr.getKey().name(), attr.getValue())
                                .withStyle(ChatFormatting.GOLD)
                );
                if ((i + 1) != clockworkAttributes.size()) {
                    summary.append(" "); // This could break some localization, but I'm honestly not sure.
                }
            }
            if (!summary.toString().equals("empty")) {
                tooltipComponents.add(summary);
                // Add a separator line for the advanced stuff further down if we're in advanced tooltip mode.
                if (flag.isAdvanced()) {
                    tooltipComponents.add(Component.empty());
                }
            }
        }
    }

    default Integer getAttribute(ClockworkAttr attr) {
        return this.getClockworkAttributes().getOrDefault(attr, 0);
    }

    @Override
    default @NotNull ClockworkComponent makeComponent() {
        return this;
    }

    Map<ClockworkAttr, Integer> getClockworkAttributes();

}
