package unwrittenfun.minecraft.unwrittenblocks.common.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import unwrittenfun.minecraft.unwrittenblocks.common.UnwrittenBlocks;

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
    UnwrittenBlocks.proxy.generateStorageBallIcons();

    for (ItemStack item : StorageBallRegistry.types) {
      StorageBallRegistry.icons.put(item, iconRegister.registerIcon("unwrittenblocks:" + getTextureNameForStack(item)));
    }
  }

  @SideOnly(Side.CLIENT)
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
