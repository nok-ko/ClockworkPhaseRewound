package me.nokko.cpr.block.entity;

import me.nokko.cpr.block.entity.inventory.SimpleContainer;
import me.nokko.cpr.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static me.nokko.cpr.init.ModBlocks.CLOCKWORK_ASSEMBLY_TABLE_BLOCK_ENTITY;

public class ClockworkAssemblyTableBlockEntity extends BlockEntity implements SimpleContainer {
    private final NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);

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

    /**
     *
     * @return
     */
    @Override @Nullable
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override @NotNull
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }
}
