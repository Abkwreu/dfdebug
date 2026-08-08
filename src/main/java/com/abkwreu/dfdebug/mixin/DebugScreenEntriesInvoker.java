package com.abkwreu.dfdebug.mixin;

//? if neoforge && >= 1.21.9 {

/*import net.minecraft.client.gui.components.debug.DebugScreenEntries;
import net.minecraft.client.gui.components.debug.DebugScreenEntry;
import net.minecraft.resources.Identifier;
import org.apache.commons.lang3.NotImplementedException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(DebugScreenEntries.class)
public interface DebugScreenEntriesInvoker {
	@Invoker("register")
	// allows register() to be invoked on neoforge post-1.21.9
	static Identifier callRegister(final Identifier identifier, final DebugScreenEntry entry) {
		throw new NotImplementedException();
	}
}
*///?}
