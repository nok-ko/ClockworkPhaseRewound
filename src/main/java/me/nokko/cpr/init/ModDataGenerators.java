package me.nokko.cpr.init;

import me.nokko.cpr.datagen.*;
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
        mainPack.addProvider(ClockworkBlockLootTableProvider::new);
    }
}
