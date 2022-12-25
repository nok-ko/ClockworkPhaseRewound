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
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
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
//        if (level.isClientSide) {
//            return InteractionResult.SUCCESS;
//        }

        if (!(level.getBlockEntity(pos) instanceof ClockworkAssemblyTableBlockEntity assemblyTable)) {
            return InteractionResult.PASS;
        }

        var heldItem = player.getItemInHand(hand);

        if (!heldItem.isEmpty() && assemblyTable.canAccept(heldItem)) {
            assemblyTable.acceptItem(heldItem);
            player.getItemInHand(hand).setCount(0);
        } else {
            if (player.isCrouching()) {
                assemblyTable.tryCraft(player);
            } else {
                assemblyTable.retrieveItem(player);
            }
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

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.block();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.or(
                Shapes.block(),
                Shapes.create(
                        -0.375, 1, -0.375,
                        1.375, 1.1, 1.375
                )
        );
    }

    @Override
    public boolean hasDynamicShape() {
        return super.hasDynamicShape();
    }
}
