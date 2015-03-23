package unwrittenfun.minecraft.unwrittenblocks.common.multiblock;

import unwrittenfun.minecraft.unwrittenblocks.common.tileEntities.IWallTeleporterBlock;
import unwrittenfun.minecraft.unwrittenblocks.common.tileEntities.TEWallTeleporterBase;

import java.util.ArrayList;

/**
 * Author: James Birtles
 */
public class WallTeleporterNetwork {
  public ArrayList<IWallTeleporterBlock> walls = new ArrayList<IWallTeleporterBlock>();
  public TEWallTeleporterBase base;

  public WallTeleporterNetwork(IWallTeleporterBlock base) {
    if (base instanceof TEWallTeleporterBase) this.base = (TEWallTeleporterBase) base;
  }

  public void add(IWallTeleporterBlock wallTeleporterBlock) {
    wallTeleporterBlock.setWTNetwork(this);
    if (!walls.contains(wallTeleporterBlock)) {
      walls.add(wallTeleporterBlock);
    }
  }
}
