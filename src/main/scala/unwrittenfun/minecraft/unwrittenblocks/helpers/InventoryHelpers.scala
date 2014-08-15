package unwrittenfun.minecraft.unwrittenblocks.helpers

import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun 
 * Created: 11/08/2014.
 */
object InventoryHelpers {
  def addItem(inventory: IInventory, stack: ItemStack): ItemStack = {
    if (stack != null) {
      System.out.println("Add Item")
      var newStack: ItemStack = stack.copy()
      for (slot <- 0 until inventory.getSizeInventory) {
        if (newStack != null) {
          System.out.println("Slot: " + slot)
          val invStack: ItemStack = inventory.getStackInSlot(slot)
          if (invStack != null) {
            System.out.println("Current slot not null")
            if (invStack.isItemEqual(newStack) && invStack.getMaxStackSize > invStack.stackSize) {
              System.out.println("Items Equal")
              if (invStack.stackSize + newStack.stackSize > newStack.getMaxStackSize) {
                newStack.stackSize -= invStack.getMaxStackSize - invStack.stackSize
                invStack.stackSize = invStack.getMaxStackSize
                inventory.setInventorySlotContents(slot, invStack)
              } else {
                System.out.println("Items Not Equal")
                invStack.stackSize += newStack.stackSize
                inventory.setInventorySlotContents(slot, invStack)
                newStack = null
              }
            }
          } else {
            System.out.println("Current slot null")
            inventory.setInventorySlotContents(slot, newStack)
            newStack = null
          }
        }
      }

      return newStack
    }

    null
  }

}
