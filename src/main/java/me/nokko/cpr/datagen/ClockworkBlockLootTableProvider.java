package me.nokko.cpr.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.function.BiConsumer;

import static me.nokko.cpr.init.ModBlocks.*;

public class ClockworkBlockLootTableProvider extends FabricBlockLootTableProvider {
    public ClockworkBlockLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        dropSelf(CLOCKWORK_ASSEMBLY_TABLE.block());
        dropSelf(DISASSEMBLY_TABLE.block());
        dropSelf(WINDING_BOX.block());
    }

    // The usual way doesn't work on MojMaps, so we gotta patch in `accept` to be named `generate`
    @Override
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> biConsumer) {
        this.generate(biConsumer);
    }
}
