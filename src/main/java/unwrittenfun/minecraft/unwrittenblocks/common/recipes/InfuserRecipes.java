package unwrittenfun.minecraft.unwrittenblocks.common.recipes;

import net.minecraft.item.ItemStack;

import java.util.HashMap;

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun
 * Created: 05/11/2014.
 */
public class InfuserRecipes {
  public static InfuserRecipes instance = new InfuserRecipes();

  public HashMap<ItemStack, ItemStack> infusingList = new HashMap<ItemStack, ItemStack>();
  public HashMap<ItemStack, Integer>   ticksList    = new HashMap<ItemStack, Integer>();

  @SuppressWarnings("UnusedDeclaration")
  public void addRecipe(ItemStack input, ItemStack output, int tickTime) {
    infusingList.put(input, output);
    ticksList.put(input, tickTime);
  }

  public ItemStack getInfuserResult(ItemStack input) {
    ItemStack inStack = input.copy();
    inStack.stackSize = 1;
    return infusingList.get(inStack) == null ? null : infusingList.get(inStack).copy();
  }

  public int getInfuserTicks(ItemStack input) {
    ItemStack inStack = input.copy();
    inStack.stackSize = 1;
    return ticksList.get(inStack);
  }
}
