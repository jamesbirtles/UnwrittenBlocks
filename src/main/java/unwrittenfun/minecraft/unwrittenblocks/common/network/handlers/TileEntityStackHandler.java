package unwrittenfun.minecraft.unwrittenblocks.common.network.handlers;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import unwrittenfun.minecraft.unwrittenblocks.common.network.messages.TileEntityStackMessage;
import unwrittenfun.minecraft.unwrittenblocks.common.network.receivers.ITileEntityStackMessageReceiver;

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun
 * Created: 05/11/2014.
 */
public class TileEntityStackHandler implements IMessageHandler<TileEntityStackMessage, IMessage> {
  @Override
  public IMessage onMessage(TileEntityStackMessage message, MessageContext ctx) {
    if (ctx.side == Side.CLIENT) {
      if (Minecraft.getMinecraft().theWorld.provider.dimensionId == message.worldId) {
        TileEntity tileEntity = Minecraft.getMinecraft().theWorld.getTileEntity(message.x, message.y, message.z);
        if (tileEntity instanceof ITileEntityStackMessageReceiver) {
          ((ITileEntityStackMessageReceiver) tileEntity).receiveStackMessage(message.id, message.stack);
        }
      }
    } else if (ctx.side == Side.SERVER) {
      World world = MinecraftServer.getServer().worldServerForDimension(message.worldId);
      TileEntity tileEntity = world.getTileEntity(message.x, message.y, message.z);
      if (tileEntity instanceof ITileEntityStackMessageReceiver) {
        ((ITileEntityStackMessageReceiver) tileEntity).receiveStackMessage(message.id, message.stack);
      }
    }
    return null;
  }
}