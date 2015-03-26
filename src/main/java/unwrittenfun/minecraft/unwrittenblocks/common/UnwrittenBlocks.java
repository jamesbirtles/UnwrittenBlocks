package unwrittenfun.minecraft.unwrittenblocks.common;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import unwrittenfun.minecraft.unwrittenblocks.client.creativetab.UBCreativeTab;
import unwrittenfun.minecraft.unwrittenblocks.client.resourcepack.ExternalResourcePack;
import unwrittenfun.minecraft.unwrittenblocks.common.blocks.BlockRegister;
import unwrittenfun.minecraft.unwrittenblocks.common.handlers.GuiHandler;
import unwrittenfun.minecraft.unwrittenblocks.common.items.ItemRegister;
import unwrittenfun.minecraft.unwrittenblocks.common.items.StorageBallRegistry;
import unwrittenfun.minecraft.unwrittenblocks.common.network.NetworkRegister;
import unwrittenfun.minecraft.unwrittenblocks.common.worldgen.WorldGenPleather;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;


/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun
 * Created: 25/08/2014.
 */

@Mod(modid = ModInfo.MOD_ID, name = ModInfo.NAME, version = ModInfo.VERSION)
public class UnwrittenBlocks {
  public static Logger logger;

  @SidedProxy(clientSide = "unwrittenfun.minecraft.unwrittenblocks.client.ClientProxy",
      serverSide = "unwrittenfun.minecraft.unwrittenblocks.common.CommonProxy")
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
