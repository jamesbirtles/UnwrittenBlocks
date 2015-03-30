package unwrittenfun.minecraft.unwrittenblocks.common.recipes;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import unwrittenfun.minecraft.unwrittenblocks.common.items.ItemRegister;
import unwrittenfun.minecraft.unwrittenblocks.common.items.StorageBallRegistry;

/**
 * Author: James Birtles
 */
public class StorageBallReturnRecipe implements IRecipe {
  @Override
  public boolean matches(InventoryCrafting crafting, World world) {
    boolean matches = false;
    for (int row = 0; row < 3; row++) {
      for (int column = 0; column < 3; column++) {
        ItemStack stack = crafting.getStackInRowAndColumn(row, column);
        if (stack != null) {
          if (matches) {
            return false;
          } else {
            if (stack.isItemEqual(ItemRegister.stackStorageBall)) {
              matches = true;
            }
          }
        }
      }
    }
    return matches;
  }

  @Override
  public ItemStack getCraftingResult(InventoryCrafting crafting) {
    for (int row = 0; row < 3; row++) {
      for (int column = 0; column < 3; column++) {
        ItemStack ball = crafting.getStackInRowAndColumn(row, column);
        if (ball != null) {
          ItemStack contained = StorageBallRegistry.getContainerStackFromBall(ball).copy();
          contained.stackSize = 8;
          return contained;
        }
      }
    }
    return null;
  }

  @Override
  public int getRecipeSize() {
    return 1;
  }

  @Override
  public ItemStack getRecipeOutput() {
    return null;
  }
}
