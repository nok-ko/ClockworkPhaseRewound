package me.nokko.cpr.mixin;

import me.nokko.cpr.ClockworkPhaseRewound;
import net.fabricmc.loader.impl.game.minecraft.MinecraftGameProvider;
import net.minecraft.CrashReport;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.time.Clock;
import java.util.Arrays;
import java.util.List;

@Mixin(TitleScreen.class)
abstract public class ExampleMixin extends Screen {

	@Shadow
	private String splash;

	protected ExampleMixin(Component component) {
		super(component);
	}

	@Inject(at = @At("HEAD"), method = "init()V")
	private void init(CallbackInfo info) {
		ClockworkPhaseRewound.LOGGER.info("This line is printed by an example mod mixin!");
		this.splash = "Clockwork Phase!";

		// Debug helper to quickly jump into the world.
		final String[] argv = ProcessHandle.current().info().arguments().orElse(new String[]{});
		if (Arrays.asList(argv).contains("-Dopenfirstworld=true")) {
			this.splash = "Debug Woot";
			var levelSource = Minecraft.getInstance().getLevelSource();
			var summaries = levelSource.loadLevelSummaries(levelSource.findLevelCandidates()).exceptionally(
					horrors -> {
						Minecraft.getInstance().delayCrash(CrashReport.forThrowable(horrors, "Couldn't load level list"));
						return List.of();
					}
			);
			summaries.thenAcceptAsync(levelSummaries -> {
				try {
					var firstLevel = levelSummaries.get(0);
					Minecraft.getInstance().createWorldOpenFlows().loadLevel(this, firstLevel.getLevelId());
				} catch (Exception ignored) {}
			});
		}
		ClockworkPhaseRewound.LOGGER.info(Arrays.toString(argv));
	}
}
