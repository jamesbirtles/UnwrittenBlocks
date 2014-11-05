package unwrittenfun.minecraft.unwrittenblocks.common.network.messages;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun
 * Created: 05/11/2014.
 */
public class TileEntityStackMessage extends TileEntityCoordsMessage {
  public ItemStack stack;

  public static TileEntityStackMessage messageFrom(World worldObj, int xCoord, int yCoord, int zCoord, int id,
                                                   ItemStack stack) {
    TileEntityStackMessage message = new TileEntityStackMessage();
    message.worldId = worldObj.provider.dimensionId;
    message.x = xCoord;
    message.y = yCoord;
    message.z = zCoord;
    message.id = (byte) id;
    message.stack = stack;
    return message;
  }

  @Override
  public void fromBytes(ByteBuf buf) {
    super.fromBytes(buf);
    int id = buf.readInt();

    if (id != -1) {
      int meta = buf.readInt();
      int size = buf.readInt();
      stack = new ItemStack(Item.getItemById(id), size, meta);
    }
  }

  @Override
  public void toBytes(ByteBuf buf) {
    super.toBytes(buf);
    if (stack == null) {
      buf.writeInt(-1);
    } else {
      buf.writeInt(Item.getIdFromItem(stack.getItem()));
      buf.writeInt(stack.getItemDamage());
      buf.writeInt(stack.stackSize);
    }
  }
}
