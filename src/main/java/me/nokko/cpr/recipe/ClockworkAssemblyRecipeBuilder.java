package me.nokko.cpr.recipe;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Consumer;

public class ClockworkAssemblyRecipeBuilder {
    private ResourceLocation id;
    private Ingredient toolIngredient;
    private Ingredient itemIngredient;
    private Optional<Ingredient> maybeTransmuteItem = Optional.empty();

    public static ClockworkAssemblyRecipeBuilder withId(ResourceLocation id) {
        var builder = new ClockworkAssemblyRecipeBuilder();
        builder.id = id;
        return builder;
    }

    public ClockworkAssemblyRecipeBuilder setTool(Ingredient toolIngredient) {
        this.toolIngredient = toolIngredient;
        return this;
    }

    public ClockworkAssemblyRecipeBuilder setAdditionalItem(Ingredient additionalItem) {
        this.itemIngredient = additionalItem;
        return this;
    }

    public ClockworkAssemblyRecipeBuilder setTransmuteItem(Optional<Ingredient> maybeTransmuteItem) {
        this.maybeTransmuteItem = maybeTransmuteItem;
        return this;
    }

    public ClockworkAssemblyRecipe build() {
        return new ClockworkAssemblyRecipe(id, toolIngredient, itemIngredient, maybeTransmuteItem);
    }

    public void save(Consumer<FinishedRecipe> consumer) {
        consumer.accept(new Result());
    }

    private class Result implements FinishedRecipe {
        @Override
        public void serializeRecipeData(JsonObject json) {
            ClockworkAssemblyRecipeSerializer.INSTANCE.toJson(build(), json);
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return ClockworkAssemblyRecipeSerializer.INSTANCE;
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }
}
