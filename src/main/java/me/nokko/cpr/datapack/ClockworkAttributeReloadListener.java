package me.nokko.cpr.datapack;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import me.nokko.cpr.ClockworkPhaseRewound;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Reload listener that tries to apply any “Clockwork Attribute Modifier” JSON files it can find.
 */
public class ClockworkAttributeReloadListener implements SimpleSynchronousResourceReloadListener {
    public static final FileToIdConverter JSON_CONVERTER = FileToIdConverter.json("clockwork_attribute_modifiers");

    @Override
    public ResourceLocation getFabricId() {
        return new ResourceLocation(ClockworkPhaseRewound.MOD_ID, "clockwork_attribute_modifiers");
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        // TODO: reduce log spam in this method.
        int resourcesCount = 0;
        for (Map.Entry<ResourceLocation, Resource> entry : JSON_CONVERTER.listMatchingResources(resourceManager).entrySet()) {
            ResourceLocation resourceLocation = entry.getKey();
            Resource resource = entry.getValue();
            try (InputStream stream = resource.open()) {
                // Parse the document with Gson:
                var json = JsonParser.parseReader(new InputStreamReader(stream));
                var data = new GsonBuilder().create().fromJson(json, ClockworkAttributeData.class);

                // This will throw JsonParseExceptions if anything is amiss:
                data.validate();

                var targetItem = BuiltInRegistries.ITEM.get(new ResourceLocation(data.getId()));
                if (targetItem instanceof ClockworkAttributeReloadable) {
                    // We're good and validated, let's update those attributes!
                    // TODO: introduce a priority queue here so conflicts between overlapping datapacks aren't resolved stochastically
                    ((ClockworkAttributeReloadable) targetItem).updateAttributes(data.getAttributes());
                } else {
                    throw new RuntimeException(("The target specified in the JSON document (%s) was not a " +
                            "ClockworkAttributeReloadable item.").formatted(data.getId()));
                }
                ClockworkPhaseRewound.LOGGER.info(resourceLocation.toString(), resourceLocation.toDebugFileName());
                resourcesCount++;
            } catch (IOException exception) {
                ClockworkPhaseRewound.LOGGER.error("Error occurred while loading resource JSON `%s`"
                        .formatted(resourceLocation.toDebugFileName()));
                ClockworkPhaseRewound.LOGGER.error(exception.toString());
            } catch (JsonParseException exception) {
                ClockworkPhaseRewound.LOGGER.error("Error occurred while parsing resource JSON `%s`"
                        .formatted(resourceLocation.toDebugFileName()));
                ClockworkPhaseRewound.LOGGER.error(exception.toString());
            } catch (RuntimeException exception) {
                ClockworkPhaseRewound.LOGGER.error("Error occurred while modifying targets specified in resource JSON `%s`"
                        .formatted(resourceLocation.toDebugFileName()));
                ClockworkPhaseRewound.LOGGER.error(exception.toString());
            }
        }
        ClockworkPhaseRewound.LOGGER.debug("Reloaded %d resources.".formatted(resourcesCount));
    }
}
