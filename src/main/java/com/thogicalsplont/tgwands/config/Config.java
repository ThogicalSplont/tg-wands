package com.thogicalsplont.tgwands.config;

import java.util.List;

import com.thogicalsplont.tgwands.TGWands;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * Configuration class.
 * <p>
 * Demonstrates how to use NeoForge’s configuration API to define mod-specific settings.
 * This class organizes configuration values, their defaults, and validation logic.
 * </p>
 *
 * @since 0.1.0
 */
@EventBusSubscriber(modid = TGWands.MODID)
public class Config {

    /** Default constructor for Config. */
    public Config() {
        // No-op constructor to satisfy Javadoc tools.
    }

    /** Builder for defining configuration spec. */
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    /**
     * A list of item identifiers to log on common setup.
     * <p>
     * These strings must be valid resource locations for registered items.
     * Default: ["minecraft:iron_ingot"]
     * </p>
     */
    public static final ModConfigSpec.ConfigValue<List<? extends String>> ITEM_STRINGS = BUILDER
            .comment("A list of items to log on common setup.")
            .defineListAllowEmpty("items", List.of("minecraft:iron_ingot"), () -> "", Config::validateItemName);

    /** The built configuration specification for this mod. */
    public static final ModConfigSpec SPEC = BUILDER.build();

    /**
     * Validates whether the provided object is a valid item resource location.
     *
     * @param obj the object to validate
     * @return true if the object is a string and corresponds to a registered item
     */
    private static boolean validateItemName(final Object obj) {
        if (obj instanceof String itemName) {
            try {
                ResourceLocation resLoc = ResourceLocation.tryParse(itemName);
                return resLoc != null && BuiltInRegistries.ITEM.containsKey(resLoc);
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }
}
