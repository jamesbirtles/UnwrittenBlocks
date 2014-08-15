package unwrittenfun.minecraft.unwrittenblocks.blocks.multiblocks

import net.minecraft.inventory.IInventory
import unwrittenfun.minecraft.unwrittenblocks.blocks.tileentities.TileEntityWallTeleporter
import scala.collection.mutable.ArrayBuffer
import net.minecraft.entity.player.{EntityPlayer, EntityPlayerMP}
import net.minecraft.nbt.{NBTTagIntArray, NBTTagList, NBTTagCompound}
import net.minecraft.item.{Item, ItemStack}
import scala.collection.JavaConversions._
import net.minecraft.world.World
import unwrittenfun.minecraft.unwrittenblocks.handlers.PacketHandler
import unwrittenfun.minecraft.unwrittenblocks.blocks
import unwrittenfun.minecraft.unwrittenblocks.items.UBItems
import cpw.mods.fml.common.FMLCommonHandler
import unwrittenfun.minecraft.unwrittenblocks.gui.containers.ContainerWallTeleporter
import unwrittenfun.minecraft.unwrittenblocks.network.PacketReceiver
import dan200.computer.api.{ILuaContext, IComputerAccess, IPeripheral}
import unwrittenfun.minecraft.unwrittenblocks.blocks.UBBlocks

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
object MultiblockWallTeleporter {
  def merge(multiblock1: MultiblockWallTeleporter, multiblock2: MultiblockWallTeleporter) {
    if (multiblock1 != multiblock2) for (teleporter <- multiblock2.teleporters) {
      multiblock1 add teleporter
    }
  }
}

class MultiblockWallTeleporter extends IInventory with PacketReceiver {
  var teleporters: ArrayBuffer[TileEntityWallTeleporter] = null
  var controller: TileEntityWallTeleporter = null
  var destinationWorldName: String = ""
  var destinationWorldId: Int = 0
  var destinationX: Float = 0f
  var destinationY: Float = -1f
  var destinationZ: Float = 0f
  var destinationRotation: Float = 0f
  var cooldown = -1
  private var _locked: Boolean = false
  private var _useRotation: Boolean = true
  var container: ContainerWallTeleporter = null
  private var _blocks: ArrayBuffer[Array[Int]] = null
  private var tripsLeft = 0 // max: 16

  teleporters = new ArrayBuffer[TileEntityWallTeleporter]

  def locked = _locked

  def locked_=(value: Boolean) {
    _locked = value
    if (!controller.getWorldObj.isRemote) PacketHandler.sendLockedOrRotationPacket(0.toByte, this, null)
  }

  def useRotation = _useRotation

  def useRotation_=(value: Boolean) {
    _useRotation = value
    if (!controller.getWorldObj.isRemote) PacketHandler.sendLockedOrRotationPacket(1.toByte, this, null)
  }

  def hasDestination: Boolean = destinationY >= 0

  def setDestination(worldName: String, worldId: Int, x: Float, y: Float, z: Float, r: Float) {
    destinationWorldName = worldName
    destinationWorldId = worldId
    destinationX = x
    destinationY = y
    destinationZ = z
    destinationRotation = r

    if (!controller.getWorldObj.isRemote) PacketHandler.sendDestinationPacket(this, null)
  }

  def clearDestination() {
    setDestination("", 0, 0F, -1F, 0F, 0F)
  }

  def teleportToDestination(player: EntityPlayerMP) {
    if (tripsLeft > 0 && cooldown == -1) {
      if (destinationWorldId != player.worldObj.provider.dimensionId) player.travelToDimension(destinationWorldId)

      var teleportR: Float = player.rotationYaw
      if (useRotation) teleportR = destinationRotation
      player.playerNetServerHandler.setPlayerLocation(destinationX, destinationY + 0.5F, destinationZ, teleportR, player.rotationPitch)
      setTrips(tripsLeft - 1)
      controller.startCooldown()
    }
  }

  def setTrips(newTrips: Integer) {
    tripsLeft = newTrips

    if (!controller.getWorldObj.isRemote) {
      PacketHandler.sendTEIntegerPacket(controller, 0, newTrips, null)
      if (tripsLeft == 0) refuel()
    }
  }

  def getTrips: Integer = tripsLeft

  def hasController: Boolean = controller != null

  def isController(teleporter: TileEntityWallTeleporter): Boolean = controller == teleporter

  def count: Int = teleporters.size

  def add(teleporter: TileEntityWallTeleporter): MultiblockWallTeleporter = {
    if (!hasController) controller = teleporter
    if (!teleporters.contains(teleporter)) teleporters += teleporter
    teleporter.multiblock = this
    this
  }

  def recalculate() {
    for (teleporter <- teleporters) new MultiblockWallTeleporter() add teleporter
    for (teleporter <- teleporters) teleporter.notifyNeighboursOfConnectionChanged()
  }

