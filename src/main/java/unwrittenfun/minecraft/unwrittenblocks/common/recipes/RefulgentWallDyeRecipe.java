package unwrittenfun.minecraft.unwrittenblocks.common.recipes;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import unwrittenfun.minecraft.unwrittenblocks.common.blocks.BlockRegister;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RefulgentWallDyeRecipe implements IRecipe {
  @Override
  public boolean matches(InventoryCrafting crafting, World world) {
    int dyeId = OreDictionary.getOreID("dye");
    for (int slot = 0; slot < crafting.getSizeInventory(); slot++) {
      ItemStack stack = crafting.getStackInSlot(slot);
      if (slot == 4) {
        int[] oreIds = OreDictionary.getOreIDs(stack);
        boolean matchedId = false;
        for (int i = 0; i < oreIds.length; i++) {
          if (oreIds[i] == dyeId) {
            matchedId = true;
          }
        }
        if (!matchedId) {
          return false;
        }
      } else {
        if (stack == null || stack.getItem() != (new ItemStack(BlockRegister.refulgentWall)).getItem()) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public ItemStack getCraftingResult(InventoryCrafting crafting) {
    List<String> dyeStrings = Arrays.asList(BlockRegister.DYES_ORE_DICT);
    ItemStack dyeStack = crafting.getStackInSlot(4);
    int[] dyeOreIds = OreDictionary.getOreIDs(dyeStack);
    for (int i = 0; i < dyeOreIds.length; i++) {
      String dyeOreName = OreDictionary.getOreName(dyeOreIds[i]);
      if(dyeStrings.contains(dyeOreName)) {
        return new ItemStack(BlockRegister.refulgentWall, 8, dyeStrings.indexOf(dyeOreName));
      }
    }
    return null;
  }

  @Override
  public int getRecipeSize() {
    return 3 * 3;
  }

  @Override
  public ItemStack getRecipeOutput() {
    return new ItemStack(BlockRegister.refulgentWall);
  }
}
