package com.thogicalsplont.tgwands.block.entity;

import com.thogicalsplont.tgwands.TGWands;
import com.thogicalsplont.tgwands.block.entity.custom.WaterPrisonBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.thogicalsplont.tgwands.block.ModBlocks.WATER_PRISON;

/**
 * Registers custom block entities.
 * <p>
 * This class handles the creation and registration of all custom
 * block entity types.
 * </p>
 *
 * <p><strong>Example:</strong> {@link WaterPrisonBlockEntity}</p>
 *
 * @since 0.1.0
 */
public class ModBlockEntities {

    /** Default constructor for ModBlockEntities. */
    public ModBlockEntities() {
        // No-op constructor to satisfy Javadoc tools.
    }

    /** Deferred registry for block entities, linked to the mod ID. */
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, TGWands.MODID);

    /**
     * Water Prison Block Entity Type.
     * <p>
     * Represents the block entity logic for {@link
     * com.thogicalsplont.tgwands.block.custom.WaterPrisonBlock}.
     * This block entity handles ticking logic and lifespan of the water prison.
     * </p>
     */
    public static final Supplier<BlockEntityType<WaterPrisonBlockEntity>> WATER_PRISON_ENTITY =
            BLOCK_ENTITIES.register(
                    "water_prison_entity",
                    () -> new BlockEntityType<>(
                            WaterPrisonBlockEntity::new, // Block entity factory
                            WATER_PRISON.get()           // Associated block(s)
                    )
            );

    /**
     * Registers all block entity types with the provided event bus.
     *
     * @param eventBus the mod event bus
     */
    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
