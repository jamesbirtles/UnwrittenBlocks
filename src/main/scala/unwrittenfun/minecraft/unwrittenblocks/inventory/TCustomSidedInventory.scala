package unwrittenfun.minecraft.unwrittenblocks.inventory

import cpw.mods.fml.common.FMLCommonHandler
import net.minecraft.inventory.ISidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import unwrittenfun.minecraft.unwrittenblocks.handlers.PacketHandler

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun 
 * Created: 12/08/2014.
 */
trait TCustomSidedInventory extends TileEntity with ISidedInventory  {
  var ioSides: Array[Int] = Array(0, 0, 0, 0, 0, 0)

  override def getAccessibleSlotsFromSide(side: Int): Array[Int] = {
    var slots: Array[Int] = Array()
    if ((ioSides(side) & 1) == 1) slots = slots ++ getInputSlots
    if ((ioSides(side) & 2) == 2) slots = slots ++ getOutputSlots
    slots
  }

  override def canInsertItem(slot: Int, stack: ItemStack, side: Int): Boolean = getInputSlots.contains(slot) && ((ioSides(side) & 1) == 1) && isItemValidForSlot(slot, stack)

  override def canExtractItem(slot: Int, stack: ItemStack, side: Int): Boolean = getOutputSlots.contains(slot) && (ioSides(side) & 2) == 2

  def receiveSetIOPacket(side: Int, io: Int) {
    ioSides(side) = io
  }

  def getInputSlots: Array[Int]

  def getOutputSlots: Array[Int]

  def isItemValidForSlot(slot: Int, stack: ItemStack): Boolean = true

  override def openChest() {}

  override def closeChest() {}

  def setSideIO(side: Int, io: Int) {
    ioSides(side) = io

    if (FMLCommonHandler.instance().getEffectiveSide.isClient) PacketHandler.sendSetIoPacket(this, side, io)
  }

  def writeIOToNBT(compound: NBTTagCompound) {
    compound.setIntArray("IO", ioSides)
  }

  def readIOFromNBT(compound: NBTTagCompound) {
    ioSides = compound.getIntArray("IO")
  }
}
