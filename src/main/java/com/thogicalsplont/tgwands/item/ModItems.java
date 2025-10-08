package com.thogicalsplont.tgwands.item;

import com.thogicalsplont.tgwands.TGWands;
import com.thogicalsplont.tgwands.item.custom.EarthWandItem;
import com.thogicalsplont.tgwands.item.custom.LightningWandItem;
import com.thogicalsplont.tgwands.item.custom.FireWandItem;
import com.thogicalsplont.tgwands.item.custom.WaterWandItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Registers custom items.
 * <p>
 * This class handles the creation and registration of custom magical wand items,
 * specifying their behavior and stack sizes.
 * </p>
 *
 * <p><strong>Example:</strong> {@link LightningWandItem}, {@link FireWandItem}, {@link EarthWandItem}, {@link WaterWandItem}</p>
 *
 * @since 0.1.0
 */
public class ModItems {

    /** Default constructor for ModItems. */
    public ModItems() {
        // No-op constructor to satisfy Javadoc tools.
    }

    /** Deferred registry for items, linked to the mod ID. */
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(TGWands.MODID);

    /**
     * Lightning Wand.
     * <p>
     * Casts lightning strikes at targeted locations.
     * </p>
     */
    public static final DeferredItem<Item> LIGHTNING_WAND = ITEMS.registerItem(
            "lightning_wand",
            props -> new LightningWandItem(props.stacksTo(1)),
            new Item.Properties().stacksTo(1)
    );

    /**
     * Fire Wand.
     * <p>
     * Shoots large fireballs in the direction the player is facing.
     * </p>
     */
    public static final DeferredItem<Item> FIRE_WAND = ITEMS.registerItem(
            "fire_wand",
            props -> new FireWandItem(props.stacksTo(1)),
            new Item.Properties().stacksTo(1)
    );

    /**
     * Earth Wand.
     * <p>
     * Creates temporary walls of earth blocks at targeted locations.
     * </p>
     */
    public static final DeferredItem<Item> EARTH_WAND = ITEMS.registerItem(
            "earth_wand",
            props -> new EarthWandItem(props.stacksTo(1)),
            new Item.Properties().stacksTo(1)
    );

    /**
     * Water Wand.
     * <p>
     * Creates a temporary sphere of water prison blocks in front of the player.
     * </p>
     */
    public static final DeferredItem<Item> WATER_WAND = ITEMS.registerItem(
            "water_wand",
            props -> new WaterWandItem(props.stacksTo(1)),
            new Item.Properties().stacksTo(1)
    );

    /**
     * Registers all custom items with the provided event bus.
     *
     * @param eventBus the mod event bus
     */
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
