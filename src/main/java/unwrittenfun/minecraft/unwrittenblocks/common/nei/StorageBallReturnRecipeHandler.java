package unwrittenfun.minecraft.unwrittenblocks.common.nei;

import codechicken.nei.recipe.ShapelessRecipeHandler;
import net.minecraft.item.ItemStack;
import unwrittenfun.minecraft.unwrittenblocks.common.items.StorageBallRegistry;

/**
 * Author: James Birtles
 */
public class StorageBallReturnRecipeHandler extends ShapelessRecipeHandler {
  public void loadCraftingRecipes(String outputId, Object... results) {
    if (outputId.equals("item"))
      loadCraftingRecipes((ItemStack) results[0]);
  }

  @Override
  public void loadCraftingRecipes(ItemStack result) {
    for (ItemStack type : StorageBallRegistry.types) {
      if (type.isItemEqual(result)) {
        type = type.copy();
        type.stackSize = 8;
        ItemStack ball = StorageBallRegistry.getBallFromContainer(type);
        arecipes.add(new CachedShapelessRecipe(new Object[]{ball}, type));
      }
    }
  }

  @Override
  public void loadUsageRecipes(ItemStack ingredient) {
    for (ItemStack ball : StorageBallRegistry.balls.values()) {
      if (ingredient.isItemEqual(ball)) {
        ItemStack contained = StorageBallRegistry.getContainerStackFromBall(ball);
        ItemStack ingredContained = StorageBallRegistry.getContainerStackFromBall(ingredient);
        if (ingredContained != null && contained != null && ingredContained.isItemEqual(contained)) {
          contained = contained.copy();
          contained.stackSize = 8;
          arecipes.add(new CachedShapelessRecipe(new Object[]{ball}, contained));
        }
      }
    }
  }

  @Override
  public String getRecipeName() {
    return "Storage Balls";
  }
}
