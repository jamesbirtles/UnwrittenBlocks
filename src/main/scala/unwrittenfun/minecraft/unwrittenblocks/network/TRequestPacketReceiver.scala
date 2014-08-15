package unwrittenfun.minecraft.unwrittenblocks.network

import cpw.mods.fml.common.network.Player

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun 
 * Created: 12/08/2014.
 */
trait TRequestPacketReceiver {
  def receiveRequestPacket(id: Byte, player: Player)
}
