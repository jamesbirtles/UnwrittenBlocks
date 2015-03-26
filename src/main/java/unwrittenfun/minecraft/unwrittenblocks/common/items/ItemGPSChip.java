package unwrittenfun.minecraft.unwrittenblocks.common.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

/**
 * Project: UnwrittenBlocks Author: UnwrittenFun Created: 29/08/2014.
 */
public class ItemGPSChip extends ItemUB {
  public IIcon linkedIcon;

  public ItemGPSChip() {
    super(ItemRegister.GPS_CHIP_KEY);

    setMaxStackSize(1);
  }

  @Override
  public IIcon getIconFromDamage(int meta) {
    return meta == 1 ? linkedIcon : itemIcon;
  }

  @Override
  public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
    if (!world.isRemote) {
      if (stack.getItemDamage() == 0) {
        if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());

        NBTTagCompound locationData = new NBTTagCompound();
        locationData.setString("worldName", world.provider.getDimensionName());
        locationData.setInteger("worldId", world.provider.dimensionId);
        locationData.setFloat("locationX", (float) player.posX);
        locationData.setFloat("locationY", (float) player.posY);
        locationData.setFloat("locationZ", (float) player.posZ);
        locationData.setFloat("rotationYaw", player.rotationYaw);
        stack.getTagCompound().setTag("LocationData", locationData);

        stack.setItemDamage(1);
      } else if (stack.getItemDamage() == 1) {
        stack.getTagCompound().removeTag("LocationData");
        stack.setItemDamage(0);
      }
    }

    return stack;
  }

  @Override
  public String getUnlocalizedName(ItemStack stack) {
    return "item." + ItemRegister.GPS_CHIP_KEY + (stack.getItemDamage() == 1 ? "Linked" : "");
  }

  @SuppressWarnings("unchecked")
  @Override
  public void addInformation(ItemStack stack, EntityPlayer player, List list,
                             boolean bool) {
    super.addInformation(stack, player, list, bool);

    validateStack(stack);
    if (stack.getItemDamage() == 1) {
      NBTTagCompound locationData = stack.getTagCompound().getCompoundTag("LocationData");
      list.add(EnumChatFormatting.DARK_AQUA + "Right click to unlink");
      list.add("World: " + EnumChatFormatting.WHITE + locationData.getString("worldName"));
      list.add("     X: " + EnumChatFormatting.WHITE + (int) locationData.getFloat("locationX"));
      list.add("     Y: " + EnumChatFormatting.WHITE + (int) locationData.getFloat("locationY"));
      list.add("     Z: " + EnumChatFormatting.WHITE + (int) locationData.getFloat("locationZ"));
      list.add("  Yaw: " + EnumChatFormatting.WHITE + (int) locationData.getFloat("rotationYaw"));
    } else {
      list.add(EnumChatFormatting.DARK_AQUA + "Right click to link");
    }
  }

//  @SuppressWarnings("unchecked")
//  @Override
//  public void getSubItems(Item item, CreativeTabs creativeTab, List list) {
//    list.add(0, new ItemStack(this, 1, 0));
//    list.add(1, new ItemStack(this, 1, 1));
//  }

  @Override
  public void registerIcons(IIconRegister iconRegister) {
    super.registerIcons(iconRegister);
    linkedIcon = iconRegister.registerIcon(getIconString() + "Linked");
  }

  private void validateStack(ItemStack stack) {
    if (stack.getItemDamage() == 1) {
      if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());

      if (!stack.getTagCompound().hasKey("LocationData")) {
        NBTTagCompound locationData = new NBTTagCompound();
        locationData.setString("worldName", "Overworld");
        locationData.setInteger("worldId", 0);
        locationData.setFloat("locationX", 0F);
        locationData.setFloat("locationY", 0F);
        locationData.setFloat("locationZ", 0F);
        locationData.setFloat("rotationYaw", 0F);
        stack.getTagCompound().setTag("LocationData", locationData);
      }
    }
  }
}
