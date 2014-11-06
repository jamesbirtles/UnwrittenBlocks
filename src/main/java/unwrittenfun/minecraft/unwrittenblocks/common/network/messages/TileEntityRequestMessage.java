package unwrittenfun.minecraft.unwrittenblocks.common.network.messages;

import net.minecraft.world.World;

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun
 * Created: 06/11/2014.
 */
public class TileEntityRequestMessage extends TileEntityCoordsMessage {
  public static TileEntityRequestMessage messageFrom(World worldObj, int xCoord, int yCoord, int zCoord, int id) {
    TileEntityRequestMessage message = new TileEntityRequestMessage();
    message.worldId = worldObj.provider.dimensionId;
    message.x = xCoord;
    message.y = yCoord;
    message.z = zCoord;
    message.id = (byte) id;
    return message;
  }
}
