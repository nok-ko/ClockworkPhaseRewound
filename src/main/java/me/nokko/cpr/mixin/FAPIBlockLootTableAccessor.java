package me.nokko.cpr.mixin;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import org.spongepowered.asm.mixin.Mixin;

// Awful, horrible, no good.
@Mixin(FabricBlockLootTableProvider.class)
public interface FAPIBlockLootTableAccessor {

}
