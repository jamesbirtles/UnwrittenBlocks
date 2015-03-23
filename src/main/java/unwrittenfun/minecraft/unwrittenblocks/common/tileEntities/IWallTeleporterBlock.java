package unwrittenfun.minecraft.unwrittenblocks.common.tileEntities;

import unwrittenfun.minecraft.unwrittenblocks.common.multiblock.WallTeleporterNetwork;

/**
 * Author: James Birtles
 */
public interface IWallTeleporterBlock {

  /**
   * Set to true when block is being broken (to exclude it from the network refresh)
   * @return Should ignore when adding to network
   */
  public boolean shouldIgnoreWT();

  /**
   * Will be called on breakBlock to exclude it from the network refresh
   * @param ignore New value for ignore
   */
  public void setIgnoreWT(boolean ignore);

  public boolean hasWTNetwork();

  public WallTeleporterNetwork getWTNetwork();

  public void setWTNetwork(WallTeleporterNetwork wtNetwork);

  public void connectToWallsAround();
}
