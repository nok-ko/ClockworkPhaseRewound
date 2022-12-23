package me.nokko.cpr.block;

import me.nokko.cpr.block.entity.ClockworkAssemblyTableBlockEntity;
import me.nokko.cpr.block.entity.inventory.SimpleContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class ClockworkAssemblyTableBlock extends Block implements EntityBlock {
    public ClockworkAssemblyTableBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ClockworkAssemblyTableBlockEntity(pos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) {
            if (player.getItemInHand(hand).isEmpty()) {
                player.sendSystemMessage(Component.literal(
                        ((SimpleContainer) level.getBlockEntity(pos)).getItems().toString()
                ));
            }
            return InteractionResult.SUCCESS;
        }
        Container blockEntity = (Container) level.getBlockEntity(pos);
        if (!player.getItemInHand(hand).isEmpty()) {
            if (blockEntity.getItem(0).isEmpty()) {
                blockEntity.setItem(0, player.getItemInHand(hand).copy());
                player.getItemInHand(hand).setCount(0);
            }
            level.sendBlockUpdated(pos, state, state, Block.UPDATE_ALL);
        } else {
            player.sendSystemMessage(Component.literal(
                    ((SimpleContainer) level.getBlockEntity(pos)).getItems().toString()
            ));
        }
        return InteractionResult.SUCCESS;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.is(newState.getBlock())) {
            return;
        }
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof Container) {
            Containers.dropContents(level, pos, (Container) blockEntity);
            level.updateNeighbourForOutputSignal(pos, this);
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }
}
