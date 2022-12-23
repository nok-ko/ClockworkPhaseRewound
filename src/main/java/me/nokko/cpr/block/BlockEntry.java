package me.nokko.cpr.block;

import me.nokko.cpr.init.ModBlocks;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

/**
 * Record that puts all of the info about a Block+BlockItem combo together.
 * @param block
 * @param item
 * @param name
 */
public record BlockEntry(Block block, BlockItem item, String name) {
    public static BlockEntry from(Block block, String name) {
        return new BlockEntry(block, new BlockItem(block, new FabricItemSettings()), name);
    }

    public static BlockEntry from(Block block, BlockItem item, String name) {
        return new BlockEntry(block, item, name);
    }

    public static BlockEntry from(Block block, String name, FabricItemSettings settings) {
        return new BlockEntry(block, new BlockItem(block, settings), name);
    }
}
