package unwrittenfun.minecraft.unwrittenblocks.blocks.tileentities

import net.minecraft.inventory.IInventory
import net.minecraft.tileentity.TileEntity
import net.minecraftforge.common.ForgeDirection
import cpw.mods.fml.common.FMLCommonHandler
import unwrittenfun.minecraft.unwrittenblocks.blocks.UBBlocks
import unwrittenfun.minecraft.unwrittenblocks.blocks.multiblocks.MultiblockWallTeleporter
import unwrittenfun.minecraft.unwrittenblocks.handlers.PacketHandler
import net.minecraft.entity.player.{EntityPlayer, EntityPlayerMP}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.item.ItemStack
import unwrittenfun.minecraft.unwrittenblocks.network.PacketReceiver

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
class TileEntityWallTeleporter extends TileEntity with IInventory with PacketReceiver {
  var multiblock: MultiblockWallTeleporter = new MultiblockWallTeleporter() add this
  var mask = Array[Int](0, 0)
  private var _loaded = false
  private var _countdown = 10
  private var _startCountdown = false

  def notifyNeighboursOfConnectionChanged() {
    for (direction <- ForgeDirection.VALID_DIRECTIONS) {
      worldObj.getBlockTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ) match {
        case teleporter: TileEntityWallTeleporter =>
          teleporter onConnectionChangedFromDirection direction
        case _ =>
      }
    }
  }

  def onConnectionChangedFromDirection(direction: ForgeDirection) {
    val opposite: ForgeDirection = direction.getOpposite

    worldObj.getBlockTileEntity(xCoord + opposite.offsetX, yCoord + opposite.offsetY, zCoord + opposite.offsetZ) match {
      case teleporter: TileEntityWallTeleporter =>
        MultiblockWallTeleporter.merge(multiblock, teleporter.multiblock)
      case _ => multiblock.recalculate()
    }
  }

  def setMask(id: Int, meta: Int) {
    if ((!worldObj.isRemote && !multiblock.locked) || worldObj.isRemote) {
      mask(0) = if (id == UBBlocks.wallTeleporter.blockID) 0 else id
      mask(1) = meta

      if (!worldObj.isRemote) PacketHandler.sendNewMaskPacket(0, this, null)
    }
  }

  def teleportPlayer(player: EntityPlayerMP) {
    if (multiblock.hasDestination) multiblock teleportToDestination player
  }

  override def writeToNBT(compound: NBTTagCompound) {
    super.writeToNBT(compound)
    compound.setInteger("MaskId", mask(0))
    compound.setInteger("MaskMeta", mask(1))
    if (multiblock isController this) multiblock writeToNBT compound
  }

  override def readFromNBT(compound: NBTTagCompound) {
    super.readFromNBT(compound)
    mask(0) = compound getInteger "MaskId"
    mask(1) = compound getInteger "MaskMeta"
    if (compound hasKey "WTMultiblock") {
      new MultiblockWallTeleporter() add this
      multiblock readFromNBT compound
    }
  }

  override def updateEntity() {
    if (!_loaded && hasWorldObj) {
      _loaded = true
      if (!worldObj.isRemote && multiblock != null) multiblock init worldObj
      else if (worldObj.isRemote) {
        notifyNeighboursOfConnectionChanged()
        _startCountdown = true
      }
    }
    if (_countdown != -1 && _startCountdown) _countdown -= 1
    if (_countdown == 0 && multiblock.isController(this)) PacketHandler.requestMultiblockInfoPacket(this)

    if (cooldown) {
      multiblock.cooldown -= 1
      if (multiblock.cooldown == -1) {
        cooldown = false
      }
    }
  }

  override def validate() {
    super.validate()
    if (FMLCommonHandler.instance.getEffectiveSide.isClient) PacketHandler.requestMaskPacket(0.toByte, this)
  }

  override def invalidate() {
    super.invalidate()
    notifyNeighboursOfConnectionChanged()
  }

  override def getSizeInventory: Int = multiblock.getSizeInventory

  override def getStackInSlot(i: Int): ItemStack = multiblock getStackInSlot i

  override def decrStackSize(i: Int, j: Int): ItemStack = multiblock.decrStackSize(i, j)

  override def getStackInSlotOnClosing(i: Int): ItemStack = multiblock.getStackInSlotOnClosing(i)

  override def setInventorySlotContents(i: Int, stack: ItemStack) {
    multiblock.setInventorySlotContents(i, stack)
  }

  override def getInvName: String = multiblock.getInvName

  override def isInvNameLocalized: Boolean = multiblock.isInvNameLocalized

  override def getInventoryStackLimit: Int = multiblock.getInventoryStackLimit

  override def isUseableByPlayer(player: EntityPlayer): Boolean = multiblock.isUseableByPlayer(player)

  override def openChest() {}

  override def closeChest() {}

  override def isItemValidForSlot(i: Int, stack: ItemStack): Boolean = multiblock.isItemValidForSlot(i, stack)

  override def receiveIntPacket(id: Byte, integer: Int) {
    multiblock.receiveIntPacket(id, integer)
  }

  private var cooldown: Boolean = false

  def startCooldown() {
    multiblock.cooldown = 10
    cooldown = true
  }

  // TODO: Re-implement Computer Craft integration.
}
