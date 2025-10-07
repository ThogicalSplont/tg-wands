package com.thogicalsplont.tgwands.entity.custom;

import com.thogicalsplont.tgwands.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the block entity logic for the {@link com.thogicalsplont.tgwands.block.custom.WaterPrisonBlock}.
 * <p>
 * The {@code WaterPrisonBlockEntity} manages the lifespan of a Water Prison block,
 * automatically removing the block after a set duration to simulate the magical
 * prison dissipating over time.
 * </p>
 *
 * <p><strong>Behavior:</strong></p>
 * <ul>
 *   <li>Exists for a fixed number of game ticks (default: 400 ticks or ~20 seconds).</li>
 *   <li>Automatically removes itself when its lifetime expires.</li>
 *   <li>Does not perform any actions on the client side.</li>
 * </ul>
 *
 * @see com.thogicalsplont.tgwands.block.custom.WaterPrisonBlock
 * @since 1.0.0
 * @author
 *     Tathluach Chol
 */
public class WaterPrisonBlockEntity extends BlockEntity {

    /**
     * The number of ticks this block entity has existed for.
     */
    private int ticksExisted = 0;

    /**
     * The total lifespan of the block entity in ticks before it disappears.
     * <p>
     * Default: 400 ticks (20 seconds at 20 ticks per second).
     * </p>
     */
    private static final int LIFETIME_TICKS = 400;

    /**
     * Constructs a new {@code WaterPrisonBlockEntity} at the given position and state.
     *
     * @param pos   the position of the block in the world
     * @param state the current block state
     */
    public WaterPrisonBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        super(ModEntities.WATER_PRISON_ENTITY.get(), pos, state);
    }

    /**
     * Performs server-side ticking logic for the water prison block.
     * <p>
     * Each tick increases the entity’s internal timer. When the lifetime threshold
     * is reached, the block is automatically removed from the world.
     * </p>
     *
     * @param level  the level where this entity exists
     * @param pos    the block position
     * @param state  the current block state
     * @param entity the instance of {@code WaterPrisonBlockEntity} being ticked
     */
    public static void tick(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull WaterPrisonBlockEntity entity) {
        if (!level.isClientSide) {
            entity.ticksExisted++;

            // Remove the block when its lifetime expires
            if (entity.ticksExisted >= LIFETIME_TICKS) {
                level.removeBlock(pos, false);
            }
        }
    }
}
