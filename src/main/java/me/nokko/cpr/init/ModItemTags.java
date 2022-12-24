package me.nokko.cpr.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static me.nokko.cpr.ClockworkPhaseRewound.MOD_ID;

public class ModItemTags {
    public static final TagKey<Item> CLOCKWORK_TOOLS = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "clockwork_tools"));
    public static final TagKey<Item> FRAMEWORKS = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "frameworks"));
    public static final TagKey<Item> FRAMEWORK_ACCEPTORS = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "framework_acceptors"));
    public static final TagKey<Item> CPR_GEARS = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "gears"));
    public static final TagKey<Item> CPR_GEAR_ACCEPTORS = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "gear_acceptors"));
}
