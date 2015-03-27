package unwrittenfun.minecraft.unwrittenblocks.common.items;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Author: James Birtles
 */
public class StorageBallRegistry {
  public static ArrayList<ItemStack> types = new ArrayList<ItemStack>();
  public static HashMap<ItemStack, ItemStack> balls = new HashMap<ItemStack, ItemStack>();
  public static ArrayList<IIcon> icons = new ArrayList<IIcon>();
  public static HashMap<ItemStack, ResourceLocation> resources = new HashMap<ItemStack, ResourceLocation>();

  public static void addStorageBall(ItemStack stack) {
    if (!types.contains(stack)) {
      types.add(stack);
      ItemStack ball = new ItemStack(ItemRegister.storageBall);
      ball.setTagCompound(new NBTTagCompound());
      NBTTagCompound compound = new NBTTagCompound();
      stack.writeToNBT(compound);
      ball.getTagCompound().setTag("StorageBall", compound);
      balls.put(stack, ball);
    }
  }

  public static ItemStack getContainerStackFromBall(ItemStack ball) {
    if (ball.hasTagCompound()) {
      return ItemStack.loadItemStackFromNBT(ball.getTagCompound().getCompoundTag("StorageBall"));
    }
    return null;
  }

  public static ItemStack getBallFromContainer(ItemStack container) {
    for (ItemStack stack : balls.keySet()) {
      if (stack.isItemEqual(container)) {
        return balls.get(stack).copy();
      }
    }
    return null;
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

  public static boolean isBallable(ItemStack stack) {
    for (ItemStack cont : types) {
      if (stack.isItemEqual(cont)) {
        return true;
      }
    }
    return false;
  }
}
