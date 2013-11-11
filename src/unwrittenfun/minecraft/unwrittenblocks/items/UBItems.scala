package unwrittenfun.minecraft.unwrittenblocks.items

import net.minecraft.item.{ItemStack, Item}
import cpw.mods.fml.common.registry.{GameRegistry, LanguageRegistry}
import net.minecraftforge.common.MinecraftForge

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
object UBItems {
  var pleatherBulb: Item = null
  var pleatherStrips: Item = null

  def registerItems() {
    pleatherBulb = new ItemPleatherBulb(PLEATHER_BULB_ID, PLEATHER_BULB_KEY)
    pleatherStrips = new ItemPleatherStrips(PLEATHER_STRIPS_ID, PLEATHER_STRIPS_KEY)

    MinecraftForge.addGrassSeed(new ItemStack(pleatherBulb), 5)
  }

  def registerNames() {
    LanguageRegistry.addName(new ItemStack(pleatherBulb, 1), PLEATHER_BULB_NAME)
    LanguageRegistry.addName(new ItemStack(pleatherStrips, 1), PLEATHER_STRIPS_NAME)
  }

  def registerRecipes() {
    GameRegistry.addShapelessRecipe(new ItemStack(Item.leather, 1), pleatherStrips, pleatherStrips, pleatherStrips, pleatherStrips)
  }
}
