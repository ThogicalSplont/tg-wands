package com.tathkage.tgwands.item.EarthWand;

import com.tathkage.tgwands.TGWands;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class EarthWandItem extends Item {
    public EarthWandItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level world, Player player, InteractionHand hand) {
        TGWands.LOGGER.info("[EarthWand] use() called by player: {}", player.getName().getString());

        if (!world.isClientSide) {
            TGWands.LOGGER.info("[EarthWand] Server side confirmed.");

            // Raycast to find where player is looking
            HitResult hit = player.pick(50.0D, 0.0F, false);
            TGWands.LOGGER.info("[EarthWand] Raycast hit type: {}", hit.getType());

            if (hit.getType() == HitResult.Type.BLOCK) {
                Vec3 pos = hit.getLocation();
                TGWands.LOGGER.info("[EarthWand] Hit location: x={}, y={}, z={}", pos.x, pos.y, pos.z);

                BlockPos basePos = new BlockPos((int) pos.x, (int) pos.y, (int) pos.z);
                Direction playerFacing = player.getDirection();
                TGWands.LOGGER.info("[EarthWand] Player facing: {}", playerFacing);

                final BlockPos.MutableBlockPos[] mutablePos = {new BlockPos.MutableBlockPos()};
                BlockState dirtBlock = Blocks.DIRT.defaultBlockState();

                TGWands.LOGGER.info("[EarthWand] Creating 3x4 dirt wall...");
                for (int dx = -1; dx <= 1; dx++) { // width = 3
                    for (int dy = 0; dy < 4; dy++) { // height = 4
                        mutablePos[0].set(basePos);
                        mutablePos[0] = mutablePos[0].relative(playerFacing.getOpposite(), 1)
                                .offset(dx, dy, 0).mutable();

                        TGWands.LOGGER.info("[EarthWand] Checking block at {}", mutablePos[0]);
                        if (world.isEmptyBlock(mutablePos[0])) {
                            TGWands.LOGGER.info("[EarthWand] Placing dirt at {}", mutablePos[0]);
                            world.setBlock(mutablePos[0], dirtBlock, 3);
                        } else {
                            TGWands.LOGGER.info("[EarthWand] Skipping non-air block at {}", mutablePos[0]);
                        }
                    }
                }

                TGWands.LOGGER.info("[EarthWand] Scheduling wall removal in 5 seconds.");
                if (world instanceof ServerLevel serverWorld) {
                    TGWands.LOGGER.info("[EarthWand] Scheduling wall removal in 100 ticks.");

                    serverWorld.getServer().addTickable(new Runnable() {
                        int ticks = 0;

                        @Override
                        public void run() {
                            ticks++;

                            if (ticks >= 100) { // 5 seconds (20 ticks per second)
                                TGWands.LOGGER.info("[EarthWand] Removing wall after delay.");

                                for (int dx = -1; dx <= 1; dx++) {
                                    for (int dy = 0; dy < 4; dy++) {
                                        mutablePos[0].set(basePos);
                                        mutablePos[0] = mutablePos[0].relative(playerFacing.getOpposite(), 1)
                                                .offset(dx, dy, 0).mutable();

                                        if (world.getBlockState(mutablePos[0]).is(Blocks.DIRT)) {
                                            TGWands.LOGGER.info("[EarthWand] Removing dirt at {}", mutablePos[0]);
                                            world.setBlock(mutablePos[0], Blocks.AIR.defaultBlockState(), 3);
                                        }
                                    }
                                }

                                // Stop ticking after removal
                                return;
                            }

                            // Keep ticking
                            serverWorld.getServer().addTickable(this);
                        }
                    });
                }

                world.playSound(
                        null,
                        player.getX(),
                        player.getY(),
                        player.getZ(),
                        SoundEvents.GRASS_PLACE,
                        SoundSource.PLAYERS,
                        1.0F,
                        1.0F
                );

                player.awardStat(Stats.ITEM_USED.get(this));
                TGWands.LOGGER.info("[EarthWand] Wall creation successful.");
                return InteractionResult.SUCCESS;
            } else {
                TGWands.LOGGER.info("[EarthWand] Raycast did not hit a block.");
            }
        } else {
            TGWands.LOGGER.info("[EarthWand] Client side detected, skipping logic.");
        }

        return InteractionResult.PASS;
    }
}
