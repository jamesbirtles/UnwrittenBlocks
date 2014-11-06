package unwrittenfun.minecraft.unwrittenblocks.common.network.handlers;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import unwrittenfun.minecraft.unwrittenblocks.common.network.messages.TileEntityIOSideMessage;
import unwrittenfun.minecraft.unwrittenblocks.common.network.receivers.ITileEntityIOSidesMessageReceiver;

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun
 * Created: 05/11/2014.
 */
public class TileEntityIOSideHandler implements IMessageHandler<TileEntityIOSideMessage, IMessage> {
  @Override
  public IMessage onMessage(TileEntityIOSideMessage message, MessageContext ctx) {
    if (ctx.side == Side.SERVER) {
      World world = ctx.getServerHandler().playerEntity.worldObj; //TODO: Probably not the best way to do this
      if (world.provider.dimensionId == message.worldId) {
        TileEntity tileEntity = world.getTileEntity(message.x, message.y, message.z);
        if (tileEntity instanceof ITileEntityIOSidesMessageReceiver) {
          ((ITileEntityIOSidesMessageReceiver) tileEntity).receiveIOSideMessage(message.side, message.value);
        }
      }
    }
    return null;
  }
}