package unwrittenfun.minecraft.unwrittenblocks.common.network.messages;

import io.netty.buffer.ByteBuf;
import net.minecraft.world.World;

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun
 * Created: 05/11/2014.
 */
public class TileEntityIntegerMessage extends TileEntityCoordsMessage {
  public int value;

  public static TileEntityIntegerMessage messageFrom(World worldObj, int xCoord, int yCoord, int zCoord, int id,
                                                     int value) {
    TileEntityIntegerMessage message = new TileEntityIntegerMessage();
    message.worldId = worldObj.provider.dimensionId;
    message.x = xCoord;
    message.y = yCoord;
    message.z = zCoord;
    message.id = (byte) id;
    message.value = value;
    return message;
  }

  @Override
  public void fromBytes(ByteBuf buf) {
    super.fromBytes(buf);
    value = buf.readInt();
  }

  @Override
  public void toBytes(ByteBuf buf) {
    super.toBytes(buf);
    buf.writeInt(value);
  }
}
