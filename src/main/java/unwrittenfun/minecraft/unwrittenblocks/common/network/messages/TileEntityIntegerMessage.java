package unwrittenfun.minecraft.unwrittenblocks.common.network.messages;

import io.netty.buffer.ByteBuf;

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun
 * Created: 05/11/2014.
 */
public class TileEntityIntegerMessage extends TileEntityCoordsMessage {
  public int value;

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
