package unwrittenfun.minecraft.unwrittenblocks.common.nei;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import unwrittenfun.minecraft.unwrittenblocks.client.gui.GuiDarkInfuser;
import unwrittenfun.minecraft.unwrittenblocks.common.recipes.InfuserRecipes;

import java.util.Map;

import static codechicken.lib.gui.GuiDraw.*;

/**
 * Author: James Birtles
 */
public class InfuserRecipeHandler extends TemplateRecipeHandler {
  @Override
  public String getGuiTexture() {
    return GuiDarkInfuser.texture.toString();
  }

  @Override
  public String getRecipeName() {
    return "Dark Infuser";
  }

  @Override
  public void drawBackground(int recipe) {
    GL11.glColor4f(1, 1, 1, 1);
    changeTexture(getGuiTexture());
    drawTexturedModalRect(38, 16, 43, 32, 93, 24);
  }

  @Override
  public void drawExtras(int recipe) {
    drawProgressBar(57, 16, 177, 0, 50, 24, 25, 0);
    int ticks = InfuserRecipes.instance.getInfuserTicks(arecipes.get(recipe).getIngredient().item);
    String time = ticks / 20 + " seconds";
    fontRenderer.drawString(time, 84 - (fontRenderer.getStringWidth(time) / 2), 46, 0x6a6a6a);
  }

  @Override
  public void loadUsageRecipes(ItemStack ingredient) {
    for (Map.Entry<ItemStack, ItemStack> recipe : InfuserRecipes.instance.infusingList.entrySet()) {
      if (NEIServerUtils.areStacksSameTypeCrafting(recipe.getKey(), ingredient)) {
        arecipes.add(new InfuserCachedRecipe(recipe.getKey(), recipe.getValue()));
      }
    }
  }

  @Override
  public void loadCraftingRecipes(ItemStack result) {
    for (Map.Entry<ItemStack, ItemStack> recipe : InfuserRecipes.instance.infusingList.entrySet()) {
      if (NEIServerUtils.areStacksSameTypeCrafting(recipe.getValue(), result)) {
        arecipes.add(new InfuserCachedRecipe(recipe.getKey(), recipe.getValue()));
      }
    }
  }

  @Override
  public Class<? extends GuiContainer> getGuiClass() {
    return GuiDarkInfuser.class;
  }

  public class InfuserCachedRecipe extends CachedRecipe {
    public ItemStack input, output;

    public InfuserCachedRecipe(ItemStack input, ItemStack output) {
      this.input = input;
      this.output = output;
    }

    @Override
    public PositionedStack getResult() {
      return new PositionedStack(output, 111, 20, false);
    }

    @Override
    public PositionedStack getIngredient() {
      return new PositionedStack(input, 39, 20, false);
    }
  }

}