package me.nokko.cpr.datagen;

import me.nokko.cpr.init.ModItemTags;
import me.nokko.cpr.init.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

import static me.nokko.cpr.ClockworkPhaseRewound.MOD_ID;

public class RecipeProvider extends FabricRecipeProvider {
    public RecipeProvider(FabricDataOutput output) {
        super(output);
    }

    private ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> exporter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.CLOCKWORK_PICKAXE, 1)
                .unlockedBy("has_framework", has(ModItemTags.FRAMEWORKS))
                .pattern("IFI")
                .pattern(" S ")
                .pattern(" S ")
                .define('I', Items.GOLD_INGOT)
                .define('F', ModItemTags.FRAMEWORKS)
                .define('S', Items.STICK)
                .save(exporter, id("tools/clockwork_pickaxe"));
    }
}
