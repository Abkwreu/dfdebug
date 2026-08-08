package com.abkwreu.dfdebug.config;

import com.abkwreu.dfdebug.DFDebugMod;
import com.google.gson.reflect.TypeToken;
import net.minecraft.resources.Identifier;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ModConfigEntries {
    public static final ModConfigEntry<List<Identifier>> DISPLAYED_DENSITY_FUNCTIONS = new ModConfigEntry<>(
          "displayed_density_functions",
          List.of(DFDebugMod.minecraftId("y")),
          "IDs of density functions to display in the debug menu.",
          new TypeToken<List<Identifier>>() {
          }.getType());

    public static final ModConfigEntry<Map<Identifier, List<Identifier>>> DIMENSION_DENSITY_FUNCTIONS = new ModConfigEntry<>(
          "dimension_density_functions",
          Map.of(DFDebugMod.minecraftId("overworld"),
                List.of(DFDebugMod.minecraftId("overworld/sloped_cheese")),
                DFDebugMod.minecraftId("the_nether"),
                List.of(DFDebugMod.minecraftId("nether/base_3d_noise"))),
          "IDs of density functions to only display in certain dimensions.",
          new TypeToken<Map<Identifier, List<Identifier>>>() {
          }.getType()
    );

    public static final ModConfigEntry<Integer> DENSITY_FUNCTION_PRECISION = new ModConfigEntry<>(
          "density_function_precision",
          4,
          "The number of digits to display in density function values (-1 for unlimited precision)",
          Integer.class);

    public static final ModConfigEntry<Boolean> USE_SCIENTIFIC_NOTATION = new ModConfigEntry<>("use_scientific_notation",
          true,
          """
                If true, scientific notation will be used when the value is too long,
                and the above value is the number of significant figures to display.
                Otherwise, scientific notation will never be used,
                and the above value is the number of digits to display after the decimal point.""",
          Boolean.class);

    public static final ModConfigEntry<Boolean> DISPLAY_HEADER = new ModConfigEntry<>("display_header",
          true,
          "Whether to display a header in the debug entry.",
          Boolean.class);

    public static final ModConfigEntry<String> DEBUG_DISPLAY_COLOR = new ModConfigEntry<>("debug_display_color",
          "DEFAULT",
          """
                The color that the text added to the debug menu will be displayed in by default.
                See https://minecraft.wiki/w/Text_component_format for available colors.
                Not that DEFAULT is not the same as WHITE (it is slightly darker).
                RED is not recommended as it is used for errors.""",
          String.class);

    public static final ModConfigEntry<Map<Identifier, String>> CUSTOM_DISPLAY_COLORS = new ModConfigEntry<>(
          "custom_display_colors",
          Map.of(DFDebugMod.minecraftId("overworld/sloped_cheese"),
                "GREEN",
                DFDebugMod.minecraftId("nether/base_3d_noise"),
                "GOLD"),
          """
                Custom display colors that will be used when displaying certain density functions.
                Useful for easily distinguishing them at a glance.""",
          new TypeToken<Map<Identifier, String>>() {
          }.getType());

    public static final List<ModConfigEntry> CONFIG_ENTRIES = List.of(DISPLAYED_DENSITY_FUNCTIONS,
          DIMENSION_DENSITY_FUNCTIONS,
          DENSITY_FUNCTION_PRECISION,
          USE_SCIENTIFIC_NOTATION,
          DISPLAY_HEADER,
          DEBUG_DISPLAY_COLOR,
          CUSTOM_DISPLAY_COLORS);

    public static final Map<String, ModConfigEntry> CONFIG_KEYS = CONFIG_ENTRIES.stream()
          .collect(Collectors.toUnmodifiableMap(ModConfigEntry::key, Function.identity()));
}
