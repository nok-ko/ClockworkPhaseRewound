package me.nokko.cpr.datagen;

import me.nokko.cpr.init.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

import static me.nokko.cpr.init.ModBlocks.CLOCKWORK_ASSEMBLY_TABLE;
import static me.nokko.cpr.init.ModItems.*;

public class ModelProvider extends FabricModelProvider {
    public ModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators modelGenerators) {
        final List<Block> CUBE_ALL_BLOCKS = List.of(
                ModBlocks.DISASSEMBLY_TABLE.block(),
                ModBlocks.WINDING_BOX.block()
        );
        for (Block block : CUBE_ALL_BLOCKS) {
            modelGenerators.createTrivialCube(block);
        }

        modelGenerators.createTrivialBlock(CLOCKWORK_ASSEMBLY_TABLE.block(), TexturedModel.CUBE_TOP);
    }

    @Override
    public void generateItemModels(ItemModelGenerators modelGenerators) {
        final List<Item> FLAT_ITEMS = List.of(
                // Gears:
                BRASS_GEAR,
                BRONZE_GEAR,
                COPPER_GEAR,
                DIAMOND_GEAR,
                EMERALD_GEAR,
                IRON_GEAR,
                LEAD_GEAR,
                LEGENDARY_GEAR,
                RUSTY_GEAR,
                SILVER_GEAR,
                STEEL_GEAR,
                TEMPORAL_GEAR,
                THAUMIUM_GEAR,
                TIN_GEAR,
                // Components:
                CLOCKWORK,
                FRAMEWORK
        );
        for (Item item : FLAT_ITEMS) {
            modelGenerators.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
        }

        final List<Item> HANDHELD_ITEMS = List.of(
                // Clockwork Tools:
                CLOCKWORK_PICKAXE
        );
        for (Item item : HANDHELD_ITEMS) {
            modelGenerators.generateFlatItem(item, ModelTemplates.FLAT_HANDHELD_ITEM);
        }
    }
}
