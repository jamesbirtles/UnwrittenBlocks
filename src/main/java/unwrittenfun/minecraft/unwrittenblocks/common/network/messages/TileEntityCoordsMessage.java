package unwrittenfun.minecraft.unwrittenblocks.common.network.messages;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun
 * Created: 05/11/2014.
 */
public class TileEntityCoordsMessage implements IMessage {
  public byte id;
  public int worldId, x, y, z;

  @Override
  public void fromBytes(ByteBuf buf) {
    worldId = buf.readInt();
    x = buf.readInt();
    y = buf.readInt();
    z = buf.readInt();
    id = buf.readByte();
  }

  @Override
  public void toBytes(ByteBuf buf) {
    buf.writeInt(worldId);
    buf.writeInt(x);
    buf.writeInt(y);
    buf.writeInt(z);
    buf.writeByte(id);
  }
}
