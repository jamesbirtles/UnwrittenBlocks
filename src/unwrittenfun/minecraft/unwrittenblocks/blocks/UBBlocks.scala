package unwrittenfun.minecraft.unwrittenblocks.blocks

import net.minecraft.block.Block
import cpw.mods.fml.common.registry.{GameRegistry, LanguageRegistry}
import net.minecraftforge.common.MinecraftForge
import unwrittenfun.minecraft.unwrittenblocks.blocks.tileentities.{TileEntityBlockCutter, TileEntityWallTeleporter}
import net.minecraft.item.{Item, ItemStack}
import unwrittenfun.minecraft.unwrittenblocks.recipes.CutterRecipes

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
object UBBlocks {
  var pleatherPlant: Block = null
  var wallTeleporter: Block = null
  var blockCutter: Block = null

  def registerBlocks() {
    pleatherPlant = new BlockPleatherPlant(PLEATHER_PLANT_ID, PLEATHER_PLANT_KEY)
    wallTeleporter = new BlockWallTeleporter(WALL_TELEPORTER_ID, WALL_TELEPORTER_KEY)
    blockCutter = new BlockCutter(CUTTER_ID, CUTTER_KEY)

    GameRegistry.registerBlock(pleatherPlant, PLEATHER_PLANT_KEY)
    GameRegistry.registerBlock(wallTeleporter, WALL_TELEPORTER_KEY)
    GameRegistry.registerBlock(blockCutter, CUTTER_KEY)

    MinecraftForge.addGrassPlant(pleatherPlant, 7, 10)
  }

  def registerNames() {
    LanguageRegistry.addName(pleatherPlant, PLEATHER_PLANT_NAME)
    LanguageRegistry.addName(wallTeleporter, WALL_TELEPORTER_NAME)
    LanguageRegistry.addName(blockCutter, CUTTER_NAME)
  }

  def registerTileEntities() {
    GameRegistry.registerTileEntity(classOf[TileEntityWallTeleporter], WALL_TELEPORTER_KEY)
    GameRegistry.registerTileEntity(classOf[TileEntityBlockCutter], CUTTER_KEY)
  }

  def registerRecipes() {
    //GameRegistry.addRecipe(new ItemStack(wallTeleporter, 4), "beb", "ygp", "rer", "b", Item.blazePowder, "e", Block.whiteStone, "y", Item.eyeOfEnder, "g", Block.blockGold, "p", Item.enderPearl, "r", Item.redstone)

    // Register Vanilla Cutter Recipes

    // Stairs
    CutterRecipes.registerRecipe(CutterRecipes.MODE_STAIRS, (Block.planks.blockID, 0), (Block.stairsWoodOak.blockID, 0))
    CutterRecipes.registerRecipe(CutterRecipes.MODE_STAIRS, (Block.planks.blockID, 1), (Block.stairsWoodSpruce.blockID, 0))
    CutterRecipes.registerRecipe(CutterRecipes.MODE_STAIRS, (Block.planks.blockID, 2), (Block.stairsWoodBirch.blockID, 0))
    CutterRecipes.registerRecipe(CutterRecipes.MODE_STAIRS, (Block.planks.blockID, 3), (Block.stairsWoodJungle.blockID, 0))
    CutterRecipes.registerRecipe(CutterRecipes.MODE_STAIRS, (Block.cobblestone.blockID, 0), (Block.stairsCobblestone.blockID, 0))
    CutterRecipes.registerRecipe(CutterRecipes.MODE_STAIRS, (Block.brick.blockID, 0), (Block.stairsBrick.blockID, 0))
    CutterRecipes.registerRecipe(CutterRecipes.MODE_STAIRS, (Block.stoneBrick.blockID, 0), (Block.stairsStoneBrick.blockID, 0))
    CutterRecipes.registerRecipe(CutterRecipes.MODE_STAIRS, (Block.netherBrick.blockID, 0), (Block.stairsNetherBrick.blockID, 0))
    CutterRecipes.registerRecipe(CutterRecipes.MODE_STAIRS, (Block.sandStone.blockID, 0), (Block.stairsSandStone.blockID, 0))
    CutterRecipes.registerRecipe(CutterRecipes.MODE_STAIRS, (Block.sandStone.blockID, 1), (Block.stairsSandStone.blockID, 0))
    CutterRecipes.registerRecipe(CutterRecipes.MODE_STAIRS, (Block.sandStone.blockID, 2), (Block.stairsSandStone.blockID, 0))
    CutterRecipes.registerRecipe(CutterRecipes.MODE_STAIRS, (Block.blockNetherQuartz.blockID, 0), (Block.stairsNetherQuartz.blockID, 0))

    // Slabs TODO
  }
}
