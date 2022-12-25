package me.nokko.cpr.recipe;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import me.nokko.cpr.ClockworkPhaseRewound;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.Optional;

import static me.nokko.cpr.ClockworkPhaseRewound.LOGGER;
import static me.nokko.cpr.ClockworkPhaseRewound.MOD_ID;

public class ClockworkAssemblyRecipeSerializer implements RecipeSerializer<ClockworkAssemblyRecipe> {

    private ClockworkAssemblyRecipeSerializer () { }

    public static final ClockworkAssemblyRecipeSerializer INSTANCE = new ClockworkAssemblyRecipeSerializer();
    public static final ResourceLocation ID = new ResourceLocation(MOD_ID, "clockwork_assembly");

    public void toJson(ClockworkAssemblyRecipe recipe, JsonObject json) {
        json.addProperty("type", MOD_ID + ":" + ClockworkAssemblyRecipe.Type.ID);
        if (recipe.getTool() != null) {
            json.add("tool", recipe.getTool().toJson());
        }
        if (recipe.getAdditionalItem() != null) {
            json.add("additionalItem", recipe.getAdditionalItem().toJson());
        }
        if (recipe.getTransmuteItem().isPresent()) {
            json.add("transmuteItem", recipe.getTransmuteItem().get().toJson());
        }
    }

    @Override
    public ClockworkAssemblyRecipe fromJson(ResourceLocation recipeId, JsonObject serializedRecipe) {
        var recipeJson = new Gson().fromJson(serializedRecipe, ClockworkAssemblyRecipeFormat.class);

        if (recipeJson.additionalItem == null || recipeJson.tool == null) {
            throw new JsonSyntaxException("A required attribute is missing from the recipe json.");
        }

        Ingredient tool = Ingredient.fromJson(recipeJson.tool);
        Ingredient additionalItem = Ingredient.fromJson(recipeJson.additionalItem);
        Optional<Ingredient> transmuteItem;
        if (recipeJson.transmuteItem == null) {
            transmuteItem = Optional.empty();
        } else {
            transmuteItem = Optional.of(Ingredient.fromJson(recipeJson.transmuteItem));
        }


        LOGGER.info("Registered recipe: " + recipeId);
        return new ClockworkAssemblyRecipe(recipeId, tool, additionalItem, transmuteItem);
    }

    @Override
    public ClockworkAssemblyRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        Ingredient tool = Ingredient.fromNetwork(buffer);
        Ingredient additionalItem = Ingredient.fromNetwork(buffer);
        boolean hasTransmute = buffer.readBoolean();
        Optional<Ingredient> transmuteItem;
        if (hasTransmute) {
            transmuteItem = Optional.of(Ingredient.fromNetwork(buffer));
        } else {
            transmuteItem = Optional.empty();
        }
        return new ClockworkAssemblyRecipe(recipeId, tool, additionalItem, transmuteItem);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, ClockworkAssemblyRecipe recipe) {
        recipe.getTool().toNetwork(buffer);
        recipe.getAdditionalItem().toNetwork(buffer);
        boolean hasTransmute = recipe.getTransmuteItem().isPresent();
        buffer.writeBoolean(hasTransmute);
        if (hasTransmute) {
            recipe.getTransmuteItem().get().toNetwork(buffer);
        }
    }
}
