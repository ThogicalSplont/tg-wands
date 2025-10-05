//package com.tathkage.tgwands.block.custom;
//
//import net.minecraft.core.BlockPos;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.block.entity.BlockEntity;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.level.block.entity.BlockEntityTicker;
//import net.minecraft.world.level.block.entity.BlockEntityType;
//import net.minecraft.world.level.Level;
//
//import org.jetbrains.annotations.Nullable;
//
//public class EarthWallBlock extends Block {
//    public EarthWallBlock() {
//        super(properties);
//    }
//
//    @Override
//    public boolean hasBlockEntity(BlockState state) {
//        return true;
//    }
//
//    @Nullable
//    @Override
//    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
//        return new TemporaryDirtBlockEntity(pos, state);
//    }
//
//    @Nullable
//    @Override
//    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
//        return (lvl, pos, st, be) -> {
//            if (be instanceof TemporaryDirtBlockEntity tempEntity) {
//                tempEntity.tick();
//            }
//        };
//    }
//}
