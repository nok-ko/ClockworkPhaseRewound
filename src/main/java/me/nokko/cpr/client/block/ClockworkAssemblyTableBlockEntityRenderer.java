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
import net.minecraft.world.item.enchantment.Enchantments;

import org.lwjgl.nanovg.NVGColor;
import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_STENCIL_STROKES;
import static org.lwjgl.nanovg.NanoVGGL3.nvgCreate;

public class ClockworkAssemblyTableBlockEntityRenderer implements BlockEntityRenderer<ClockworkAssemblyTableBlockEntity> {
    private final long vg;

    public ClockworkAssemblyTableBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        this.vg = nvgCreate(NVG_STENCIL_STROKES);
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
        double offset = Math.sin((blockEntity.getLevel().getGameTime() + partialTicks) / 8.0) / 32.0;
        poseStack.translate(0.5, 1.25 + offset, 0.5);
        poseStack.mulPose(Axis.YP.rotationDegrees((blockEntity.getLevel().getGameTime() + partialTicks) * 4));
        poseStack.scale(1.2f, 1.2f, 1.2f);

        int lightAbove = LevelRenderer.getLightColor(blockEntity.getLevel(), blockEntity.getBlockPos().above());

        ItemStack floatingItem = ((Container) blockEntity).getItem(0);

        if (floatingItem.isEmpty()) {
            poseStack.popPose();
            return;
        };

        Minecraft.getInstance().getItemRenderer().renderStatic(
                floatingItem,
                ItemTransforms.TransformType.GROUND,
                lightAbove,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                buffer,
                combinedOverlay
        );

        poseStack.popPose();
    }
}
