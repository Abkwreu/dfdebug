package com.abkwreu.dfdebug.event;

//? if (forge || neoforge) && < 1.21.9 {

/*import com.abkwreu.dfdebug.DFDebugMod;
import com.abkwreu.dfdebug.config.ModConfig;
import com.abkwreu.dfdebug.config.ModConfigEntries;
import com.abkwreu.dfdebug.debug.DFDebugEntryGenerator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.server.level.ServerLevel;

//? if forge {
/^import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
^///?}
//? if neoforge {
/^import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent;
^///?}

import java.util.List;
import java.util.Optional;

//? if forge
//@Mod.EventBusSubscriber(modid = DFDebugMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
//? if neoforge
//@EventBusSubscriber(modid = DFDebugMod.MOD_ID, value = Dist.CLIENT)
public class DebugMenuHandler {
	@SubscribeEvent
	public static void onCustomizeDebugText(CustomizeGuiOverlayEvent.DebugText event) {
		Minecraft minecraft = Minecraft.getInstance();
		//? if <= 1.20.1 {
		/^boolean renderDebug = minecraft.options.renderDebug;
		 ^///?} else {
		boolean renderDebug = minecraft.getDebugOverlay().showDebugScreen();
		//?}

		if (!renderDebug || minecraft.options.reducedDebugInfo().get()) {
			return;
		}

		IntegratedServer singleplayerServer = minecraft.getSingleplayerServer();
		if (singleplayerServer == null) {
			return;
		}

		ServerLevel serverLevel = singleplayerServer.getLevel(minecraft.level.dimension());
		if (serverLevel == null) {
			return;
		}

		Optional<List<String>> lines = DFDebugEntryGenerator.generate(serverLevel,
				ModConfig.get().get(ModConfigEntries.DISPLAY_HEADER));
		lines.ifPresent(
				value -> {
					event.getLeft().add("");
					event.getLeft().addAll(value);
				}
		);
	}
}
*///?}
