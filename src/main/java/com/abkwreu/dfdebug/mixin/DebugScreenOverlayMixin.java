package com.abkwreu.dfdebug.mixin;

//? if fabric && < 1.21.9 {

/*import com.abkwreu.dfdebug.config.ModConfig;
import com.abkwreu.dfdebug.config.ModConfigEntries;
import com.abkwreu.dfdebug.debug.DFDebugEntryGenerator;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;

@Mixin(DebugScreenOverlay.class)
public abstract class DebugScreenOverlayMixin {
	@Shadow
	@Nullable
	protected abstract ServerLevel getServerLevel();

	@Inject(method="getGameInformation", at=@At("RETURN"), cancellable=true)
	// injects the mod's debug entry into the debug screen
	// fabric pre-1.21.9 provides no event handler for adding debug text
	public void addDebugText(CallbackInfoReturnable<List<String>> cir) {
		List<String> returnValue = cir.getReturnValue();

		Optional<List<String>> lines = DFDebugEntryGenerator.generate(getServerLevel(),
				ModConfig.get().get(ModConfigEntries.DISPLAY_HEADER));
		lines.ifPresent(value -> {
			returnValue.add("");
			returnValue.addAll(value);
		});

		cir.setReturnValue(returnValue);
	}
}
*///?}
