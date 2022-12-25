package me.nokko.cpr.init;

import me.nokko.cpr.datagen.ClockworkAttributeModifierProvider;
import me.nokko.cpr.datagen.ItemTagProvider;
import me.nokko.cpr.datagen.ModelProvider;
import me.nokko.cpr.datagen.RecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class ModDataGenerators implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack mainPack = fabricDataGenerator.createPack();
        mainPack.addProvider(ItemTagProvider::new);
        mainPack.addProvider(ModelProvider::new);
        mainPack.addProvider(RecipeProvider::new);
        mainPack.addProvider(ClockworkAttributeModifierProvider::new);
//        var registryAccess = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
//        pack.addProvider(packOutput -> new ItemModelProvider());
    }
}
