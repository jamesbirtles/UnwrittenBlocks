package unwrittenfun.minecraft.unwrittenblocks.blocks.tileentities

import net.minecraft.inventory.IInventory
import net.minecraft.tileentity.TileEntity
import net.minecraft.item.ItemStack
import net.minecraft.entity.player.EntityPlayer
import unwrittenfun.minecraft.unwrittenblocks.blocks
import net.minecraft.nbt.NBTTagCompound
import unwrittenfun.minecraft.unwrittenblocks.blocks.UBBlocks
import unwrittenfun.minecraft.unwrittenblocks.recipes.InfuserRecipes
import unwrittenfun.minecraft.unwrittenblocks.network.PacketReceiver

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
class TileEntityDarkInfuser extends TileEntity with IInventory with PacketReceiver {
  var infuserTicks: Int = 1001
  var infuserMaxTicks: Int = 1000
  var infuserProgress: Int = 0 // Client side only
  var loaded = false

  override def writeToNBT(compound: NBTTagCompound) {
    super.writeToNBT(compound)
    UBBlocks.writeItemsToNBT(compound, this)

    compound.setInteger("InfuserTicks", infuserTicks)
    compound.setInteger("InfuserMaxTicks", infuserMaxTicks)
  }

  override def readFromNBT(compound: NBTTagCompound) {
    super.readFromNBT(compound)
    UBBlocks.loadItemsFromNBT(compound, this)

    infuserTicks = compound.getInteger("InfuserTicks")
    infuserMaxTicks = compound.getInteger("InfuserMaxTicks")
  }

  override def updateEntity() {
    if (!loaded && hasWorldObj && !worldObj.isRemote) {
      loaded = true
      onInventoryChanged()
    }

    if (hasWorldObj && !worldObj.isRemote) {
      if (infuserTicks < infuserMaxTicks) {
        infuserTicks += 1
        var stack = getStackInSlot(0)
        if (stack != null) infuserMaxTicks = InfuserRecipes.getTickTime(stack.itemID, stack.getItemDamage)
      }

      if (infuserTicks == infuserMaxTicks && getStackInSlot(0) != null && InfuserRecipes.getInfusingResult(getStackInSlot(0).itemID, getStackInSlot(0).getItemDamage) != null) {
        var stack = getStackInSlot(0)
        val infuserResult = InfuserRecipes.getInfusingResult(stack.itemID, stack.getItemDamage)
        var resultStack = getStackInSlot(1)
        if (resultStack == null || ((resultStack.itemID == infuserResult.itemID && resultStack.getItemDamage == infuserResult.getItemDamage) &&
          (resultStack.stackSize + infuserResult.stackSize) <= resultStack.getMaxStackSize)) {
          stack.stackSize -= 1
          if (stack.stackSize < 1) stack = null
          if (resultStack == null) resultStack = infuserResult.copy() else resultStack.stackSize += infuserResult.stackSize

          items(0) = stack
          items(1) = resultStack
          infuserMaxTicks = 1000
          infuserTicks = 1001
          onInventoryChanged()
        }
      }
    }
  }

  override def onInventoryChanged() {
    if (hasWorldObj && !worldObj.isRemote) {
      var stack = getStackInSlot(0)
      if (stack != null && InfuserRecipes.getInfusingResult(stack.itemID, stack.getItemDamage) != null) {
        val infuserResult = InfuserRecipes.getInfusingResult(stack.itemID, stack.getItemDamage)
        val resultStack = getStackInSlot(1)
        if (resultStack == null || ((resultStack.itemID == infuserResult.itemID && resultStack.getItemDamage == infuserResult.getItemDamage) &&
                                    (resultStack.stackSize + infuserResult.stackSize) <= resultStack.getMaxStackSize)) {
          if (infuserTicks > infuserMaxTicks) {
            infuserMaxTicks = InfuserRecipes.getTickTime(stack.itemID, stack.getItemDamage)
            infuserTicks = 0
          }
        } else {
          infuserTicks = 1001
          infuserMaxTicks = 1000
        }
      } else {
        infuserTicks = 1001
        infuserMaxTicks = 1000
      }
    }

    super.onInventoryChanged()
  }

  ////
  // IInventory Implementation
  ////

  var items: Array[ItemStack] = new Array[ItemStack](2)

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

  override def getStackInSlotOnClosing(i: Int): ItemStack = {
    val stack: ItemStack = getStackInSlot(i)
    setInventorySlotContents(i, null)
    stack
  }

  override def setInventorySlotContents(i: Int, stack: ItemStack) {
    items(i) = stack
    onInventoryChanged()
  }

  def getInvName: String = blocks.DARK_INFUSER_NAME

  def isInvNameLocalized: Boolean = true

  def getInventoryStackLimit: Int = 64

  def isUseableByPlayer(player: EntityPlayer): Boolean = true

  def openChest() {}

  def closeChest() {}

  def isItemValidForSlot(i: Int, stack: ItemStack): Boolean = true

  ////
  // Packet Receiver Implementation
  ////
  override def receiveIntPacket(id: Byte, integer: Int) {
    id match {
      case 0 => infuserProgress = integer
      case _ =>
    }
  }
}
