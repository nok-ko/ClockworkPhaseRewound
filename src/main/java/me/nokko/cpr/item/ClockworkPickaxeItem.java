package me.nokko.cpr.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

//import static me.nokko.cpr.ClockworkPhaseRewound.LOGGER;

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
            context.getPlayer().sendSystemMessage(Component.literal(context.getItemInHand().getTag().toString()));
        }
        return super.useOn(context);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        var components = stack.getTag().getList("ClockworkComponents", Tag.TAG_COMPOUND);

        // Early exit if there are no components, so we don't write an empty "Contains:" line.
        if (components.isEmpty()) {
            tooltipComponents.add(Component.translatable("cpr.no_components").withStyle(ChatFormatting.DARK_GRAY));
            super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
            return;
        }

        // List all the components in the tooltip.
        tooltipComponents.add(Component.translatable("cpr.has_components").withStyle(ChatFormatting.GRAY));
        for (Tag component : components) {
            tooltipComponents.add(
                    ItemStack.of((CompoundTag) component).getHoverName().copy()
                            .withStyle(ChatFormatting.DARK_PURPLE)
            );
        }
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
    }
}
