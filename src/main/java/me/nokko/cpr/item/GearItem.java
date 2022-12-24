package me.nokko.cpr.item;

import me.nokko.cpr.datapack.ClockworkAttributeReloadable;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public abstract class GearItem extends Item implements ClockworkComponent, ClockworkAttributeReloadable {
    protected Map<ClockworkAttr, Integer> clockworkAttributes;

    @Override
    public Map<ClockworkAttr, Integer> getClockworkAttributes() {
        return clockworkAttributes;
    }

    public GearItem(Properties properties) {
        super(properties);
        clockworkAttributes = ClockworkComponent.defaultAttributes();
    }

    public void updateAttributes(Map<ClockworkAttr, Integer> newAttributes) {
        clockworkAttributes.putAll(newAttributes);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag flag) {
        // List all the ClockworkAttrs on this item.
        // Pass in a fake TooltipFlag to prevent the blank space code in `addBasicTooltip` from triggering.
        // TODO: maybe remove the blank space code and inline it where relevant?
        ClockworkComponent.addBasicTooltip(makeComponent(stack), tooltipComponents, new TooltipFlag.Default(false, false));
        super.appendHoverText(stack, level, tooltipComponents, flag);
    }
}
