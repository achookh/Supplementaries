package net.mehvahdjukaar.supplementaries.client.renderers.items;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.mehvahdjukaar.moonlight.api.client.ItemStackRenderer;
import net.mehvahdjukaar.moonlight.api.client.model.BakedQuadBuilder;
import net.mehvahdjukaar.moonlight.api.client.util.VertexUtil;
import net.mehvahdjukaar.moonlight.api.platform.ClientHelper;
import net.mehvahdjukaar.supplementaries.Supplementaries;
import net.mehvahdjukaar.supplementaries.configs.ClientConfigs;
import net.mehvahdjukaar.supplementaries.reg.ClientRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AltimeterItemRenderer extends ItemStackRenderer {

    private static final Map<ResourceKey<Level>, Pair<TextureAtlasSprite, Int2ObjectMap<BakedModel>>> MODEL_CACHE = new HashMap<>();

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext transformType, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        poseStack.translate(0.5, 0.5, 0.5);


        ClientLevel level = Minecraft.getInstance().level;
        ResourceKey<Level> dimension = level == null ? Level.OVERWORLD : level.dimension();


        var pair = MODEL_CACHE.getOrDefault(dimension, MODEL_CACHE.get(Level.OVERWORLD));
        if(pair == null) {
            Supplementaries.error();
            return;
        }
        TextureAtlasSprite sprite = pair.getFirst();
        int textureH = sprite.contents().height();

        double stripDepth = calculateDepthIndex(level, textureH);
        int mult = ClientConfigs.Items.DEPTH_METER_STEP_MULT.get();
        int index = (int) Math.round(stripDepth * mult);

        poseStack.popPose();
    }

    private static double calculateDepthIndex(@Nullable ClientLevel level, int textureH) {
        int min = level == null ? -64 : level.getMinBuildHeight();
        int max = level == null ? 312 : level.getMaxBuildHeight();

        LocalPlayer player = Minecraft.getInstance().player;
        double depth = player == null ? 64 : player.position().y;
        //from 0 to 1
        double normDepth = Mth.clamp((depth - min) / (max - min), 0, 1);
        return (normDepth * (textureH - 6));
    }


    public static void onReload() {
        MODEL_CACHE.clear();
        List<ResourceLocation> resourceLocations = new ArrayList<>(ClientConfigs.Items.DEPTH_METER_DIMENSIONS.get());
        resourceLocations.add(Level.OVERWORLD.location());
        for (var d : resourceLocations) {
            ResourceKey<Level> res = ResourceKey.create(Registries.DIMENSION, d);
            TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(
                    Supplementaries.res("item/altimeter/" + d.toString().replace(":", "_"))
            );
            if (sprite != null) {
                MODEL_CACHE.put(res, Pair.of(sprite, new Int2ObjectOpenHashMap<>()));
            }
        }
    }

}
