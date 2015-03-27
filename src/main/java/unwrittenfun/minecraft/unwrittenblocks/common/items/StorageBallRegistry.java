package unwrittenfun.minecraft.unwrittenblocks.common.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import unwrittenfun.minecraft.unwrittenblocks.client.resourcepack.ExternalResourcePack;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Author: James Birtles
 */
public class StorageBallRegistry {
  public static ArrayList<ItemStack> types = new ArrayList<ItemStack>();
  public static ArrayList<IIcon> icons = new ArrayList<IIcon>();
  public static HashMap<ItemStack, ResourceLocation> resources = new HashMap<ItemStack, ResourceLocation>();

  public static void addStorageBall(ItemStack stack) {
    if (!types.contains(stack)) {
      types.add(stack);
    }
  }

  public static ItemStack getContainerStackFromBall(ItemStack ball) {
    if (ball.hasTagCompound()) {
      return ItemStack.loadItemStackFromNBT(ball.getTagCompound().getCompoundTag("StorageBall"));
    }
    return null;
  }

  public static ItemStack getBallFromContainer(ItemStack container) {
    ItemStack stack = new ItemStack(ItemRegister.storageBall);
    stack.setTagCompound(new NBTTagCompound());
    NBTTagCompound compound = new NBTTagCompound();
    container.writeToNBT(compound);
    stack.getTagCompound().setTag("StorageBall", compound);
    return stack;
  }

  public static IIcon getIconForBall(ItemStack ball) {
    for (int index = 0; index < types.size(); index++) {
      ItemStack stack = types.get(index);
      ItemStack container = getContainerStackFromBall(ball);
      if (container != null && container.isItemEqual(stack)) {
        return icons.get(index + 2);
      }
    }
    return null;
  }
}
