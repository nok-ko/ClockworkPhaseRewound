package me.nokko.cpr.item;

import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

/**
 * Something that might be convertible to a {@link ClockworkComponent}.
 *
 * @apiNote This interface exists because we can't attach {@link ClockworkComponent} to our {@link ClockworkTool} items
 *          in any useful way. (We'd need to read data from an ItemStack to work out what its component attribute values
 *          should be.)
 */
public interface ClockworkComponentLike {
    /**
     * Determines whether a given ItemStack associated with this ComponentLike can be converted to a ClockworkComponent instance.
     */
    static boolean canMakeComponent(ItemStack stack) {
        // If it's an instance of the proper ClockworkComponent class, great, we're done here.
        // Note that instances of ClockworkComponentLike aren't always valid ClockworkComponents.
        // For example, an empty tool item has no ComponentAttrs.
        if (stack.getItem() instanceof ClockworkComponent) {
            return true;
        }

        // TODO: Maybe check a custom mod registry.

        // Onto checking the ItemStack's tags. Early-exit if there are no tags.
        var tag = stack.getTag();
        if (tag == null) {
            return false;
        }

        // Contains a "ClockworkComponents" key that maps to a ListTag.
        // This case covers tools and other object that may have other ComponentLikes within.
        var componentsTag = tag.get("ClockworkComponents");
        if (componentsTag != null && componentsTag.getId() == Tag.TAG_LIST) {
            return true;
        }
        return false;
    }

    static String asString(ClockworkComponentLike component, ItemStack stack) {
        var output = new StringBuilder();
        output.append("{valid=");
        if (ClockworkComponentLike.canMakeComponent(stack)) {
            output.append("true, attrs=");
            output.append(component.makeComponent(stack).getClockworkAttributes().toString());
            output.append('}');
        } else {
            output.append("false}");
        }
        return output.toString();
    }

    /**
     * Try to convert this ComponentLike to a proper component.
     */
    default ClockworkComponent makeComponent() {
        return ClockworkComponentImpl.EMPTY;
    }

    /**
     * Try to convert this to a proper component, but now using an ItemStack instance related to the ComponentLike.
     * This is overridden by tools, and other components that rely on NBT information for their attribute values.
     */
    default ClockworkComponent makeComponent(ItemStack stack) {
        return this.makeComponent();
    }
}
