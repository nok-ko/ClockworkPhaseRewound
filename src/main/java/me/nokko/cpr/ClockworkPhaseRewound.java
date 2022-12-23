package me.nokko.cpr;

import me.nokko.cpr.init.ModBlocks;
import me.nokko.cpr.init.ModItems;
import net.fabricmc.api.ModInitializer;
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
	}
}
