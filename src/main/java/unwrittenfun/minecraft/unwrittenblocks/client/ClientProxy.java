package unwrittenfun.minecraft.unwrittenblocks.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import unwrittenfun.minecraft.unwrittenblocks.client.models.ModelDarkInfuser;
import unwrittenfun.minecraft.unwrittenblocks.client.renderers.blocks.TESRDarkInfuser;
import unwrittenfun.minecraft.unwrittenblocks.client.renderers.items.IRDarkInfuser;
import unwrittenfun.minecraft.unwrittenblocks.common.CommonProxy;
import unwrittenfun.minecraft.unwrittenblocks.common.blocks.BlockRegister;
import unwrittenfun.minecraft.unwrittenblocks.common.tileEntities.TEDarkInfuser;

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun
 * Created: 29/08/2014.
 */
@SuppressWarnings("UnusedDeclaration")
public class ClientProxy extends CommonProxy {
  @Override
  public void registerRenderers() {
    ModelDarkInfuser modelDarkInfuser = new ModelDarkInfuser();
    ClientRegistry.bindTileEntitySpecialRenderer(TEDarkInfuser.class, new TESRDarkInfuser(modelDarkInfuser));
    MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockRegister.darkInfuser), new IRDarkInfuser(modelDarkInfuser));
  }
}
