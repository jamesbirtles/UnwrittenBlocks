package unwrittenfun.minecraft.unwrittenblocks.gui.containers

import cpw.mods.fml.common.network.Player
import net.minecraft.entity.player.{EntityPlayer, InventoryPlayer}
import net.minecraft.inventory.{Container, ICrafting, Slot}
import net.minecraft.item.ItemStack
import unwrittenfun.minecraft.unwrittenblocks.blocks.tileentities.TileEntityDarkInfuser
import unwrittenfun.minecraft.unwrittenblocks.handlers.PacketHandler
import unwrittenfun.minecraft.unwrittenblocks.network.TButtonPacketReceiver

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
class ContainerDarkInfuser(invPlayer: InventoryPlayer, infuser: TileEntityDarkInfuser) extends Container with TButtonPacketReceiver {
  var x: Int = 0
  while (x < 9) {
    addSlotToContainer(new Slot(invPlayer, x, 8 + 18 * x, 142))
    x += 1
  }

  var y: Int = 0
  while (y < 3) {
    var x: Int = 0
    while (x < 9) {
      addSlotToContainer(new Slot(invPlayer, x + y * 9 + 9, 8 + 18 * x, 84 + 18 * y))
      x += 1
    }
    y += 1
  }

  addSlotToContainer(new Slot(infuser, 0, 44, 36))
  addSlotToContainer(new Slot(infuser, 1, 116, 36))
  addSlotToContainer(new Slot(infuser, 2, 152, 8))

  def canInteractWith(entityplayer: EntityPlayer): Boolean = true

  var oldInfusingProgress = 0

  override def detectAndSendChanges(){
    super.detectAndSendChanges()

    if (getInfusingProgress != oldInfusingProgress) {
      oldInfusingProgress = getInfusingProgress
      PacketHandler.sendTEIntegerPacket(infuser, 0, oldInfusingProgress, invPlayer.player.asInstanceOf[Player])
    }
  }

  def getInfusingProgress: Int = {
    if (infuser.infuserTicks > infuser.infuserMaxTicks) {
      0
    } else {
      (50f * (infuser.infuserTicks.toFloat / infuser.infuserMaxTicks.toFloat)).toInt
    }
  }

  override def transferStackInSlot(player: EntityPlayer, i: Int): ItemStack = {
    val slot: Slot = getSlot(i)
    if (slot != null && slot.getHasStack) {
      val stack: ItemStack = slot.getStack
      val result: ItemStack = stack.copy
      if (i >= 36) {
        if (!mergeItemStack(stack, 0, 36, false)) return null
      } else if (!mergeItemStack(stack, 36, 37, false)) {
        return null
      }
      if (stack.stackSize == 0) slot.putStack(null) else slot.onSlotChanged()
      slot.onPickupFromSlot(player, stack)
      return result
    }
    null
  }

  override def addCraftingToCrafters(crafting: ICrafting) {
    super.addCraftingToCrafters(crafting)

    PacketHandler.sendTEIntegerPacket(infuser, 0, getInfusingProgress, invPlayer.player.asInstanceOf[Player])

    for (side <- 0 until infuser.ioSides.length) {
      crafting.sendProgressBarUpdate(this, side, infuser.ioSides(side))
    }
  }

  override def updateProgressBar(side: Int, io: Int) {
    infuser.ioSides(side) = io
  }

  /////
  /// Implement TButtonPacketReceiver
  /////

  override def onButtonPacket(id: Int) {
    id match {
      case _ =>
    }
  }
}
