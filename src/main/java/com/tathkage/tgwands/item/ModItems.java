package com.tathkage.tgwands.item;

import com.tathkage.tgwands.TGWands;
import com.tathkage.tgwands.item.custom.EarthWandItem;
import com.tathkage.tgwands.item.custom.LightningWandItem;
import com.tathkage.tgwands.item.custom.FireWandItem;
import com.tathkage.tgwands.item.custom.WaterWandItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(TGWands.MODID);

    public static final DeferredItem<Item> LIGHTNING_WAND = ITEMS.registerItem(
            "lightning_wand",
            props -> new LightningWandItem(props.stacksTo(1)),
            new Item.Properties()
                    .stacksTo(1)
    );

    public static final DeferredItem<Item> FIRE_WAND = ITEMS.registerItem(
        "fire_wand",
            props -> new FireWandItem(props.stacksTo(1)),
            new Item.Properties()
                    .stacksTo(1)
    );

    public static final DeferredItem<Item> EARTH_WAND = ITEMS.registerItem(
            "earth_wand",
            props -> new EarthWandItem(props.stacksTo(1)),
            new Item.Properties()
                    .stacksTo(1)
    );

    public static final DeferredItem<Item> WATER_WAND = ITEMS.registerItem(
            "water_wand",
            props -> new WaterWandItem(props.stacksTo(1)),
            new Item.Properties()
                    .stacksTo(1)
    );

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
