package unwrittenfun.minecraft.unwrittenblocks.common.tileEntities;

import unwrittenfun.minecraft.unwrittenblocks.common.multiblock.WallTeleporterNetwork;

/**
 * Author: James Birtles
 */
public interface IWallTeleporterBlock {
  public boolean hasWTNetwork();

  public WallTeleporterNetwork getWTNetwork();

  public void setWTNetwork(WallTeleporterNetwork wtNetwork);
}
