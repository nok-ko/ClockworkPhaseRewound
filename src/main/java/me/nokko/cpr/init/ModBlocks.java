package me.nokko.cpr.init;

import me.nokko.cpr.ClockworkPhaseRewound;
import me.nokko.cpr.block.BlockEntry;
import me.nokko.cpr.block.ClockworkAssemblyTableBlock;
import me.nokko.cpr.block.DisassemblyTableBlock;
import me.nokko.cpr.block.WindingBoxBlock;
import me.nokko.cpr.block.entity.ClockworkAssemblyTableBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Material;

import static me.nokko.cpr.ClockworkPhaseRewound.MOD_ID;

public class ModBlocks {

    public static final BlockEntry CLOCKWORK_ASSEMBLY_TABLE = BlockEntry.from(
            new ClockworkAssemblyTableBlock(FabricBlockSettings.of(Material.WOOD)), "clockwork_assembly_table"
    );

    public static final BlockEntry WINDING_BOX = BlockEntry.from(
            new WindingBoxBlock(FabricBlockSettings.of(Material.METAL)), "winding_box"
    );

    public static final BlockEntry DISASSEMBLY_TABLE = BlockEntry.from(
            new DisassemblyTableBlock(FabricBlockSettings.of(Material.METAL)), "disassembly_table"
    );

    public static final BlockEntityType<ClockworkAssemblyTableBlockEntity> CLOCKWORK_ASSEMBLY_TABLE_BLOCK_ENTITY = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(MOD_ID, "clockwork_assembly_table_block_entity"),
            FabricBlockEntityTypeBuilder.create(ClockworkAssemblyTableBlockEntity::new, CLOCKWORK_ASSEMBLY_TABLE.block()).build()
    );

    private static void registerBlockEntry(BlockEntry entry) {
        var resourceLocation = new ResourceLocation(MOD_ID, entry.name());
        Registry.register(BuiltInRegistries.BLOCK, resourceLocation, entry.block());
        Registry.register(BuiltInRegistries.ITEM, resourceLocation, entry.item());
    }
    public static void registerBlocks() {
        registerBlockEntry(CLOCKWORK_ASSEMBLY_TABLE);
        registerBlockEntry(WINDING_BOX);
        registerBlockEntry(DISASSEMBLY_TABLE);
    }
}
