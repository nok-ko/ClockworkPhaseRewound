package me.nokko.cpr.block;

import me.nokko.cpr.item.ClockworkTool;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class WindingBoxBlock extends Block {
    public WindingBoxBlock(Properties properties) {
        super(properties);
    }


    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        var usedStack = player.getItemInHand(hand);
        if (usedStack.getItem() instanceof ClockworkTool && usedStack.isDamaged()) {
            usedStack.setDamageValue(usedStack.getDamageValue() - 1);
            // also call tool.windUp() or something
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
