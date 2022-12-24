package me.nokko.cpr.init;

import me.nokko.cpr.ClockworkPhaseRewound;
import me.nokko.cpr.item.ClockworkPickaxeItem;
import me.nokko.cpr.item.FrameworkItem;
import me.nokko.cpr.item.GearItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.impl.itemgroup.MinecraftItemGroups;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;

import static me.nokko.cpr.init.ModBlocks.*;

public class ModItems {

    public static final Item CLOCKWORK_PICKAXE = registerItem("clockwork_pickaxe", new ClockworkPickaxeItem(
            Tiers.DIAMOND, 1 ,1f, new FabricItemSettings().stacksTo(1).defaultDurability(1000)
    ));

    public static final Item CLOCKWORK = registerItem("clockwork", new Item(new FabricItemSettings()));
    public static final Item FRAMEWORK = registerItem("framework", new FrameworkItem(new FabricItemSettings()));
    public static final Item BRASS_GEAR = registerItem("gear_brass", new GearItem(20, 55, new FabricItemSettings()) {});
    public static final CreativeModeTab MAIN_GROUP = FabricItemGroup.builder(new ResourceLocation(ClockworkPhaseRewound.MOD_ID, "main"))
        .icon(() -> new ItemStack(CLOCKWORK_PICKAXE))
        .build();

    /**
     * Helper fn. that quickly registers an item.
     * @param name - Item name, will be under the `cpr` namespace.
     * @param item - Item class to register.
     * @return
     */
    private static Item registerItem(String name, Item item){
        return Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(ClockworkPhaseRewound.MOD_ID, name), item);
    }

    public static void registerItems() {
        // Add the items to our custom item group.
        ItemGroupEvents.modifyEntriesEvent(MAIN_GROUP).register(entries -> {
            // Gears:
            entries.accept(BRASS_GEAR);

            // Components:
            entries.accept(CLOCKWORK);
            entries.accept(FRAMEWORK);

            // Tools:
            entries.accept(CLOCKWORK_PICKAXE);

            // All the BlockItems:
            entries.accept(WINDING_BOX.item());
            entries.accept(DISASSEMBLY_TABLE.item());
            entries.accept(CLOCKWORK_ASSEMBLY_TABLE.item());
        });
    }
}
