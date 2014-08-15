package unwrittenfun.minecraft.unwrittenblocks.blocks.tileentities

import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.common.network.Player
import net.minecraft.client.Minecraft
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.{TileEntity, TileEntityHopper}
import net.minecraftforge.common.ForgeDirection
import unwrittenfun.minecraft.unwrittenblocks.blocks
import unwrittenfun.minecraft.unwrittenblocks.blocks.UBBlocks
import unwrittenfun.minecraft.unwrittenblocks.handlers.PacketHandler
import unwrittenfun.minecraft.unwrittenblocks.helpers.InventoryHelpers
import unwrittenfun.minecraft.unwrittenblocks.inventory.TCustomSidedInventory
import unwrittenfun.minecraft.unwrittenblocks.items.UBItems
import unwrittenfun.minecraft.unwrittenblocks.network.{PacketReceiver, TRequestPacketReceiver, TStackPacketReceiver}
import unwrittenfun.minecraft.unwrittenblocks.recipes.InfuserRecipes

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
class TileEntityDarkInfuser extends TileEntity with TCustomSidedInventory with PacketReceiver with TStackPacketReceiver with TRequestPacketReceiver {
  var infuserTicks: Int = 1001
  var infuserMaxTicks: Int = 1000
  var infuserProgress: Int = 0 // Client side only
  var loaded = false

  var itemEntity: EntityItem = null
  var itemYaw: Float = 0F

  override def writeToNBT(compound: NBTTagCompound) {
    super.writeToNBT(compound)
    UBBlocks.writeItemsToNBT(compound, this)

    compound.setInteger("InfuserTicks", infuserTicks)
    compound.setInteger("InfuserMaxTicks", infuserMaxTicks)

    writeIOToNBT(compound)
  }

  override def readFromNBT(compound: NBTTagCompound) {
    super.readFromNBT(compound)
    UBBlocks.loadItemsFromNBT(compound, this)

    infuserTicks = compound.getInteger("InfuserTicks")
    infuserMaxTicks = compound.getInteger("InfuserMaxTicks")

    readIOFromNBT(compound)
  }

  override def updateEntity() {
    if (!loaded && hasWorldObj && !worldObj.isRemote) {
      loaded = true
      onInventoryChanged()
    }

    if (hasWorldObj && !worldObj.isRemote) {
      if (infuserTicks < infuserMaxTicks) {
        infuserTicks += 1 * getSpeedMultiplier
        if (infuserTicks > infuserMaxTicks) infuserTicks = infuserMaxTicks
        val stack = getStackInSlot(0)
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

//      if (items(1) != null) {
//        import scala.util.control.Breaks._
//        breakable {
//          for (direction <- ForgeDirection.VALID_DIRECTIONS) {
//            worldObj.getBlockTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ) match {
//              case hopper: TileEntityHopper =>
//              case inventory: IInventory =>
//                setInventorySlotContents(1, InventoryHelpers.addItem(inventory, items(1)))
//                if (items(1) == null) break()
//              case _ =>
//            }
//          }
//        }
//      }
    }

    if (worldObj.isRemote && itemEntity != null) {
      itemYaw = itemYaw + 1
      itemEntity.rotationYaw = itemYaw
    }
  }

  override def onInventoryChanged() {
    if (hasWorldObj && !worldObj.isRemote) {
      val stack = getStackInSlot(0)
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

      PacketHandler.sendTEStackPacket(this, 0, 0, getStackInSlot(0), null)
    }

    super.onInventoryChanged()
  }

  override def validate() {
    super.validate()
    if (FMLCommonHandler.instance.getEffectiveSide.isClient) PacketHandler.requestTEPacket(this, 0.toByte)
  }

  def getSpeedMultiplier: Int = {
    if (getStackInSlot(2) != null && getStackInSlot(2).isItemEqual(new ItemStack(UBItems.upgrade, 1, 1))) {
      1 + Math.min(getStackInSlot(2).stackSize, 8)
    } else {
      1
    }
  }

  ////
  // IInventory Implementation
  ////

  var items: Array[ItemStack] = new Array[ItemStack](3)

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

  override def getInvName: String = blocks.DARK_INFUSER_NAME

  override def isInvNameLocalized: Boolean = true

  override def getInventoryStackLimit: Int = 64

  override def isUseableByPlayer(player: EntityPlayer): Boolean = true

  override def isItemValidForSlot(i: Int, stack: ItemStack): Boolean = true

  ////
  // Packet Receiver Implementation
  ////
  override def receiveIntPacket(id: Byte, integer: Int) {
    id match {
      case 0 => infuserProgress = integer
      case _ =>
    }
  }

  override def receiveStackPacket(id: Byte, slot: Int, stack: ItemStack) {
    id match {
      case 0 =>
        if (stack == null) {
          itemEntity = null
        } else {
          itemEntity = new EntityItem(Minecraft.getMinecraft.theWorld, 0D, 0D, 0D, stack)
          itemEntity.rotationYaw = itemYaw
          itemEntity.hoverStart = 0
        }
      case _ =>
    }
  }

  override def receiveRequestPacket(id: Byte, player: Player) {
    id match {
      case 0 => PacketHandler.sendTEStackPacket(this, 0, 0, getStackInSlot(0), player)
      case _ =>
    }
  }

  ////
  // TCustomSidedInventory
  ////
  override def getInputSlots: Array[Int] = Array(0)

  override def getOutputSlots: Array[Int] = Array(1)
}
