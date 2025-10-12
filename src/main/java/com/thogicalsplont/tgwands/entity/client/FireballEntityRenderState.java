package com.thogicalsplont.tgwands.entity.client;

import net.minecraft.client.renderer.entity.state.EntityRenderState;

public class FireballEntityRenderState extends EntityRenderState {
    /** Whether this fireball is a dangerous variant (e.g., powered or charged). */
    public boolean isCharged;

    /** Rotation of the fireball on the X axis (pitch). */
    public float xRot;

    /** Rotation of the fireball on the Y axis (yaw). */
    public float yRot;
}
