package me.nokko.cpr.item;

import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;

public class ClockworkPickaxeItem extends PickaxeItem implements ClockworkTool {
    public ClockworkPickaxeItem(Tier tier, int damage, float speed, Properties properties) {
        super(tier, damage, speed, properties);
    }
}
