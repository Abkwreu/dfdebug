package com.abkwreu.dfdebug.api;

import net.minecraft.world.level.levelgen.DensityFunction;

public interface RandomStateMixinAccessor {
    DensityFunction.Visitor getVisitor();
}
