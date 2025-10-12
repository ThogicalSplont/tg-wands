package com.thogicalsplont.tgwands.event;

import com.thogicalsplont.tgwands.TGWands;
import com.thogicalsplont.tgwands.entity.ModEntities;
import com.thogicalsplont.tgwands.entity.client.FireballEntityRenderer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = TGWands.MODID)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // Register your FireballEntity renderer
        event.registerEntityRenderer(ModEntities.FIREBALL_ENTITY.get(), FireballEntityRenderer::new);
    }
}
