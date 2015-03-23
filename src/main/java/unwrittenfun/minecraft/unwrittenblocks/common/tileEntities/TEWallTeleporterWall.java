package unwrittenfun.minecraft.unwrittenblocks.common.tileEntities;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Author: James Birtles
 */
public class TEWallTeleporterWall extends TEWallTeleporter {
  @Override
  public void connectToWallsAround() {
    for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
      TileEntity tileEntity = worldObj.getTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
      if (tileEntity instanceof IWallTeleporterBlock) {
        IWallTeleporterBlock teleporter = (IWallTeleporterBlock) tileEntity;
        if (!teleporter.shouldIgnoreWT() && teleporter.hasWTNetwork() && teleporter.getWTNetwork() != getWTNetwork()) {
          teleporter.getWTNetwork().add(this);
        }
      }
    }

    if (hasWTNetwork()) {
      for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
        TileEntity tileEntity = worldObj.getTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
        if (tileEntity instanceof IWallTeleporterBlock) {
          IWallTeleporterBlock teleporter = (IWallTeleporterBlock) tileEntity;
          if (!teleporter.shouldIgnoreWT() && !teleporter.hasWTNetwork()) {
            teleporter.connectToWallsAround();
          }
        }
      }
    }
  }
}
