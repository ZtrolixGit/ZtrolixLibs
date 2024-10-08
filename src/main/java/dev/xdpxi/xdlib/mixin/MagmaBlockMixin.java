package dev.xdpxi.xdlib.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.xdpxi.xdlib.ModBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BubbleColumnBlock;
import net.minecraft.block.MagmaBlock;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MagmaBlock.class)
public class MagmaBlockMixin extends Block {
    public MagmaBlockMixin(Settings settings) {
        super(settings);
    }

    @ModifyExpressionValue(method = "getStateForNeighborUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"))
    public boolean isOf(boolean original, @Local(ordinal = 1, argsOnly = true) BlockState neighborState) {
        return original || (ModBlockTags.isACopperGrates(neighborState) && neighborState.contains(Properties.WATERLOGGED));
    }

    @Inject(method = "getStateForNeighborUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/WorldAccess;scheduleBlockTick(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;I)V"))
    public void addMoreSchedules(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos, CallbackInfoReturnable<BlockState> cir) {
        final BlockPos.Mutable movePos = pos.mutableCopy().move(Direction.UP).move(Direction.UP);
        BubbleColumnBlock.update(world, movePos, world.getBlockState(pos));
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        super.onBroken(world, pos, state);
        BlockPos.Mutable mutable = pos.mutableCopy().move(Direction.UP);
        while (ModBlockTags.isACopperGrates(world.getBlockState(mutable))) {
            mutable.move(Direction.UP);
            world.scheduleBlockTick(mutable, world.getBlockState(mutable).getBlock(), 20);
        }
    }
}