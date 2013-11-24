package unwrittenfun.minecraft.unwrittenblocks.gui.slots

import net.minecraft.inventory.{IInventory, Slot}
import net.minecraft.item.ItemStack
import unwrittenfun.minecraft.unwrittenblocks.items.UBItems

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
class SlotGpsChip(inventory: IInventory, id: Int, x: Int, y: Int) extends Slot(inventory, id, x, y) {
  override def isItemValid(stack: ItemStack): Boolean = stack.itemID == UBItems.gpsChip.itemID && stack.getItemDamage == 1

  override def getSlotStackLimit: Int = 1
}
