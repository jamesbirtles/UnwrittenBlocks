package unwrittenfun.minecraft.unwrittenblocks.blocks

import net.minecraft.block.Block
import cpw.mods.fml.common.registry.{GameRegistry, LanguageRegistry}
import net.minecraftforge.common.MinecraftForge

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
object UBBlocks {
  var pleatherPlant: Block = null

  def registerBlocks() {
    pleatherPlant = new BlockPleatherPlant(PLEATHER_PLANT_ID, PLEATHER_PLANT_KEY)

    GameRegistry.registerBlock(pleatherPlant, PLEATHER_PLANT_KEY)

    MinecraftForge.addGrassPlant(pleatherPlant, 7, 10)
  }

  def registerNames() {
    LanguageRegistry.addName(pleatherPlant, PLEATHER_PLANT_NAME)
  }
}
