package unwrittenfun.minecraft.unwrittenblocks.recipes

import scala.collection.mutable
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.FurnaceRecipes

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
object InfuserRecipes {
  private val infusingList = new mutable.HashMap[List[Int], ItemStack]()
  private val ticksList = new mutable.HashMap[List[Int], Int]()
  private val ignoreMetaList = new mutable.HashMap[Int, Boolean]()

  // Set meta to -1 to ignore meta
  def addRecipe(id: Int, meta: Int, result: ItemStack, ticks: Int) {
    infusingList.put(List(id, meta), result)
    ticksList.put(List(id, meta), ticks)
    ignoreMetaList.put(id, meta == -1)
  }

  def getTickTime(id: Int, meta: Int): Int = {
    ticksList.get(List(id, getMeta(id, meta))).get
  }

  def getInfusingResult(id: Int, meta: Int): ItemStack = {
    if (infusingList.contains(List(id, getMeta(id, meta)))) {
      infusingList.get(List(id, getMeta(id, meta))).get
    } else {
      null
    }
  }

  // Return meta or -1 if should ignore meta
  private def getMeta(id: Int, meta: Int): Int = {
    if (!ignoreMetaList.contains(id) || ignoreMetaList.get(id).get) -1 else meta
  }
}