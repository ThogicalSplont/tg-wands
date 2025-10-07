package com.thogicalsplont.tgwands.client;

import com.thogicalsplont.tgwands.TGWands;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

/**
 * Client-side entry point for the TGWands mod.
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
}
