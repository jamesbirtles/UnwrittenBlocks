package unwrittenfun.minecraft.unwrittenblocks.items

import net.minecraft.item.{ItemStack, Item}
import cpw.mods.fml.common.registry.{GameRegistry, LanguageRegistry}
import net.minecraftforge.common.MinecraftForge
import net.minecraft.block.Block
import unwrittenfun.minecraft.unwrittenblocks.recipes.InfuserRecipes

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
object UBItems {
  var pleatherBulb: Item = null
  var pleatherStrips: Item = null
  var gpsChip: Item = null
  var cobbleBall: Item = null

  def registerItems() {
    pleatherBulb = new ItemPleatherBulb(PLEATHER_BULB_ID, PLEATHER_BULB_KEY)
    pleatherStrips = new ItemPleatherStrips(PLEATHER_STRIPS_ID, PLEATHER_STRIPS_KEY)
    gpsChip = new ItemGpsChip(GPS_CHIP_ID, GPS_CHIP_KEY)
    cobbleBall = new ItemCobbleBall(COBBLE_BALL_ID, COBBLE_BALL_KEY)
  }

  def registerNames() {
    LanguageRegistry.addName(new ItemStack(pleatherBulb, 1), PLEATHER_BULB_NAME)
    LanguageRegistry.addName(new ItemStack(pleatherStrips, 1), PLEATHER_STRIPS_NAME)
    LanguageRegistry.addName(new ItemStack(gpsChip, 1), GPS_CHIP_NAME)
    LanguageRegistry.addName(new ItemStack(gpsChip, 1, 1), GPS_CHIP_LINKED_NAME)
    LanguageRegistry.addName(new ItemStack(cobbleBall, 1, 0), COBBLE_BALL_NAMES(0))
    LanguageRegistry.addName(new ItemStack(cobbleBall, 1, 1), COBBLE_BALL_NAMES(1))
  }

  def registerRecipes() {
    GameRegistry.addShapelessRecipe(new ItemStack(Item.leather, 1), pleatherStrips, pleatherStrips, pleatherStrips, pleatherStrips)
    GameRegistry.addShapelessRecipe(new ItemStack(Block.cobblestoneMossy, 5), new ItemStack(cobbleBall, 1, 0))
    GameRegistry.addShapelessRecipe(new ItemStack(Block.cobblestone, 5), new ItemStack(cobbleBall, 1, 1))

    GameRegistry.addRecipe(new ItemStack(gpsChip), "trt", "clc", "iri", 't'.asInstanceOf[Character], Block.torchRedstoneActive, 'l'.asInstanceOf[Character], Block.redstoneLampIdle, 'r'.asInstanceOf[Character], Item.redstone, 'i'.asInstanceOf[Character], Item.ingotIron, 'c'.asInstanceOf[Character], new ItemStack(Item.dyePowder, 1, 2))
    GameRegistry.addRecipe(new ItemStack(cobbleBall, 1, 0), " c ", "ccc", " c ", 'c'.asInstanceOf[Character], Block.cobblestoneMossy)
    GameRegistry.addRecipe(new ItemStack(cobbleBall, 1, 1), " c ", "ccc", " c ", 'c'.asInstanceOf[Character], Block.cobblestone)
    GameRegistry.addRecipe(new ItemStack(Item.blazeRod), "bb", "bb", "bb", 'b'.asInstanceOf[Character], Item.blazePowder)


    InfuserRecipes.addRecipe(cobbleBall.itemID, 0, new ItemStack(Item.enderPearl), 2000)
    InfuserRecipes.addRecipe(cobbleBall.itemID, 1, new ItemStack(Item.slimeBall), 1500)
  }
}
