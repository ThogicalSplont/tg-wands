package com.thogicalsplont.tgwands.entity;

import com.thogicalsplont.tgwands.TGWands;
import com.thogicalsplont.tgwands.entity.custom.FireballEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

/**
 * Registers all custom entities for the TGWands mod.
 * <p>
 * This includes projectiles, mobs, and any other custom entity types
 * used in the mod.
 * </p>
 *
 * @since 1.0.0
 */
public class ModEntities {

    /** Deferred registry for entity types, linked to the mod ID. */
    public static final DeferredRegister.Entities ENTITY_TYPES =
            DeferredRegister.createEntities(TGWands.MODID);

    /**
     * Custom Fireball Entity.
     * <p>
     * A magical projectile fired from the Fire Wand.
     * Functions like Minecraft's LargeFireball but uses a custom model.
     * </p>
     */
    public static final Supplier<EntityType<FireballEntity>> FIREBALL_ENTITY =
            ENTITY_TYPES.registerEntityType(
                    "fireball_entity",
                    FireballEntity::new,
                    MobCategory.MISC,
                    builder -> builder
                            .sized(1.0f, 1.0f)             // Width and height
                            .eyeHeight(0.5f)                // Eye height for rendering
                            .fireImmune()                   // Fire immunity
                            .immuneTo(Blocks.POWDER_SNOW)   // Immune to certain damage types
                            .noSummon()                     // Cannot be summoned manually
                            .noSave()                       // Does not save to disk
                            .canSpawnFarFromPlayer()        // Spawns regardless of distance
                            .clientTrackingRange(8)         // How far clients track the entity
                            .updateInterval(10)             // Ticks between updates
            );

    /**
     * Registers all entity types with the provided event bus.
     *
     * @param eventBus the mod event bus
     */
    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
