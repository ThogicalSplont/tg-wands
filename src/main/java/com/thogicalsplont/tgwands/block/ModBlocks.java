package com.thogicalsplont.tgwands.block;

import com.thogicalsplont.tgwands.TGWands;
import com.thogicalsplont.tgwands.block.custom.EarthWallBlock;
import com.thogicalsplont.tgwands.block.custom.WaterPrisonBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Registers custom blocks.
 * <p>
 * This class handles the creation and registration of custom block types,
 * including their properties such as hardness, resistance, sound, and behavior.
 * </p>
 *
 * <p><strong>Example:</strong> {@link EarthWallBlock} and {@link WaterPrisonBlock}</p>
 *
 * @since 0.1.0
 */
public class ModBlocks {

    /** Default constructor for ModBlocks. */
    public ModBlocks() {
        // No-op constructor to satisfy Javadoc tools.
    }

    /** Deferred registry for blocks, linked to the mod ID. */
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(TGWands.MODID);

    /**
     * Earth Wall Block.
     * <p>
     * A temporary block that automatically disappears after random ticks.
     * Creates a short-lived wall effect.
     * </p>
     */
    public static final DeferredBlock<Block> EARTH_WALL = BLOCKS.register("earth_wall", props ->
            new EarthWallBlock(BlockBehaviour.Properties.of()
                    .setId(ResourceKey.create(Registries.BLOCK, props))
                    .destroyTime(1.5f)           // Block hardness
                    .explosionResistance(0.5f)   // Resistance to explosions
                    .sound(SoundType.MUD_BRICKS) // Sound when interacted with
                    .randomTicks()               // Enables randomTick calls
            )
    );

    /**
     * Water Prison Block.
     * <p>
     * A special temporary block that traps entities inside by applying slowing and drowning effects.
     * This block has no collision and no occlusion, and the block entity automatically disappears
     * after a set amount of ticks.
     * </p>
     */
    public static final DeferredBlock<Block> WATER_PRISON = BLOCKS.register("water_prison", props ->
            new WaterPrisonBlock(BlockBehaviour.Properties.of()
                    .setId(ResourceKey.create(Registries.BLOCK, props))
                    .randomTicks()                // Enables randomTick calls
                    .noCollission()               // No collision detection
                    .noOcclusion()                // No occlusion for rendering
                    .destroyTime(-1.0f)           // Unbreakable by conventional means
                    .explosionResistance(3.6E6f)  // Extremely high resistance
            )
    );

    /**
     * Registers all custom blocks with the provided event bus.
     *
     * @param eventBus the mod event bus
     */
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
