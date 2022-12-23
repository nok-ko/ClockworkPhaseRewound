package me.nokko.cpr.client.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import me.nokko.cpr.block.entity.ClockworkAssemblyTableBlockEntity;
import me.nokko.cpr.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

import java.util.Set;

public class ClockworkAssemblyTableBlockEntityRenderer implements BlockEntityRenderer<ClockworkAssemblyTableBlockEntity> {

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
        double offset = Math.sin((time + partialTicks) / 8.0) / 32.0;
        poseStack.translate(0.5, 1.25 + offset, 0.5);
        poseStack.mulPose(Axis.YP.rotationDegrees((time + partialTicks) * 4));
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

    @Override
    public void render(ClockworkAssemblyTableBlockEntity blockEntity,
                       float partialTicks,
                       PoseStack poseStack,
                       MultiBufferSource buffer,
                       int combinedLight,
                       int combinedOverlay) {
        poseStack.pushPose();

        // Height offset (for the bobbing animation.)

        float time = blockEntity.getLevel().getGameTime();
        int lightAbove = LevelRenderer.getLightColor(blockEntity.getLevel(), blockEntity.getBlockPos().above());
        var toolStack = blockEntity.getItems().get(ClockworkAssemblyTableBlockEntity.TOOL_SLOT);
        if (!toolStack.isEmpty()) {
            renderClockworkTool(toolStack, partialTicks, time, poseStack, buffer, lightAbove, combinedOverlay);
        } else {
            poseStack.popPose();
            return;
        }

        poseStack.popPose();
    }
}
