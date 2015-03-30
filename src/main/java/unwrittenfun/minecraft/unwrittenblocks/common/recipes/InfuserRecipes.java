package unwrittenfun.minecraft.unwrittenblocks.common.recipes;

import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun
 * Created: 05/11/2014.
 */
public class InfuserRecipes {
  public static InfuserRecipes instance = new InfuserRecipes();

  public HashMap<ItemStack, ItemStack> infusingList = new HashMap<ItemStack, ItemStack>();
  public HashMap<ItemStack, Integer> ticksList = new HashMap<ItemStack, Integer>();

  @SuppressWarnings("UnusedDeclaration")
  public void addRecipe(ItemStack input, ItemStack output, int tickTime) {
    infusingList.put(input, output);
    ticksList.put(input, tickTime);
  }

  public ItemStack getInfuserResult(ItemStack input) {
    for (Map.Entry<ItemStack, ItemStack> entry : infusingList.entrySet()) {
      if (input.isItemEqual(entry.getKey()) && ItemStack.areItemStackTagsEqual(entry.getKey(), input)) return entry.getValue();
    }
    return null;
  }

  public int getInfuserTicks(ItemStack input) {
    for (Map.Entry<ItemStack, Integer> entry : ticksList.entrySet()) {
      if (input.isItemEqual(entry.getKey()) && ItemStack.areItemStackTagsEqual(entry.getKey(), input)) return entry.getValue();
    }
    return 0;
  }
}
