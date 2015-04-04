package unwrittenfun.minecraft.unwrittenblocks.common;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import org.apache.logging.log4j.Logger;
import unwrittenfun.minecraft.unwrittenblocks.client.creativetab.UBCreativeTab;
import unwrittenfun.minecraft.unwrittenblocks.common.blocks.BlockRegister;
import unwrittenfun.minecraft.unwrittenblocks.common.handlers.GuiHandler;
import unwrittenfun.minecraft.unwrittenblocks.common.items.ItemRegister;
import unwrittenfun.minecraft.unwrittenblocks.common.network.NetworkRegister;
import unwrittenfun.minecraft.unwrittenblocks.common.worldgen.WorldGenPleather;


/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun
 * Created: 25/08/2014.
 */

@Mod(modid = ModInfo.MOD_ID, name = ModInfo.NAME, version = ModInfo.VERSION)
public class UnwrittenBlocks {
  public static Logger logger;

  @SidedProxy(clientSide = "unwrittenfun.minecraft.unwrittenblocks.client.ClientProxy", serverSide = "unwrittenfun.minecraft.unwrittenblocks.common.CommonProxy")
  public static CommonProxy proxy;

  @Mod.Instance
  public static UnwrittenBlocks instance;

  public static CreativeTabs creativeTabUB;

  @Mod.EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    logger = event.getModLog();

    creativeTabUB = new UBCreativeTab();

    BlockRegister.registerBlocks();
    ItemRegister.registerItems();

    BlockRegister.registerRecipes();
    ItemRegister.registerRecipes();

    GameRegistry.registerWorldGenerator(new WorldGenPleather(), 10);

    proxy.registerRenderers();

    NetworkRegister.initMessages();

    NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
  }
}
