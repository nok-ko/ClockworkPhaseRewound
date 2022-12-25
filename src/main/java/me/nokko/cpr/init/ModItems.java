package me.nokko.cpr.init;

import me.nokko.cpr.ClockworkPhaseRewound;
import me.nokko.cpr.datagen.ClockworkAttributes;
import me.nokko.cpr.item.ClockworkPickaxeItem;
import me.nokko.cpr.item.FrameworkItem;
import me.nokko.cpr.item.GearItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;

import static me.nokko.cpr.init.ModBlocks.*;

public class ModItems {

    public static final Item CLOCKWORK_PICKAXE = registerItem("clockwork_pickaxe", new ClockworkPickaxeItem(
            Tiers.DIAMOND, 1 ,1f, new FabricItemSettings().stacksTo(1).defaultDurability(1000)
    ));

    public static final Item CLOCKWORK = registerItem("clockwork", new Item(new FabricItemSettings()));
    public static final Item FRAMEWORK = registerItem("framework", new FrameworkItem(new FabricItemSettings()));

    @ClockworkAttributes(quality = 20, speed = 55, memory = 0)
    public static final Item BRASS_GEAR = registerItem("gear_brass", new GearItem(new FabricItemSettings()) {});

    @ClockworkAttributes(quality = 50, speed = 25, memory = 0)
    public static final Item BRONZE_GEAR = registerItem("gear_bronze", new GearItem(new FabricItemSettings()) {});

    @ClockworkAttributes(quality = 40, speed = 30, memory = 0)
    public static final Item COPPER_GEAR = registerItem("gear_copper", new GearItem(new FabricItemSettings()) {});

    @ClockworkAttributes(quality = 60, speed = 40, memory = 0)
    public static final Item DIAMOND_GEAR = registerItem("gear_diamond", new GearItem(new FabricItemSettings()) {});

    @ClockworkAttributes(quality = 35, speed = 60, memory = 0)
    public static final Item EMERALD_GEAR = registerItem("gear_emerald", new GearItem(new FabricItemSettings()) {});

    @ClockworkAttributes(quality = 45, speed = 25, memory = 0)
    public static final Item IRON_GEAR = registerItem("gear_iron", new GearItem(new FabricItemSettings()) {});

    @ClockworkAttributes(quality = 70, speed = 10, memory = 0)
    public static final Item LEAD_GEAR = registerItem("gear_lead", new GearItem(new FabricItemSettings()) {});

    @ClockworkAttributes(quality = 70, speed = 70, memory = 250)
    public static final Item LEGENDARY_GEAR = registerItem("gear_legendary", new GearItem(new FabricItemSettings().rarity(Rarity.EPIC)) {});

    @ClockworkAttributes(quality = 5, speed = 27, memory = 175)
    public static final Item RUSTY_GEAR = registerItem("gear_rusty", new GearItem(new FabricItemSettings().rarity(Rarity.UNCOMMON)) {});

    @ClockworkAttributes(quality = 25, speed = 50, memory = 0)
    public static final Item SILVER_GEAR = registerItem("gear_silver", new GearItem(new FabricItemSettings()) {});

    @ClockworkAttributes(quality = 80, speed = 20, memory = 0)
    public static final Item STEEL_GEAR = registerItem("gear_steel", new GearItem(new FabricItemSettings()) {});

    @ClockworkAttributes(quality = 20, speed = 90, memory = 150)
    public static final Item TEMPORAL_GEAR = registerItem("gear_temporal", new GearItem(new FabricItemSettings().rarity(Rarity.RARE)) {});

    @ClockworkAttributes(quality = 45, speed = 40, memory = 120)
    public static final Item THAUMIUM_GEAR = registerItem("gear_thaumium", new GearItem(new FabricItemSettings().rarity(Rarity.UNCOMMON)) {});

    @ClockworkAttributes(quality = 30, speed = 40, memory = 0)
    public static final Item TIN_GEAR = registerItem("gear_tin", new GearItem(new FabricItemSettings()) {});


    // In original Clockwork Phase, the Memento's memory value was set by NBT data.
    @ClockworkAttributes(quality = 0, speed = 0, memory = 0)
    public static final Item MEMENTO = registerItem("memento", new GearItem(new FabricItemSettings()) {});

    @ClockworkAttributes(quality = 0, speed = 0, memory = 350)
    public static final Item NOTE_BOTTLE = registerItem("note_bottle", new GearItem(new FabricItemSettings()) {});

    @ClockworkAttributes(quality = 0, speed = 0, memory = 237)
    public static final Item OLD_COIN = registerItem("old_coin", new GearItem(new FabricItemSettings()) {});

    @ClockworkAttributes(quality = 0, speed = 0, memory = 500)
    public static final Item PRECIOUS_CHARM = registerItem("precious_charm", new GearItem(new FabricItemSettings()) {});

    public static final CreativeModeTab MAIN_GROUP = FabricItemGroup.builder(new ResourceLocation(ClockworkPhaseRewound.MOD_ID, "main"))
        .icon(() -> new ItemStack(CLOCKWORK_PICKAXE))
        .build();

    /**
     * Helper method that quickly registers an item.
     * @param name Item name, will be under the `cpr` namespace.
     * @param item Item class to register.
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
            entries.accept(BRONZE_GEAR);
            entries.accept(COPPER_GEAR);
            entries.accept(DIAMOND_GEAR);
            entries.accept(EMERALD_GEAR);
            entries.accept(IRON_GEAR);
            entries.accept(LEAD_GEAR);
            entries.accept(LEGENDARY_GEAR);
            entries.accept(RUSTY_GEAR);
            entries.accept(SILVER_GEAR);
            entries.accept(STEEL_GEAR);
            entries.accept(TEMPORAL_GEAR);
            entries.accept(THAUMIUM_GEAR);
            entries.accept(TIN_GEAR);

            // Components:
            entries.accept(CLOCKWORK);
            entries.accept(FRAMEWORK);

            // Clockwork Tools:
            entries.accept(CLOCKWORK_PICKAXE);

            // All the BlockItems:
            entries.accept(WINDING_BOX.item());
            entries.accept(DISASSEMBLY_TABLE.item());
            entries.accept(CLOCKWORK_ASSEMBLY_TABLE.item());
        });
    }
}
