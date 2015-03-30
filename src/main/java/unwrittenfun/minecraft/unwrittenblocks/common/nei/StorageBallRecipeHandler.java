package unwrittenfun.minecraft.unwrittenblocks.common.nei;

import codechicken.nei.recipe.ShapedRecipeHandler;
import net.minecraft.item.ItemStack;
import unwrittenfun.minecraft.unwrittenblocks.common.items.ItemRegister;
import unwrittenfun.minecraft.unwrittenblocks.common.items.StorageBallRegistry;
import unwrittenfun.minecraft.unwrittenblocks.common.recipes.StorageBallRecipe;

/**
 * Author: James Birtles
 */
public class StorageBallRecipeHandler extends ShapedRecipeHandler {
  @Override
  public void loadCraftingRecipes(String outputId, Object... results) {
    if (outputId.equals("item"))
      loadCraftingRecipes((ItemStack) results[0]);
  }

  @Override
  public void loadCraftingRecipes(ItemStack result) {
    if (result.isItemEqual(ItemRegister.stackStorageBall)) {
      ItemStack contained = StorageBallRegistry.getContainerStackFromBall(result);
      arecipes.add(new CachedShapedRecipe(3, 3, new Object[] {
          contained, contained,                               contained,
          contained, ItemRegister.stackStorageBallContainer,  contained,
          contained, contained,                               contained
      }, result));
    }
  }

  @Override
  public void loadUsageRecipes(ItemStack ingredient) {
    if (StorageBallRegistry.isBallable(ingredient)) {
      ItemStack result = StorageBallRegistry.getBallFromContainer(ingredient);
      arecipes.add(new CachedShapedRecipe(3, 3, new Object[] {
          ingredient, ingredient,                             ingredient,
          ingredient, ItemRegister.stackStorageBallContainer, ingredient,
          ingredient, ingredient,                             ingredient
      }, result));
    } else if (ingredient.isItemEqual(ItemRegister.stackStorageBallContainer)) {
      for (ItemStack type : StorageBallRegistry.types) {
        ItemStack result = StorageBallRegistry.getBallFromContainer(type);
        arecipes.add(new CachedShapedRecipe(3, 3, new Object[] {
            type, type,       type,
            type, ingredient, type,
            type, type,       type
        }, result));
      }
    }
  }

  @Override
  public String getRecipeName() {
    return "Storage Balls";
  }
}
