package com.thogicalsplont.tgwands.item.custom;

import com.thogicalsplont.tgwands.block.ModBlocks;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a magical wand that allows the player to summon an {@link com.thogicalsplont.tgwands.block.custom.EarthWallBlock}.
 * <p>
 * When used, the {@code EarthWandItem} casts a short-range spell that creates a temporary 3×4 wall of earth
 * in front of the player’s facing direction. The summoned blocks automatically disappear after a short duration
 * (as handled by {@link com.thogicalsplont.tgwands.block.custom.EarthWallBlock}).
 * </p>
 *
 * <p><strong>Behavior:</strong></p>
 * <ul>
 *   <li>Performs a raycast up to 50 blocks in front of the player.</li>
 *   <li>Places a 3-block-wide, 4-block-tall wall of {@code EarthWallBlock} one block in front of the hit location.</li>
 *   <li>Plays a dirt/grass placement sound and counts as an item use.</li>
 * </ul>
 *
 * <p><strong>Usage Example:</strong></p>
 * <pre>{@code
 * // In-game: Right-click while holding the wand
 * // A wall of earth appears where you're looking
 * }</pre>
 *
 * @author
 *     ThogicalSplont
 * @since
 *     0.1.0
 */
public class EarthWandItem extends Item {

    /**
     * Constructs a new {@code EarthWandItem} with the given item properties.
     *
     * @param properties the item’s behavior and attributes (e.g., durability, rarity, etc.)
     */
    public EarthWandItem(Item.Properties properties) {
        super(properties);
    }

    /**
     * Called when the player right-clicks while holding this item.
     * <p>
     * The wand will raycast up to 50 blocks ahead to find a target surface,
     * and if a block is hit, it creates a 3×4 earth wall in front of that surface.
     * </p>
     *
     * @param world  the current world level
     * @param player the player using the item
     * @param hand   the hand used (main or off-hand)
     * @return an {@link InteractionResult} indicating the outcome of the use action
     */
    @Override
    public @NotNull InteractionResult use(@NotNull Level world, @NotNull Player player, @NotNull InteractionHand hand) {

        // Server-side logic only
        if (!world.isClientSide) {

            // Perform a raycast (up to 50 blocks) to find where the player is looking
            HitResult hit = player.pick(50.0D, 0.0F, false);

            if (hit.getType() == HitResult.Type.BLOCK) {
                Vec3 hitLocation = hit.getLocation();

                // Determine base position and player direction
                BlockPos basePos = new BlockPos((int) hitLocation.x, (int) hitLocation.y, (int) hitLocation.z);
                Direction playerFacing = player.getDirection();

                // Default Earth Wall block state
                BlockState earthWallBlock = ModBlocks.EARTH_WALL.get().defaultBlockState();

                // Create a 3x4 wall structure
                for (int dx = -1; dx <= 1; dx++) { // width = 3
                    for (int dy = 0; dy < 4; dy++) { // height = 4
                        BlockPos wallPos = basePos
                                .relative(playerFacing.getOpposite(), 1)
                                .offset(dx, dy, 0);

                        if (world.isEmptyBlock(wallPos)) {
                            world.setBlock(wallPos, earthWallBlock, 3);
                        }
                    }
                }

                // Play a dirt/grass placement sound effect
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

                // Register item usage in statistics
                player.awardStat(Stats.ITEM_USED.get(this));

                return InteractionResult.SUCCESS;
            }
        }

        // Do nothing client-side or if no block was hit
        return InteractionResult.PASS;
    }
}
