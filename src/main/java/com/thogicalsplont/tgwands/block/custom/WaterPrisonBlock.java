package com.thogicalsplont.tgwands.block.custom;

import com.thogicalsplont.tgwands.block.entity.ModBlockEntities;
import com.thogicalsplont.tgwands.block.entity.custom.WaterPrisonBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a water prison block that traps and slows entities caught inside it.
 * <p>
 * The {@code WaterPrisonBlock} contains a {@link WaterPrisonBlockEntity} that can manage
 * timed behavior or additional state. While inside this block, entities experience
 * heavy movement resistance and gradually lose air, eventually taking drowning damage.
 * </p>
 *
 * <p><strong>Behavior:</strong></p>
 * <ul>
 *   <li>Slows entity movement along X, Y, and Z axes.</li>
 *   <li>Depletes air supply for living entities and inflicts drowning damage if submerged too long.</li>
 *   <li>Uses a {@link BlockEntityTicker} for server-side ticking logic.</li>
 * </ul>
 *
 * <p><strong>Usage Example:</strong></p>
 * <pre>{@code
 * // Inside a wand item use method:
 * level.setBlock(targetPos, ModBlocks.WATER_PRISON.get().defaultBlockState(), 3);
 * }</pre>
 *
 * @author
 *     ThogicalSplont
 * @since
 *     0.1.0
 */
public class WaterPrisonBlock extends Block implements EntityBlock {

    /**
     * Constructs a new {@code WaterPrisonBlock} with the specified behavior properties.
     *
     * @param properties the block’s behavior properties (e.g., material, strength, etc.)
     */
    public WaterPrisonBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    /**
     * Provides a ticker for the {@link WaterPrisonBlockEntity}.
     * <p>
     * The ticker is only active on the server side and will execute the
     * {@link WaterPrisonBlockEntity#tick(Level, BlockPos, BlockState, WaterPrisonBlockEntity)} method.
     * </p>
     *
     * @param level the current world level
     * @param state the block state
     * @param type  the block entity type
     * @param <T>   the block entity class
     * @return a ticker function for the water prison entity, or {@code null} if not applicable
     */
    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            @NotNull Level level,
            @NotNull BlockState state,
            @NotNull BlockEntityType<T> type
    ) {
        return type == ModBlockEntities.WATER_PRISON_ENTITY.get() && !level.isClientSide()
                ? (lvl, pos, st, be) -> WaterPrisonBlockEntity.tick(lvl, pos, st, (WaterPrisonBlockEntity) be)
                : null;
    }

    /**
     * Called every tick when an entity is inside this block.
     * <p>
     * This method slows the entity’s motion, gradually drains its air supply,
     * and inflicts drowning damage when air runs out.
     * </p>
     *
     * @param state          the block state
     * @param level          the current level
     * @param pos            the position of the block
     * @param entity         the entity inside the block
     * @param effectApplier  the effect applier (unused, but part of the method signature)
     */
    @Override
    public void entityInside(
            @NotNull BlockState state,
            @NotNull Level level,
            @NotNull BlockPos pos,
            @NotNull Entity entity,
            @NotNull InsideBlockEffectApplier effectApplier
    ) {
        // Apply heavy resistance to movement (simulating thick water)
        Vec3 motion = entity.getDeltaMovement();
        entity.setDeltaMovement(motion.multiply(0.25D, 0.05D, 0.25D));

        // If the entity can drown, apply air depletion and drowning damage
        if (entity instanceof LivingEntity livingEntity && level instanceof ServerLevel serverLevel) {
            livingEntity.setAirSupply(livingEntity.getAirSupply() - 1);

            if (livingEntity.getAirSupply() <= -20) {
                livingEntity.setAirSupply(0);
                livingEntity.hurtServer(serverLevel, livingEntity.damageSources().drown(), 2.0F);
            }
        }

        super.entityInside(state, level, pos, entity, effectApplier);
    }

    /**
     * Creates a new {@link WaterPrisonBlockEntity} instance when this block is placed.
     *
     * @param pos   the position of the block
     * @param state the current block state
     * @return a new {@code WaterPrisonBlockEntity}
     */
    @Override
    public @NotNull BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new WaterPrisonBlockEntity(pos, state);
    }

    /**
     * Gets the collision shape of this block.
     * <p>
     * This determines the physical shape used for entity collisions. For the
     * {@code WaterPrisonBlock}, this returns an empty shape so that entities
     * can freely fall into or move through the block without obstruction.
     * </p>
     *
     * @param state   the current block state
     * @param level   the block’s containing level
     * @param pos     the position of the block
     * @param context the collision context, such as entity size and movement
     * @return an empty {@link VoxelShape} to allow unobstructed movement
     * @since 0.1.0
     */
    @Override
    public @NotNull VoxelShape getCollisionShape(
            @NotNull BlockState state,
            @NotNull net.minecraft.world.level.BlockGetter level,
            @NotNull BlockPos pos,
            @NotNull net.minecraft.world.phys.shapes.CollisionContext context
    ) {
        // No collision — allows entities to fall through
        return net.minecraft.world.phys.shapes.Shapes.empty();
    }

    /**
     * Gets the visual shape of this block.
     * <p>
     * The visual shape defines how the block is represented for rendering,
     * outline, and ray tracing. For the {@code WaterPrisonBlock}, this returns
     * an empty shape so that the block does not obstruct outlines or ray tracing.
     * This ensures a fully transparent and non-solid visual effect.
     * </p>
     *
     * @param state   the current block state
     * @param level   the block’s containing level
     * @param pos     the position of the block
     * @param context the collision context
     * @return an empty {@link VoxelShape} to prevent obstruction of outlines and ray tracing
     * @since 0.1.0
     */
    @Override
    public @NotNull VoxelShape getVisualShape(
            @NotNull BlockState state,
            @NotNull net.minecraft.world.level.BlockGetter level,
            @NotNull BlockPos pos,
            @NotNull net.minecraft.world.phys.shapes.CollisionContext context
    ) {
        // Makes the block not obstruct block outlines or ray tracing
        return net.minecraft.world.phys.shapes.Shapes.empty();
    }
}
