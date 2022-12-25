package me.nokko.cpr.client.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import me.nokko.cpr.block.entity.ClockworkAssemblyTableBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.joml.Math;

public class ClockworkAssemblyTableBlockEntityRenderer implements BlockEntityRenderer<ClockworkAssemblyTableBlockEntity> {

    private final boolean additionalItemIs3d = false;

    public ClockworkAssemblyTableBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
    }

    public void renderClockworkTool(ItemStack stack,
                                    float time,
                                    float partialTicks,
                                    PoseStack poseStack,
                                    MultiBufferSource buffer,
                                    int light,
                                    int combinedOverlay) {
        poseStack.pushPose();

        // Height offset (for the bobbing animation.)
        double offset = (Math.sin((time + partialTicks) / 8.0) + 0.5)/ 32.0;
        if (additionalItemIs3d) {
            offset += 0.525;
        }

        poseStack.translate(0.5, 1.25 + offset, 0.5);

        // Slowly rotate it.
        poseStack.mulPose(Axis.YP.rotationDegrees((time + partialTicks) * 4));

        // Make it a tad bigger.
        poseStack.scale(1.2f, 1.2f, 1.2f);

        Minecraft.getInstance().getItemRenderer().renderStatic(
                stack,
                ItemTransforms.TransformType.GROUND,
                light,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                buffer,
                combinedOverlay
        );

        poseStack.popPose();
    }


    public void renderAdditionalItem(ItemStack stack,
                                    float time,
                                    float partialTicks,
                                    PoseStack poseStack,
                                    MultiBufferSource buffer,
                                    int light,
                                    int combinedOverlay) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.XP.rotationDegrees(90));
        poseStack.translate(0.5, 0.5, -1.0625);
        poseStack.scale(0.75f, 0.75f, 0.75f);
        Minecraft.getInstance().getItemRenderer().renderStatic(
                stack,
                ItemTransforms.TransformType.FIXED,
                light,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                buffer,
                combinedOverlay
        );
        poseStack.popPose();
    }

    @Override
    public void render(ClockworkAssemblyTableBlockEntity blockEntity,
                       float partialTicks,
                       PoseStack poseStack,
                       MultiBufferSource buffer,
                       int combinedLight,
                       int combinedOverlay) {
        poseStack.pushPose();


        float time = blockEntity.getLevel().getGameTime();
        int lightAbove = LevelRenderer.getLightColor(blockEntity.getLevel(), blockEntity.getBlockPos().above());

        var toolStack = blockEntity.getItems().get(ClockworkAssemblyTableBlockEntity.TOOL_SLOT);
        var additionalStack = blockEntity.getItems().get(ClockworkAssemblyTableBlockEntity.ADDITIONAL_SLOT);
        if (!toolStack.isEmpty()) {
            if( is3d(additionalStack) && !additionalStack.isEmpty() ) {
                poseStack.translate(0, 0.30, 0);
            }
            renderClockworkTool(toolStack, partialTicks, time, poseStack, buffer, lightAbove, combinedOverlay);
        }
        if (!additionalStack.isEmpty()) {
            if( is3d(additionalStack) && !toolStack.isEmpty()) {
                poseStack.translate(0, -0.15, 0);
            } else if (is3d(additionalStack) && toolStack.isEmpty()) {
                poseStack.translate(0, 0.15, 0);
            }
            renderAdditionalItem(additionalStack, partialTicks, time, poseStack, buffer, lightAbove, combinedOverlay);
        }

        var player = Minecraft.getInstance().player;
        if (player == null) {
            poseStack.popPose();
            return;
        }

        var rayTraceStart = player.getEyePosition(partialTicks);
        var viewVector = player.getViewVector(partialTicks);
        var rayTraceEnd = rayTraceStart.add(viewVector.x * 20, viewVector.y * 20, viewVector.z * 20);
        var hitResult = blockEntity.getBlockState().getShape(blockEntity.getLevel(), blockEntity.getBlockPos()).clip(rayTraceStart, rayTraceEnd, blockEntity.getBlockPos());

        if (hitResult != null && hitResult.getType() != HitResult.Type.MISS) {
            this.renderHoverSlots(poseStack, buffer);
        }

        poseStack.popPose();
    }

    private void renderHoverSlots(PoseStack poseStack, MultiBufferSource buffer) {
        double[] slotPosX = new double[8];
        double[] slotPosY = new double[8];
        for (int i = 0; i < 8; i++) {
            // # rotations -> rads
            double rotation = 6.283 * i/8;
            // -> coordinates. these are along the circle's perimeter.
            double perimeterX = Math.sin(rotation) * 0.75 - 0.125; // correct for width
            double perimeterY = Math.cos(rotation) * 0.75;
            slotPosX[i] = perimeterX + 0.5;
            slotPosY[i] = perimeterY + 0.375;
        }

        for (int i = 0; i < 8; i++) {
            LevelRenderer.renderLineBox(poseStack, buffer.getBuffer(RenderType.lines()),
                    slotPosX[i], 1, slotPosY[i],
                    slotPosX[i] + 0.25, 1.1, slotPosY[i] + 0.25,
                    1f, 0f, 0f, 0.7f
            );
        }
    }

    private static boolean is3d(ItemStack additionalStack) {
        return Minecraft.getInstance().getItemRenderer().getItemModelShaper().getItemModel(additionalStack).isGui3d();
    }
}
