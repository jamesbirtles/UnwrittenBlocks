package unwrittenfun.minecraft.unwrittenblocks.client.renderers.blocks;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;
import unwrittenfun.minecraft.unwrittenblocks.common.UnwrittenBlocks;
import unwrittenfun.minecraft.unwrittenblocks.common.blocks.BlockRefulgentFabricator;
import unwrittenfun.minecraft.unwrittenblocks.common.blocks.IRefulgentBlock;

import java.lang.reflect.Field;

/**
 * Author: James Birtles
 */
public class BlockRendererRefulgent implements ISimpleBlockRenderingHandler {
  public int renderId;

  public BlockRendererRefulgent(int renderId) {
    this.renderId = renderId;
  }

  @Override
  public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
//    renderer.renderBlockAsItem();
    if (block instanceof IRefulgentBlock) {
      IRefulgentBlock refulgentBlock = (IRefulgentBlock) block;
      IIcon refulgentIcon = BlockRefulgentFabricator.refulgent; // TODO: put this somewhere more generic
      int hex = refulgentBlock.getRGBForMeta(metadata);
      int r = (hex & 0xFF0000) >> 16;
      int g = (hex & 0xFF00) >> 8;
      int b = (hex & 0xFF);
      int x = 0;
      int y = 0;
      int z = 0;
      float f1 = 0.0625F;

      Tessellator tess = Tessellator.instance;
      int oldBrightness = getTessBrightness(tess);

      tess.setColorRGBA(255, 255, 255, 255);

      GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
      // TOP (+Y)
      tess.startDrawingQuads();
      IIcon upIcon = refulgentBlock.getIconFromDirection(ForgeDirection.UP);
      tess.addVertexWithUV(x + 1, y + 1, z + 1, upIcon.getMaxU(), upIcon.getMaxV());
      tess.addVertexWithUV(x + 1, y + 1, z, upIcon.getMaxU(), upIcon.getMinV());
      tess.addVertexWithUV(x, y + 1, z, upIcon.getMinU(), upIcon.getMinV());
      tess.addVertexWithUV(x, y + 1, z + 1, upIcon.getMinU(), upIcon.getMaxV());
      tess.draw();

      // BOTTOM (-Y)
      tess.startDrawingQuads();
      IIcon downIcon = refulgentBlock.getIconFromDirection(ForgeDirection.DOWN);
      tess.addVertexWithUV(x, y, z, downIcon.getMinU(), downIcon.getMinV());
      tess.addVertexWithUV(x + 1, y, z, downIcon.getMaxU(), downIcon.getMinV());
      tess.addVertexWithUV(x + 1, y, z + 1, downIcon.getMaxU(), downIcon.getMaxV());
      tess.addVertexWithUV(x, y, z + 1, downIcon.getMinU(), downIcon.getMaxV());
      tess.draw();

      // NORTH (-Z)
      tess.startDrawingQuads();
      IIcon northIcon = refulgentBlock.getIconFromDirection(ForgeDirection.NORTH);
      tess.addVertexWithUV(x + 1, y + 1, z, northIcon.getMinU(), northIcon.getMinV());
      tess.addVertexWithUV(x + 1, y, z, northIcon.getMinU(), northIcon.getMaxV());
      tess.addVertexWithUV(x, y, z, northIcon.getMaxU(), northIcon.getMaxV());
      tess.addVertexWithUV(x, y + 1, z, northIcon.getMaxU(), northIcon.getMinV());
      tess.draw();

      // WEST (+X)
      tess.startDrawingQuads();
      IIcon westIcon = refulgentBlock.getIconFromDirection(ForgeDirection.WEST);
      tess.addVertexWithUV(x, y, z, westIcon.getMinU(), westIcon.getMaxV());
      tess.addVertexWithUV(x, y, z + 1, westIcon.getMaxU(), westIcon.getMaxV());
      tess.addVertexWithUV(x, y + 1, z + 1, westIcon.getMaxU(), westIcon.getMinV());
      tess.addVertexWithUV(x, y + 1, z, westIcon.getMinU(), westIcon.getMinV());
      tess.draw();

      // EAST (-X)
      tess.startDrawingQuads();
      IIcon eastIcon = refulgentBlock.getIconFromDirection(ForgeDirection.EAST);
      tess.addVertexWithUV(x + 1, y, z, eastIcon.getMaxU(), eastIcon.getMaxV());
      tess.addVertexWithUV(x + 1, y + 1, z, eastIcon.getMaxU(), eastIcon.getMinV());
      tess.addVertexWithUV(x + 1, y + 1, z + 1, eastIcon.getMinU(), eastIcon.getMinV());
      tess.addVertexWithUV(x + 1, y, z + 1, eastIcon.getMinU(), eastIcon.getMaxV());
      tess.draw();

      // SOUTH (+Z)
      tess.startDrawingQuads();
      IIcon southIcon = refulgentBlock.getIconFromDirection(ForgeDirection.SOUTH);
      tess.addVertexWithUV(x, y, z + 1, southIcon.getMinU(), southIcon.getMaxV());
      tess.addVertexWithUV(x + 1, y, z + 1, southIcon.getMaxU(), southIcon.getMaxV());
      tess.addVertexWithUV(x + 1, y + 1, z + 1, southIcon.getMaxU(), southIcon.getMinV());
      tess.addVertexWithUV(x, y + 1, z + 1, southIcon.getMinU(), southIcon.getMinV());
      tess.draw();

      tess.setBrightness(15728880);
//      tess.setColorRGBA(r, g, b, 255);
      RenderHelper.disableStandardItemLighting();
      GL11.glColor3f(r / 255f, g / 255f, b / 255f);

      // TOP (+Y)
      tess.startDrawingQuads();
      tess.addVertexWithUV(x + 1, y + 0.999f, z + 1, refulgentIcon.getMaxU(), refulgentIcon.getMaxV());
      tess.addVertexWithUV(x + 1, y + 0.999f, z, refulgentIcon.getMaxU(), refulgentIcon.getMinV());
      tess.addVertexWithUV(x, y + 0.999f, z, refulgentIcon.getMinU(), refulgentIcon.getMinV());
      tess.addVertexWithUV(x, y + 0.999f, z + 1, refulgentIcon.getMinU(), refulgentIcon.getMaxV());
      tess.draw();

      // DOWN (-Y)
      tess.startDrawingQuads();
      tess.addVertexWithUV(x, y + 0.001f, z, refulgentIcon.getMinU(), refulgentIcon.getMinV());
      tess.addVertexWithUV(x + 1, y + 0.001f, z, refulgentIcon.getMaxU(), refulgentIcon.getMinV());
      tess.addVertexWithUV(x + 1, y + 0.001f, z + 1, refulgentIcon.getMaxU(), refulgentIcon.getMaxV());
      tess.addVertexWithUV(x, y + 0.001f, z + 1, refulgentIcon.getMinU(), refulgentIcon.getMaxV());
      tess.draw();

      // NORTH (-Z)
      tess.startDrawingQuads();
      tess.addVertexWithUV(x + 1, y + 1, z + 0.001f, refulgentIcon.getMaxU(), refulgentIcon.getMaxV());
      tess.addVertexWithUV(x + 1, y, z + 0.001f, refulgentIcon.getMaxU(), refulgentIcon.getMinV());
      tess.addVertexWithUV(x, y, z + 0.001f, refulgentIcon.getMinU(), refulgentIcon.getMinV());
      tess.addVertexWithUV(x, y + 1, z + 0.001f, refulgentIcon.getMinU(), refulgentIcon.getMaxV());
      tess.draw();

      // WEST (+X)
      tess.startDrawingQuads();
      tess.addVertexWithUV(x + 0.001f, y, z, refulgentIcon.getMinU(), refulgentIcon.getMinV());
      tess.addVertexWithUV(x + 0.001f, y, z + 1, refulgentIcon.getMaxU(), refulgentIcon.getMinV());
      tess.addVertexWithUV(x + 0.001f, y + 1, z + 1, refulgentIcon.getMaxU(), refulgentIcon.getMaxV());
      tess.addVertexWithUV(x + 0.001f, y + 1, z, refulgentIcon.getMinU(), refulgentIcon.getMaxV());
      tess.draw();

      // EAST (-X)
      tess.startDrawingQuads();
      tess.addVertexWithUV(x + 0.999f, y + 1, z + 1, refulgentIcon.getMaxU(), refulgentIcon.getMaxV());
      tess.addVertexWithUV(x + 0.999f, y, z + 1, refulgentIcon.getMaxU(), refulgentIcon.getMinV());
      tess.addVertexWithUV(x + 0.999f, y, z, refulgentIcon.getMinU(), refulgentIcon.getMinV());
      tess.addVertexWithUV(x + 0.999f, y + 1, z, refulgentIcon.getMinU(), refulgentIcon.getMaxV());
      tess.draw();

      // SOUTH (+Z)
      tess.startDrawingQuads();
      tess.addVertexWithUV(x, y, z + 0.999f, refulgentIcon.getMinU(), refulgentIcon.getMinV());
      tess.addVertexWithUV(x + 1, y, z + 0.999f, refulgentIcon.getMaxU(), refulgentIcon.getMinV());
      tess.addVertexWithUV(x + 1, y + 1, z + 0.999f, refulgentIcon.getMaxU(), refulgentIcon.getMaxV());
      tess.addVertexWithUV(x, y + 1, z + 0.999f, refulgentIcon.getMinU(), refulgentIcon.getMaxV());
      tess.draw();
      GL11.glTranslatef(0.5F, 0.5F, 0.5F);
      RenderHelper.enableStandardItemLighting();

      tess.setBrightness(oldBrightness);
    }
  }

  @Override
  public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
    if (block instanceof IRefulgentBlock) {
      IRefulgentBlock refulgentBlock = (IRefulgentBlock) block;
      IIcon refulgentIcon = BlockRefulgentFabricator.refulgent; // TODO: put this somewhere more generic
      int hex = refulgentBlock.getRGB(world, x, y, z);
      int r = (hex & 0xFF0000) >> 16;
      int g = (hex & 0xFF00) >> 8;
      int b = (hex & 0xFF);

      Tessellator tess = Tessellator.instance;
      int oldBrightness = getTessBrightness(tess);

      tess.setColorRGBA(255, 255, 255, 255);

      // TOP (+Y)
      IIcon upIcon = refulgentBlock.getIconFromDirection(ForgeDirection.UP);
      tess.addVertexWithUV(x + 1, y + 1, z + 1, upIcon.getMaxU(), upIcon.getMaxV());
      tess.addVertexWithUV(x + 1, y + 1, z, upIcon.getMaxU(), upIcon.getMinV());
      tess.addVertexWithUV(x, y + 1, z, upIcon.getMinU(), upIcon.getMinV());
      tess.addVertexWithUV(x, y + 1, z + 1, upIcon.getMinU(), upIcon.getMaxV());

      // DOWN (-Y)
      IIcon downIcon = refulgentBlock.getIconFromDirection(ForgeDirection.DOWN);
      tess.addVertexWithUV(x, y, z, downIcon.getMinU(), downIcon.getMinV());
      tess.addVertexWithUV(x + 1, y, z, downIcon.getMaxU(), downIcon.getMinV());
      tess.addVertexWithUV(x + 1, y, z + 1, downIcon.getMaxU(), downIcon.getMaxV());
      tess.addVertexWithUV(x, y, z + 1, downIcon.getMinU(), downIcon.getMaxV());

      // NORTH (-Z)
      IIcon northIcon = refulgentBlock.getIconFromDirection(ForgeDirection.NORTH);
      tess.addVertexWithUV(x + 1, y + 1, z, northIcon.getMinU(), northIcon.getMinV());
      tess.addVertexWithUV(x + 1, y, z, northIcon.getMinU(), northIcon.getMaxV());
      tess.addVertexWithUV(x, y, z, northIcon.getMaxU(), northIcon.getMaxV());
      tess.addVertexWithUV(x, y + 1, z, northIcon.getMaxU(), northIcon.getMinV());

      // WEST (+X)
      IIcon westIcon = refulgentBlock.getIconFromDirection(ForgeDirection.WEST);
      tess.addVertexWithUV(x, y, z, westIcon.getMinU(), westIcon.getMaxV());
      tess.addVertexWithUV(x, y, z + 1, westIcon.getMaxU(), westIcon.getMaxV());
      tess.addVertexWithUV(x, y + 1, z + 1, westIcon.getMaxU(), westIcon.getMinV());
      tess.addVertexWithUV(x, y + 1, z, westIcon.getMinU(), westIcon.getMinV());

      // EAST (-X)
      IIcon eastIcon = refulgentBlock.getIconFromDirection(ForgeDirection.EAST);
      tess.addVertexWithUV(x + 1, y, z, eastIcon.getMaxU(), eastIcon.getMaxV());
      tess.addVertexWithUV(x + 1, y + 1, z, eastIcon.getMaxU(), eastIcon.getMinV());
      tess.addVertexWithUV(x + 1, y + 1, z + 1, eastIcon.getMinU(), eastIcon.getMinV());
      tess.addVertexWithUV(x + 1, y, z + 1, eastIcon.getMinU(), eastIcon.getMaxV());

      // SOUTH (+Z)
      IIcon southIcon = refulgentBlock.getIconFromDirection(ForgeDirection.SOUTH);
      tess.addVertexWithUV(x, y, z + 1, southIcon.getMinU(), southIcon.getMaxV());
      tess.addVertexWithUV(x + 1, y, z + 1, southIcon.getMaxU(), southIcon.getMaxV());
      tess.addVertexWithUV(x + 1, y + 1, z + 1, southIcon.getMaxU(), southIcon.getMinV());
      tess.addVertexWithUV(x, y + 1, z + 1, southIcon.getMinU(), southIcon.getMinV());

      tess.setBrightness(15728880);
      tess.setColorRGBA(r, g, b, 255);

      // TOP (+Y)
      tess.addVertexWithUV(x + 1, y + 0.999f, z + 1, refulgentIcon.getMaxU(), refulgentIcon.getMaxV());
      tess.addVertexWithUV(x + 1, y + 0.999f, z, refulgentIcon.getMaxU(), refulgentIcon.getMinV());
      tess.addVertexWithUV(x, y + 0.999f, z, refulgentIcon.getMinU(), refulgentIcon.getMinV());
      tess.addVertexWithUV(x, y + 0.999f, z + 1, refulgentIcon.getMinU(), refulgentIcon.getMaxV());

      // BOTTOM (-Y)
      tess.addVertexWithUV(x, y + 0.001f, z, refulgentIcon.getMinU(), refulgentIcon.getMinV());
      tess.addVertexWithUV(x + 1, y + 0.001f, z, refulgentIcon.getMaxU(), refulgentIcon.getMinV());
      tess.addVertexWithUV(x + 1, y + 0.001f, z + 1, refulgentIcon.getMaxU(), refulgentIcon.getMaxV());
      tess.addVertexWithUV(x, y + 0.001f, z + 1, refulgentIcon.getMinU(), refulgentIcon.getMaxV());

      // NORTH (-Z)
      tess.addVertexWithUV(x + 1, y + 1, z + 0.001f, refulgentIcon.getMinU(), refulgentIcon.getMinV());
      tess.addVertexWithUV(x + 1, y, z + 0.001f, refulgentIcon.getMinU(), refulgentIcon.getMaxV());
      tess.addVertexWithUV(x, y, z + 0.001f, refulgentIcon.getMaxU(), refulgentIcon.getMaxV());
      tess.addVertexWithUV(x, y + 1, z + 0.001f, refulgentIcon.getMaxU(), refulgentIcon.getMinV());

      // WEST (+X)
      tess.addVertexWithUV(x + 0.001f, y, z, refulgentIcon.getMinU(), refulgentIcon.getMaxV());
      tess.addVertexWithUV(x + 0.001f, y, z + 1, refulgentIcon.getMaxU(), refulgentIcon.getMaxV());
      tess.addVertexWithUV(x + 0.001f, y + 1, z + 1, refulgentIcon.getMaxU(), refulgentIcon.getMinV());
      tess.addVertexWithUV(x + 0.001f, y + 1, z, refulgentIcon.getMinU(), refulgentIcon.getMinV());

      // EAST (-X)
      tess.addVertexWithUV(x + 0.999f, y, z, refulgentIcon.getMaxU(), refulgentIcon.getMaxV());
      tess.addVertexWithUV(x + 0.999f, y + 1, z, refulgentIcon.getMaxU(), refulgentIcon.getMinV());
      tess.addVertexWithUV(x + 0.999f, y + 1, z + 1, refulgentIcon.getMinU(), refulgentIcon.getMinV());
      tess.addVertexWithUV(x + 0.999f, y, z + 1, refulgentIcon.getMinU(), refulgentIcon.getMaxV());

      // SOUTH (+Z)
      tess.addVertexWithUV(x, y, z + 0.999f, refulgentIcon.getMinU(), refulgentIcon.getMaxV());
      tess.addVertexWithUV(x + 1, y, z + 0.999f, refulgentIcon.getMaxU(), refulgentIcon.getMaxV());
      tess.addVertexWithUV(x + 1, y + 1, z + 0.999f, refulgentIcon.getMaxU(), refulgentIcon.getMinV());
      tess.addVertexWithUV(x, y + 1, z + 0.999f, refulgentIcon.getMinU(), refulgentIcon.getMinV());

      tess.setBrightness(oldBrightness);
    }
    return false;
  }

  private int getTessBrightness(Tessellator tess) {
    try {
      Field brightnessField = Tessellator.class.getDeclaredField("brightness");
      brightnessField.setAccessible(true);
      return brightnessField.getInt(tess);
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    UnwrittenBlocks.logger.warn("Unable to determine old brightness");
    return 0;
  }

  @Override
  public boolean shouldRender3DInInventory(int modelId) {
    return true;
  }

  @Override
  public int getRenderId() {
    return renderId;
  }
}
