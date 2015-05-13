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
import unwrittenfun.minecraft.unwrittenblocks.client.renderers.BlockRenderHelper;
import unwrittenfun.minecraft.unwrittenblocks.common.UnwrittenBlocks;
import unwrittenfun.minecraft.unwrittenblocks.common.blocks.BlockRefulgentFabricator;
import unwrittenfun.minecraft.unwrittenblocks.common.blocks.IConnectedTextures;
import unwrittenfun.minecraft.unwrittenblocks.common.blocks.IRefulgentBlock;
import unwrittenfun.minecraft.unwrittenblocks.common.helpers.Diagonal;

import java.lang.reflect.Field;

import static unwrittenfun.minecraft.unwrittenblocks.client.renderers.BlockRenderHelper.*;

/**
 * Author: James Birtles
 */
public class BlockRendererRefulgent implements ISimpleBlockRenderingHandler {
  public static final float zFightingOffset = 0.001f;
  public int renderId;

  public BlockRendererRefulgent(int renderId) {
    this.renderId = renderId;
  }

  @Override
  public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
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

      IIcon[] sideIcons = new IIcon[6];
      for (int direction = 0; direction < sideIcons.length; direction++) {
        sideIcons[direction] = refulgentBlock.getIconFromDirection(direction);
      }

      Tessellator tess = Tessellator.instance;
      int oldBrightness = getTessBrightness(tess);

      tess.setColorRGBA(255, 255, 255, 255);

      GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

      for (int side = 0; side < 6; side++) {
        tess.startDrawingQuads();
        BlockRenderHelper.renderFaceSide(sideIcons[side], x + zFightingX(zFightingOffset, side), y + zFightingY(zFightingOffset, side), z + zFightingZ(zFightingOffset, side), side);
        tess.draw();
      }

      tess.setBrightness(15728880);
//      tess.setColorRGBA(r, g, b, 255);
      RenderHelper.disableStandardItemLighting();
      GL11.glColor3f(r / 255f, g / 255f, b / 255f);

      for (int side = 0; side < 6; side++) {
        tess.startDrawingQuads();
        BlockRenderHelper.renderFaceSide(refulgentIcon, x, y, z, side);
        tess.draw();
      }

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

      IIcon[] sideIcons = new IIcon[6];
      for (int direction = 0; direction < sideIcons.length; direction++) {
        sideIcons[direction] = refulgentBlock.getIconFromDirection(direction);
      }

      Tessellator tess = Tessellator.instance;
      int oldBrightness = getTessBrightness(tess);

      tess.setColorOpaque_F(1, 1, 1);
      if (block instanceof IConnectedTextures && ((IConnectedTextures) block).hasConnectedTextures()) {
        IConnectedTextures blockConnected = (IConnectedTextures) block;
        for (int side = 0; side < 6; side++) {
          if (block.shouldSideBeRendered(world, x, y, z, side)) {
            for (int i = 0; i < 4; i++) {
              ForgeDirection direction = planeDirections[side][i];
              Block compBlock = world.getBlock(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ);
              if (!blockConnected.canConnectToBlock(compBlock)) {
                renderFaceSide(blockConnected.getIconForDisconnectedSide(i), x + zFightingX(zFightingOffset, side), y + zFightingY(zFightingOffset, side), z + zFightingZ(zFightingOffset, side), side);
              }
            }
            for (Diagonal diagonal : Diagonal.values()) {
              int offsetX = diagonal.get3DOffsetX(side);
              int offsetY = diagonal.get3DOffsetY(side);
              int offsetZ = diagonal.get3DOffsetZ(side);
              Block blockCompXY = world.getBlock(x + offsetX, y + offsetY, z + offsetZ);
              if (!blockConnected.canConnectToBlock(blockCompXY)) {
                int compYX, compYY, compYZ;
                int compXX = compYX = x;
                int compXY = compYY = y;
                int compXZ = compYZ = z;
                if (offsetX == 0) {
                  compXZ += offsetZ;
                  compYY += offsetY;
                } else if (offsetY == 0) {
                  compXX += offsetX;
                  compYZ += offsetZ;
                } else if (offsetZ == 0) {
                  compXX += offsetX;
                  compYY += offsetY;
                }
                Block blockCompX = world.getBlock(compXX, compXY, compXZ);
                Block blockCompY = world.getBlock(compYX, compYY, compYZ);
                if (blockConnected.canConnectToBlock(blockCompX) && blockConnected.canConnectToBlock(blockCompY)) {
                  renderFaceSide(blockConnected.getIconForDisconnectedSide(4 + diagonal.ordinal()), x + zFightingX(zFightingOffset, side), y + zFightingY(zFightingOffset, side), z + zFightingZ(zFightingOffset, side), side);
                }
              }
            }
          }
        }
      } else {
        for (int side = 0; side < 6; side++) {
          if (block.shouldSideBeRendered(world, x, y, z, side)) {
            BlockRenderHelper.renderFaceSide(sideIcons[side], x + zFightingX(zFightingOffset, side), y + zFightingY(zFightingOffset, side), z + zFightingZ(zFightingOffset, side), side);
          }
        }
      }

      tess.setBrightness(15728880);
      tess.setColorRGBA(r, g, b, 255);

      for (int side = 0; side < 6; side++) {
        if (block.shouldSideBeRendered(world, x, y, z, side)) {
          BlockRenderHelper.renderFaceSide(refulgentIcon, x, y, z, side);
        }
      }
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
