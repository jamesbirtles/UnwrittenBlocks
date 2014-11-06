package unwrittenfun.minecraft.unwrittenblocks.common.helpers;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun
 * Created: 06/11/2014.
 */
public class InventoryHelpers {
  public static void writeInventoryToNBT(NBTTagCompound compound, IInventory inventory) {
    NBTTagList items = new NBTTagList();
    for (byte slot = 0; slot < inventory.getSizeInventory(); slot++) {
      ItemStack stack = inventory.getStackInSlot(slot);
      if (stack != null) {
        NBTTagCompound item = new NBTTagCompound();
        item.setByte("Slot", slot);
        stack.writeToNBT(item);
        items.appendTag(item);
      }
    }
    compound.setTag("InventoryItems", items);
  }

  public static void readInventoryFromNBT(NBTTagCompound compound, IInventory inventory) {
    NBTTagList items = compound.getTagList("InventoryItems", 10);
    for (int i = 0; i < items.tagCount(); i++) {
      NBTTagCompound item = items.getCompoundTagAt(i);
      int slot = item.getByte("Slot");
      inventory.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(item));
    }
  }
}
