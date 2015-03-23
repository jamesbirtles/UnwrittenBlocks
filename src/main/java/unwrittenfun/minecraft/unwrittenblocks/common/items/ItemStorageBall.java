package unwrittenfun.minecraft.unwrittenblocks.common.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import unwrittenfun.minecraft.unwrittenblocks.client.resourcepack.ExternalResourcePack;

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
  public ItemStorageBall() {
    super(ItemRegister.STORAGE_BALL_KEY);

    setHasSubtypes(true);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void addInformation(ItemStack stack, EntityPlayer player, List lines,
                             boolean bool) {
    super.addInformation(stack, player, lines, bool);

    lines.add(StorageBallRegistry.getContainerStackFromBall(stack).getDisplayName());
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
    for (ItemStack container : StorageBallRegistry.types) {
      list.add(StorageBallRegistry.getBallFromContainer(container));
    }
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void registerIcons(IIconRegister iconRegister) {
    for (ItemStack item : StorageBallRegistry.types) {
      String name = item.getIconIndex().getIconName();
      if (item.getItem() instanceof ItemBlock) {
        String[] splt = name.split(":");
        if (splt.length < 2) {
          name = "minecraft:textures/blocks/" + splt[0];
        } else {
          name = splt[0] + ":textures/blocks/" + splt[1];
        }
      } else {
        String[] splt = name.split(":");
        if (splt.length < 2) {
          name = "minecraft:textures/items/" + splt[0];
        } else {
          name = splt[0] + ":textures/items/" + splt[1];
        }
      }
      System.out.println(name);

      try {
        InputStream outlineStream = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("unwrittenblocks:textures/items/storageBall.png")).getInputStream();
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

        String[] parts = name.split(":");
        String[] dirs = parts[1].split("/");
        String fileName = "storageBall_" + parts[0] + "_" + dirs[1] + "_" + dirs[2];

        System.out.println(fileName);

        File file = new File(new File(Minecraft.getMinecraft().mcDataDir, "unwrittenblocks"), "assets/unwrittenblocks/textures/items/" + fileName + ".png");
        file.mkdirs();
        ImageIO.write(image, "png", file);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    ((SimpleReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).reloadResourcePack(new ExternalResourcePack(new File(Minecraft.getMinecraft().mcDataDir, "unwrittenblocks")));

    for (ItemStack item : StorageBallRegistry.types) {
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

      StorageBallRegistry.icons.add(iconRegister.registerIcon("unwrittenblocks:" + name));
    }
  }
}
