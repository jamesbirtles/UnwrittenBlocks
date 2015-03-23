package unwrittenfun.minecraft.unwrittenblocks.common.tileEntities;

import net.minecraft.tileentity.TileEntity;
import unwrittenfun.minecraft.unwrittenblocks.common.multiblock.WallTeleporterNetwork;

/**
 * Author: James Birtles
 */
public abstract class TEWallTeleporter extends TileEntity implements IWallTeleporterBlock {
  public WallTeleporterNetwork network;
  public boolean ignoreWT;

  @Override
  public boolean hasWTNetwork() {
    return network != null;
  }

  @Override
  public WallTeleporterNetwork getWTNetwork() {
    return network;
  }

  @Override
  public void setWTNetwork(WallTeleporterNetwork wtNetwork) {
    network = wtNetwork;
  }

  @Override
  public boolean shouldIgnoreWT() {
    return ignoreWT;
  }

  @Override
  public void setIgnoreWT(boolean ignore) {
    ignoreWT = ignore;
  }
}
