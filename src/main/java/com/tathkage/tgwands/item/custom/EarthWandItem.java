package com.tathkage.tgwands.item.custom;

import com.tathkage.tgwands.TGWands;
import com.tathkage.tgwands.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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

import java.util.ArrayList;
import java.util.List;

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

                BlockState earthWallBlock = ModBlocks.EARTH_WALL.get().defaultBlockState();

                TGWands.LOGGER.info("[EarthWand] Creating 3x4 dirt wall...");
                for (int dx = -1; dx <= 1; dx++) { // width = 3
                    for (int dy = 0; dy < 4; dy++) { // height = 4
                        BlockPos wallPos = basePos
                                .relative(playerFacing.getOpposite(), 1)
                                .offset(dx, dy, 0);

                        TGWands.LOGGER.info("[EarthWand] Checking block at {}", wallPos);
                        if (world.isEmptyBlock(wallPos)) {
                            TGWands.LOGGER.info("[EarthWand] Placing dirt at {}", wallPos);
                            world.setBlock(wallPos, earthWallBlock, 3);
                        } else {
                            TGWands.LOGGER.info("[EarthWand] Skipping non-air block at {}", wallPos);
                        }
                    }
                }

                // Play sound effect
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
