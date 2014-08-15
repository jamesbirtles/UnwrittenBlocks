package unwrittenfun.minecraft.unwrittenblocks.client

import cpw.mods.fml.client.registry.ClientRegistry
import net.minecraftforge.client.MinecraftForgeClient
import unwrittenfun.minecraft.unwrittenblocks.{CommonProxy, blocks}
import unwrittenfun.minecraft.unwrittenblocks.blocks.tileentities.TileEntityDarkInfuser
import unwrittenfun.minecraft.unwrittenblocks.client.models.ModelDarkInfuser
import unwrittenfun.minecraft.unwrittenblocks.client.renderers.{IRDarkInfuser, TESRDarkInfuser}

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun 
 * Created: 09/08/2014.
 */
class ClientProxy extends CommonProxy {
  override def initRenderers() {
    val infuserModel: ModelDarkInfuser = new ModelDarkInfuser
    ClientRegistry.bindTileEntitySpecialRenderer(classOf[TileEntityDarkInfuser], new TESRDarkInfuser(infuserModel))
    MinecraftForgeClient.registerItemRenderer(blocks.DARK_INFUSER_ID, new IRDarkInfuser(infuserModel))
  }
}
