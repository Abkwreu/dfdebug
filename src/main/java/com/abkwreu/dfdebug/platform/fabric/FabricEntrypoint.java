package com.abkwreu.dfdebug.platform.fabric;

//? fabric {

import com.abkwreu.dfdebug.DFDebugMod;
import dev.kikugie.fletching_table.annotation.fabric.Entrypoint;
import net.fabricmc.api.ModInitializer;

@Entrypoint("main")
public class FabricEntrypoint implements ModInitializer {

    @Override
    public void onInitialize() {
        DFDebugMod.onInitialize();
        FabricEventSubscriber.registerEvents();
    }
}
//?}
