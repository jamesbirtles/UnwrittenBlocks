package unwrittenfun.minecraft.unwrittenblocks.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import unwrittenfun.minecraft.unwrittenblocks.common.containers.slots.SlotResult;
import unwrittenfun.minecraft.unwrittenblocks.common.items.ItemRegister;
import unwrittenfun.minecraft.unwrittenblocks.common.recipes.InfuserRecipes;
import unwrittenfun.minecraft.unwrittenblocks.common.tileEntities.TERefulgentFabricator;

/**
 * Author: James Birtles
 */
public class ContainerRefulgentInfuser extends Container {
  public TERefulgentFabricator fabricator;

  public ContainerRefulgentInfuser(InventoryPlayer playerInventory, TERefulgentFabricator fabricator) {
    this.fabricator = fabricator;

    for (int x = 0; x < 9; x++) {
      addSlotToContainer(new Slot(playerInventory, x, 8 + 18 * x, 142));
    }

    for (int y = 0; y < 3; y++) {
      for (int x = 0; x < 9; x++) {
        addSlotToContainer(new Slot(playerInventory, x + y * 9 + 9, 8 + 18 * x, 84 + 18 * y));
      }
    }

    for (int y = 0; y < 3; y++) {
      for (int x = 0; x < 3; x++) {
        addSlotToContainer(new Slot(fabricator, (y * 3) + x, 24 + 18 * x, 14 + 18 * y));
      }
    }

    addSlotToContainer(new SlotResult(fabricator, 9, 80, 50));

    for (int y = 0; y < 3; y++) {
      for (int x = 0; x < 3; x++) {
        addSlotToContainer(new Slot(fabricator, 10 + (y * 3) + x, 100 + 18 * x, 14 + 18 * y));
      }
    }
  }

  @Override
  public boolean canInteractWith(EntityPlayer p_75145_1_) {
    return true;
  }

  @Override
  public ItemStack transferStackInSlot(EntityPlayer player, int i) {
    Slot slot = getSlot(i);
    if (slot != null && slot.getHasStack()) {
      ItemStack stack = slot.getStack();
      ItemStack result = stack.copy();
      if (i >= 36) {
        if (!mergeItemStack(stack, 0, 36, false)) return null;
      } else {
        if (!mergeItemStack(stack, 46, 55, false)) return null;
      }
      if (stack.stackSize == 0) slot.putStack(null);
      else slot.onSlotChanged();
      slot.onPickupFromSlot(player, stack);
      return result;
    }
    return null;
  }

  @Override
  public void addCraftingToCrafters(ICrafting crafting) {

    for (int i = 0; i < fabricator.ioSides.length; i++) {
      crafting.sendProgressBarUpdate(this, i, fabricator.ioSides[i]);
    }
    super.addCraftingToCrafters(crafting);
  }

  @Override
  public void updateProgressBar(int id, int value) {
    fabricator.ioSides[id] = value;
  }
}
