package unwrittenfun.minecraft.unwrittenblocks.items

import net.minecraft.item.{ItemStack, Item}
import cpw.mods.fml.common.registry.{GameRegistry, LanguageRegistry}
import net.minecraftforge.common.MinecraftForge
import net.minecraft.block.Block

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
object UBItems {
  var pleatherBulb: Item = null
  var pleatherStrips: Item = null
  var gpsChip: Item = null

  def registerItems() {
    pleatherBulb = new ItemPleatherBulb(PLEATHER_BULB_ID, PLEATHER_BULB_KEY)
    pleatherStrips = new ItemPleatherStrips(PLEATHER_STRIPS_ID, PLEATHER_STRIPS_KEY)
    gpsChip = new ItemGpsChip(GPS_CHIP_ID, GPS_CHIP_KEY)
  }

  def registerNames() {
    LanguageRegistry.addName(new ItemStack(pleatherBulb, 1), PLEATHER_BULB_NAME)
    LanguageRegistry.addName(new ItemStack(pleatherStrips, 1), PLEATHER_STRIPS_NAME)
    LanguageRegistry.addName(new ItemStack(gpsChip, 1), GPS_CHIP_NAME)
    LanguageRegistry.addName(new ItemStack(gpsChip, 1, 1), GPS_CHIP_LINKED_NAME)
  }

  def registerRecipes() {
    GameRegistry.addShapelessRecipe(new ItemStack(Item.leather, 1), pleatherStrips, pleatherStrips, pleatherStrips, pleatherStrips)
    GameRegistry.addRecipe(new ItemStack(gpsChip), "trt", "clc", "iri", 't'.asInstanceOf[Character], Block.torchRedstoneActive, 'l'.asInstanceOf[Character], Block.redstoneLampIdle, 'r'.asInstanceOf[Character], Item.redstone, 'i'.asInstanceOf[Character], Item.ingotIron, 'c'.asInstanceOf[Character], new ItemStack(Item.dyePowder, 1, 2))
  }
}
