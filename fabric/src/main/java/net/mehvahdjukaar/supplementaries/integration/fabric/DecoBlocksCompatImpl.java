package net.mehvahdjukaar.supplementaries.integration.fabric;

import com.google.common.base.Suppliers;
import lilypuree.decorative_blocks.blocks.ChandelierBlock;
import lilypuree.decorative_blocks.blocks.PalisadeBlock;
import net.mehvahdjukaar.moonlight.api.platform.ClientHelper;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import net.mehvahdjukaar.supplementaries.Supplementaries;
import net.mehvahdjukaar.supplementaries.common.block.blocks.AbstractRopeBlock;
import net.mehvahdjukaar.supplementaries.integration.CompatHandler;
import net.mehvahdjukaar.supplementaries.integration.CompatObjects;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootParams;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class DecoBlocksCompatImpl {


    public static boolean isPalisade(BlockState state) {
        return state.getBlock() instanceof PalisadeBlock;
    }

    public static void tryConvertingRopeChandelier(BlockState facingState, LevelAccessor world, BlockPos facingPos) {

    }

    public static void init() {
    }


    public static class RopeChandelierBlock extends ChandelierBlock {
        private final Supplier<Block> mimic;
        private final Supplier<BlockState> defMimic;
        protected final Supplier<SimpleParticleType> particleData;

        public <T extends ParticleType<?>> RopeChandelierBlock(Properties properties, Supplier<Block> chandelier, Supplier<T> particleData) {
            super(properties, false);

            this.mimic = chandelier;
            this.defMimic = Suppliers.memoize(() -> this.mimic.get().defaultBlockState());

            this.particleData = Suppliers.memoize(() -> {
                SimpleParticleType data = (SimpleParticleType) particleData.get();
                if (data == null) data = ParticleTypes.FLAME;
                return data;
            });
        }

        @Override
        public MutableComponent getName() {
            return mimic.get().getName();
        }


        @Override
        public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
            if (facing == Direction.UP && !(facingState.getBlock() instanceof AbstractRopeBlock)) {
                return defMimic.get();
            }
            return stateIn;
        }

        @Override
        public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, Random rand) {
            double d0 = pos.getX() + 0.5D;
            double d1 = pos.getY() + 0.7D;
            double d2 = pos.getZ() + 0.5D;
            double off1 = 0.1875D;
            double off2 = 0.3125D;
            double off3 = 0.0625D;
            worldIn.addParticle(ParticleTypes.SMOKE, d0 - off1, d1, d2 - off2, 0.0D, 0.0D, 0.0D);
            worldIn.addParticle(ParticleTypes.SMOKE, d0 - off2 - off3, d1, d2 + off1 - off3, 0.0D, 0.0D, 0.0D);
            worldIn.addParticle(ParticleTypes.SMOKE, d0 + off1 - off3, d1, d2 + off2 + off3, 0.0D, 0.0D, 0.0D);
            worldIn.addParticle(ParticleTypes.SMOKE, d0 + off2, d1, d2 - off1, 0.0D, 0.0D, 0.0D);

            worldIn.addParticle(particleData.get(), d0 - off1, d1, d2 - off2, 0.0D, 0.0D, 0.0D);
            worldIn.addParticle(particleData.get(), d0 - off2 - off3, d1, d2 + off1 - off3, 0.0D, 0.0D, 0.0D);
            worldIn.addParticle(particleData.get(), d0 + off1 - off3, d1, d2 + off2 + off3, 0.0D, 0.0D, 0.0D);
            worldIn.addParticle(particleData.get(), d0 + off2, d1, d2 - off1, 0.0D, 0.0D, 0.0D);
        }

    }


    public static void setupClient() {
    }
}
