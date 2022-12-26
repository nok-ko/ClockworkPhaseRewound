package me.nokko.cpr.item;

import me.nokko.cpr.init.ModRegistries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.time.Clock;

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
        var itemName = BuiltInRegistries.ITEM.getKey(stack.getItem());
        if (ModRegistries.EXTRA_COMPONENTS.containsKey(itemName)) {
            return true;
        }

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
    @NotNull
    default ClockworkComponent makeComponent() {
        return ClockworkComponentImpl.EMPTY;
    }

    /**
     * Try to convert this to a proper component, but now using an ItemStack instance related to the ComponentLike.
     * This is overridden by tools, and other components that rely on NBT information for their attribute values.
     */
    @NotNull
    default ClockworkComponent makeComponent(ItemStack stack) {
        return this.makeComponent();
    }

    static ClockworkComponent fromAnyStack(ItemStack stack) {
        Item item = stack.getItem();
        if (item instanceof ClockworkComponent) {
            return (ClockworkComponent) item;
        }
        if (item instanceof ClockworkComponentLike) {
            return ((ClockworkComponentLike) item).makeComponent(stack);
        }

        var itemName = BuiltInRegistries.ITEM.getKey(item);
        if (ModRegistries.EXTRA_COMPONENTS.containsKey(itemName)) {
            return ModRegistries.EXTRA_COMPONENTS.get(itemName).makeComponent(stack);
        }

        if (stack.getOrCreateTag().contains("ClockworkComponent", Tag.TAG_COMPOUND)) {
            var tagAttrs = stack.getOrCreateTag().getCompound("ClockworkComponent");
            int speed = 0, quality = 0, memory = 0;
            if (tagAttrs.contains("SPEED", Tag.TAG_INT)) speed = tagAttrs.getInt("SPEED");
            if (tagAttrs.contains("QUALITY", Tag.TAG_INT)) quality = tagAttrs.getInt("QUALITY");
            if (tagAttrs.contains("MEMORY", Tag.TAG_INT)) memory = tagAttrs.getInt("MEMORY");
            return new ClockworkComponentImpl(speed, quality, memory);
        }
        else return new ClockworkComponentImpl(0, 0, 0);
    }
}
