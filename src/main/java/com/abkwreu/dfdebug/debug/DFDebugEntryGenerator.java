package com.abkwreu.dfdebug.debug;

import com.abkwreu.dfdebug.api.RandomStateMixinAccessor;
import com.abkwreu.dfdebug.config.ModConfig;
import com.abkwreu.dfdebug.config.ModConfigEntries;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.RandomState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DFDebugEntryGenerator {
    public static Optional<List<String>> generate(Level serverOrClientLevel, boolean includeHeader) {
        Minecraft minecraft = Minecraft.getInstance();
        Entity entity = minecraft.getCameraEntity();
        if (entity == null) {
            return Optional.empty();
        }

        BlockPos pos = entity.blockPosition();
        if (serverOrClientLevel instanceof ServerLevel serverLevel) {
            ChunkGenerator generator = serverLevel.getChunkSource().getGenerator();
            ResourceKey<Level> dimension = serverLevel.dimension();

            RegistryAccess registryAccess = serverLevel.registryAccess();
            RandomState randomState = serverLevel.getChunkSource().randomState();
            DensityFunction.Visitor visitor = getVisitor(randomState);

            List<Identifier> densityFunctionList = new ArrayList<>(ModConfig.get()
                  .get(ModConfigEntries.DISPLAYED_DENSITY_FUNCTIONS));
            getDimensionDensityFunctions(dimension).ifPresent(densityFunctionList::addAll);

            List<String> lines = new ArrayList<>();
            if (includeHeader) {
                lines.add("§nDensity Functions"); // underline
            }
            for (Identifier id : densityFunctionList) {
                lines.add(getDebugLine(registryAccess, id, visitor, pos));
            }

            return Optional.of(lines);
        }
        return Optional.empty();
    }

    static Optional<List<Identifier>> getDimensionDensityFunctions(ResourceKey<Level> dimension) {
        Identifier dimensionId = dimension.identifier();
        Map<Identifier, List<Identifier>> dimensionDensityFunctions = ModConfig.get()
              .get(ModConfigEntries.DIMENSION_DENSITY_FUNCTIONS);

        return Optional.ofNullable(dimensionDensityFunctions.get(dimensionId));
    }

    static String getDebugLine(RegistryAccess registryAccess,
                               Identifier id,
                               DensityFunction.Visitor visitor,
                               BlockPos pos) {
        ResourceKey<DensityFunction> dfKey = ResourceKey.create(Registries.DENSITY_FUNCTION, id);
        DensityFunction densityFunction;

        String formatCode, message;
        try {
            //? if >= 1.21.2 {
            densityFunction = registryAccess.getOrThrow(dfKey).value();
            //?} else {
			/*densityFunction = registryAccess.registryOrThrow(Registries.DENSITY_FUNCTION)
					.getHolderOrThrow(dfKey)
					.value();
			*///?}

            String formatString = getFormatString();
            formatCode = getFormatCode(id);

            double dfValue = getDFValue(densityFunction, visitor, pos);
            message = String.format(String.format("%%s: %s", formatString), id.toString(), dfValue);
        } catch (IllegalStateException e) {
            formatCode = "§c"; // red
            message = String.format("Density function %s not found", id.toString());
        }

        return String.format("%s%s", formatCode, message);
    }

    private static String getFormatString() {
        int precision = ModConfig.get().get(ModConfigEntries.DENSITY_FUNCTION_PRECISION);
        boolean scientific = ModConfig.get().get(ModConfigEntries.USE_SCIENTIFIC_NOTATION);
        return precision < 0 ? "%s" : String.format("%%.%s%s", precision, scientific ? "g" : "f");
    }

    private static String getFormatCode(Identifier id) {
        String colorName;

        Map<Identifier, String> customDisplayColors = ModConfig.get().get(ModConfigEntries.CUSTOM_DISPLAY_COLORS);
        if (customDisplayColors.containsKey(id)) {
            colorName = customDisplayColors.get(id);
        } else {
            colorName = ModConfig.get().get(ModConfigEntries.DEBUG_DISPLAY_COLOR);
        }

        colorName = colorName.toUpperCase();
        if (colorName.equals("DEFAULT")) {
            return "";
        }

        ChatFormatting formatting;
        //? if < 26.2 {
        formatting = ChatFormatting.getByName(colorName);
         //?} else {
        /*try {
            formatting = ChatFormatting.valueOf(colorName);
        } catch (IllegalArgumentException e) {
            formatting = null;
        }
        *///?}

        char formatCode;
        if (formatting == null) {
            return "";
        } else {
            //? if < 26.2 {
            formatCode = formatting.getChar();
             //?} else {
            /*String s = formatting.toString();
            formatCode = s.charAt(s.length() - 1);
            *///?}
        }

        return String.format("§%s", formatCode);
    }

    static double getDFValue(DensityFunction df, DensityFunction.Visitor visitor, BlockPos pos) {
        DensityFunction mapped = df.mapAll(visitor);
        DensityFunction.SinglePointContext context = new DensityFunction.SinglePointContext(pos.getX(),
              pos.getY(),
              pos.getZ());

        return mapped.compute(context);
    }

    static DensityFunction.Visitor getVisitor(RandomState randomState) {
        RandomStateMixinAccessor accessor = (RandomStateMixinAccessor) (Object) randomState;
        return accessor.getVisitor();
    }
}
