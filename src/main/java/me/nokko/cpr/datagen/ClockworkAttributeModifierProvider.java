package me.nokko.cpr.datagen;

import com.google.gson.GsonBuilder;
import me.nokko.cpr.ClockworkPhaseRewound;
import me.nokko.cpr.datapack.ClockworkAttributeData;
import me.nokko.cpr.init.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static me.nokko.cpr.item.ClockworkAttr.*;

public class ClockworkAttributeModifierProvider implements DataProvider {

    private final FabricDataOutput output;
    private final PackOutput.PathProvider pathProvider;

    public ClockworkAttributeModifierProvider(FabricDataOutput output) {
        this.output = output;
        this.pathProvider = output.createPathProvider(PackOutput.Target.DATA_PACK, "clockwork_attribute_modifiers");
    }

    @Override @NotNull
    public CompletableFuture<?> run(CachedOutput writer) {
        ClockworkPhaseRewound.LOGGER.warn("YOOOOO");
        Class<ModItems> modItems = ModItems.class;
        var fields = Arrays.stream(modItems.getFields())
                    .filter(field -> field.getAnnotationsByType(ClockworkAttributes.class).length > 0)
                    .toList();

        var futures = new ArrayList<CompletableFuture<?>>();
        for (var annotatedField : fields) {
            var defaultSetting = annotatedField.getAnnotationsByType(ClockworkAttributes.class)[0];
            var data = new ClockworkAttributeData();

            ResourceLocation targetItem;
            try {
                targetItem = BuiltInRegistries.ITEM.getKey((Item) annotatedField.get(null));
            } catch (IllegalAccessException e) {
                ClockworkPhaseRewound.LOGGER.error("Big reflection fail during datagen on field %s in ModBlocks."
                        .formatted(annotatedField.getName())
                );
                continue;
            }

            data.setId(targetItem.toString());
            data.setAttributes(Map.of(
                    SPEED, defaultSetting.speed(),
                    QUALITY, defaultSetting.quality(),
                    MEMORY, defaultSetting.memory()
            ));
            var document = new GsonBuilder().create().toJsonTree(data);

            futures.add(DataProvider.saveStable(writer, document, pathProvider.json(targetItem.withPath(targetItem.getPath() + "_defaults"))));
        }
        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    @Override @NotNull
    public String getName() {
        return "Default Clockwork Attribute Modifiers";
    }
}
