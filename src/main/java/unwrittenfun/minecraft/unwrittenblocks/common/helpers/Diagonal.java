package unwrittenfun.minecraft.unwrittenblocks.common.helpers;

import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;

public enum Diagonal {
  TOP_LEFT(-1, 1),
  TOP_RIGHT(1, 1),
  BOTTOM_RIGHT(1, -1),
  BOTTOM_LEFT(-1, -1);

  public final int offsetX;
  public final int offsetY;

  Diagonal(int x, int y)
  {
    offsetX = x;
    offsetY = y;
  }

  /**
   *
   * @param direction 0 - Down, 1 - Up, 2 - Right, 3 - Left
   * @return list of 2d diagonals
   */
  public static List<Diagonal> getDiagonalsForDirection(int direction) {
    List<Diagonal> diagonals = new ArrayList<Diagonal>();
    switch (direction) {
      case 0:
        diagonals.add(BOTTOM_LEFT);
        diagonals.add(BOTTOM_RIGHT);
        break;
      case 1:
        diagonals.add(TOP_LEFT);
        diagonals.add(TOP_RIGHT);
        break;
      case 2:
        diagonals.add(TOP_RIGHT);
        diagonals.add(BOTTOM_RIGHT);
        break;
      case 3:
        diagonals.add(TOP_LEFT);
        diagonals.add(BOTTOM_LEFT);
        break;
    }
    return diagonals;
  }

  public int get3DOffsetX(int side) {
    ForgeDirection direction = ForgeDirection.getOrientation(side);
    if (direction.offsetY != 0) {
      return offsetX;
    } else if (direction.offsetZ != 0) {
      return offsetX * direction.offsetZ;
    }

    return 0;
  }

  public int get3DOffsetY(int side) {
    ForgeDirection direction = ForgeDirection.getOrientation(side);
    if (direction.offsetY == 0) {
      return offsetY;
    }

    return 0;
  }

  public int get3DOffsetZ(int side) {
    ForgeDirection direction = ForgeDirection.getOrientation(side);
    if (direction.offsetY != 0) {
      return -offsetY;
    } else if (direction.offsetX != 0) {
      return offsetX * -direction.offsetX;
    }

    return 0;
  }
}
