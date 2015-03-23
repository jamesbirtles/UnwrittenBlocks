package unwrittenfun.minecraft.unwrittenblocks.common.tileEntities;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import unwrittenfun.minecraft.unwrittenblocks.common.blocks.BlockRegister;
import unwrittenfun.minecraft.unwrittenblocks.common.items.ItemRegister;
import unwrittenfun.minecraft.unwrittenblocks.common.multiblock.WallTeleporterNetwork;
import unwrittenfun.minecraft.unwrittenblocks.common.network.NetworkRegister;
import unwrittenfun.minecraft.unwrittenblocks.common.network.messages.TileEntityRequestMessage;

/**
 * Author: James Birtles
 */
public abstract class TEWallTeleporter extends TileEntity implements IWallTeleporterBlock {
  public WallTeleporterNetwork network;
  public boolean ignoreWT = false;
  public boolean loaded = false;
  public ItemStack mask = new ItemStack(BlockRegister.wallTeleporterWall);

  @Override
  public void updateEntity() {
    if (hasWorldObj() && !loaded) {
      loaded = true;
      onLoaded();
    }
  }

  protected void onLoaded() {
    connectToWallsAround();
  }

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
