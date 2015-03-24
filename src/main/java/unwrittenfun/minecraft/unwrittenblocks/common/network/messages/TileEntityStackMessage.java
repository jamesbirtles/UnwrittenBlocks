package unwrittenfun.minecraft.unwrittenblocks.common.network.messages;

import cpw.mods.fml.common.network.ByteBufUtils;
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
    boolean hasStack = buf.readBoolean();

    if (hasStack) {
      stack = ByteBufUtils.readItemStack(buf);
    }
  }

  @Override
  public void toBytes(ByteBuf buf) {
    super.toBytes(buf);
    if (stack == null) {
      buf.writeBoolean(false);
    } else {
      buf.writeBoolean(true);
      ByteBufUtils.writeItemStack(buf, stack);
    }
  }
}
