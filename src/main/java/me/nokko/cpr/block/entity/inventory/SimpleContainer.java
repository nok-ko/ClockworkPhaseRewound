package me.nokko.cpr.block.entity.inventory;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface SimpleContainer extends Container {
    default NonNullList<ItemStack> getItems() {
        return NonNullList.withSize(3, ItemStack.EMPTY);
    }

    @Override
    default int getContainerSize() {
        return getItems().size();
    }

    @Override
    default boolean isEmpty() {
        return getItems().stream().allMatch(ItemStack::isEmpty);
    }

    @Override @NotNull
    default ItemStack getItem(int index) {
        return getItems().get(index);
    }

    @Override @NotNull
    default ItemStack removeItem(int index, int amount) {
        ItemStack result = ContainerHelper.removeItem(getItems(), index, amount);
        if (!result.isEmpty()) {
            setChanged();
        }
        return result;
    }

    @Override @NotNull
    default ItemStack removeItemNoUpdate(int index) {
        return ContainerHelper.takeItem(getItems(), index);
    }

    @Override
    default void setItem(int index, ItemStack stack) {
        getItems().set(index, stack);
        // Resize to fit:
        if (stack.getCount() > stack.getMaxStackSize()) {
            stack.setCount(stack.getMaxStackSize());
        }
    }

    @Override
    default void setChanged() {

    }

    @Override
    default boolean stillValid(Player player) {
        return true;
    }

    @Override
    default void clearContent() {
        getItems().clear();
    }
}
