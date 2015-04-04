package unwrittenfun.minecraft.unwrittenblocks.common.helpers;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import unwrittenfun.minecraft.unwrittenblocks.common.tileEntities.TERefulgentFabricator;

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

  public static void dropInventory(World world, int x, int y, int z) {
    TileEntity tileEntity = world.getTileEntity(x, y, z);
    if (tileEntity instanceof IInventory) {
      IInventory inventory = (IInventory) tileEntity;
      for (int i = 0; i < inventory.getSizeInventory(); i++) {
        ItemStack stack = inventory.getStackInSlotOnClosing(i);
        if (stack != null) {
          if (!(tileEntity instanceof TERefulgentFabricator && i == 9)) {
            ItemHelpers.dropItemStack(stack, world, x, y, z);
          }
        }
      }
    }
  }

  public static int insertStackIntoSlotsInRange(IInventory inventory, ItemStack stack, int slotMin, int slotMax) {
    ItemStack item = stack.copy();
    for (int i = slotMin; i < slotMax; i++) {
      if (item.stackSize > 0) {
        ItemStack slotItem = inventory.getStackInSlot(i);
        if (slotItem == null || (slotItem.stackSize < slotItem.getMaxStackSize() && stack.isItemEqual(slotItem))) {
          if (slotItem == null) {
            inventory.setInventorySlotContents(i, item.copy());
            item.stackSize = 0;
          } else {
            if (slotItem.stackSize + item.stackSize > slotItem.getMaxStackSize()) {
              item.stackSize -= slotItem.getMaxStackSize() - slotItem.stackSize;
              slotItem.stackSize = slotItem.getMaxStackSize();
            } else {
              slotItem.stackSize += item.stackSize;
              item.stackSize = 0;
            }
          }
        }
      }
    }

    return item.stackSize;
  }

  public static boolean canInsertStackIntoSlotInRange(IInventory inventory, ItemStack stack, int slotMin, int slotMax) {
    ItemStack item = stack.copy();
    for (int i = slotMin; i < slotMax; i++) {
      if (item.stackSize > 0) {
        ItemStack slotItem = inventory.getStackInSlot(i);
        if (slotItem == null || (slotItem.stackSize < slotItem.getMaxStackSize() && stack.isItemEqual(slotItem))) {
          if (slotItem == null) {
            item.stackSize = 0;
          } else {
            if (slotItem.stackSize + item.stackSize > slotItem.getMaxStackSize()) {
              item.stackSize -= slotItem.getMaxStackSize() - slotItem.stackSize;
            } else {
              item.stackSize = 0;
            }
          }
        }
      }
    }

    return item.stackSize == 0;
  }
}
