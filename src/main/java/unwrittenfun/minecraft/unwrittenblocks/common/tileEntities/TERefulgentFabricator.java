package unwrittenfun.minecraft.unwrittenblocks.common.tileEntities;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import unwrittenfun.minecraft.unwrittenblocks.common.containers.ContainerDummy;
import unwrittenfun.minecraft.unwrittenblocks.common.helpers.InventoryHelpers;
import unwrittenfun.minecraft.unwrittenblocks.common.helpers.ItemHelpers;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Author: James Birtles
 */
public class TERefulgentFabricator extends TEConfigurableIO implements IInventory, ICraftingResult {
  public InventoryCrafting crafting = new InventoryCrafting(new ContainerDummy(), 3, 3);

  public TERefulgentFabricator() {
    this.items = new ItemStack[10];
  }

  @Override
  public void updateEntity() {
    if (hasWorldObj()) {
      if (!worldObj.isRemote) {
        if (worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
          craftItem();
        }

        for (int side = 0; side < ioSides.length; side++) {
          if (ioSides[side] == 2) {
            ForgeDirection direction = ForgeDirection.getOrientation(side);
            TileEntity tileEntity = worldObj.getTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
            if (tileEntity instanceof IInventory) {
              IInventory inventory = (IInventory) tileEntity;
              for (int i = 10; i < 19; i++) {
                ItemStack item = getStackInSlot(i);
                if (item != null) {
                  item.stackSize = InventoryHelpers.insertStackIntoSlotsInRange(inventory, item, 0, inventory.getSizeInventory());
                  if (item.stackSize == 0) {
                    setInventorySlotContents(i, null);
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  private void craftItem() {
    ItemStack result = CraftingManager.getInstance().findMatchingRecipe(crafting, worldObj);
    if (result != null && canTakeResult() && InventoryHelpers.canInsertStackIntoSlotInRange(this, result, 10, 19)) {
      int leftover = InventoryHelpers.insertStackIntoSlotsInRange(this, result, 10, 19);
      if (leftover != result.stackSize) {

        if (leftover > 0) {
          ItemStack leftoverStack = result.copy();
          leftoverStack.stackSize = leftover;
          ItemHelpers.dropItemStack(leftoverStack, worldObj, xCoord, yCoord, zCoord);
        }
        onResultTaken();
      }
    }
  }

  @Override
  public int getSizeInventory() {
    return 19;
  }

  @Override
  public ItemStack getStackInSlot(int slot) {
    if (slot < 9) {
      return crafting.getStackInSlot(slot);
    }

    return items[slot - 9];
  }

  @Override
  public ItemStack getStackInSlotOnClosing(int slot) {
    if (slot < 9) {
      return crafting.getStackInSlotOnClosing(slot);
    }

    ItemStack stack = getStackInSlot(slot);
    setInventorySlotContents(slot, null);
    return stack;
  }

  @Override
  public void setInventorySlotContents(int slot, ItemStack stack) {
    if (slot < 9) {
      crafting.setInventorySlotContents(slot, stack);
    } else {
      items[slot - 9] = stack;
    }
    onInventoryChanged();
  }

  @Override
  public void onInventoryChanged() {
    ItemStack result = CraftingManager.getInstance().findMatchingRecipe(crafting, worldObj);
    if (result != null) {
      items[0] = result.copy();
    } else {
      items[0] = null;
    }
  }

  @Override
  public void onResultTaken() {
    for (int i = 0; i < 9; i++) {
      ItemStack ingred = getStackInSlot(i);
      if (ingred != null) {
        boolean taken = false;
        for (int j = 10; j < 19; j++) {
          ItemStack mat = getStackInSlot(j);
          if (!taken && mat != null && mat.isItemEqual(ingred) && ItemStack.areItemStackTagsEqual(mat, ingred)) {
            taken = true;
            decrStackSize(j, 1);
          }
        }


        if (!taken) {
          for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
            if (!taken) {
              TileEntity tileEntity = worldObj.getTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
              if (tileEntity instanceof IInventory) {
                IInventory inventory = (IInventory) tileEntity;
                int startValue = 0;
                if (inventory instanceof TERefulgentFabricator) {
                  startValue = 10;
                }
                for (int j = startValue; j < inventory.getSizeInventory(); j++) {
                  ItemStack mat = inventory.getStackInSlot(j);
                  if (!taken && mat != null && mat.isItemEqual(ingred) && ItemStack.areItemStackTagsEqual(mat, ingred)) {
                    taken = true;
                    mat.stackSize -= 1;
                    if (mat.stackSize < 1) {
                      inventory.setInventorySlotContents(j, null);
                    }
                  }
                }
              }
            }
          }
        }

        if (!taken) {
          decrStackSize(i, 1);
        }
      }
    }
  }

  @Override
  public boolean canTakeResult() {
    ItemStack[][] directionInventories = new ItemStack[6][];
    ItemStack[] tempItems = copyItems(this);
    boolean canTake = true;
    for (int i = 0; i < 9; i++) {
      if (canTake) {
        ItemStack ingred = tempItems[i];
        if (ingred != null) {
          boolean taken = false;
          for (int j = 10; j < 19; j++) {
            ItemStack mat = tempItems[j];
            if (!taken && mat != null && mat.isItemEqual(ingred) && ItemStack.areItemStackTagsEqual(mat, ingred)) {
              taken = true;
              mat.stackSize -= 1;
              if (mat.stackSize < 1) {
                tempItems[j] = null;
              }
            }
          }

          if (!taken) {
            for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
              if (!taken) {
                TileEntity tileEntity = worldObj.getTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
                if (tileEntity instanceof IInventory) {
                  IInventory inventory = (IInventory) tileEntity;
                  if (directionInventories[direction.ordinal()] == null) {
                    directionInventories[direction.ordinal()] = copyItems(inventory);
                  }
                  int startValue = 0;
                  if (inventory instanceof TERefulgentFabricator) {
                    startValue = 10;
                  }
                  for (int j = startValue; j < inventory.getSizeInventory(); j++) {
                    ItemStack mat = directionInventories[direction.ordinal()][j];
                    if (!taken && mat != null && mat.isItemEqual(ingred) && ItemStack.areItemStackTagsEqual(mat, ingred)) {
                      taken = true;
                      mat.stackSize -= 1;
                      if (mat.stackSize < 1) {
                        directionInventories[direction.ordinal()][j] = null;
                      }
                    }
                  }
                }
              }
            }
          }

          canTake = taken;
        }
      }
    }
    return canTake;
  }

  private ItemStack[] copyItems(IInventory inventory) {
    ItemStack[] newItems = new ItemStack[inventory.getSizeInventory()];
    for (int i = 0; i < inventory.getSizeInventory(); i++) {
      ItemStack stack = inventory.getStackInSlot(i);
      if (stack != null) {
        newItems[i] = stack.copy();
      }
    }
    return newItems;
  }

  @Override
  public String getInventoryName() {
    return "Refulgent Fabricator";
  }

  @Override
  public ArrayList<Integer> getInputSlots() {
    return (ArrayList<Integer>) Arrays.asList(10, 11, 12, 13, 14, 15, 16, 17, 18);
  }

  @Override
  public ArrayList<Integer> getOutputSlots() {
    return (ArrayList<Integer>) Arrays.asList(10, 11, 12, 13, 14, 15, 16, 17, 18);
  }
}
