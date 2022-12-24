package me.nokko.cpr.data;

import com.google.gson.JsonParseException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import me.nokko.cpr.item.ClockworkAttr;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.processing.Generated;
import java.util.Map;

/**
 * Plain Java object that deserializes “Clockwork Attribute Modifier” JSON files.
 */
@Generated("jsonschema2pojo")
public class ClockworkAttributeData {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("attributes")
    @Expose
    private Map<ClockworkAttr,Integer> attributes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<ClockworkAttr,Integer> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<ClockworkAttr,Integer> attributes) {
        this.attributes = attributes;
    }

    public void validate() throws JsonParseException {
        // No target:
        if (id == null)
            throw new JsonParseException("No target was specified in JSON document");
        // Bad target:
        if (BuiltInRegistries.ITEM.getOptional(new ResourceLocation(id)).isEmpty())
            throw new JsonParseException("The target specified in the JSON document (%s) was not found in the item registry."
                    .formatted(id)
            );
        // Bad modifier key:
        if (attributes.containsKey(null))
            throw new JsonParseException("The JSON document contains a modifier key that could not be parsed as a" +
                    "ClockworkAttr enum value. Valid values are: " + ClockworkAttr.values());
        // Bad modifier value:
        if (attributes.containsValue(null))
            throw new JsonParseException("The JSON document contains a modifier value that is not an integer.");
    }

}