package com.abkwreu.dfdebug.platform.forge;

//? forge {

/*import com.abkwreu.dfdebug.DFDebugMod;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = DFDebugMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeClientEventSubscriber {

	@SubscribeEvent
	public static void onClientSetup(final FMLClientSetupEvent event) {
		DFDebugMod.onInitializeClient();
		ForgeModConfig.load(ForgeModConfig::new);
	}
}
*///?}
