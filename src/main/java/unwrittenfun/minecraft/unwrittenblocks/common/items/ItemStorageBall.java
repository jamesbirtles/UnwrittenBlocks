package unwrittenfun.minecraft.unwrittenblocks.common.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import unwrittenfun.minecraft.unwrittenblocks.client.resourcepack.ExternalResourcePack;
import unwrittenfun.minecraft.unwrittenblocks.common.UnwrittenBlocks;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun
 * Created: 29/08/2014.
 */
public class ItemStorageBall extends ItemUB {
  private static File storageBallResourcePackLocation = new File(Minecraft.getMinecraft().mcDataDir, "unwrittenblocks");
  public static IResourcePack storageBallResourcePack = new ExternalResourcePack("unwrittenblocks", storageBallResourcePackLocation);

  public ItemStorageBall() {
    super(ItemRegister.STORAGE_BALL_KEY);

    setHasSubtypes(true);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void addInformation(ItemStack stack, EntityPlayer player, List lines,
                             boolean bool) {
    super.addInformation(stack, player, lines, bool);

    ItemStack container = StorageBallRegistry.getContainerStackFromBall(stack);
    if (container != null) {
      lines.add(container.getDisplayName());
    }
  }

  @Override
  public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
    return getIconIndex(stack);
  }

  @Override
  public IIcon getIconIndex(ItemStack stack) {
    if (stack.hasTagCompound()) {
      return StorageBallRegistry.getIconForBall(stack);
    }
    return null;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void getSubItems(Item item, CreativeTabs tab, List list) {
    for (ItemStack ball : StorageBallRegistry.balls.values()) {
      list.add(ball);
    }
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void registerIcons(IIconRegister iconRegister) {
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

    for (ItemStack item : StorageBallRegistry.types) {
      StorageBallRegistry.icons.put(item, iconRegister.registerIcon("unwrittenblocks:" + getTextureNameForStack(item)));
    }
  }

  private void loadResourcePack() {
    ((SimpleReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).reloadResourcePack(storageBallResourcePack);
  }

  private String getTextureNameForStack(ItemStack item) {
    String name = "storageBall_";
    String[] nameParts = item.getIconIndex().getIconName().split(":");
    if (nameParts.length > 1) {
      name += nameParts[0] + "_";
    } else {
      name += "minecraft_";
    }
    if (item.getItem() instanceof ItemBlock) {
      name += "blocks_";
    } else {
      name += "items_";
    }
    if (nameParts.length > 1) {
      name += nameParts[1];
    } else {
      name += nameParts[0];
    }
    return name;
  }
}
