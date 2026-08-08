package com.abkwreu.dfdebug.debug;

//? if >= 1.21.9 {

import com.abkwreu.dfdebug.DFDebugMod;
import com.abkwreu.dfdebug.config.ModConfig;
import com.abkwreu.dfdebug.config.ModConfigEntries;
import net.minecraft.client.gui.components.debug.DebugScreenDisplayer;
import net.minecraft.client.gui.components.debug.DebugScreenEntry;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class DFDebugEntry implements DebugScreenEntry {
    @Override
    public void display(DebugScreenDisplayer displayer,
                        @Nullable Level serverOrClientLevel,
                        @Nullable LevelChunk clientChunk,
                        @Nullable LevelChunk serverChunk) {

        Optional<List<String>> lines = DFDebugEntryGenerator.generate(serverOrClientLevel,
              ModConfig.get().get(ModConfigEntries.DISPLAY_HEADER));
        lines.ifPresent(
              value -> displayer.addToGroup(DFDebugMod.id("density_functions"), value)
        );
    }
}
//?}
