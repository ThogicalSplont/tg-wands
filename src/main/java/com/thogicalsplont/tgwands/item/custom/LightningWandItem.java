package com.thogicalsplont.tgwands.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a magical wand that summons lightning at a targeted location.
 * <p>
 * When used, the {@code LightningWandItem} performs a raycast in the direction
 * the player is looking. If a block is hit within range, it summons a
 * {@link LightningBolt} at that location, plays a thunder sound, and notifies
 * the game world of the lightning strike event.
 * </p>
 *
 * <p><strong>Behavior:</strong></p>
 * <ul>
 *   <li>Raycasts up to 150 blocks in front of the player.</li>
 *   <li>Summons a lightning bolt at the targeted location.</li>
 *   <li>Plays a lightning impact sound.</li>
 *   <li>Triggers a game event for lightning strikes.</li>
 * </ul>
 *
 * <p><strong>Usage Example:</strong></p>
 * <pre>{@code
 * // In-game: Right-click while holding the Lightning Wand
 * // Lightning strikes at the targeted block location
 * }</pre>
 *
 * @since 1.0.0
 * @author Tathluach
 */
public class LightningWandItem extends Item {

    /**
     * Maximum range for the lightning wand's raycast, in blocks.
     */
    private static final double RAYCAST_RANGE = 150.0D;

    /**
     * Constructs a new {@code LightningWandItem} with the given properties.
     *
     * @param properties the item’s behavior and attributes
     */
    public LightningWandItem(Item.Properties properties) {
        super(properties);
    }

    /**
     * Called when the player right-clicks while holding this wand.
     * <p>
     * Performs a raycast in the direction the player is facing up to {@link #RAYCAST_RANGE}.
     * If a block is found, summons a {@link LightningBolt} at the location.
     * </p>
     *
     * @param world  the current level
     * @param player the player using the wand
     * @param hand   the hand used (main or off-hand)
     * @return {@link InteractionResult#SUCCESS} if the lightning was summoned
     */
    @Override
    public @NotNull InteractionResult use(@NotNull Level world, @NotNull Player player, @NotNull InteractionHand hand) {

        ItemStack stack = player.getItemInHand(hand);

        if (!world.isClientSide) {
            // Perform raycast to find the target location
            HitResult hit = player.pick(RAYCAST_RANGE, 0.0F, false);

            if (hit.getType() == HitResult.Type.BLOCK) {
                Vec3 hitLocation = hit.getLocation();
                BlockPos blockPos = new BlockPos((int) hitLocation.x, (int) hitLocation.y, (int) hitLocation.z);

                // Summon lightning bolt entity
                LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(world, EntitySpawnReason.TRIGGERED);

                if (lightning != null) {
                    // Move lightning to hit location
                    lightning.move(net.minecraft.world.entity.MoverType.PLAYER, hitLocation.subtract(player.position()));
                    lightning.setPos(hitLocation.x, hitLocation.y, hitLocation.z);

                    // Spawn the lightning bolt in the world
                    world.addFreshEntity(lightning);

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

                    // Notify the world of the lightning strike
                    world.gameEvent(player, GameEvent.LIGHTNING_STRIKE, blockPos);
                }
            }
        }

        return InteractionResult.SUCCESS;
    }
}
