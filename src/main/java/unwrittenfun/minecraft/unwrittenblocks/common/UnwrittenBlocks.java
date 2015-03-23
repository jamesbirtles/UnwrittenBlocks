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

  @Mod.EventHandler
  public void postInit(FMLPostInitializationEvent event) {
    if (event.getSide().isClient()) {

//      for (ItemStack item : StorageBallRegistry.types) {
//        String name = item.getIconIndex().getIconName();
//        if (item.getItem() instanceof ItemBlock) {
//          String[] splt = name.split(":");
//          if (splt.length < 2) {
//            name = "minecraft:textures/blocks/" + splt[0];
//          } else {
//            name = splt[0] + ":textures/blocks/" + splt[1];
//          }
//        } else {
//          String[] splt = name.split(":");
//          if (splt.length < 2) {
//            name = "minecraft:textures/items/" + splt[0];
//          } else {
//            name = splt[0] + ":textures/items/" + splt[1];
//          }
//        }
//        System.out.println(name);
//
//        try {
//          InputStream outlineStream = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("unwrittenblocks:textures/items/storageBall.png")).getInputStream();
//          Image outlineImage = ImageIO.read(outlineStream);
//          InputStream blockStream = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(name + ".png")).getInputStream();
//          Image blockImage = ImageIO.read(blockStream);
//          BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
//          Graphics2D g2 = image.createGraphics();
//          g2.drawImage(outlineImage, 0, 0, 16, 16, null);
//          g2.drawImage(blockImage, 4, 3, 12, 13, 4, 3, 12, 13, null);
//          g2.drawImage(blockImage, 6, 2, 10, 3, 6, 2, 10, 3, null);
//          g2.drawImage(blockImage, 6, 13, 10, 14, 6, 13, 10, 14, null);
//          g2.drawImage(blockImage, 3, 4, 4, 12, 3, 4, 4, 12, null);
//          g2.drawImage(blockImage, 12, 4, 13, 12, 12, 4, 13, 12, null);
//          g2.drawImage(blockImage, 2, 6, 3, 10, 2, 6, 3, 10, null);
//          g2.drawImage(blockImage, 13, 6, 14, 10, 13, 6, 14, 10, null);
//
//          String[] parts = name.split(":");
//          String[] dirs = parts[1].split("/");
//          String fileName = parts[0] + "/" + dirs[1] + "/" + dirs[2];
//
//          System.out.println(fileName);
//
//          File file = new File(new File(Minecraft.getMinecraft().mcDataDir, "unwrittenblocks"), "assets/unwrittenblocks/textures/balls/" + fileName + ".png");
//          file.mkdirs();
//          ImageIO.write(image, "png", file);
//          StorageBallRegistry.resourceLocations.add(new ResourceLocation("unwrittenblocks", "textures/balls/" + fileName + ".png"));
//        } catch (IOException e) {
//          e.printStackTrace();
//        }
//      }
    }
  }
}
