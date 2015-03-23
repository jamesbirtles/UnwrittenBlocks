package unwrittenfun.minecraft.unwrittenblocks.common.network.handlers;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import unwrittenfun.minecraft.unwrittenblocks.common.multiblock.WallTeleporterNetwork;
import unwrittenfun.minecraft.unwrittenblocks.common.network.messages.TeleporterDestinationMessage;
import unwrittenfun.minecraft.unwrittenblocks.common.tileEntities.TEWallTeleporterBase;

/**
 * Author: James Birtles
 */
public class TeleporterDestinationHandler implements IMessageHandler<TeleporterDestinationMessage, IMessage> {
  @Override
  public IMessage onMessage(TeleporterDestinationMessage message, MessageContext ctx) {
    if (ctx.side == Side.CLIENT) {
      if (Minecraft.getMinecraft().theWorld.provider.dimensionId == message.worldId) {
        TileEntity tileEntity = Minecraft.getMinecraft().theWorld.getTileEntity(message.x, message.y, message.z);
        if (tileEntity instanceof TEWallTeleporterBase) {
          WallTeleporterNetwork network = ((TEWallTeleporterBase) tileEntity).getWTNetwork();
          if (message.destinationData[1] == -1) {
            network.destinationName = null;
            network.destinationWorldId = 0;
            network.destinationData = null;
          } else {
            network.destinationName = message.destinationName;
            network.destinationWorldId = message.worldId;
            network.destinationData = message.destinationData;
          }
        }
      }
    }
    return null;
  }
}
