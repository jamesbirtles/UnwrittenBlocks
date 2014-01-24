package unwrittenfun.minecraft.unwrittenblocks.blocks.tileentities

import net.minecraft.inventory.IInventory
import net.minecraft.tileentity.TileEntity
import net.minecraft.item.ItemStack
import net.minecraft.entity.player.EntityPlayer
import unwrittenfun.minecraft.unwrittenblocks.blocks

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
class TileEntityDarkInfuser extends TileEntity with IInventory {

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

  def isUseableByPlayer(entityplayer: EntityPlayer): Boolean = true

  def openChest() {}

  def closeChest() {}

  def isItemValidForSlot(i: Int, itemstack: ItemStack): Boolean = true
}
