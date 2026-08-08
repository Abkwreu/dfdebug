package com.abkwreu.dfdebug;

import com.abkwreu.dfdebug.platform.Platform;

import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//? if >= 1.21.9 {
import com.abkwreu.dfdebug.debug.DFDebugEntry;
import com.abkwreu.dfdebug.debug.RegisterDebugEntries;
//?}

//? fabric {
import com.abkwreu.dfdebug.platform.fabric.FabricPlatform;
//? if >= 1.21.9 {
import net.minecraft.client.gui.components.debug.DebugScreenEntries;
//?}
//?} neoforge {
/*import com.abkwreu.dfdebug.platform.neoforge.NeoforgePlatform;
//? if >= 1.21.9
import com.abkwreu.dfdebug.mixin.DebugScreenEntriesInvoker;
*///?} forge {
/*import com.abkwreu.dfdebug.platform.forge.ForgePlatform;
 *///?}

//? if < 1.21
//@SuppressWarnings({"LoggingSimilarMessage", "removal"})
public class DFDebugMod {

    public static final String MOD_ID = /*$ mod_id*/ "dfdebug";
    public static final String MOD_VERSION = /*$ mod_version*/ "1.0.0";
    public static final String MOD_FRIENDLY_NAME = /*$ mod_name*/ "Density Functions in Debug Screen";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static final Platform PLATFORM = createPlatformInstance();

    public static void onInitialize() {
        LOGGER.info("Initializing {} on {}", MOD_ID, DFDebugMod.xplat().loader());
    }

    public static void onInitializeClient() {
        LOGGER.info("Initializing {} Client on {}", MOD_ID, DFDebugMod.xplat().loader());
        //? if >= 1.21.9
        RegisterDebugEntries.register();
    }

    static Platform xplat() {
        return PLATFORM;
    }

    private static Platform createPlatformInstance() {
        //? fabric {
        return new FabricPlatform();
        //?} neoforge {
        /*return new NeoforgePlatform();
         *///?} forge {
        /*return new ForgePlatform();
         *///?}
    }

    public static Identifier id(String path) {
        //? >= 1.21 {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
        //?} < 1.21 {
        /*return new Identifier(MOD_ID, path);
         *///?}
    }

    public static Identifier id(String namespace, String path) {
        //? >= 1.21 {
        return Identifier.fromNamespaceAndPath(namespace, path);
        //?} < 1.21 {
        /*return new Identifier(namespace, path);
         *///?}
    }

    public static Identifier minecraftId(String path) {
        //? >= 1.21 {
        return Identifier.withDefaultNamespace(path);
        //?} < 1.21 {
        /*return new Identifier("minecraft", path);
         *///?}
    }

    public static Identifier namespacedId(String namespacedId) {
        //? >= 1.21 {
        return Identifier.parse(namespacedId);
        //?} else {
        /*return new Identifier(namespacedId);
         *///?}
    }
}
