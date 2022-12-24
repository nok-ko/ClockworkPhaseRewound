package me.nokko.cpr.client.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import me.nokko.cpr.block.entity.ClockworkAssemblyTableBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemStack;

public class ClockworkAssemblyTableBlockEntityRenderer implements BlockEntityRenderer<ClockworkAssemblyTableBlockEntity> {

    private boolean additionalItemIs3d = false;

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

        poseStack.popPose();
    }

    private static boolean is3d(ItemStack additionalStack) {
        return Minecraft.getInstance().getItemRenderer().getItemModelShaper().getItemModel(additionalStack).isGui3d();
    }
}
