package com.abkwreu.dfdebug.mixin;

import com.abkwreu.dfdebug.api.RandomStateMixinAccessor;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.RandomState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(RandomState.class)
public abstract class RandomStateMixin implements RandomStateMixinAccessor {

    @Unique
    private DensityFunction.Visitor visitor;

    //? if <= 26.2 {
	@ModifyArg(method = "<init>(Lnet/minecraft/world/level/levelgen/NoiseGeneratorSettings;" +
	                    "Lnet/minecraft/core/HolderGetter;J)V",
			at = @At(value = "INVOKE",
					target = "Lnet/minecraft/world/level/levelgen/NoiseRouter;" +
					         "mapAll(Lnet/minecraft/world/level/levelgen/DensityFunction$Visitor;" +
					         ")Lnet/minecraft/world/level/levelgen/NoiseRouter;"),
			index = 0)
	//?} else {
    /*@ModifyArg(method="<init>(Lnet/minecraft/core/HolderGetter;" +
                      "JZLnet/minecraft/world/level/block/state/BlockState;" +
                      "ILnet/minecraft/world/level/levelgen/NoiseRouter;Ljava/util/List;" +
                      "Ljava/util/Optional;Ljava/util/List;)V",
          at=@At(value="INVOKE",
                target="Lnet/minecraft/world/level/levelgen/NoiseRouter;" +
                       "mapAll(Lnet/minecraft/world/level/levelgen/DensityFunction$Visitor;" +
                       ")Lnet/minecraft/world/level/levelgen/NoiseRouter;"),
          index=0)
          *///?}
    // captures the visitor as it is being created inside the constructor for RandomState
    private DensityFunction.Visitor captureVisitor(DensityFunction.Visitor visitor) {
        this.visitor = visitor;
        return visitor;
    }

    @Override
    public DensityFunction.Visitor getVisitor() {
        return visitor;
    }
}
