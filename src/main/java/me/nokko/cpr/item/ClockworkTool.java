package me.nokko.cpr.item;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

/**
 * A tool that has some derived {@link ClockworkAttr}s attached.
 */
public interface ClockworkTool extends ClockworkComponentLike {

    /**
     * Helper function, called inside {@link Item#appendHoverText} to display the “advanced tooltip” for this tool.
     * @param stack - The tool's ItemStack.
     * @param tooltipComponents - The tooltip components list that will be appended to.
     */
    static void addAdvancedTooltip(ItemStack stack, List<Component> tooltipComponents) {
        var components = stack.getOrCreateTag().getList("ClockworkComponents", Tag.TAG_COMPOUND);
        // Early exit if there are no components, so we don't write an empty "Contains:" line.
        if (components.isEmpty()) {
            tooltipComponents.add(Component.translatable("cpr.no_components").withStyle(ChatFormatting.DARK_GRAY));
        } else {
            // List all the components in the tooltip.
            tooltipComponents.add(Component.translatable("cpr.has_components").withStyle(ChatFormatting.GRAY));
            components.stream()
                    .map(
                            itemTag -> ItemStack.of((CompoundTag) itemTag).getHoverName().copy()
                    ).map(
                            itemName -> itemName.withStyle(ChatFormatting.DARK_PURPLE)
                    ).forEach(tooltipComponents::add);
        }
    }

    /**
     * Get the base clockwork attributes for this tool.
     * Intended to be overridden in cases like an upgraded ClockworkTool awarding a base +15 Quality bonus.
     */
    default Map<ClockworkAttr, Integer> getBaseAttributes() {
        return ClockworkComponent.defaultAttributes();
    }

    default Integer getAttribute(ItemStack stack, ClockworkAttr attr) {
        return getClockworkAttributes(stack).getOrDefault(attr, 0);
    };

    @Override
    default @NotNull ClockworkComponent makeComponent(ItemStack stack) {
        return new ClockworkComponentImpl(getClockworkAttributes(stack));
    }

    /**
     * Get a Map that we can turn into a ClockworkComponent.
     * The default implementation looks at the passed stack's NBT tags and sums any child ClockworkComponents'
     * attribute values.
     */
    default Map<ClockworkAttr, Integer> getClockworkAttributes(ItemStack stack) {
        var baseAttrs = getBaseAttributes();
        var components = stack.getOrCreateTag().getList("ClockworkComponents", Tag.TAG_COMPOUND);
        for (Tag component : components) {
            var componentAttrs = ClockworkComponent.fromTag((CompoundTag) component);
            for (ClockworkAttr key : ClockworkAttr.values()) {
                baseAttrs.compute(key, (oldKey, oldVal) -> (oldVal == null ? 0 : oldVal) + componentAttrs.get(key));
            }
        }
        return baseAttrs;
    }
}
