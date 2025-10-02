package com.tathkage.tgwands.item.EarthWand;

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

        if (!world.isClientSide) {
            // Raycast to find where player is looking
            HitResult hit = player.pick(50.0D, 0.0F, false);

            if (hit.getType() == HitResult.Type.BLOCK) {
                Vec3 pos = hit.getLocation();
                BlockPos basePos = new BlockPos((int) pos.x, (int) pos.y, (int) pos.z);

                Direction playerFacing = player.getDirection(); // The direction player is looking

                final BlockPos.MutableBlockPos[] mutablePos = {new BlockPos.MutableBlockPos()};

                BlockState dirtBlock = Blocks.DIRT.defaultBlockState();

                // Create a 3x4 wall
                for (int dx = -1; dx <= 1; dx++) { // width = 3
                    for (int dy = 0; dy < 4; dy++) { // height = 4
                        mutablePos[0].set(basePos);

                        // Offset position so wall faces player
                        mutablePos[0] = mutablePos[0].relative(playerFacing.getOpposite(), 1) // wall in front of target
                                .offset(dx, dy, 0).mutable();

                        // Only replace air blocks
                        if (world.isEmptyBlock(mutablePos[0])) {
                            world.setBlock(mutablePos[0], dirtBlock, 3);
                        }
                    }
                }

                // Schedule removal after 5 seconds (100 ticks)
                if (world instanceof ServerLevel serverWorld) {
                    serverWorld.getServer().execute(new Runnable() {
                        @Override
                        public void run() {
                            for (int dx = -1; dx <= 1; dx++) {
                                for (int dy = 0; dy < 4; dy++) {
                                    mutablePos[0].set(basePos);
                                    mutablePos[0] = mutablePos[0].relative(playerFacing.getOpposite(), 1)
                                            .offset(dx, dy, 0).mutable();

                                    if (world.getBlockState(mutablePos[0]).is(Blocks.DIRT)) {
                                        world.setBlock(mutablePos[0], Blocks.AIR.defaultBlockState(), 3);
                                    }
                                }
                            }
                        }
                    }); // 100 ticks = 5 seconds
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
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }
}
