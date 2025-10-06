package com.tathkage.tgwands.block;

import com.tathkage.tgwands.TGWands;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredBlock;
import com.tathkage.tgwands.block.custom.EarthWallBlock;
import com.tathkage.tgwands.block.custom.WaterPrisonBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(TGWands.MODID);

    public static final DeferredBlock<Block> EARTH_WALL = BLOCKS.register("earth_wall", props ->
            new EarthWallBlock(BlockBehaviour.Properties.of()
                    .setId(ResourceKey.create(Registries.BLOCK, props))
                    .destroyTime(1.5f)
                    .explosionResistance(0.5f)
                    .sound(SoundType.MUD_BRICKS)
                    .randomTicks() // enables randomTick calls
            )
    );

    public static final DeferredBlock<Block> WATER_PRISON = BLOCKS.register("water_prison", props ->
            new WaterPrisonBlock(BlockBehaviour.Properties.of()
                    .setId(ResourceKey.create(Registries.BLOCK, props))
                    .randomTicks()
                    .noCollission()
                    .noOcclusion()
                    .destroyTime(-1.0f)
                    .explosionResistance(3600000.0f)
            )
    );

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
