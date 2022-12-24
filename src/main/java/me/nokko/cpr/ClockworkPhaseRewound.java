package me.nokko.cpr;

import me.nokko.cpr.init.ModBlocks;
import me.nokko.cpr.init.ModItems;
import me.nokko.cpr.datapack.ClockworkAttributeReloadListener;
import me.nokko.cpr.recipe.ModRecipes;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClockworkPhaseRewound implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("cpr");
	public static final String MOD_ID = "cpr";

	@Override
	public void onInitialize() {
		LOGGER.info("Clockwork Phase: Rewound initializing!");

		// Run the items module:
		ModItems.registerItems();
		ModBlocks.registerBlocks();

		// Register recipes:
		ModRecipes.registerRecipes();

		// Register reload listeners:

		// Note that 1.19.3 changed reload listeners, the Fabric wiki is currently outdated but there are helpful tips
		// in the release notes.
		ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(
				new ClockworkAttributeReloadListener()
		);
	}

}
