package me.nokko.cpr.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.concurrent.CompletableFuture;

import static me.nokko.cpr.ClockworkPhaseRewound.MOD_ID;
import static me.nokko.cpr.init.ModItems.*;

public class ItemTagProvider extends FabricTagProvider<Item> {
    /**
     * Constructs a new {@link FabricTagProvider} with the default computed path.
     *
     * <p>Common implementations of this class are provided.
     *
     * @param output           the {@link FabricDataOutput} instance
     * @param registriesFuture the backing registry for the tag type
     */
    public ItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.ITEM, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        getOrCreateTagBuilder(TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "gears")))
                .add(BRASS_GEAR)
                .add(BRASS_GEAR)
                .add(BRONZE_GEAR)
                .add(COPPER_GEAR)
                .add(DIAMOND_GEAR)
                .add(EMERALD_GEAR)
                .add(IRON_GEAR)
                .add(LEAD_GEAR)
                .add(LEGENDARY_GEAR)
                .add(RUSTY_GEAR)
                .add(SILVER_GEAR)
                .add(STEEL_GEAR)
                .add(TEMPORAL_GEAR)
                .add(THAUMIUM_GEAR)
                .add(TIN_GEAR);
    }
}
