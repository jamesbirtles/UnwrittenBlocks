package unwrittenfun.minecraft.unwrittenblocks

import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPreInitializationEvent}
import cpw.mods.fml.common.network.NetworkMod
import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.common.{Mod, SidedProxy}
import unwrittenfun.minecraft.unwrittenblocks.blocks.UBBlocks
import unwrittenfun.minecraft.unwrittenblocks.handlers.{ConfigHandler, GuiHandler, PacketHandler}
import unwrittenfun.minecraft.unwrittenblocks.items.UBItems
import unwrittenfun.minecraft.unwrittenblocks.worldgen.GenPleather


/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */

@Mod(modid = ID, name = NAME, version = VERSION, modLanguage = "scala")
@NetworkMod(channels = Array(CHANNEL), clientSideRequired = true, serverSideRequired = false, packetHandler = classOf[PacketHandler])
object UnwrittenBlocks {
  @SidedProxy(clientSide = "unwrittenfun.minecraft.unwrittenblocks.client.ClientProxy", serverSide = "unwrittenfun.minecraft.unwrittenblocks.CommonProxy")
  var proxy: CommonProxy = null

  @EventHandler def preInit(event: FMLPreInitializationEvent) {
    ConfigHandler init event.getSuggestedConfigurationFile
  }

  @EventHandler def load(event: FMLInitializationEvent) {
    UBItems.registerItems()
    UBBlocks.registerBlocks()

    proxy.initRenderers()

    UBItems.registerNames()
    UBBlocks.registerNames()

    UBBlocks.registerRecipes()
    UBItems.registerRecipes()

    UBBlocks.registerTileEntities()

    GameRegistry registerWorldGenerator new GenPleather()

    new GuiHandler()
  }
}
