package com.thogicalsplont.tgwands;

import com.thogicalsplont.tgwands.block.ModBlocks;
import com.thogicalsplont.tgwands.config.Config;
import com.thogicalsplont.tgwands.entity.ModEntities;
import com.thogicalsplont.tgwands.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.*;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

/**
 * The main mod class for TGWands.
 * <p>
 * Responsible for registering all mod items, blocks, entities, creative tabs, and configurations.
 * Also handles lifecycle events like common setup and server starting.
 * </p>
 *
 * @since 0.1.0
 */
@Mod(TGWands.MODID)
public class TGWands {

    /** Mod ID constant for the TGWands namespace. */
    public static final String MODID = "tgwands";

    /** Logger instance for mod logging. */
    public static final Logger LOGGER = LogUtils.getLogger();

    /** Deferred register for blocks under the mod namespace. */
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);

    /** Deferred register for items under the mod namespace. */
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    /** Deferred register for entities under the mod namespace. */
    public static final DeferredRegister.Entities ENTITIES = DeferredRegister.createEntities(MODID);

    /** Deferred register for creative mode tabs under the mod namespace. */
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    /** Example block for demonstration purposes. */
    public static final DeferredBlock<Block> EXAMPLE_BLOCK = BLOCKS.registerSimpleBlock(
            "example_block",
            BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
    );

    /** Example block item for demonstration purposes. */
    public static final DeferredItem<BlockItem> EXAMPLE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(
            "example_block",
            EXAMPLE_BLOCK
    );

    /** Example food item for demonstration purposes. */
    public static final DeferredItem<Item> EXAMPLE_ITEM = ITEMS.registerSimpleItem(
            "example_item",
            new Item.Properties().food(new FoodProperties.Builder()
                    .alwaysEdible()
                    .nutrition(1)
                    .saturationModifier(2f)
                    .build())
    );

    /** Custom creative mode tab for TGWands mod items. */
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TGWANDS_TAB = CREATIVE_MODE_TABS.register(
            "tgwands_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tgwands"))
                    .icon(() -> new ItemStack(ModItems.LIGHTNING_WAND.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(EXAMPLE_ITEM.get());
                        output.accept(ModItems.LIGHTNING_WAND.get());
                        output.accept(ModItems.FIRE_WAND.get());
                        output.accept(ModItems.EARTH_WAND.get());
                        output.accept(ModItems.WATER_WAND.get());
                    })
                    .build()
    );

    /**
     * TGWands mod constructor.
     * <p>
     * Registers all deferred objects, event listeners, and configurations.
     * </p>
     *
     * @param modEventBus   the mod event bus
     * @param modContainer  the mod container
     */
    public TGWands(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        ENTITIES.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEntities.register(modEventBus);

        modEventBus.addListener(this::addCreative);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    /**
     * Called during the common setup phase.
     * Logs mod information and config values.
     *
     * @param event the common setup event
     */
    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.LOG_DIRT_BLOCK.getAsBoolean()) {
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
        }

        LOGGER.info("{}{}", Config.MAGIC_NUMBER_INTRODUCTION.get(), Config.MAGIC_NUMBER.getAsInt());

        Config.ITEM_STRINGS.get().forEach(item -> LOGGER.info("ITEM >> {}", item));
    }

    /**
     * Adds items and blocks to appropriate creative mode tabs.
     *
     * @param event the creative mode tab contents event
     */
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(EXAMPLE_BLOCK_ITEM);
        }

        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ModItems.LIGHTNING_WAND);
            event.accept(ModItems.FIRE_WAND);
            event.accept(ModItems.EARTH_WAND);
            event.accept(ModItems.WATER_WAND);
        }
    }

    /**
     * Called when the server is starting.
     *
     * @param event the server starting event
     */
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("TGWands: Server Starting");
    }
}