  def writeToNBT(compound: NBTTagCompound) {
    val wtCompound: NBTTagCompound = new NBTTagCompound

    compound.setBoolean("Locked", _locked)
    compound.setBoolean("Rotation", _useRotation)
    compound.setInteger("TripsLeft", tripsLeft)

    if (hasDestination) {
      wtCompound.setString("destWorldName", destinationWorldName)
      wtCompound.setInteger("destWorldId", destinationWorldId)
      wtCompound.setFloat("destX", destinationX)
      wtCompound.setFloat("destY", destinationY)
      wtCompound.setFloat("destZ", destinationZ)
      wtCompound.setFloat("destRotation", destinationRotation)
    }

    val wtBlocksCompound: NBTTagCompound = new NBTTagCompound
    for (teleporter <- teleporters) {
      wtBlocksCompound.setIntArray("block" + teleporters.indexOf(teleporter), Array[Int](teleporter.xCoord, teleporter.yCoord, teleporter.zCoord))
    }
    wtCompound.setCompoundTag("WTBlocks", wtBlocksCompound)

    UBBlocks.writeItemsToNBT(wtCompound, this)

    compound.setCompoundTag("WTMultiblock", wtCompound)
  }

  def readFromNBT(compound: NBTTagCompound) {
    val wtCompound: NBTTagCompound = compound getCompoundTag "WTMultiblock"

    _locked = compound getBoolean "Locked"
    _useRotation = compound getBoolean "Rotation"
    tripsLeft = compound getInteger "TripsLeft"

    if (wtCompound hasKey "destWorldId") {
      destinationWorldName = wtCompound getString "destWorldName"
      destinationWorldId = wtCompound getInteger "destWorldId"
      destinationX = wtCompound getFloat "destX"
      destinationY = wtCompound getFloat "destY"
      destinationZ = wtCompound getFloat "destZ"
      destinationRotation = wtCompound getFloat "destRotation"
    }

    val wtBlocksCompound: NBTTagCompound = wtCompound getCompoundTag "WTBlocks"
    _blocks = new ArrayBuffer[Array[Int]]
    for (tag <- wtBlocksCompound.getTags) {
      tag match {
        case nbtTag: NBTTagIntArray => _blocks add nbtTag.intArray
        case _ =>
      }
    }

    UBBlocks.loadItemsFromNBT(wtCompound, this)
  }

  def init(world: World) {
    if (_blocks != null) {
      for (blockCoords <- _blocks) {
          world.getBlockTileEntity(blockCoords(0), blockCoords(1), blockCoords(2)) match {
          case teleporter: TileEntityWallTeleporter => add(teleporter)
          case _ =>
        }
      }
      _blocks = null
    }
  }

  // Inventory implementation
  var items: Array[ItemStack] = new Array[ItemStack](3)

  override def getSizeInventory: Int = items.length

  override def getStackInSlot(i: Int): ItemStack = items(i)

  override def decrStackSize(i: Int, count: Int): ItemStack = {
    var stack: ItemStack = getStackInSlot(i)
    if (stack != null) {
      if (stack.stackSize <= count) {
        setInventorySlotContents(i, null)
      }
      else {
        stack = stack splitStack count
        onInventoryChanged()
      }
    }
    stack
  }

  override def getStackInSlotOnClosing(i: Int): ItemStack = {
    val stack: ItemStack = getStackInSlot(i)
    setInventorySlotContents(i, null)
    stack
  }

  override def setInventorySlotContents(i: Int, stack: ItemStack) {
    items(i) = stack
    onInventoryChanged()
  }

  override def getInvName: String = blocks.WALL_TELEPORTER_NAME

  override def isInvNameLocalized: Boolean = true

  override def getInventoryStackLimit: Int = 64

  override def isUseableByPlayer(player: EntityPlayer): Boolean = true

  override def openChest() {}

  override def closeChest() {}

  override def isItemValidForSlot(i: Int, stack: ItemStack): Boolean = (stack != null) && (stack.getItem == UBItems.gpsChip) && (stack.getItemDamage == 1)

  @Override def onInventoryChanged() {
    if (FMLCommonHandler.instance.getEffectiveSide.isServer) {
      val stack: ItemStack = getStackInSlot(0)
      if (getStackInSlot(1) == null && stack != null && stack.getItem.itemID == UBItems.gpsChip.itemID && stack.getItemDamage == 1 && stack.hasTagCompound) {
        val stackCompound: NBTTagCompound = stack.getTagCompound
        if (stackCompound hasKey "LocationData") {
          val locationCompound: NBTTagCompound = stackCompound getCompoundTag "LocationData"
          val worldName: String = locationCompound getString "worldName"
          val worldId: Int = locationCompound getInteger "worldId"
          val x: Float = locationCompound getFloat "locationX"
          val y: Float = locationCompound getFloat "locationY"
          val z: Float = locationCompound getFloat "locationZ"
          val r: Float = locationCompound getFloat "locationRotation"
          setDestination(worldName, worldId, x, y, z, r)
          setInventorySlotContents(0, null)
          setInventorySlotContents(1, stack)
          if (container != null) {
            container.sendSlotContentsToCrafters(36, null)
            container.sendSlotContentsToCrafters(37, stack)
          }
        }
      }

      refuel()
    }

    for (teleporter <- teleporters) teleporter.onInventoryChanged()
  }

  def refuel() {
    if (tripsLeft == 0 && getStackInSlot(2) != null && getStackInSlot(2).getItem.itemID == Item.enderPearl.itemID) {
      val pearlStack: ItemStack = getStackInSlot(2)
      pearlStack.stackSize -= 1
      if (pearlStack.stackSize < 1) {
        setInventorySlotContents(2, null)
      }
      setTrips(16)

      if (container != null) {
        container.sendSlotContentsToCrafters(38, getStackInSlot(2))
      }
    }
  }

  override def receiveIntPacket(id: Byte, integer: Int) {
    if (id == 0) {
      setTrips(integer)
    }
  }
}
