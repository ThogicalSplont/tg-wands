package com.tathkage.tgwands.block.custom;


import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class EarthWallBlock extends Block {

    public EarthWallBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        // This is called during a random tick
        level.setBlock(pos, net.minecraft.world.level.block.Blocks.AIR.defaultBlockState(), 3);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true; // Allow random ticks
    }
}