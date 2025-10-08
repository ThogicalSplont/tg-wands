package com.thogicalsplont.tgwands.item.custom;

import com.thogicalsplont.tgwands.block.ModBlocks;
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
import org.jetbrains.annotations.NotNull;

/**
 * Represents a magical wand that creates a spherical water prison in front of the player.
 * <p>
 * When used, the {@code WaterWandItem} projects a sphere of {@link
 * com.thogicalsplont.tgwands.block.custom.WaterPrisonBlock} blocks centered approximately
 * 10 blocks in front of the player. The sphere does not overwrite existing blocks.
 * </p>
 *
 * <p><strong>Behavior:</strong></p>
 * <ul>
 *   <li>Determines a target position based on player's look direction.</li>
 *   <li>Generates a spherical structure of radius 4 composed of water prison blocks.</li>
 *   <li>Plays a water-related sound effect upon casting.</li>
 *   <li>Tracks usage statistics.</li>
 * </ul>
 *
 * <p><strong>Usage Example:</strong></p>
 * <pre>{@code
 * // In-game: Right-click while holding the Water Wand
 * // A water prison sphere is generated 10 blocks ahead
 * }</pre>
 *
 * @author
 *     ThogicalSplont
 * @since
 *     0.1.0
 */
public class WaterWandItem extends Item {

    /**
     * Distance ahead of the player where the sphere will be generated.
     */
    private static final double TARGET_DISTANCE = 10.0D;

    /**
     * Radius of the water prison sphere in blocks.
     */
    private static final int SPHERE_RADIUS = 4;

    /**
     * Constructs a new {@code WaterWandItem} with the given properties.
     *
     * @param properties the item’s behavior and attributes
     */
    public WaterWandItem(Item.Properties properties) {
        super(properties);
    }

    /**
     * Called when the player right-clicks while holding this wand.
     * <p>
     * Creates a sphere of {@link com.thogicalsplont.tgwands.block.custom.WaterPrisonBlock}
     * blocks centered a set distance ahead of the player.
     * </p>
     *
     * @param world  the current level
     * @param player the player using the wand
     * @param hand   the hand used (main or off-hand)
     * @return {@link InteractionResult#SUCCESS} if the sphere was created, otherwise {@link InteractionResult#PASS}
     */
    @Override
    public @NotNull InteractionResult use(@NotNull Level world, @NotNull Player player, @NotNull InteractionHand hand) {

        if (!world.isClientSide) {
            // Calculate the target center position for the sphere
            Vec3 lookVec = player.getLookAngle().normalize().scale(TARGET_DISTANCE);
            Vec3 sphereCenterVec = player.position().add(lookVec);

            BlockPos centerPos = new BlockPos(
                    (int) sphereCenterVec.x,
                    (int) sphereCenterVec.y,
                    (int) sphereCenterVec.z
            );

            BlockState waterPrisonBlock = ModBlocks.WATER_PRISON.get().defaultBlockState();

            // Generate sphere of water prison blocks
            for (int dx = -SPHERE_RADIUS; dx <= SPHERE_RADIUS; dx++) {
                for (int dy = -SPHERE_RADIUS; dy <= SPHERE_RADIUS; dy++) {
                    for (int dz = -SPHERE_RADIUS; dz <= SPHERE_RADIUS; dz++) {
                        BlockPos spherePos = centerPos.offset(dx, dy, dz);

                        double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
                        if (distance <= SPHERE_RADIUS && world.isEmptyBlock(spherePos)) {
                            world.setBlock(spherePos, waterPrisonBlock, 3);
                        }
                    }
                }
            }

            // Play water placement sound effect
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

            // Register item usage
            player.awardStat(Stats.ITEM_USED.get(this));

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}
