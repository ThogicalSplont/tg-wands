package com.thogicalsplont.tgwands.entity.client;// Made with Blockbench 4.12.6
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.thogicalsplont.tgwands.TGWands;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.*;

public class FireballEntityModel extends EntityModel<FireballEntityRenderState> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(TGWands.MODID, "fireball_entity"), "main");
	private final ModelPart bone;

	public FireballEntityModel(ModelPart root) {
        super(root);
        this.bone = root.getChild("bone");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(34, 64).addBox(-18.0F, -20.0F, -16.0F, 20.0F, 20.0F, 20.0F, new CubeDeformation(0.0F))
		.texOffs(25, 5).addBox(-16.0F, -18.0F, 4.0F, 0.0F, 16.0F, 15.0F, new CubeDeformation(0.0F))
		.texOffs(25, 5).addBox(0.0F, -18.0F, 4.0F, 0.0F, 16.0F, 15.0F, new CubeDeformation(0.0F))
		.texOffs(0, 40).addBox(-16.0F, -2.0F, 4.0F, 16.0F, 0.0F, 15.0F, new CubeDeformation(0.0F))
		.texOffs(-12, 40).addBox(-16.0F, -18.0F, 4.0F, 16.0F, 0.0F, 15.0F, new CubeDeformation(0.0F))
		.texOffs(51, 30).addBox(-13.0F, -15.0F, 4.0F, 0.0F, 10.0F, 21.0F, new CubeDeformation(0.0F))
		.texOffs(50, -1).addBox(-3.0F, -15.0F, 4.0F, 0.0F, 10.0F, 21.0F, new CubeDeformation(0.0F))
		.texOffs(-11, 56).addBox(-13.0F, -5.0F, 4.0F, 10.0F, 0.0F, 21.0F, new CubeDeformation(0.0F))
		.texOffs(0, 56).addBox(-13.0F, -15.0F, 4.0F, 10.0F, 0.0F, 21.0F, new CubeDeformation(0.0F))
		.texOffs(3, 56).addBox(-10.0F, -12.0F, 5.0F, 4.0F, 0.0F, 27.0F, new CubeDeformation(0.0F))
		.texOffs(0, 56).addBox(-10.0F, -8.0F, 5.0F, 4.0F, 0.0F, 27.0F, new CubeDeformation(0.0F))
		.texOffs(60, -17).addBox(-6.0F, -10.0F, 5.0F, 0.0F, 2.0F, 27.0F, new CubeDeformation(0.0F))
		.texOffs(60, -17).addBox(-6.0F, -12.0F, 5.0F, 0.0F, 2.0F, 27.0F, new CubeDeformation(0.0F))
		.texOffs(36, -13).addBox(-10.0F, -12.0F, 5.0F, 0.0F, 4.0F, 27.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(2.0F, -20.0F, 4.0F, 0.0F, 20.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(-5, 104).addBox(-18.0F, 0.0F, 4.0F, 20.0F, 0.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(5, 55).addBox(-18.0F, -20.0F, 4.0F, 0.0F, 20.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(-5, 104).addBox(-18.0F, -20.0F, 4.0F, 20.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 24.0F, -8.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(@NotNull FireballEntityRenderState state) {
        super.setupAnim(state);
        this.root.xRot = state.xRot * ((float)Math.PI / 180F);
        this.root.yRot = state.yRot * ((float)Math.PI / 180F);
	}

//    public void renderToBuffer(@NotNull PoseStack poseStack, VertexConsumer buffer, int packedLight, int noOverlay, float v, float v1, float v2, float v3) {
//    }
}