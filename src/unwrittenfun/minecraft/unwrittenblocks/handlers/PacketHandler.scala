package unwrittenfun.minecraft.unwrittenblocks.handlers

import com.google.common.io.ByteArrayDataInput
import com.google.common.io.ByteStreams
import cpw.mods.fml.common.network.{PacketDispatcher, Player, IPacketHandler}
import net.minecraft.network.packet.Packet250CustomPayload
import net.minecraft.network.INetworkManager
import net.minecraft.entity.player.EntityPlayer
import java.io.{IOException, DataOutputStream, ByteArrayOutputStream}
import unwrittenfun.minecraft.unwrittenblocks.blocks.tileentities.TileEntityWallTeleporter
import unwrittenfun.minecraft.unwrittenblocks.CHANNEL
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World
import unwrittenfun.minecraft.unwrittenblocks.blocks.multiblocks.MultiblockWallTeleporter
import net.minecraftforge.common.DimensionManager
import net.minecraft.inventory.Container
import unwrittenfun.minecraft.unwrittenblocks.gui.containers.ContainerWallTeleporter

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
object PacketHandler {
  def sendNewMaskPacket(id: Byte, teleporter: TileEntityWallTeleporter, player: Player) {
    System.out.println("Send New Mask Packet")
    val byteStream: ByteArrayOutputStream = new ByteArrayOutputStream
    val dataStream: DataOutputStream = new DataOutputStream(byteStream)
    try {
      dataStream writeByte 0
      dataStream writeByte id
      dataStream writeInt teleporter.mask(0)
      dataStream writeByte teleporter.mask(1)
      dataStream writeInt teleporter.xCoord
      dataStream writeInt teleporter.yCoord
      dataStream writeInt teleporter.zCoord
      if (player == null) PacketDispatcher.sendPacketToAllInDimension(PacketDispatcher.getPacket(CHANNEL, byteStream.toByteArray), teleporter.worldObj.provider.dimensionId)
      else PacketDispatcher.sendPacketToPlayer(PacketDispatcher.getPacket(CHANNEL, byteStream.toByteArray), player)
    }
    catch {
      case ex: IOException => System.err append "[UnwrittenBlocks] [Wall Teleporter] Failed to send new mask (" + id + ") packet"
    }
  }

  def onNewMaskPacket(reader: ByteArrayDataInput, world: World) {
    val id: Byte = reader.readByte
    val maskId: Int = reader.readInt
    val maskMeta: Byte = reader.readByte
    val x: Int = reader.readInt
    val y: Int = reader.readInt
    val z: Int = reader.readInt
    val tileEntity: TileEntity = world.getBlockTileEntity(x, y, z)
    id match {
      case 0 =>
        tileEntity match {
          case teleporter: TileEntityWallTeleporter =>
            teleporter.setMask(maskId, maskMeta)
            world.markBlockForRenderUpdate(x, y, z)
          case _ =>
        }
      case _ =>
    }
  }

  def requestMaskPacket(id: Byte, teleporter: TileEntityWallTeleporter) {
    val byteStream: ByteArrayOutputStream = new ByteArrayOutputStream
    val dataStream: DataOutputStream = new DataOutputStream(byteStream)
    try {
      dataStream writeByte 1
      dataStream writeByte id
      dataStream writeInt teleporter.xCoord
      dataStream writeInt teleporter.yCoord
      dataStream writeInt teleporter.zCoord
      PacketDispatcher sendPacketToServer PacketDispatcher.getPacket(CHANNEL, byteStream.toByteArray)
    }
    catch {
      case ex: IOException => System.err append "[UnwrittenBlocks] [Wall Teleporter] Failed to request mask (" + id + ") packet"
    }
  }

  def onRequestMaskPacket(reader: ByteArrayDataInput, world: World, player: Player) {
    val id: Byte = reader.readByte
    val x: Int = reader.readInt
    val y: Int = reader.readInt
    val z: Int = reader.readInt
    val tileEntity: TileEntity = world.getBlockTileEntity(x, y, z)
    id match {
      case 0 =>
        tileEntity match {
          case teleporter: TileEntityWallTeleporter => sendNewMaskPacket(0.toByte, teleporter, player)
          case _ =>
        }
      case _ =>
    }
  }

