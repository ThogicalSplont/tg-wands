package com.tathkage.tgwands.block.custom;

import com.tathkage.tgwands.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.block.entity.BlockEntity;

import com.tathkage.tgwands.entity.custom.WaterPrisonBlockEntity;

public class WaterPrisonBlock extends Block implements EntityBlock {

    public WaterPrisonBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == ModEntities.WATER_PRISON_ENTITY.get() && !level.isClientSide()
                ? (lvl, pos, st, be) -> WaterPrisonBlockEntity.tick(lvl, pos, st, (WaterPrisonBlockEntity) be)
                : null;
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier) {
        Vec3 motion = entity.getDeltaMovement();
        entity.setDeltaMovement(motion.multiply(0.25D, 0.05D, 0.25D)); // Slow movement

        if (entity instanceof net.minecraft.world.entity.LivingEntity livingEntity && level instanceof net.minecraft.server.level.ServerLevel serverLevel) {
            livingEntity.setAirSupply(livingEntity.getAirSupply() - 1);

            if (livingEntity.getAirSupply() <= -20) {
                livingEntity.setAirSupply(0);
                livingEntity.hurtServer(serverLevel, livingEntity.damageSources().drown(), 2.0F);
            }
        }

        super.entityInside(state, level, pos, entity, effectApplier);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new WaterPrisonBlockEntity(pos, state);
    }
}
