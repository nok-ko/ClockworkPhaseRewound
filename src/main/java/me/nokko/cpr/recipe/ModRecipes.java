package me.nokko.cpr.recipe;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public class ModRecipes {
    public static void registerRecipes () {
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER,
            ClockworkAssemblyRecipeSerializer.ID,
            ClockworkAssemblyRecipeSerializer.INSTANCE
        );
        Registry.register(BuiltInRegistries.RECIPE_TYPE,
                ClockworkAssemblyRecipe.Type.ID,
                ClockworkAssemblyRecipe.Type.INSTANCE
        );
    }
}
