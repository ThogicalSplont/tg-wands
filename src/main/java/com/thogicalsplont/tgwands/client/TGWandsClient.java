package com.thogicalsplont.tgwands.client;

import com.thogicalsplont.tgwands.TGWands;
import com.thogicalsplont.tgwands.block.ModBlocks;
import com.thogicalsplont.tgwands.entity.client.FireballEntityRenderState;
import com.thogicalsplont.tgwands.entity.ModEntities;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

/**
 * Client-side entry point.
 * <p>
 * Handles client-specific initialization, configuration screen registration, and event handling.
 * This class is only loaded on the client distribution.
 * </p>
 *
 * @since 0.1.0
 */
@Mod(value = TGWands.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = TGWands.MODID, value = Dist.CLIENT)
public class TGWandsClient {

    /**
     * Constructor for the TGWandsClient.
     * <p>
     * Registers the configuration screen factory for NeoForge.
     * </p>
     *
     * @param container the mod container used for registering extensions
     */
    public TGWandsClient(ModContainer container) {
        // Registers a custom config screen accessed via Mods screen → your mod → Config.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    /**
     * Called during the client setup phase.
     * <p>
     * This is the ideal place to register client-only features such as rendering,
     * key bindings, GUIs, and client-side events.
     * </p>
     *
     * @param event the client setup event
     */
    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        TGWands.LOGGER.info("HELLO FROM CLIENT SETUP");
        TGWands.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }

    /**
     * Registers block color handlers for client-side rendering.
     * <p>
     * This method is called during the appropriate NeoForge client event to
     * register tint handlers for blocks. Tint handlers allow blocks to have
     * dynamic or static color overlays when rendered. The returned ARGB value
     * determines the tint color applied to the block model based on the tint index.
     * </p>
     * <p>
     * For example, this handler tints the {@link ModBlocks#WATER_PRISON}
     * block with a semi-transparent blue color. This can be customized to
     * return biome-specific water colors or other effects.
     * </p>
     *
     * @param event the {@link RegisterColorHandlersEvent.Block} event used to register color handlers
     * @since 0.1.0
     */
    @SubscribeEvent
    public static void registerBlockColorHandlers(RegisterColorHandlersEvent.Block event) {
        event.register((state, level, pos, tintIndex) -> {
            // ARGB format: 0xAARRGGBB
            // Example: pure blue with some transparency
            return 0x88416bdf;

            // Or: biome water color
            // return level != null && pos != null ? level.getBiome(pos).value().getWaterColor() : 0x880000FF;
        }, ModBlocks.WATER_PRISON.get());
    }
}
