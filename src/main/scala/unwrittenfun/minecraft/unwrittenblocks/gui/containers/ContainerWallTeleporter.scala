package unwrittenfun.minecraft.unwrittenblocks.gui.containers

import net.minecraft.inventory.{ICrafting, Slot, Container}
import unwrittenfun.minecraft.unwrittenblocks.blocks.multiblocks.MultiblockWallTeleporter
import net.minecraft.entity.player.{EntityPlayer, InventoryPlayer}
import unwrittenfun.minecraft.unwrittenblocks.gui.slots.SlotGpsChip
import net.minecraft.item.{Item, ItemStack}
import unwrittenfun.minecraft.unwrittenblocks.items.UBItems
import unwrittenfun.minecraft.unwrittenblocks.network.TButtonPacketReceiver

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
class ContainerWallTeleporter(invPlayer: InventoryPlayer, _multiblock: MultiblockWallTeleporter) extends Container with TButtonPacketReceiver {
  multiblock.container = this

  var x: Int = 0
  while (x < 9) {
    addSlotToContainer(new Slot(invPlayer, x, 8 + 18 * x, 133))
    x += 1
  }

  var y: Int = 0
  while (y < 3) {
    var x: Int = 0
    while (x < 9) {
      addSlotToContainer(new Slot(invPlayer, x + y * 9 + 9, 8 + 18 * x, 75 + 18 * y))
      x += 1
    }
    y += 1
  }

  addSlotToContainer(new SlotGpsChip(multiblock, 0, 152, 8))
  addSlotToContainer(new SlotGpsChip(multiblock, 1, 152, 53))
  addSlotToContainer(new Slot(multiblock, 2, 130, 53))

  def multiblock: MultiblockWallTeleporter = _multiblock

  override def canInteractWith(player: EntityPlayer): Boolean = true

  override def transferStackInSlot(player: EntityPlayer, i: Int): ItemStack = {
    val slot: Slot = getSlot(i)
    if (slot != null && slot.getHasStack) {
      val stack: ItemStack = slot.getStack
      val result: ItemStack = stack.copy
      if (i >= 36) {
        if (!mergeItemStack(stack, 0, 36, false)) return null
      } else {
        if (stack.itemID == UBItems.gpsChip.itemID) {
          if (!mergeItemStack(stack, 36, 37, false)) return null
        } else if (stack.itemID == Item.enderPearl.itemID) {
          if (!mergeItemStack(stack, 38, 39, false)) return null
        } else {
          return null
        }
      }
      if (stack.stackSize == 0) slot.putStack(null) else slot.onSlotChanged()
      slot.onPickupFromSlot(player, stack)
      return result
    }
    null
  }

  override def onContainerClosed(player: EntityPlayer) {
    super.onContainerClosed(player)
    if (crafters.size == 0) multiblock.container = null
  }

  def sendSlotContentsToCrafters(i: Int, stack: ItemStack) {
    var j: Int = 0
    while (j < crafters.size) {
      crafters.get(j).asInstanceOf[ICrafting].sendSlotContents(this, i, stack)
      j += 1
    }
  }

  /////
  /// Implement TButtonPacketReceiver
  /////

  override def onButtonPacket(id: Int) {
    id match {
      case 0 => multiblock.clearDestination()
      case 1 => multiblock.locked = false
      case 2 => multiblock.locked = true
      case 3 => multiblock.useRotation = false
      case 4 => multiblock.useRotation = true
      case _ =>
    }
  }
}
