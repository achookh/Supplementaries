package net.mehvahdjukaar.supplementaries.integration.shulkerboxtooltip;

import com.misterpemodder.shulkerboxtooltip.api.PreviewContext;
import com.misterpemodder.shulkerboxtooltip.api.provider.BlockEntityPreviewProvider;
import com.misterpemodder.shulkerboxtooltip.api.renderer.PreviewRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.mehvahdjukaar.supplementaries.common.block.tiles.SackBlockTile;

public final class SackPreviewProvider extends BlockEntityPreviewProvider {

    @Environment(EnvType.CLIENT)
    private static PreviewRenderer renderer;

    public SackPreviewProvider() {
        super(SackBlockTile.getUnlockedSlots(), true);
    }

    @Override
    public int getInventoryMaxSize(PreviewContext context) {
        return SackBlockTile.getUnlockedSlots();
    }

    @Override
    @Environment(EnvType.CLIENT)
    public PreviewRenderer getRenderer() {
        if (renderer == null) {
            renderer = new VariableSizePreviewRenderer(SackBlockTile::getUnlockedSlots);
        }
        return renderer;
    }

}
