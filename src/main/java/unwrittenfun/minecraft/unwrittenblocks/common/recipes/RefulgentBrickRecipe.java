package unwrittenfun.minecraft.unwrittenblocks.common.recipes;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import unwrittenfun.minecraft.unwrittenblocks.common.blocks.BlockRegister;

public class RefulgentBrickRecipe implements IRecipe {
  @Override
  public boolean matches(InventoryCrafting crafting, World world) {
    ItemStack foundWall = null;
    int wallCount = 0;
    for (int slot = 0; slot < crafting.getSizeInventory(); slot++) {
      ItemStack stack = crafting.getStackInSlot(slot);
      if (stack != null && stack.getItem() == (new ItemStack(BlockRegister.refulgentWall)).getItem()) {
        if (foundWall != null) {
          if (foundWall.isItemEqual(stack)) {
            wallCount++;
          } else {
            return false;
          }
        } else {
          foundWall = stack.copy();
          wallCount = 1;
        }
      }
    }
    return wallCount == 4;
  }

  @Override
  public ItemStack getCraftingResult(InventoryCrafting crafting) {
    for (int slot = 0; slot < crafting.getSizeInventory(); slot++) {
      ItemStack stack = crafting.getStackInSlot(slot);
      if (stack != null && stack.getItem() == (new ItemStack(BlockRegister.refulgentWall)).getItem()) {
        return new ItemStack(BlockRegister.refulgentBrick, 4, stack.getItemDamage());
      }
    }
    return null;
  }

  @Override
  public int getRecipeSize() {
    return 4;
  }

  @Override
  public ItemStack getRecipeOutput() {
    return new ItemStack(BlockRegister.refulgentBrick);
  }
}
