package me.nokko.cpr.recipe;

import me.nokko.cpr.block.entity.ClockworkAssemblyTableBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class ClockworkAssemblyRecipe implements Recipe<ClockworkAssemblyTableBlockEntity> {
    private ResourceLocation id;
    private Optional<Ingredient> transmuteItem;

    public Ingredient getTool() {
        return tool;
    }

    public Ingredient getAdditionalItem() {
        return additionalItem;
    }

    private Ingredient tool;
    private Ingredient additionalItem;

    public ClockworkAssemblyRecipe(ResourceLocation id, Ingredient tool, Ingredient additionalItem, Optional<Ingredient> transmuteItem) {
        this.id = id;
        this.tool = tool;
        this.additionalItem = additionalItem;
        this.transmuteItem = transmuteItem;
    }

    @Override
    public boolean matches(ClockworkAssemblyTableBlockEntity container, Level level) {
        boolean hasTool = container.hasAnyMatching(stack -> tool.test(stack));
        boolean hasAddition = container.hasAnyMatching(stack -> additionalItem.test(stack));
        return hasTool && hasAddition;
    }

    @Override @NotNull
    public ItemStack assemble(ClockworkAssemblyTableBlockEntity container) {
        List<ItemStack> toolStacks = container.getItems().stream().filter(tool).toList();
        List<ItemStack> additionStacks = container.getItems().stream().filter(additionalItem).toList();

        ItemStack baseTool = toolStacks.get(0).copy();

        // Transmute the initial item into the new thing specified in the recipe, if any.
        if (transmuteItem.isPresent()) {
            // Will probably break somewhat if someone stuffs an entire tag into the transmute ingredient,
            // but that's kind of an edge case. (What would you expect to happen?)
            var oldBaseTool = baseTool;
            baseTool = transmuteItem.get().getItems()[0].copy();

            // Copy NBT, hopefully.
            baseTool.setTag(oldBaseTool.getOrCreateTag());
        }

        ListTag clockworkComponents;
        if (baseTool.getTag() != null) {
            clockworkComponents = baseTool.getTag().getList("ClockworkComponents", Tag.TAG_COMPOUND);
        } else {
            clockworkComponents = new ListTag();
        }

        for (var additionStack : additionStacks) {
            CompoundTag output = new CompoundTag();
            additionStack.save(output);
            clockworkComponents.add(output);
        }

        baseTool.addTagElement("ClockworkComponents", clockworkComponents);
        return baseTool;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override @NotNull
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    @Override @NotNull
    public ResourceLocation getId() {
        return this.id;
    }

    @Override @NotNull
    public RecipeSerializer<?> getSerializer() {
        return ClockworkAssemblyRecipeSerializer.INSTANCE;
    }

    public Optional<Ingredient> getTransmuteItem() {
        return transmuteItem;
    }

    // Singleton type
    public static class Type implements RecipeType<ClockworkAssemblyRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "clockwork_assembly";
    }

    @Override @NotNull
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }
}
