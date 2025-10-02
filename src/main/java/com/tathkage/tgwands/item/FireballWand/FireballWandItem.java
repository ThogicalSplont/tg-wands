package com.tathkage.tgwands.item.FireballWand;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.phys.Vec3;

public class FireballWandItem extends Item {
    public FireballWandItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level world, Player player, InteractionHand hand) {

        if (!world.isClientSide) {

            // Get player's look direction
            Vec3 lookVec = player.getLookAngle();

            // Spawn the fireball
            LargeFireball fireball = new LargeFireball(
                    EntityType.FIREBALL,
                    world
            );

            fireball.setPos(
                    player.getX() + lookVec.x,
                    player.getEyeY() + lookVec.y,
                    player.getZ() + lookVec.z
            );

            // Give it direction and speed
            fireball.setDeltaMovement(lookVec.scale(1.5)); // speed multiplier

            // Assign shooter (optional but good for tracking)
            fireball.setOwner(player);

            world.addFreshEntity(fireball);

            // Play fireball sound
            world.playSound(
                    null,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    SoundEvents.GHAST_SHOOT,
                    SoundSource.PLAYERS,
                    1.0F,
                    1.0F
            );
        }

        player.awardStat(Stats.ITEM_USED.get(this));

        return InteractionResult.SUCCESS;
    }
}
