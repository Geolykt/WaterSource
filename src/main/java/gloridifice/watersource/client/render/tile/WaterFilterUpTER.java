package gloridifice.watersource.client.render.tile;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import gloridifice.watersource.WaterSource;
import gloridifice.watersource.common.item.StrainerBlockItem;
import gloridifice.watersource.common.tile.WaterFilterUpTile;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidStack;

public class WaterFilterUpTER extends TileEntityRenderer<WaterFilterUpTile> {
    public WaterFilterUpTER(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(WaterFilterUpTile tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Minecraft mc = Minecraft.getInstance();
        matrixStackIn.push();
        //Render strainer
        tileEntityIn.getStrainer().ifPresent(itemStackHandler -> {
            matrixStackIn.translate(0,-0.125,0);
            if (!itemStackHandler.getStackInSlot(0).isEmpty() && itemStackHandler.getStackInSlot(0).getItem() instanceof StrainerBlockItem){
                Block block = Block.getBlockFromItem(itemStackHandler.getStackInSlot(0).getItem());
                BlockRendererDispatcher rendererDispatcher = mc.getBlockRendererDispatcher();
                rendererDispatcher.renderBlock(block.getDefaultState(),matrixStackIn,bufferIn,60,combinedOverlayIn, EmptyModelData.INSTANCE);
            }
        });
        matrixStackIn.pop();
        IVertexBuilder buffer = bufferIn.getBuffer(RenderType.getTranslucent());
        matrixStackIn.push();
        GlStateManager.disableCull();
        tileEntityIn.getUpTank().ifPresent(fluidTankUp -> {
            if (!fluidTankUp.isEmpty()) {
                FluidStack fluidStackUp = fluidTankUp.getFluid();
                TextureAtlasSprite still = mc.getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(fluidStackUp.getFluid().getAttributes().getStillTexture());
                int colorRGB = fluidStackUp.getFluid().getAttributes().getColor();

                float height = (float) fluidStackUp.getAmount() / (float) fluidTankUp.getCapacity() * 0.75f;
                float vHeight = (still.getMaxV() - still.getMinV()) * (1f - (float) fluidStackUp.getAmount() / (float) fluidTankUp.getCapacity());

                add(buffer, matrixStackIn, 0.125f, 0.125f + height, 0.125f, still.getMinU(), still.getMinV(), colorRGB, 1.0F);
                add(buffer, matrixStackIn, 0.125f, 0.125f + height, 0.875f, still.getMaxU(), still.getMinV(), colorRGB, 1.0F);
                add(buffer, matrixStackIn, 0.875f, 0.125f + height, 0.875f, still.getMaxU(), still.getMaxV(), colorRGB, 1.0F);
                add(buffer, matrixStackIn, 0.875f, 0.125f + height, 0.125f, still.getMinU(), still.getMaxV(), colorRGB, 1.0F);

                add(buffer, matrixStackIn, 0.875f, 0.125f + height, 0.125f, still.getMinU(), still.getMinV() + vHeight, colorRGB, 1.0F);
                add(buffer, matrixStackIn, 0.875f, 0.125f + height, 0.875f, still.getMaxU(), still.getMinV() + vHeight, colorRGB, 1.0F);
                add(buffer, matrixStackIn, 0.875f, 0.125f, 0.875f, still.getMaxU(), still.getMaxV(), colorRGB, 1.0F);
                add(buffer, matrixStackIn, 0.875f, 0.125f, 0.125f, still.getMinU(), still.getMaxV(), colorRGB, 1.0F);

                add(buffer, matrixStackIn, 0.125f, 0.125f, 0.125f, still.getMinU(), still.getMinV(), colorRGB, 1.0F);
                add(buffer, matrixStackIn, 0.125f, 0.125f, 0.875f, still.getMaxU(), still.getMinV(), colorRGB, 1.0F);
                add(buffer, matrixStackIn, 0.125f, 0.125f + height, 0.875f, still.getMaxU(), still.getMaxV() - vHeight, colorRGB, 1.0F);
                add(buffer, matrixStackIn, 0.125f, 0.125f + height, 0.125f, still.getMinU(), still.getMaxV() - vHeight, colorRGB, 1.0F);

                add(buffer, matrixStackIn, 0.125f, 0.125f + height, 0.125f, still.getMinU(), still.getMinV() + vHeight, colorRGB, 1.0F);
                add(buffer, matrixStackIn, 0.875f, 0.125f + height, 0.125f, still.getMaxU(), still.getMinV() + vHeight, colorRGB, 1.0F);
                add(buffer, matrixStackIn, 0.875f, 0.125f, 0.125f, still.getMaxU(), still.getMaxV(), colorRGB, 1.0F);
                add(buffer, matrixStackIn, 0.125f, 0.125f, 0.125f, still.getMinU(), still.getMaxV(), colorRGB, 1.0F);

                add(buffer, matrixStackIn, 0.125f, 0.125f, 0.875f, still.getMinU(), still.getMinV() + vHeight, colorRGB, 1.0F);
                add(buffer, matrixStackIn, 0.875f, 0.125f, 0.875f, still.getMaxU(), still.getMinV() + vHeight, colorRGB, 1.0F);
                add(buffer, matrixStackIn, 0.875f, 0.125f + height, 0.875f, still.getMaxU(), still.getMaxV(), colorRGB, 1.0F);
                add(buffer, matrixStackIn, 0.125f, 0.125f + height, 0.875f, still.getMinU(), still.getMaxV(), colorRGB, 1.0F);

/*                add(buffer, matrixStackIn, 0.125f, 0.126f, 0.125f, still.getMinU(), still.getMinV(), colorRGBA);
                add(buffer, matrixStackIn, 0.125f, 0.126f, 0.875f, still.getMaxU(), still.getMinV(), colorRGBA);
                add(buffer, matrixStackIn, 0.875f, 0.126f, 0.875f, still.getMaxU(), still.getMaxV(), colorRGBA);
                add(buffer, matrixStackIn, 0.875f, 0.126f, 0.125f, still.getMinU(), still.getMaxV(), colorRGBA);*/
            }
        });

        GlStateManager.enableCull();
        matrixStackIn.pop();
    }

    private void add(IVertexBuilder renderer, MatrixStack stack, float x, float y, float z, float u, float v) {
        renderer.pos(stack.getLast().getMatrix(), x, y, z)
                .color(1.0f, 1.0f, 1.0f, 1.0f)
                .tex(u, v)
                .lightmap(0, 240)
                .normal(1, 0, 0)
                .endVertex();
    }

    private void add(IVertexBuilder renderer, MatrixStack stack, float x, float y, float z, float u, float v, int RGBA, float alpha) {
        float red = ((RGBA >> 16) & 0xFF) / 255f;
        float green = ((RGBA >> 8) & 0xFF) / 255f;
        float blue = ((RGBA >> 0) & 0xFF) / 255f;
        renderer.pos(stack.getLast().getMatrix(), x, y, z)
                .color(red, green, blue, alpha)
                .tex(u, v)
                .lightmap(0, 240)
                .normal(1, 0, 0)
                .endVertex();
    }
    private void add(IVertexBuilder renderer, MatrixStack stack, float x, float y, float z, float u, float v, float colorR, float colorG, float colorB, float alpha) {
        renderer.pos(stack.getLast().getMatrix(), x, y, z)
                .color(colorR, colorG, colorB, alpha)
                .tex(u, v)
                .lightmap(0, 240)
                .normal(1, 0, 0)
                .endVertex();
    }
}
