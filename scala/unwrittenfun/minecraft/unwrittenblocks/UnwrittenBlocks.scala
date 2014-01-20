package unwrittenfun.minecraft.unwrittenblocks

import cpw.mods.fml.common.Mod
import unwrittenfun.minecraft.unwrittenblocks.handlers.{GuiHandler, ConfigHandler, PacketHandler}
import cpw.mods.fml.common.network.NetworkMod
import cpw.mods.fml.common.Mod.EventHandler
import unwrittenfun.minecraft.unwrittenblocks.items.UBItems
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPreInitializationEvent}
import unwrittenfun.minecraft.unwrittenblocks.blocks.UBBlocks
import cpw.mods.fml.common.registry.GameRegistry
import unwrittenfun.minecraft.unwrittenblocks.worldgen.GenPleather


/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */

@Mod(modid = ID, name = NAME, version = VERSION, modLanguage = "scala")
@NetworkMod(channels = Array(CHANNEL), clientSideRequired = true, serverSideRequired = false, packetHandler = classOf[PacketHandler])
object UnwrittenBlocks {
  @EventHandler def preInit(event: FMLPreInitializationEvent) {
    ConfigHandler init event.getSuggestedConfigurationFile

    UBItems.registerItems()
    UBBlocks.registerBlocks()
  }

  @EventHandler def load(event: FMLInitializationEvent) {
    UBItems.registerNames()
    UBBlocks.registerNames()

    UBBlocks.registerRecipes()
    UBItems.registerRecipes()

    UBBlocks.registerTileEntities()

    GameRegistry registerWorldGenerator new GenPleather()

    new GuiHandler()
  }
}
