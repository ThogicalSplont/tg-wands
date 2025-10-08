package com.thogicalsplont.tgwands.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a temporary earth wall block that disappears after a short period of time.
 * <p>
 * This block uses Minecraft's random tick system to automatically remove itself
 * from the world after a random delay, simulating a temporary "summoned wall" effect.
 * </p>
 *
 * <p><strong>Behavior:</strong></p>
 * <ul>
 *   <li>When placed, it will remain until it randomly ticks.</li>
 *   <li>During a random tick, it will replace itself with air (disappear).</li>
 * </ul>
 *
 * <p><strong>Usage Example:</strong></p>
 * <pre>{@code
 * // Inside a custom wand item:
 * level.setBlock(targetPos, ModBlocks.EARTH_WALL.get().defaultBlockState(), 3);
 * }</pre>
 *
 * @author
 *     ThogicalSplont
 * @since
 *     0.1.0
 */
public class EarthWallBlock extends Block {

    /**
     * Constructs a new {@code EarthWallBlock} with the given block properties.
     *
     * @param properties the block behavior properties (e.g., strength, material, etc.)
     */
    public EarthWallBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    /**
     * Called randomly by Minecraft’s world tick system.
     * When triggered, this block removes itself from the world.
     *
     * @param state  the current block state
     * @param level  the server-level instance
     * @param pos    the position of the block
     * @param random the random source
     */
    @Override
    public void randomTick(
            @NotNull BlockState state,
            @NotNull ServerLevel level,
            @NotNull BlockPos pos,
            @NotNull RandomSource random
    ) {
        level.setBlock(pos, net.minecraft.world.level.block.Blocks.AIR.defaultBlockState(), 3);
    }

    /**
     * Determines whether this block should receive random ticks.
     * Returning {@code true} enables the randomTick method.
     *
     * @param state the block state
     * @return {@code true} if the block should tick randomly
     */
    @Override
    public boolean isRandomlyTicking(@NotNull BlockState state) {
        return true;
    }
}
