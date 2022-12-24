package me.nokko.cpr.item;

import me.nokko.cpr.data.ClockworkAttributeReloadable;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

import static me.nokko.cpr.item.ClockworkAttr.*;

public abstract class GearItem extends Item implements ClockworkComponent, ClockworkAttributeReloadable {
    protected Map<ClockworkAttr, Integer> clockworkAttributes;

    @Override
    public Map<ClockworkAttr, Integer> getClockworkAttributes() {
        return clockworkAttributes;
    }

    public GearItem(int defaultSpeed, int defaultQuality, Properties properties) {
        super(properties);
        clockworkAttributes = ClockworkComponent.defaultAttributes();
        clockworkAttributes.put(SPEED, defaultSpeed);
        clockworkAttributes.put(QUALITY, defaultQuality);
    }

    public void updateAttributes(Map<ClockworkAttr, Integer> newAttributes) {
        clockworkAttributes.putAll(newAttributes);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag flag) {
        // List all the ClockworkAttrs on this item.
        ClockworkComponent.addBasicTooltip(makeComponent(stack), tooltipComponents, new TooltipFlag.Default(false, false));
        super.appendHoverText(stack, level, tooltipComponents, flag);
    }
}
