package com.tathkage.tgwands.item.LightningWand;

import com.tathkage.tgwands.TGWands;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.core.BlockPos;

public class LightningWandItem extends Item {
    public LightningWandItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level world, Player player, InteractionHand hand) {

        ItemStack stack = player.getItemInHand(hand);

        TGWands.LOGGER.info("LightningWandItem used by player: {}", player.getName().getString());

        if (!world.isClientSide) {
            TGWands.LOGGER.info("Server side use detected");

            // Get where the player is looking
            HitResult hit = player.pick(150.0D, 0.0F, false);
            TGWands.LOGGER.info("HitResult obtained: type = {}", hit.getType());

            if (hit.getType() == HitResult.Type.BLOCK) {
                Vec3 pos = hit.getLocation();
                TGWands.LOGGER.info(String.format("Hit location: x=%.2f, y=%.2f, z=%.2f", pos.x, pos.y, pos.z));

                BlockPos blockPos = new BlockPos((int) pos.x, (int) pos.y, (int) pos.z);
                TGWands.LOGGER.info("Converted to BlockPos: {}", blockPos);

                // Summon lightning at the targeted position
                LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(
                        world,
                        EntitySpawnReason.TRIGGERED
                );

                if (lightning != null) {
                    TGWands.LOGGER.info("LightningBolt entity created successfully");

                    lightning.move(net.minecraft.world.entity.MoverType.PLAYER, pos.subtract(player.position()));
                    lightning.setPos(pos.x, pos.y, pos.z);
                    world.addFreshEntity(lightning);
                    TGWands.LOGGER.info("LightningBolt spawned at {}", blockPos);

                    // Play lightning impact sound
                    world.playSound(
                            null,
                            player.getX(),
                            player.getY(),
                            player.getZ(),
                            SoundEvents.LIGHTNING_BOLT_IMPACT,
                            SoundSource.WEATHER,
                            1.0F,
                            1.0F
                    );
                    TGWands.LOGGER.info("Played lightning impact sound");

                    // Notify world of lightning event
                    world.gameEvent(player, GameEvent.LIGHTNING_STRIKE, blockPos);
                    TGWands.LOGGER.info("Triggered lightning strike game event at {}", blockPos);
                } else {
                    TGWands.LOGGER.error("Failed to create LightningBolt entity");
                }
            } else {
                TGWands.LOGGER.warn("HitResult type is not BLOCK, skipping lightning summon");
            }
        } else {
            TGWands.LOGGER.info("Client side detected, skipping lightning summon");
        }

        return InteractionResult.SUCCESS;
    }
}
