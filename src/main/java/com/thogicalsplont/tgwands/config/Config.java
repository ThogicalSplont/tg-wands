package com.thogicalsplont.tgwands.config;

import java.util.List;

import com.thogicalsplont.tgwands.TGWands;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * Configuration class for the TGWands mod.
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
     * Whether to log the dirt block during the mod’s common setup phase.
     * Default: true
     */
    public static final ModConfigSpec.BooleanValue LOG_DIRT_BLOCK = BUILDER
            .comment("Whether to log the dirt block on common setup")
            .define("logDirtBlock", true);

    /**
     * A magic number setting, configurable by the user.
     * Default: 42
     */
    public static final ModConfigSpec.IntValue MAGIC_NUMBER = BUILDER
            .comment("A magic number")
            .defineInRange("magicNumber", 42, 0, Integer.MAX_VALUE);

    /**
     * Custom introduction message for the magic number.
     * Default: "The magic number is... "
     */
    public static final ModConfigSpec.ConfigValue<String> MAGIC_NUMBER_INTRODUCTION = BUILDER
            .comment("What you want the introduction message to be for the magic number")
            .define("magicNumberIntroduction", "The magic number is... ");

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
    static final ModConfigSpec SPEC = BUILDER.build();

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
