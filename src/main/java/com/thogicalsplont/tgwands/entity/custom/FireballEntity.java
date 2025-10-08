package com.thogicalsplont.tgwands.entity.custom;

import com.thogicalsplont.tgwands.entity.ModEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a custom fireball projectile with unique visuals and behavior.
 * <p>
 * Functions similarly to {@link net.minecraft.world.entity.projectile.LargeFireball}
 * but uses a custom model and texture defined in client rendering.
 * </p>
 *
 * @since 0.1.0
 */
public class FireballEntity extends Fireball {

    private static final byte DEFAULT_EXPLOSION_POWER = 1;
    private int explosionPower = DEFAULT_EXPLOSION_POWER;

    /**
     * Default constructor required for entity registration.
     */
    public FireballEntity(EntityType<? extends FireballEntity> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * Custom constructor used when spawning a fireball with an owner and movement vector.
     *
     * @param level           Level the fireball exists in.
     * @param owner           Entity that fired the fireball.
     * @param movement        Initial movement vector.
     * @param explosionPower  Explosion strength of the fireball.
     */
    public FireballEntity(Level level, LivingEntity owner, Vec3 movement, int explosionPower) {
        super(ModEntities.FIREBALL_ENTITY.get(), owner, movement, level);
        this.explosionPower = explosionPower;
    }

    /**
     * Called when the fireball hits a block or entity.
     */
    @Override
    protected void onHit(@NotNull HitResult result) {
        super.onHit(result);

        if (this.level() instanceof ServerLevel serverLevel) {
            boolean griefing = net.neoforged.neoforge.event.EventHooks.canEntityGrief(serverLevel, this.getOwner());
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), this.explosionPower, griefing, Level.ExplosionInteraction.MOB);
            this.discard(); // Removes entity from world
        }
    }

    /**
     * Called when the fireball hits another entity.
     */
    @Override
    protected void onHitEntity(@NotNull EntityHitResult result) {
        super.onHitEntity(result);

        if (this.level() instanceof ServerLevel serverLevel) {
            Entity target = result.getEntity();
            Entity owner = this.getOwner();
            DamageSource damageSource = this.damageSources().fireball(this, owner);
            target.hurtServer(serverLevel, damageSource, 6.0F);
        }
    }

    /**
     * Saves additional data for persistence.
     */
    @Override
    protected void addAdditionalSaveData(@NotNull ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putByte("ExplosionPower", (byte) this.explosionPower);
    }

    /**
     * Reads additional saved data.
     */
    @Override
    protected void readAdditionalSaveData(@NotNull ValueInput input) {
        super.readAdditionalSaveData(input);
        this.explosionPower = input.getByteOr("ExplosionPower", DEFAULT_EXPLOSION_POWER);
    }
}
