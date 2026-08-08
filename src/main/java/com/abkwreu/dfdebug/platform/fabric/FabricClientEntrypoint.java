package com.abkwreu.dfdebug.platform.fabric;

//? fabric {

import com.abkwreu.dfdebug.DFDebugMod;
import com.abkwreu.dfdebug.config.ModConfig;
import dev.kikugie.fletching_table.annotation.fabric.Entrypoint;
import net.fabricmc.api.ClientModInitializer;

@Entrypoint("client")
public class FabricClientEntrypoint implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        DFDebugMod.onInitializeClient();
        FabricModConfig.load(FabricModConfig::new);
    }

}
//?}
