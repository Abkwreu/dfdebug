package com.abkwreu.dfdebug.mixin;

//? if >= 1.21.9 {

import com.abkwreu.dfdebug.DFDebugMod;
import net.minecraft.client.gui.components.debug.DebugScreenEntries;
import net.minecraft.client.gui.components.debug.DebugScreenEntryStatus;
import net.minecraft.client.gui.components.debug.DebugScreenProfile;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(DebugScreenEntries.class)
public class DebugScreenEntriesMixin {
    @Mutable
    @Shadow
    @Final
    public static Map<DebugScreenProfile, Map<Identifier, DebugScreenEntryStatus>> PROFILES;

    @Inject(method="<clinit>", at=@At("TAIL"))
    private static void addDebugEntryToPreset(CallbackInfo ci) {
        HashMap<DebugScreenProfile, Map<Identifier, DebugScreenEntryStatus>> profiles = new HashMap<>(PROFILES);
        Map<Identifier, DebugScreenEntryStatus> defaultProfile = new HashMap<>(PROFILES.get(DebugScreenProfile.DEFAULT));

        defaultProfile.put(DFDebugMod.id("density_functions"), DebugScreenEntryStatus.IN_OVERLAY);
        profiles.put(DebugScreenProfile.DEFAULT, defaultProfile);

        PROFILES = Map.copyOf(profiles);
    }
}
//?}
