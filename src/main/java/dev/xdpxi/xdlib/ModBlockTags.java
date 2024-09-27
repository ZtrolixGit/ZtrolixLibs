package dev.xdpxi.xdlib;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

import java.util.Arrays;
import java.util.List;

public class ModBlockTags {
    private final static List<Block> copperGrates = Arrays.asList(Blocks.COPPER_GRATE, Blocks.EXPOSED_COPPER_GRATE, Blocks.WAXED_COPPER_GRATE, Blocks.OXIDIZED_COPPER_GRATE, Blocks.WEATHERED_COPPER_GRATE, Blocks.WAXED_EXPOSED_COPPER_GRATE, Blocks.WAXED_OXIDIZED_COPPER_GRATE, Blocks.WAXED_WEATHERED_COPPER_GRATE);

    public static boolean isACopperGrates(BlockState state) {
        return copperGrates.stream().anyMatch(t -> t.getDefaultState().isOf(state.getBlock()));
    }
}