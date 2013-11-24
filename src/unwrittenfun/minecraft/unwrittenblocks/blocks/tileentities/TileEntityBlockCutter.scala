package unwrittenfun.minecraft.unwrittenblocks.blocks.tileentities

import net.minecraft.inventory.IInventory
import net.minecraft.tileentity.TileEntity
import net.minecraft.item.{ItemBlock, ItemStack}
import net.minecraft.entity.player.EntityPlayer
import unwrittenfun.minecraft.unwrittenblocks.blocks
import net.minecraft.nbt.{NBTTagList, NBTTagCompound}
import unwrittenfun.minecraft.unwrittenblocks.recipes.CutterRecipes
import unwrittenfun.minecraft.unwrittenblocks.handlers.PacketHandler
import unwrittenfun.minecraft.unwrittenblocks.network.PacketReceiver

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
class TileEntityBlockCutter extends TileEntity with IInventory with PacketReceiver {
  override def writeToNBT(compound: NBTTagCompound) {
    val cutterCompound: NBTTagCompound = new NBTTagCompound()

    val items: NBTTagList = new NBTTagList
    var i: Int = 0
    while (i < getSizeInventory) {
      val stack: ItemStack = getStackInSlot(i)
      if (stack != null) {
        val item: NBTTagCompound = new NBTTagCompound
        item.setByte("Slot", i.toByte)
        stack writeToNBT item
        items appendTag item
      }
      i += 1
    }
    cutterCompound.setTag("Items", items)

    compound.setCompoundTag("BlockCutter", cutterCompound)
  }

  override def readFromNBT(compound: NBTTagCompound) {
    val cutterCompound: NBTTagCompound = compound getCompoundTag "BlockCutter"

    val items: NBTTagList = cutterCompound getTagList "Items"
    var i: Int = 0
    while (i < items.tagCount) {
      val item: NBTTagCompound = items.tagAt(i).asInstanceOf[NBTTagCompound]
      val slot: Int = item getByte "Slot"
      if (slot >= 0 && slot < getSizeInventory) {
        setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(item))
      }
      i += 1
    }
  }

  var items: Array[ItemStack] = new Array[ItemStack](2)

  def closeChest() {}

  def openChest() {}

  def setInventorySlotContents(i: Int, stack: ItemStack) {
    items(i) = stack
    onInventoryChanged()
  }

  def getSizeInventory: Int = items.length

  def getStackInSlot(i: Int): ItemStack = items(i)

  def decrStackSize(i: Int, count: Int): ItemStack = {
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

  def getStackInSlotOnClosing(i: Int): ItemStack = {
    val stack: ItemStack = getStackInSlot(i)
    setInventorySlotContents(i, null)
    stack
  }

  def getInvName: String = blocks.CUTTER_NAME

  def isInvNameLocalized: Boolean = true

  def getInventoryStackLimit: Int = 64

  def isUseableByPlayer(player: EntityPlayer): Boolean = true

  def isItemValidForSlot(i: Int, stack: ItemStack): Boolean = stack.getItem.isInstanceOf[ItemBlock]

  private var cuttingTick: Int = -1
  private var _cuttingMode: Int = CutterRecipes.MODE_STAIRS

  override def onInventoryChanged() {
    super.onInventoryChanged()

    if (cuttingTick < 0 && canCut) {
      cuttingTick = 5
    }
  }

  override def updateEntity() {
    if (cuttingTick == 0) {
      cuttingTick = -1

      if (canCut) {
        var stack1: ItemStack = getStackInSlot(1)
        var stack0: ItemStack = getStackInSlot(0)
        val output: (Int, Int) = CutterRecipes.getOutput(_cuttingMode, (stack0.itemID, stack0.getItemDamage))

        if (stack1 == null) stack1 = new ItemStack(output._1, output._2, 0)

        stack0.stackSize -= 1
        if (stack0.stackSize <= 0) stack0 = null
        items(0) = stack0

        stack1.stackSize += 1
        items(1) = stack1

        onInventoryChanged()

      }
    }

    if (cuttingTick > -1) cuttingTick -= 1
  }

  def canCut: Boolean = {
    var result: Boolean = getStackInSlot(0) != null && getStackInSlot(0).getItem.isInstanceOf[ItemBlock]

    if (result && CutterRecipes.hasRecipe(_cuttingMode, (getStackInSlot(0).itemID, getStackInSlot(0).getItemDamage))) {
      val output: (Int, Int) = CutterRecipes.getOutput(_cuttingMode, (getStackInSlot(0).itemID, getStackInSlot(0).getItemDamage))
      result = result && (getStackInSlot(1) == null ||
        (getStackInSlot(1).stackSize < getStackInSlot(1).getMaxStackSize && (getStackInSlot(1).itemID == output._1 && getStackInSlot(1).getItemDamage == output._2)))
    } else result = false

    result
  }

  def cuttingMode: Int = _cuttingMode

  def cuttingMode_=(mode: Int) {
    _cuttingMode = mode

    if (hasWorldObj && !worldObj.isRemote) PacketHandler.sendTEIntegerPacket(this, 0, mode, null)
  }

  override def receiveIntPacket(id: Byte, integer: Int) {
    id match {
      case 0 => cuttingMode = integer
      case _ =>
    }
  }
}
