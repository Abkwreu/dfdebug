package com.abkwreu.dfdebug.debug;

//? if >= 1.21.9 {

import com.abkwreu.dfdebug.DFDebugMod;
import net.minecraft.resources.Identifier;

//? if fabric
import net.minecraft.client.gui.components.debug.DebugScreenEntries;
//? if neoforge
//import com.abkwreu.dfdebug.mixin.DebugScreenEntriesInvoker;

public class RegisterDebugEntries {
    public static void register() {
        Identifier id = DFDebugMod.id("density_functions");
        //? if fabric
        DebugScreenEntries.register(id, new DFDebugEntry());
        //? if neoforge
        //DebugScreenEntriesInvoker.callRegister(id, new DFDebugEntry());
    }
}
//?}