  def sendDestinationPacket(multiblock: MultiblockWallTeleporter, player: Player) {
    val byteStream: ByteArrayOutputStream = new ByteArrayOutputStream
    val dataStream: DataOutputStream = new DataOutputStream(byteStream)
    try {
      dataStream writeByte 3
      dataStream writeInt multiblock.controller.worldObj.provider.dimensionId
      dataStream writeInt multiblock.controller.xCoord
      dataStream writeInt multiblock.controller.yCoord
      dataStream writeInt multiblock.controller.zCoord
      dataStream writeInt multiblock.destinationWorldId
      dataStream writeFloat multiblock.destinationX
      dataStream writeFloat multiblock.destinationY
      dataStream writeFloat multiblock.destinationZ
      dataStream writeFloat multiblock.destinationRotation
      dataStream writeUTF multiblock.destinationWorldName
      if (player == null) PacketDispatcher.sendPacketToAllInDimension(PacketDispatcher.getPacket(CHANNEL, byteStream.toByteArray), multiblock.controller.worldObj.provider.dimensionId)
      else PacketDispatcher.sendPacketToPlayer(PacketDispatcher.getPacket(CHANNEL, byteStream.toByteArray), player)
    }
    catch {
      case ex: IOException => System.err append "[UnwrittenBlocks] [Wall Teleporter] Failed to send destination packet"
    }
  }

  def onDestinationPacket(reader: ByteArrayDataInput, player: EntityPlayer) {
    val cWorldId: Int = reader.readInt
    val cX: Int = reader.readInt
    val cY: Int = reader.readInt
    val cZ: Int = reader.readInt
    val dWorldId: Int = reader.readInt
    val dX: Float = reader.readFloat
    val dY: Float = reader.readFloat
    val dZ: Float = reader.readFloat
    val dR: Float = reader.readFloat
    val dWorldName: String = reader.readUTF
    if (player.worldObj.provider.dimensionId == cWorldId) {
      player.worldObj.getBlockTileEntity(cX, cY, cZ) match {
        case teleporter: TileEntityWallTeleporter => teleporter.multiblock.setDestination(dWorldName, dWorldId, dX, dY, dZ, dR)
        case _ =>
      }
    }
  }

  def requestMultiblockInfoPacket(teleporter: TileEntityWallTeleporter) {
    val byteStream: ByteArrayOutputStream = new ByteArrayOutputStream
    val dataStream: DataOutputStream = new DataOutputStream(byteStream)
    try {
      dataStream writeByte 4
      dataStream writeInt teleporter.worldObj.provider.dimensionId
      dataStream writeInt teleporter.xCoord
      dataStream writeInt teleporter.yCoord
      dataStream writeInt teleporter.zCoord
      PacketDispatcher sendPacketToServer PacketDispatcher.getPacket(CHANNEL, byteStream.toByteArray)
    }
    catch {
      case ex: IOException => System.err append "[UnwrittenBlocks] [Wall Teleporter] Failed to send destination packet"
    }
  }

  def onRequestMultiblockInfoPacket(reader: ByteArrayDataInput, player: Player) {
    val worldId: Int = reader.readInt
    val world: World = DimensionManager getWorld worldId
    val x: Int = reader.readInt
    val y: Int = reader.readInt
    val z: Int = reader.readInt
    world.getBlockTileEntity(x, y, z) match {
      case teleporter: TileEntityWallTeleporter =>
        sendDestinationPacket(teleporter.multiblock, player)
        sendLockedOrRotationPacket(0, teleporter.multiblock, player)
        sendLockedOrRotationPacket(1, teleporter.multiblock, player)
      case _ =>
    }
  }

