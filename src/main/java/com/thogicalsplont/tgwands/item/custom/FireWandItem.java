package com.thogicalsplont.tgwands.item.custom;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a magical wand that allows the player to cast a large fireball projectile.
 * <p>
 * When used, the {@code FireWandItem} spawns a {@link LargeFireball} in front of the player
 * and launches it in the direction they are facing. It plays a ghast fireball sound
 * and increments the player's item usage statistic.
 * </p>
 *
 * <p><strong>Behavior:</strong></p>
 * <ul>
 *   <li>Spawns a {@code LargeFireball} entity at the player’s eye level.</li>
 *   <li>Launches it forward with a configurable velocity multiplier (default: 1.5).</li>
 *   <li>Plays a sound effect when fired.</li>
 *   <li>Tracks item usage statistics.</li>
 * </ul>
 *
 * <p><strong>Usage Example:</strong></p>
 * <pre>{@code
 * // In-game: Right-click while holding the Fire Wand
 * // A fireball is launched in the direction the player is facing.
 * }</pre>
 *
 * @author
 *     Tathluach Chol
 * @since
 *     1.0.0
 */
public class FireWandItem extends Item {

    /**
     * The speed multiplier applied to the fireball’s movement vector.
     */
    private static final double FIREBALL_SPEED_MULTIPLIER = 1.5D;

    /**
     * Constructs a new {@code FireWandItem} with the given item properties.
     *
     * @param properties the item’s attributes and behavior settings
     */
    public FireWandItem(Item.Properties properties) {
        super(properties);
    }

    /**
     * Called when the player right-clicks while holding this wand.
     * <p>
     * Creates and launches a {@link LargeFireball} in the direction the player is looking.
     * </p>
     *
     * @param world  the current world level
     * @param player the player using the wand
     * @param hand   the hand used (main or off-hand)
     * @return {@link InteractionResult#SUCCESS} when the fireball is successfully launched
     */
    @Override
    public @NotNull InteractionResult use(@NotNull Level world, @NotNull Player player, @NotNull InteractionHand hand) {

        if (!world.isClientSide) {
            // Get player's look direction
            Vec3 lookVec = player.getLookAngle();

            // Create a fireball entity
            LargeFireball fireball = new LargeFireball(EntityType.FIREBALL, world);

            // Position the fireball slightly in front of the player
            fireball.setPos(
                    player.getX() + lookVec.x,
                    player.getEyeY() + lookVec.y,
                    player.getZ() + lookVec.z
            );

            // Set fireball velocity
            fireball.setDeltaMovement(lookVec.scale(FIREBALL_SPEED_MULTIPLIER));

            // Assign the player as the owner for damage attribution
            fireball.setOwner(player);

            // Spawn the fireball in the world
            world.addFreshEntity(fireball);

            // Play the Ghast shoot sound effect
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

        // Update player statistics
        player.awardStat(Stats.ITEM_USED.get(this));

        return InteractionResult.SUCCESS;
    }
}
