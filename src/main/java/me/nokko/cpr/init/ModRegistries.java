package me.nokko.cpr.init;

import me.nokko.cpr.item.ClockworkComponentImpl;
import me.nokko.cpr.item.ClockworkComponentWrapper;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

import static me.nokko.cpr.ClockworkPhaseRewound.id;

public class ModRegistries {
    public static final Registry<ClockworkComponentWrapper> EXTRA_COMPONENTS =
            FabricRegistryBuilder.createSimple(
                    ClockworkComponentWrapper.class,
                    id("extra_components")
            )
            .attribute(RegistryAttribute.SYNCED)
            .buildAndRegister();
    public static void registerExtraComponents() {
        Registry.register(EXTRA_COMPONENTS, new ResourceLocation("minecraft", "dirt"), new ClockworkComponentWrapper(new ClockworkComponentImpl(10, 10, 1000)) {});
    }
}
