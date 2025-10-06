package com.tathkage.tgwands.entity;

import com.tathkage.tgwands.TGWands;
import com.tathkage.tgwands.entity.custom.WaterPrisonBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.tathkage.tgwands.block.ModBlocks.WATER_PRISON;

public class ModEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, TGWands.MODID);

    public static final Supplier<BlockEntityType<WaterPrisonBlockEntity>> WATER_PRISON_ENTITY = BLOCK_ENTITIES.register(
            "water_prison_entity",
                    () -> new BlockEntityType<>(
                            WaterPrisonBlockEntity::new,
                            WATER_PRISON.get()
                    )
            );

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

}
