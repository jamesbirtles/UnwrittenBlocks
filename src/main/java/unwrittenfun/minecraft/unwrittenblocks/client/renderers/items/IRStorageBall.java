package unwrittenfun.minecraft.unwrittenblocks.client.renderers.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import unwrittenfun.minecraft.unwrittenblocks.common.items.ItemStorageBall;
import unwrittenfun.minecraft.unwrittenblocks.common.items.StorageBallRegistry;

/**
 * Author: James Birtles
 */
public class IRStorageBall implements IItemRenderer {
  public static RenderItem renderItem = new RenderItem();
  public static RenderBlocks renderBlocks = new RenderBlocks();

  @Override
  public boolean handleRenderType(ItemStack item, ItemRenderType type) {
    return true;
  }

  @Override
  public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
    return helper == ItemRendererHelper.ENTITY_BOBBING || helper == ItemRendererHelper.ENTITY_ROTATION;
  }

  @Override
  public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
//    if (item.getIconIndex() != null) {
//      GL11.glPushMatrix();
//      GL11.glEnable(GL11.GL_BLEND);
//      GL11.glDisable(GL11.GL_LIGHTING);
//      renderIcon(0, 0, 16, 16, ItemStorageBall.outlineIcon);
//      if (StorageBallRegistry.getItemStackForStorageBall(item).getItem() instanceof ItemBlock) {
//        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
//      }
//      renderIcon(4, 3, 8, 10, item.getIconIndex());
//      renderIcon(6, 2, 4, 1, item.getIconIndex());
//      renderIcon(6, 13, 4, 1, item.getIconIndex());
//      renderIcon(3, 4, 1, 8, item.getIconIndex());
//      renderIcon(12, 4, 1, 8, item.getIconIndex());
//      renderIcon(2, 6, 1, 4, item.getIconIndex());
//      renderIcon(13, 6, 1, 4, item.getIconIndex());
//      GL11.glEnable(GL11.GL_LIGHTING);
//      GL11.glDisable(GL11.GL_BLEND);
//      GL11.glPopMatrix();
//    }
//    ResourceLocation texture = StorageBallRegistry.get(item);
//    Minecraft.getMinecraft().renderEngine.bindTexture(texture);
    renderItem.renderIcon(0, 0, StorageBallRegistry.getIconForBall(item), 16, 16);
  }

  public static void renderIcon(int x, int y, int width, int height, IIcon icon) {
    double upx = (icon.getMaxU() - icon.getMinU()) / 16f;
    double vpx = (icon.getMaxV() - icon.getMinV()) / 16f;

    Tessellator tessellator = Tessellator.instance;
    tessellator.startDrawingQuads();
    tessellator.addVertexWithUV((double) (x), (double) (y + height), 0, (double) icon.getMinU() + x * upx, (double) icon.getMinV() + (y + height) * vpx);
    tessellator.addVertexWithUV((double) (x + width), (double) (y + height), 0, (double) icon.getMinU() + (x + width) * upx, (double) icon.getMinV() + (y + height) * vpx);
    tessellator.addVertexWithUV((double) (x + width), (double) (y), 0, (double) icon.getMinU() + (x + width) * upx, (double) icon.getMinV() + y * vpx);
    tessellator.addVertexWithUV((double) (x), (double) (y), 0, (double) icon.getMinU() + x * upx, (double) icon.getMinV() + y * vpx);
    tessellator.draw();
  }

//  private void renderItem(ItemStack item) {
//    TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
//
//    texturemanager.bindTexture(textureLocation);
//    TextureUtil.func_152777_a(false, false, 1.0F);
//    Tessellator tessellator = Tessellator.instance;
//    float f = iicon.getMinU();
//    float f1 = iicon.getMaxU();
//    float f2 = iicon.getMinV();
//    float f3 = iicon.getMaxV();
//    float f4 = 0.0F;
//    float f5 = 0.3F;
//    GL11.glEnable(GL12.GL_RESCALE_NORMAL);
//    GL11.glTranslatef(-f4, -f5, 0.0F);
//    float f6 = 1.5F;
//    GL11.glScalef(f6, f6, f6);
//    GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
//    GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
//    GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
//    renderItemIn2D(tessellator, f1, f2, f, f3, iicon.getIconWidth(), iicon.getIconHeight(), 0.0625F);
//
//    if (p_78443_2_.hasEffect(p_78443_3_))
//    {
//      GL11.glDepthFunc(GL11.GL_EQUAL);
//      GL11.glDisable(GL11.GL_LIGHTING);
//      texturemanager.bindTexture(RES_ITEM_GLINT);
//      GL11.glEnable(GL11.GL_BLEND);
//      OpenGlHelper.glBlendFunc(768, 1, 1, 0);
//      float f7 = 0.76F;
//      GL11.glColor4f(0.5F * f7, 0.25F * f7, 0.8F * f7, 1.0F);
//      GL11.glMatrixMode(GL11.GL_TEXTURE);
//      GL11.glPushMatrix();
//      float f8 = 0.125F;
//      GL11.glScalef(f8, f8, f8);
//      float f9 = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
//      GL11.glTranslatef(f9, 0.0F, 0.0F);
//      GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
//      renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
//      GL11.glPopMatrix();
//      GL11.glPushMatrix();
//      GL11.glScalef(f8, f8, f8);
//      f9 = (float)(Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
//      GL11.glTranslatef(-f9, 0.0F, 0.0F);
//      GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
//      renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
//      GL11.glPopMatrix();
//      GL11.glMatrixMode(GL11.GL_MODELVIEW);
//      GL11.glDisable(GL11.GL_BLEND);
//      GL11.glEnable(GL11.GL_LIGHTING);
//      GL11.glDepthFunc(GL11.GL_LEQUAL);
//    }
//
//    GL11.glDisable(GL12.GL_RESCALE_NORMAL);
//    texturemanager.bindTexture(texturemanager.getResourceLocation(p_78443_2_.getItemSpriteNumber()));
//    TextureUtil.func_147945_b();
//  }
}
