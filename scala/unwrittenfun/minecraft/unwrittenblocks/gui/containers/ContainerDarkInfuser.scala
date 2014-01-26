package unwrittenfun.minecraft.unwrittenblocks.gui.containers

import net.minecraft.entity.player.{EntityPlayer, InventoryPlayer}
import unwrittenfun.minecraft.unwrittenblocks.blocks.tileentities.TileEntityDarkInfuser
import net.minecraft.inventory.{ICrafting, Slot, Container}
import unwrittenfun.minecraft.unwrittenblocks.handlers.PacketHandler
import cpw.mods.fml.common.network.Player
import net.minecraft.item.{Item, ItemStack}
import unwrittenfun.minecraft.unwrittenblocks.items.UBItems

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
class ContainerDarkInfuser(invPlayer: InventoryPlayer, infuser: TileEntityDarkInfuser) extends Container {

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
      (62f * (infuser.infuserTicks.toFloat / infuser.infuserMaxTicks.toFloat)).toInt
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
}
