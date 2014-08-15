package unwrittenfun.minecraft.unwrittenblocks.blocks.tileentities

import dan200.computer.api.{ILuaContext, IComputerAccess, IPeripheral}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.{ISidedInventory, IInventory}
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraftforge.common.ForgeDirection
import unwrittenfun.minecraft.unwrittenblocks.blocks
import unwrittenfun.minecraft.unwrittenblocks.blocks.UBBlocks
import unwrittenfun.minecraft.unwrittenblocks.helpers.{NBTHelpers, CCHelpers}

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun 
 * Created: 30/07/2014.
 */
class TEItemScanner extends TileEntity with IInventory with ISidedInventory with IPeripheral {

  override def readFromNBT(compound: NBTTagCompound){
    super.readFromNBT(compound)
    UBBlocks.loadItemsFromNBT(compound, this)
  }

  override def writeToNBT(compound: NBTTagCompound){
    super.writeToNBT(compound)
    UBBlocks.writeItemsToNBT(compound, this)
  }

  ////
  // IInventory
  ////

  var items: Array[ItemStack] = new Array[ItemStack](1)

  override def getSizeInventory: Int = items.length

  override def decrStackSize(i: Int, count: Int): ItemStack = {
    var stack: ItemStack = getStackInSlot(i)
    if (stack != null) {
      if (stack.stackSize <= count) {
        setInventorySlotContents(i, null)
      }
      else {
        stack = stack splitStack count
      }
    }
    stack
  }

  override def isInvNameLocalized: Boolean = true

  override def getInventoryStackLimit: Int = 64

  override def getInvName: String = blocks.ITEM_SCANNER_NAME

  override def isItemValidForSlot(i: Int, stack: ItemStack): Boolean = true

  override def getStackInSlotOnClosing(i: Int): ItemStack = {
    val stack: ItemStack = getStackInSlot(i)
    setInventorySlotContents(i, null)
    stack
  }

  override def setInventorySlotContents(i: Int, stack: ItemStack) {
    items(i) = stack
  }

  override def isUseableByPlayer(entityplayer: EntityPlayer): Boolean = true

  override def getStackInSlot(i: Int): ItemStack = items(i)

  override def openChest() {}
  override def closeChest() {}


  ////
  // IPeripheral
  ////

  override def getType: String = blocks.ITEM_SCANNER_KEY

  override def canAttachToSide(side: Int): Boolean = true

  override def detach(computer: IComputerAccess) {}

  override def attach(computer: IComputerAccess) {}

  override def getMethodNames: Array[String] = {
    Array("getNBT", "getMeta", "getName", "getId")
  }

  override def callMethod(computer: IComputerAccess, context: ILuaContext, method: Int, arguments: Array[AnyRef]): Array[AnyRef] = {
    val stack: ItemStack = getStackInSlot(0)
    if (stack != null) {
      method match {
        case 0 =>
          if (getStackInSlot(0).getTagCompound == null) return CCHelpers.argsOfOne(false)
          CCHelpers.argsOfOne(NBTHelpers.compoundToMap(stack.getTagCompound))
        case 1 => CCHelpers.argsOfOne(stack.getItemDamage)
        case 2 => CCHelpers.argsOfOne(stack.getDisplayName)
        case _ => CCHelpers.nullArgs
      }
    } else {
      CCHelpers.argsOfOne(false)
    }
  }


  ////
  // ISidedInventory
  ////
  override def getAccessibleSlotsFromSide(side: Int): Array[Int] = Array(0)

  override def canExtractItem(slot: Int, stack: ItemStack, j: Int): Boolean = true

  override def canInsertItem(slot: Int, stack: ItemStack, j: Int): Boolean = true
}
