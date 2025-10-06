package com.tathkage.tgwands.item.custom;

import com.tathkage.tgwands.TGWands;
import com.tathkage.tgwands.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class WaterWandItem extends Item {
    public WaterWandItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level world, Player player, InteractionHand hand) {
        TGWands.LOGGER.info("[WaterWand] use() called by player: {}", player.getName().getString());

        if (!world.isClientSide) {
            TGWands.LOGGER.info("[WaterWand] Server side confirmed.");

            // Find the position 10 blocks ahead of where the player is looking
            Vec3 lookVec = player.getLookAngle().normalize().scale(10);
            Vec3 sphereCenterVec = player.position().add(lookVec);

            BlockPos centerPos = new BlockPos(
                    (int) sphereCenterVec.x,
                    (int) sphereCenterVec.y,
                    (int) sphereCenterVec.z
            );

            BlockState waterPrisonBlock = ModBlocks.WATER_PRISON.get().defaultBlockState();

            TGWands.LOGGER.info("[WaterWand] Creating sphere at {}", centerPos);

            int radius = 4; // sphere radius

            for (int dx = -radius; dx <= radius; dx++) {
                for (int dy = -radius; dy <= radius; dy++) {
                    for (int dz = -radius; dz <= radius; dz++) {
                        BlockPos spherePos = centerPos.offset(dx, dy, dz);

                        // Sphere condition: distance from center <= radius
                        double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
                        if (distance <= radius) {
                            if (world.isEmptyBlock(spherePos)) { // don't overwrite existing blocks
                                world.setBlock(spherePos, waterPrisonBlock, 3);
                            }
                        }
                    }
                }
            }

            // Play water-related sound effect
            world.playSound(
                    null,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    SoundEvents.BUCKET_FILL,
                    SoundSource.PLAYERS,
                    1.0F,
                    1.0F
            );

            player.awardStat(Stats.ITEM_USED.get(this));
            TGWands.LOGGER.info("[WaterWand] Sphere creation successful.");
            return InteractionResult.SUCCESS;
        } else {
            TGWands.LOGGER.info("[WaterWand] Client side detected, skipping logic.");
        }

        return InteractionResult.PASS;
    }
}
