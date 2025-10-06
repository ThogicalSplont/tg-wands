package com.tathkage.tgwands.entity.custom;

import com.tathkage.tgwands.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import com.tathkage.tgwands.block.ModBlocks;

public class WaterPrisonBlockEntity extends BlockEntity {
    private int ticksExisted = 0;
    private static final int LIFETIME_TICKS = 400; // 5 seconds

    public WaterPrisonBlockEntity(BlockPos pos, BlockState state) {
        super(ModEntities.WATER_PRISON_ENTITY.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, WaterPrisonBlockEntity entity) {
        if (!level.isClientSide) {
            entity.ticksExisted++;
            if (entity.ticksExisted >= LIFETIME_TICKS) {
                level.removeBlock(pos, false);
            }
        }
    }
}
