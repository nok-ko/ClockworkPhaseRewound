package me.nokko.cpr.data;

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

    public boolean isValid() {
        // No target:
        if (id == null) return false;
        // Bad target:
        if (BuiltInRegistries.ITEM.getOptional(new ResourceLocation(id)).isEmpty()) return false;
        // Bad modifier key:
        if (attributes.containsKey(null)) return false;
        // Bad modifier value:
        if (attributes.containsValue(null)) return false;
        return true;
    }

}