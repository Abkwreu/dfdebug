package com.abkwreu.dfdebug.platform.fabric;

//? fabric {

import com.abkwreu.dfdebug.DFDebugMod;
import com.abkwreu.dfdebug.config.ModConfig;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class FabricModConfig extends ModConfig {

    @Override
    protected Path getConfigPath() {
        return FabricLoader.getInstance().getConfigDir().resolve(String.format("%s.json", DFDebugMod.MOD_ID));
    }
}
//?}
