package com.tathkage.tgwands.item;

import com.tathkage.tgwands.TGWands;
import com.tathkage.tgwands.itemFireballWandItem.FireballWandItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(TGWands.MODID);

    public static final DeferredItem<Item> FIREBALL_WAND = ITEMS.register("fireball_wand",
            () -> new FireballWandItem(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
