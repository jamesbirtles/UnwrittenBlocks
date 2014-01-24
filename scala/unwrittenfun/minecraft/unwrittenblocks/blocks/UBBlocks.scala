package unwrittenfun.minecraft.unwrittenblocks.blocks

import net.minecraft.block.Block
import cpw.mods.fml.common.registry.{GameRegistry, LanguageRegistry}
import net.minecraftforge.common.MinecraftForge
import unwrittenfun.minecraft.unwrittenblocks.blocks.tileentities.{TileEntityDarkInfuser, TileEntityWallTeleporter}
import net.minecraft.item.{Item, ItemStack}

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
object UBBlocks {
  var pleatherPlant: Block = null
  var wallTeleporter: Block = null
  var darkInfuser: Block = null

  def registerBlocks() {
    pleatherPlant = new BlockPleatherPlant(PLEATHER_PLANT_ID, PLEATHER_PLANT_KEY)
    wallTeleporter = new BlockWallTeleporter(WALL_TELEPORTER_ID, WALL_TELEPORTER_KEY)
    darkInfuser = new BlockDarkInfuser(DARK_INFUSER_ID, DARK_INFUSER_KEY)

    GameRegistry.registerBlock(pleatherPlant, PLEATHER_PLANT_KEY)
    GameRegistry.registerBlock(wallTeleporter, WALL_TELEPORTER_KEY)
    GameRegistry.registerBlock(darkInfuser, DARK_INFUSER_KEY)

    MinecraftForge.addGrassPlant(pleatherPlant, 7, 10)
  }

  def registerNames() {
    LanguageRegistry.addName(pleatherPlant, PLEATHER_PLANT_NAME)
    LanguageRegistry.addName(wallTeleporter, WALL_TELEPORTER_NAME)
    LanguageRegistry.addName(darkInfuser, DARK_INFUSER_NAME)
  }

  def registerTileEntities() {
    GameRegistry.registerTileEntity(classOf[TileEntityWallTeleporter], WALL_TELEPORTER_KEY)
    GameRegistry.registerTileEntity(classOf[TileEntityDarkInfuser], DARK_INFUSER_KEY)
  }

  def registerRecipes() {
    GameRegistry.addRecipe(new ItemStack(wallTeleporter, 4), "beb", "ygp", "rer", 'b'.asInstanceOf[Character], Item.blazePowder, 'e'.asInstanceOf[Character], Block.whiteStone, 'y'.asInstanceOf[Character], Item.eyeOfEnder, 'g'.asInstanceOf[Character], Block.blockGold, 'p'.asInstanceOf[Character], Item.enderPearl, 'r'.asInstanceOf[Character], Item.redstone)
  }
}
