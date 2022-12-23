package me.nokko.cpr.client;

import me.nokko.cpr.client.block.ClockworkAssemblyTableBlockEntityRenderer;
import me.nokko.cpr.init.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;

import static me.nokko.cpr.ClockworkPhaseRewound.LOGGER;
import static me.nokko.cpr.init.ModBlocks.CLOCKWORK_ASSEMBLY_TABLE_BLOCK_ENTITY;

public class ClockworkPhaseRewoundClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        LOGGER.info("Initializing Clockwork Phase: Rewound Client!");
        BlockEntityRendererRegistry.register(
                CLOCKWORK_ASSEMBLY_TABLE_BLOCK_ENTITY,
                ClockworkAssemblyTableBlockEntityRenderer::new
        );
    }
}
