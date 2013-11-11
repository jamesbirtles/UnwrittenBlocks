package unwrittenfun.minecraft.unwrittenblocks.handlers

import cpw.mods.fml.common.network.{Player, IPacketHandler}
import net.minecraft.network.packet.Packet250CustomPayload
import net.minecraft.network.INetworkManager

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
class PacketHandler extends IPacketHandler {
  def onPacketData(manager: INetworkManager, packet: Packet250CustomPayload, player: Player) {
    // TODO: Add packets
  }
}
