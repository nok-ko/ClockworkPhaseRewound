package me.nokko.cpr.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ClockworkPickaxeItem extends PickaxeItem implements ClockworkTool {
    public ClockworkPickaxeItem(Tier tier, int damage, float speed, Properties properties) {
        super(tier, damage, speed, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getPlayer() == null) {
            return super.useOn(context);
        }

        if (context.getPlayer().isCrouching()) {
            context.getPlayer().sendSystemMessage(
                    Component.literal(
                            context.getPlayer().getItemInHand(InteractionHand.OFF_HAND).getOrCreateTag().toString()
                    )
            );
            var stack = context.getPlayer().getItemInHand(InteractionHand.OFF_HAND);
            var component = makeComponent(stack);
            context.getPlayer().sendSystemMessage(
                    Component.literal(
                            ClockworkComponentLike.asString(component, stack)
                    )
            );
        }
        return super.useOn(context);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag flag) {
        // Basic tooltip section, listing the total speed, quality, and memory.
        ClockworkComponent.addBasicTooltip(makeComponent(stack), tooltipComponents, flag);

        // Advanced tooltip section, listing all the components for debug purposes.
        if (flag.isAdvanced()) {
            ClockworkTool.addAdvancedTooltip(stack, tooltipComponents);
        }

        super.appendHoverText(stack, level, tooltipComponents, flag);
    }
}
