package unwrittenfun.minecraft.unwrittenblocks.common.items;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;

import java.util.ArrayList;

/**
 * Author: James Birtles
 */
public class StorageBallRegistry {
  public static ArrayList<ItemStack> types = new ArrayList<ItemStack>();
  public static ArrayList<IIcon> icons = new ArrayList<IIcon>();

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
      if (getContainerStackFromBall(ball).isItemEqual(stack)) {
        return icons.get(index);
      }
    }
    return null;
  }
}
