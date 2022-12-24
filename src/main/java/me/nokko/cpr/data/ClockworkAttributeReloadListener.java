package me.nokko.cpr.data;

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
        int resourcesCount = 0;
        for (Map.Entry<ResourceLocation, Resource> entry : JSON_CONVERTER.listMatchingResources(resourceManager).entrySet()) {
            ResourceLocation resourceLocation = entry.getKey();
            Resource resource = entry.getValue();
            try (InputStream stream = resource.open()) {
                var json = JsonParser.parseReader(new InputStreamReader(stream));
                var data = new GsonBuilder().create().fromJson(json, ClockworkAttributeData.class);
                if (!data.isValid()) {
                    throw new JsonParseException("Clockwork Attribute Modifier JSON failed validation.");
                }
                var targetItem = BuiltInRegistries.ITEM.get(new ResourceLocation(data.getId()));
                // We're good and validated, let's update those attributes!
                if (targetItem instanceof ClockworkAttributeReloadable) {
                    ((ClockworkAttributeReloadable) targetItem).updateAttributes(data.getAttributes());
                }
                ClockworkPhaseRewound.LOGGER.info(resourceLocation.toString(), resourceLocation.toDebugFileName());
                resourcesCount++;
            } catch (IOException exception) {
                ClockworkPhaseRewound.LOGGER.error("Error occurred while loading resource JSON clockwork_attributes/%s.json"
                        .formatted(resourceLocation.toDebugFileName()));
                ClockworkPhaseRewound.LOGGER.error(exception.toString());
            } catch (JsonParseException exception) {
                ClockworkPhaseRewound.LOGGER.error("Error occurred while parsing resource JSON clockwork_attributes/%s.json"
                        .formatted(resourceLocation.toDebugFileName()));
                ClockworkPhaseRewound.LOGGER.error(exception.toString());
            }
        }
        ClockworkPhaseRewound.LOGGER.debug("Reloaded %d resources.".formatted(resourcesCount));
    }
}
