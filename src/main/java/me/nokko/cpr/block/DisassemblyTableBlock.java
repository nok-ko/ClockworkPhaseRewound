package me.nokko.cpr.block;

import me.nokko.cpr.item.ClockworkTool;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.Tag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DisassemblyTableBlock extends Block {
    public DisassemblyTableBlock(Properties properties) {
        super(properties);
    }

    @Override @NotNull
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        var stack = player.getItemInHand(hand);
        if (    stack.getItem() instanceof ClockworkTool
            &&  stack.getTag() != null
            &&  stack.getTag().get("ClockworkComponents") != null)
        {
            var components = stack.getTag().getList("ClockworkComponents", Tag.TAG_COMPOUND);
            for (Tag component : components) {
               if (component.getId() == Tag.TAG_COMPOUND) {
                   player.getInventory().placeItemBackInInventory(
                           ItemStack.of((CompoundTag) component)
                   );
               }
            }
            // Clear the list of components on this item.
            stack.getTag().put("ClockworkComponents", new ListTag());
        }
        return super.use(state, level, pos, player, hand, hit);
    }
}
