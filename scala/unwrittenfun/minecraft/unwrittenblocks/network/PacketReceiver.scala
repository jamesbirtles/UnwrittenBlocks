package unwrittenfun.minecraft.unwrittenblocks.network

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
trait PacketReceiver {
  def receiveIntPacket(id: Byte, integer: Int) {}
}