  def sendButtonPacket(guiId: Byte, buttonId: Byte) {
    val byteStream: ByteArrayOutputStream = new ByteArrayOutputStream
    val dataStream: DataOutputStream = new DataOutputStream(byteStream)
    try {
      dataStream writeByte 5
      dataStream writeByte guiId
      dataStream writeByte buttonId
      PacketDispatcher sendPacketToServer PacketDispatcher.getPacket(CHANNEL, byteStream.toByteArray)
    }
    catch {
      case ex: IOException => System.err append "[UnwrittenBlocks] [Wall Teleporter] Failed to send button (" + guiId + ", " + buttonId + ") packet"
    }
  }

  def onButtonPacket(reader: ByteArrayDataInput, player: EntityPlayer) {
    val guiId: Int = reader.readByte
    val buttonId: Int = reader.readByte
    val container: Container = player.openContainer
    guiId match {
      case 0 =>
        container match {
          case containerTeleporter: ContainerWallTeleporter =>
            buttonId match {
              case 0 => containerTeleporter.multiblock.clearDestination()
              case 1 => containerTeleporter.multiblock.locked = false
              case 2 => containerTeleporter.multiblock.locked = true
              case 3 => containerTeleporter.multiblock.useRotation = false
              case 4 => containerTeleporter.multiblock.useRotation = true
              case _ =>
            }
        }
      case _ =>
    }
  }

  def sendLockedOrRotationPacket(id: Byte, multiblock: MultiblockWallTeleporter, player: Player) {
    val byteStream: ByteArrayOutputStream = new ByteArrayOutputStream
    val dataStream: DataOutputStream = new DataOutputStream(byteStream)
    try {
      dataStream writeByte 6
      dataStream writeByte id
      dataStream writeInt multiblock.controller.worldObj.provider.dimensionId
      dataStream writeInt multiblock.controller.xCoord
      dataStream writeInt multiblock.controller.yCoord
      dataStream writeInt multiblock.controller.zCoord
      id match {
        case 0 => dataStream writeBoolean multiblock.locked
        case 1 => dataStream writeBoolean multiblock.useRotation
        case _ =>
      }
      if (player == null) PacketDispatcher.sendPacketToAllInDimension(PacketDispatcher.getPacket(CHANNEL, byteStream.toByteArray), multiblock.controller.worldObj.provider.dimensionId)
      else PacketDispatcher.sendPacketToPlayer(PacketDispatcher.getPacket(CHANNEL, byteStream.toByteArray), player)
    }
    catch {
      case ex: IOException => System.err append "[UnwrittenBlocks] [Wall Teleporter] Failed to send locked packet"
    }
  }

  def onLockedOrRotationPacket(reader: ByteArrayDataInput, player: EntityPlayer) {
    val id: Byte = reader.readByte
    val cWorldId: Int = reader.readInt
    val cX: Int = reader.readInt
    val cY: Int = reader.readInt
    val cZ: Int = reader.readInt
    val bool: Boolean = reader.readBoolean
    if (player.worldObj.provider.dimensionId == cWorldId) {
      player.worldObj.getBlockTileEntity(cX, cY, cZ) match {
        case teleporter: TileEntityWallTeleporter =>
          id match {
            case 0 => teleporter.multiblock.locked = bool
            case 1 => teleporter.multiblock.useRotation = bool
            case _ =>
          }
        case _ =>
      }
    }
  }
}

class PacketHandler extends IPacketHandler {
  def onPacketData(manager: INetworkManager, packet: Packet250CustomPayload, player: Player) {
    val reader: ByteArrayDataInput = ByteStreams newDataInput packet.data
    val entityPlayer: EntityPlayer = player.asInstanceOf[EntityPlayer]
    val packetId: Byte = reader.readByte

    packetId match {
      case 0 => PacketHandler.onNewMaskPacket(reader, entityPlayer.worldObj)
      case 1 => PacketHandler.onRequestMaskPacket(reader, entityPlayer.worldObj, player)
      case 3 => PacketHandler.onDestinationPacket(reader, entityPlayer)
      case 4 => PacketHandler.onRequestMultiblockInfoPacket(reader, player)
      case 5 => PacketHandler.onButtonPacket(reader, entityPlayer)
      case 6 => PacketHandler.onLockedOrRotationPacket(reader, entityPlayer)
      case _ =>
    }
  }
}
