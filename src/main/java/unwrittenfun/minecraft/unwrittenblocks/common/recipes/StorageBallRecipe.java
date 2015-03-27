package unwrittenfun.minecraft.unwrittenblocks.common.recipes;

import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import unwrittenfun.minecraft.unwrittenblocks.common.items.ItemRegister;
import unwrittenfun.minecraft.unwrittenblocks.common.items.StorageBallRegistry;

/**
 * Author: James Birtles
 */
public class StorageBallRecipe implements IRecipe {
  @Override
  public boolean matches(InventoryCrafting crafting, World world) {
    ItemStack target = crafting.getStackInRowAndColumn(0, 0);
    if (target == null || !StorageBallRegistry.isBallable(target)) return false;
    for (int row = 0; row < 3; row++) {
      for (int column = 0; column < 3; column++) {
        ItemStack stack = crafting.getStackInRowAndColumn(row, column);
        if (column == 1 && row == 1) {
          if (stack == null || !stack.isItemEqual(ItemRegister.stackStorageBallContainer)) return false;
        } else {
          if (stack == null || !stack.isItemEqual(target)) return false;
        }
      }
    }
    return true;
  }

  @Override
  public ItemStack getCraftingResult(InventoryCrafting crafting) {
    ItemStack target = crafting.getStackInRowAndColumn(0, 0);
    return StorageBallRegistry.getBallFromContainer(target);
  }

  @Override
  public int getRecipeSize() {
    return 9;
  }

  @Override
  public ItemStack getRecipeOutput() {
    return null;
  }
}
