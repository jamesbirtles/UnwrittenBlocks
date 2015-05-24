package unwrittenfun.minecraft.unwrittenblocks.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;
import unwrittenfun.minecraft.unwrittenblocks.client.models.ModelDarkInfuser;
import unwrittenfun.minecraft.unwrittenblocks.client.renderers.blocks.BlockRendererRefulgent;
import unwrittenfun.minecraft.unwrittenblocks.client.renderers.blocks.TESRDarkInfuser;
import unwrittenfun.minecraft.unwrittenblocks.client.renderers.items.IRDarkInfuser;
import unwrittenfun.minecraft.unwrittenblocks.client.resourcepack.ExternalResourcePack;
import unwrittenfun.minecraft.unwrittenblocks.common.CommonProxy;
import unwrittenfun.minecraft.unwrittenblocks.common.UnwrittenBlocks;
import unwrittenfun.minecraft.unwrittenblocks.common.blocks.BlockRegister;
import unwrittenfun.minecraft.unwrittenblocks.common.items.StorageBallRegistry;
import unwrittenfun.minecraft.unwrittenblocks.common.tileEntities.TEDarkInfuser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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

    refulgentRenderID = RenderingRegistry.getNextAvailableRenderId();
    refulgentRenderer = new BlockRendererRefulgent(refulgentRenderID);
    RenderingRegistry.registerBlockHandler(refulgentRenderer);

    storageBallResourcePackLocation = new File(Minecraft.getMinecraft().mcDataDir, "unwrittenblocks");
    storageBallResourcePack = new ExternalResourcePack("unwrittenblocks", storageBallResourcePackLocation);
  }

  @Override
  public void generateStorageBallIcons() {
    for (ItemStack item : StorageBallRegistry.types) {
      String[] splitName = item.getIconIndex().getIconName().split(":");
      String name = (splitName.length < 2 ? "minecraft" : splitName[0]) + ":textures/";
      name += (item.getItem() instanceof ItemBlock ? "blocks" : "items") + "/";
      name += (splitName.length < 2 ? splitName[0] : splitName[1]);

      String[] parts = name.split(":");
      String[] dirs = parts[1].split("/");
      String fileName = "storageBall_" + parts[0] + "_" + dirs[1] + "_" + dirs[2];

      File texturePath = new File(storageBallResourcePackLocation, "assets/unwrittenblocks/textures/items/" + fileName + ".png");
      if (!texturePath.exists()) {
        UnwrittenBlocks.logger.info("Generating icon for storage ball at " + texturePath.getAbsolutePath());
        try {
          InputStream outlineStream = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("unwrittenblocks:textures/items/storageBallContainer.png")).getInputStream();
          Image outlineImage = ImageIO.read(outlineStream);
          InputStream blockStream = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(name + ".png")).getInputStream();
          Image blockImage = ImageIO.read(blockStream);
          BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
          Graphics2D g2 = image.createGraphics();
          g2.drawImage(outlineImage, 0, 0, 16, 16, null);
          g2.drawImage(blockImage, 4, 3, 12, 13, 4, 3, 12, 13, null);
          g2.drawImage(blockImage, 6, 2, 10, 3, 6, 2, 10, 3, null);
          g2.drawImage(blockImage, 6, 13, 10, 14, 6, 13, 10, 14, null);
          g2.drawImage(blockImage, 3, 4, 4, 12, 3, 4, 4, 12, null);
          g2.drawImage(blockImage, 12, 4, 13, 12, 12, 4, 13, 12, null);
          g2.drawImage(blockImage, 2, 6, 3, 10, 2, 6, 3, 10, null);
          g2.drawImage(blockImage, 13, 6, 14, 10, 13, 6, 14, 10, null);

          texturePath.mkdirs();
          ImageIO.write(image, "png", texturePath);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    loadResourcePack();
  }

  private void loadResourcePack() {
    ((SimpleReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).reloadResourcePack(storageBallResourcePack);
  }
}
