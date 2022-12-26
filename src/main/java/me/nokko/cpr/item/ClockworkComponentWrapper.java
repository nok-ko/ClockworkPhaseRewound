package me.nokko.cpr.item;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ClockworkComponentWrapper implements ClockworkComponentLike {
    private final ClockworkComponentLike component;

    public ClockworkComponentWrapper(ClockworkComponentLike ccl) {
        if (ccl != null) {
            this.component = ccl;
        } else {
            this.component = ClockworkComponentImpl.EMPTY;
        }
    }

    @NotNull
    public ClockworkComponent makeComponent(ItemStack stack) {
        return component.makeComponent(stack);
    }
}
