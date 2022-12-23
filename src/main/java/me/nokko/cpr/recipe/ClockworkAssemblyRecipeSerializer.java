package me.nokko.cpr.recipe;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import me.nokko.cpr.ClockworkPhaseRewound;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import static me.nokko.cpr.ClockworkPhaseRewound.LOGGER;

public class ClockworkAssemblyRecipeSerializer implements RecipeSerializer<ClockworkAssemblyRecipe> {

    private ClockworkAssemblyRecipeSerializer () { }

    public static final ClockworkAssemblyRecipeSerializer INSTANCE = new ClockworkAssemblyRecipeSerializer();
    public static final ResourceLocation ID = new ResourceLocation(ClockworkPhaseRewound.MOD_ID, "clockwork_assembly");

    @Override
    public ClockworkAssemblyRecipe fromJson(ResourceLocation recipeId, JsonObject serializedRecipe) {
        var recipeJson = new Gson().fromJson(serializedRecipe, ClockworkAssemblyRecipeFormat.class);

        if (recipeJson.additionalItem == null || recipeJson.tool == null) {
            throw new JsonSyntaxException("A required attribute is missing from the recipe json.");
        }

        Ingredient tool = Ingredient.fromJson(recipeJson.tool);
        Ingredient additionalItem = Ingredient.fromJson(recipeJson.additionalItem);


        LOGGER.info("Registered recipe: " + recipeId);
        return new ClockworkAssemblyRecipe(recipeId, tool, additionalItem);
    }

    @Override
    public ClockworkAssemblyRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        Ingredient tool = Ingredient.fromNetwork(buffer);
        Ingredient additionalItem = Ingredient.fromNetwork(buffer);
        return new ClockworkAssemblyRecipe(recipeId, tool, additionalItem);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, ClockworkAssemblyRecipe recipe) {
        recipe.getTool().toNetwork(buffer);
        recipe.getAdditionalItem().toNetwork(buffer);
    }
}
