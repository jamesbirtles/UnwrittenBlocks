package unwrittenfun.minecraft.unwrittenblocks.client.renderers;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;

import static net.minecraftforge.common.util.ForgeDirection.*;

public class BlockRenderHelper {
  public static ForgeDirection[][] planeDirections = new ForgeDirection[][]{
      new ForgeDirection[]{SOUTH, NORTH, EAST, WEST},
      new ForgeDirection[]{SOUTH, NORTH, EAST, WEST},
      new ForgeDirection[]{DOWN, UP, WEST, EAST},
      new ForgeDirection[]{DOWN, UP, EAST, WEST},
      new ForgeDirection[]{DOWN, UP, SOUTH, NORTH},
      new ForgeDirection[]{DOWN, UP, NORTH, SOUTH}
  };

  public static int[][] sideZFightingMult = {
      {0, 1, 0},
      {0, -1, 0},
      {0, 0, 1},
      {0, 0, -1},
      {1, 0, 0},
      {-1, 0, 0},
  };
  public static int renderPass;

  public static void renderFaceUp(IIcon icon, float x, float y, float z) {
    Tessellator tess = Tessellator.instance;
    tess.addVertexWithUV(x + 1, y + 1, z + 1, icon.getMaxU(), icon.getMaxV());
    tess.addVertexWithUV(x + 1, y + 1, z, icon.getMaxU(), icon.getMinV());
    tess.addVertexWithUV(x, y + 1, z, icon.getMinU(), icon.getMinV());
    tess.addVertexWithUV(x, y + 1, z + 1, icon.getMinU(), icon.getMaxV());
  }

  public static void renderFaceDown(IIcon icon, float x, float y, float z) {
    Tessellator tess = Tessellator.instance;
    tess.addVertexWithUV(x, y, z, icon.getMinU(), icon.getMinV());
    tess.addVertexWithUV(x + 1, y, z, icon.getMaxU(), icon.getMinV());
    tess.addVertexWithUV(x + 1, y, z + 1, icon.getMaxU(), icon.getMaxV());
    tess.addVertexWithUV(x, y, z + 1, icon.getMinU(), icon.getMaxV());
  }

  public static void renderFaceNorth(IIcon icon, float x, float y, float z) {
    Tessellator tess = Tessellator.instance;
    tess.addVertexWithUV(x + 1, y + 1, z, icon.getMinU(), icon.getMinV());
    tess.addVertexWithUV(x + 1, y, z, icon.getMinU(), icon.getMaxV());
    tess.addVertexWithUV(x, y, z, icon.getMaxU(), icon.getMaxV());
    tess.addVertexWithUV(x, y + 1, z, icon.getMaxU(), icon.getMinV());
  }

  public static void renderFaceEast(IIcon icon, float x, float y, float z) {
    Tessellator tess = Tessellator.instance;
    tess.addVertexWithUV(x + 1, y, z, icon.getMaxU(), icon.getMaxV());
    tess.addVertexWithUV(x + 1, y + 1, z, icon.getMaxU(), icon.getMinV());
    tess.addVertexWithUV(x + 1, y + 1, z + 1, icon.getMinU(), icon.getMinV());
    tess.addVertexWithUV(x + 1, y, z + 1, icon.getMinU(), icon.getMaxV());
  }

  public static void renderFaceSouth(IIcon icon, float x, float y, float z) {
    Tessellator tess = Tessellator.instance;
    tess.addVertexWithUV(x, y, z + 1, icon.getMinU(), icon.getMaxV());
    tess.addVertexWithUV(x + 1, y, z + 1, icon.getMaxU(), icon.getMaxV());
    tess.addVertexWithUV(x + 1, y + 1, z + 1, icon.getMaxU(), icon.getMinV());
    tess.addVertexWithUV(x, y + 1, z + 1, icon.getMinU(), icon.getMinV());
  }

  public static void renderFaceWest(IIcon icon, float x, float y, float z) {
    Tessellator tess = Tessellator.instance;
    tess.addVertexWithUV(x, y, z, icon.getMinU(), icon.getMaxV());
    tess.addVertexWithUV(x, y, z + 1, icon.getMaxU(), icon.getMaxV());
    tess.addVertexWithUV(x, y + 1, z + 1, icon.getMaxU(), icon.getMinV());
    tess.addVertexWithUV(x, y + 1, z, icon.getMinU(), icon.getMinV());
  }

  public static void renderFaceSide(IIcon icon, float x, float y, float z, int side) {
    switch (side) {
      case 0:
        renderFaceDown(icon, x, y, z);
        break;
      case 1:
        renderFaceUp(icon, x, y, z);
        break;
      case 2:
        renderFaceNorth(icon, x, y, z);
        break;
      case 3:
        renderFaceSouth(icon, x, y, z);
        break;
      case 4:
        renderFaceWest(icon, x, y, z);
        break;
      case 5:
        renderFaceEast(icon, x, y, z);
        break;
    }
  }

  public static float zFightingX(float offset, int side) {
    return offset * -sideZFightingMult[side][0];
  }

  public static float zFightingY(float offset, int side) {
    return offset * -sideZFightingMult[side][1];
  }

  public static float zFightingZ(float offset, int side) {
    return offset * -sideZFightingMult[side][2];
  }
}
