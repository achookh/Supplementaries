package net.mehvahdjukaar.supplementaries.mixins;

import net.mehvahdjukaar.supplementaries.common.block.IAntiquable;
import net.mehvahdjukaar.supplementaries.reg.ModTextures;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(BookViewScreen.class)
public abstract class BookViewScreenMixin {

    @Shadow
    private BookViewScreen.BookAccess bookAccess;

    @Shadow
    protected abstract void init();

    @Shadow private PageButton forwardButton;

    @Shadow private PageButton backButton;
}
