package net.mehvahdjukaar.supplementaries.mixins.fabric;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.moulberry.flashback.io.ReplayReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ReplayReader.class)
public class FlahsbackShushMixin {

    @ModifyExpressionValue(method = "handleSnapshot",
            remap = false,
            at = @At(value = "INVOKE",
                    remap = true,
                    target = "Lio/netty/buffer/ByteBuf;readerIndex()I"))
    private int aaa(int original) {
        return Integer.MAX_VALUE;
    }
}
