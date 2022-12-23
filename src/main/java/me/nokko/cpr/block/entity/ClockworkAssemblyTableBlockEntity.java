package me.nokko.cpr.block.entity;

import me.nokko.cpr.block.entity.inventory.SimpleContainer;
import me.nokko.cpr.item.ClockworkTool;
import me.nokko.cpr.recipe.ClockworkAssemblyRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static me.nokko.cpr.init.ModBlocks.CLOCKWORK_ASSEMBLY_TABLE_BLOCK_ENTITY;
import static me.nokko.cpr.ClockworkPhaseRewound.LOGGER;

public class ClockworkAssemblyTableBlockEntity extends BlockEntity implements SimpleContainer {
    public static int SLOTS = 2;
    public static int TOOL_SLOT = 0;
    public static int ADDITIONAL_SLOT = 1;
    private final NonNullList<ItemStack> items = NonNullList.withSize(SLOTS, ItemStack.EMPTY);

    @Override
    public NonNullList<ItemStack> getItems() {
        return items;
    }

    public ClockworkAssemblyTableBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(CLOCKWORK_ASSEMBLY_TABLE_BLOCK_ENTITY, blockPos, blockState);
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
        if (stack.getItem() instanceof ClockworkTool) {
            return items.get(TOOL_SLOT).isEmpty();
        } else {
            return items.get(ADDITIONAL_SLOT).isEmpty();
        }
    }


    /**
     * Player has just right-clicked the BE with an item.
     * @param heldItem
     */
    public void acceptItem(ItemStack heldItem) {
        if (heldItem.getItem() instanceof ClockworkTool) {
            if (items.get(TOOL_SLOT).isEmpty()) {
                this.setItem(TOOL_SLOT, heldItem.copy());
            }
        } else if (items.get(ADDITIONAL_SLOT).isEmpty()) {
            this.setItem(ADDITIONAL_SLOT, heldItem.copy());
        }
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        LOGGER.info(items.toString());
    }

    /**
     * Try to craft something with the ingredients currently in the assembly table.
     * Give the result to the passed-in player.
     */
    public void tryCraft(Player crafter) {
        crafter.sendSystemMessage(Component.literal(
                this.getItems().toString()
        ));
        Optional<ClockworkAssemblyRecipe> canCraft = level.getRecipeManager()
                .getRecipeFor(ClockworkAssemblyRecipe.Type.INSTANCE, this, level);

        if (canCraft.isPresent()) {
            crafter.sendSystemMessage(Component.literal("Can craft!"));
            crafter.getInventory().add(canCraft.get().assemble(this));
            this.clearContent();
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        } else {
            crafter.sendSystemMessage(Component.literal("No match!"));
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
