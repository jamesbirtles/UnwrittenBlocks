package unwrittenfun.minecraft.unwrittenblocks.common.network.messages;

import io.netty.buffer.ByteBuf;
import net.minecraft.world.World;

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun
 * Created: 05/11/2014.
 */
public class TileEntityIOSideMessage extends TileEntityCoordsMessage {
  public int side, value;

  public static TileEntityIOSideMessage messageFrom(World worldObj, int xCoord, int yCoord, int zCoord, int side,
                                                    int value) {
    TileEntityIOSideMessage message = new TileEntityIOSideMessage();
    message.worldId = worldObj.provider.dimensionId;
    message.x = xCoord;
    message.y = yCoord;
    message.z = zCoord;
    message.id = 0;
    message.side = side;
    message.value = value;
    return message;
  }

  @Override
  public void fromBytes(ByteBuf buf) {
    super.fromBytes(buf);
    side = buf.readInt();
    value = buf.readInt();
  }

  @Override
  public void toBytes(ByteBuf buf) {
    super.toBytes(buf);
    buf.writeInt(side);
    buf.writeInt(value);
  }
}
