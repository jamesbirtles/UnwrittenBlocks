package unwrittenfun.minecraft.unwrittenblocks.common.network.receivers;

import net.minecraft.entity.player.EntityPlayerMP;

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun
 * Created: 06/11/2014.
 */
public interface ITileEntityRequestMessageReceiver {
  public void receiveRequestMessage(byte id, EntityPlayerMP player);
}
