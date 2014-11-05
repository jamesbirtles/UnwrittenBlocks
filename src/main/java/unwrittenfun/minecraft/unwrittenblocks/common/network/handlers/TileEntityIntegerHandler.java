package unwrittenfun.minecraft.unwrittenblocks.common.network.handlers;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import unwrittenfun.minecraft.unwrittenblocks.common.network.messages.TileEntityIntegerMessage;
import unwrittenfun.minecraft.unwrittenblocks.common.network.receivers.ITileEntityIntegerMessageReceiver;

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun
 * Created: 05/11/2014.
 */
public class TileEntityIntegerHandler implements IMessageHandler<TileEntityIntegerMessage, IMessage> {
  @Override
  public IMessage onMessage(TileEntityIntegerMessage message, MessageContext ctx) {
    if (ctx.side == Side.CLIENT) {
      if (Minecraft.getMinecraft().theWorld.provider.dimensionId == message.worldId) {
        TileEntity tileEntity = Minecraft.getMinecraft().theWorld.getTileEntity(message.x, message.y, message.z);
        if (tileEntity instanceof ITileEntityIntegerMessageReceiver) {
          ((ITileEntityIntegerMessageReceiver) tileEntity).receiveIntegerMessage(message.id, message.value);
        }
      }
    }
    return null;
  }
}
