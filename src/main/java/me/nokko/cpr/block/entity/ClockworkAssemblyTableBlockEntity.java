package me.nokko.cpr.block.entity;

import me.nokko.cpr.block.entity.inventory.SimpleContainer;
import me.nokko.cpr.recipe.ClockworkAssemblyRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static me.nokko.cpr.ClockworkPhaseRewound.LOGGER;
import static me.nokko.cpr.init.ModBlocks.CLOCKWORK_ASSEMBLY_TABLE_BLOCK_ENTITY;
import static me.nokko.cpr.init.ModItemTags.*;

public class ClockworkAssemblyTableBlockEntity extends BlockEntity implements SimpleContainer {
    public static int SLOTS = 2;
    public static int TOOL_SLOT = 0;
    public static int ADDITIONAL_SLOT = 1;
    private final NonNullList<ItemStack> items = NonNullList.withSize(SLOTS, ItemStack.EMPTY);

    @Override
    public NonNullList<ItemStack> getItems() {
        return items;
    }

    /**
     * A VoxelShape that's like a big plate, slightly above the Assembly Table block and bigger than it.
     * When a player's gaze rests upon this shape, they will see placement hints for gear items.
     */
    private VoxelShape gearPlacementShape;

    public VoxelShape getGearPlacementShape() {
        if (gearPlacementShape == null) {
            return this.getBlockState().getShape(this.getLevel(), this.getBlockPos());
        }
        return gearPlacementShape;
    }

    public ClockworkAssemblyTableBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(CLOCKWORK_ASSEMBLY_TABLE_BLOCK_ENTITY, blockPos, blockState);
        // Maybe put the shape in the BlockState?
        gearPlacementShape =
            Shapes.create(
                -0.3125, 1, -0.3125,
                1.3125, 1.1, 1.3125
            );
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        ContainerHelper.loadAllItems(tag, items);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        ContainerHelper.saveAllItems(tag, items);
        super.saveAdditional(tag);
    }

    @Override @Nullable
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override @NotNull
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    public boolean isInventoryFull() {
        return items.stream().noneMatch(ItemStack::isEmpty);
    }

    public boolean canAccept(ItemStack stack) {
        if (items.get(TOOL_SLOT).isEmpty() && (stack.is(FRAMEWORK_ACCEPTORS) || stack.is(CPR_GEAR_ACCEPTORS))) {
            // Things that take frameworks or gears can go in the main slot.
            return true;
        } else if (items.get(ADDITIONAL_SLOT).isEmpty() && (stack.is(FRAMEWORKS) || stack.is(CPR_GEARS))) {
            // Other things go here in the additional slot.
            return true;
        } else {
            return false;
        }
    }


    /**
     * Player has just right-clicked the BE with an item.
     * @param heldItem
     */
    public void acceptItem(ItemStack heldItem) {
        if (heldItem.is(FRAMEWORK_ACCEPTORS) || heldItem.is(CPR_GEAR_ACCEPTORS)) {
            if (items.get(TOOL_SLOT).isEmpty()) {
                this.setItem(TOOL_SLOT, heldItem.copy());
            } else {
                // Try not to eat items, even if we can't take them.
                if (!level.isClientSide) {
                    level.addFreshEntity(
                            new ItemEntity(level, worldPosition.getX(), worldPosition.getY() + 1, worldPosition.getZ(), heldItem.copy())
                    );
                }
            }
        } else if (heldItem.is(FRAMEWORKS) || heldItem.is(CPR_GEARS)) {
            if (items.get(ADDITIONAL_SLOT).isEmpty()) {
                this.setItem(ADDITIONAL_SLOT, heldItem.copy());
            } else {
                if (!level.isClientSide) {
                    level.addFreshEntity(
                            new ItemEntity(level, worldPosition.getX(), worldPosition.getY() + 1, worldPosition.getZ(), heldItem.copy())
                    );
                }
            }
        }
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        LOGGER.info(items.toString());
    }

    /**
     * Try to craft something with the ingredients currently in the assembly table.
     * Give the result to the passed-in player.
     */
    public void tryCraft(Player crafter) {
        Optional<ClockworkAssemblyRecipe> canCraft = level.getRecipeManager()
                .getRecipeFor(ClockworkAssemblyRecipe.Type.INSTANCE, this, level);

        if (canCraft.isPresent()) {
            crafter.getInventory().add(canCraft.get().assemble(this));
            this.clearContent();
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
            level.playLocalSound(getBlockPos(), SoundEvents.NOTE_BLOCK_BELL.value(), SoundSource.BLOCKS, 1.0F, 1.0F, false);
        } else {
            level.playLocalSound(getBlockPos(), SoundEvents.NOTE_BLOCK_SNARE.value(), SoundSource.BLOCKS, 1.0F, 1.0F, false);
        }
    }

    /**
     * Give one of our items back to a player.
     */
    public void retrieveItem(Player player) {
        NonNullList<ItemStack> itemStacks = this.getItems();
        for (int i = 0; i < SLOTS; i++) {
            ItemStack item = itemStacks.get(i);
            if (!item.isEmpty()) {
                player.getInventory().add(item.copy());
                this.getItems().set(i, ItemStack.EMPTY);
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
                break;
            }
        }
    }
}
