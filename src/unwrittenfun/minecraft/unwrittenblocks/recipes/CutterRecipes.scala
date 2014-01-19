package unwrittenfun.minecraft.unwrittenblocks.recipes

import scala.collection.mutable

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
object CutterRecipes {
  final val MODE_STAIRS: Int = 0
  final val MODE_SLABS: Int = 1

  var recipes: mutable.HashMap[Int, mutable.HashMap[(Int, Int), (Int, Int)]] = new mutable.HashMap[Int, mutable.HashMap[(Int, Int), (Int, Int)]]
  recipes.put(MODE_STAIRS, new mutable.HashMap[(Int, Int), (Int, Int)])
  recipes.put(MODE_SLABS, new mutable.HashMap[(Int, Int), (Int, Int)])

  /**
   * @param mode Cutting mode (MODE_STAIRS, MODE_SLABS)
   * @param fromTuple Tuple in form (id, meta) of the block to cut from
   * @param toId Tuple in form (id, meta) of the block to cut into
   */
  def registerRecipe(mode: Int, fromTuple: (Int, Int), toId: (Int, Int)) {
    recipes.get(mode).get.put(fromTuple, toId)
  }

  /**
   * @param mode Cutting mode (MODE_STAIRS, MODE_SLABS)
   * @param fromTuple Tuple in form (id, meta) of the block to cut from
   * @return The block id for the output of the cutting
   */
  def getOutput(mode:Int, fromTuple: (Int, Int)): (Int, Int) = recipes.get(mode).get.get(fromTuple).get

  /**
   *
   * @param mode Cutting mode (MODE_STAIRS, MODE_SLABS)
   * @param fromTuple Tuple in form (id, meta) of the block to cut from
   * @return Whether or not the block has a cut form already registered
   */
  def hasRecipe(mode: Int, fromTuple: (Int, Int)): Boolean = recipes.get(mode).get.contains(fromTuple)
}
