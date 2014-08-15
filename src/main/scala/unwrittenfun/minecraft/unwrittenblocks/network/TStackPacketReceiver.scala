package unwrittenfun.minecraft.unwrittenblocks.network

import net.minecraft.item.ItemStack

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun 
 * Created: 10/08/2014.
 */
trait TStackPacketReceiver {
  def receiveStackPacket(id: Byte, slot: Int, stack: ItemStack) {}
}
