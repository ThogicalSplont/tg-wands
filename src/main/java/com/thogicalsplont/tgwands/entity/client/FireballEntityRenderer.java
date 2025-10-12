package com.thogicalsplont.tgwands.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.thogicalsplont.tgwands.TGWands;
import com.thogicalsplont.tgwands.entity.custom.FireballEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class FireballEntityRenderer extends EntityRenderer<FireballEntity, FireballEntityRenderState> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(TGWands.MODID, "textures/entity/fireball.png");

    private final FireballEntityModel model;

    public FireballEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new FireballEntityModel(context.bakeLayer(FireballEntityModel.LAYER_LOCATION));
    }

    @Override
    public @NotNull FireballEntityRenderState createRenderState() {
        return new FireballEntityRenderState();
    }

    @Override
    public void extractRenderState(@NotNull FireballEntity entity,
                                   @NotNull FireballEntityRenderState state,
                                   float partialTick) {
        super.extractRenderState(entity, state, partialTick);

        state.xRot = entity.getXRot(partialTick);
        state.yRot = entity.getYRot(partialTick);
        state.isCharged = entity.isOnFire(); // Optional: visual effect
    }

    @Override
    public void render(@NotNull FireballEntityRenderState state,
                       @NotNull PoseStack poseStack,
                       @NotNull MultiBufferSource bufferSource,
                       int packedLight) {

        poseStack.pushPose();

        // Optional: flip or scale like WitherSkull
        poseStack.scale(-1.0F, -1.0F, 1.0F);

        // Get vertex consumer with the entity texture
        VertexConsumer buffer = bufferSource.getBuffer(model.renderType(getTextureLocation(state)));

        // Apply rotations from render state
        model.setupAnim(state);
//        model.renderToBuffer(poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);

        poseStack.popPose();

        super.render(state, poseStack, bufferSource, packedLight);
    }

    private ResourceLocation getTextureLocation(FireballEntityRenderState state) {
        // Can switch textures if your fireball has multiple states
        return TEXTURE;
    }
}
