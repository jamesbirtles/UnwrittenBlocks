package unwrittenfun.minecraft.unwrittenblocks.common.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import java.beans.Introspector;
import java.util.List;

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun
 * Created: 29/08/2014.
 */
public class ItemStorageBall extends ItemUB {
  public static final String[] BALL_TYPES = new String[] {
      "Cobblestone", "MossyCobblestone", "Netherrack", "OakWood"
  };
  private IIcon[] icons;

  public ItemStorageBall() {
    super(ItemRegister.STORAGE_BALL_KEY);

    setHasSubtypes(true);
  }

  @Override
  public void registerIcons(IIconRegister iconRegister) {
    icons = new IIcon[BALL_TYPES.length];
    for (int i = 0; i < BALL_TYPES.length; i++) {
      icons[i] = iconRegister.registerIcon(getIconString() + BALL_TYPES[i]);
    }
  }

  @Override
  public IIcon getIconFromDamage(int meta) {
    return meta < icons.length ? icons[meta] : icons[0];
  }

  @SuppressWarnings("unchecked")
  @Override
  public void getSubItems(Item item, CreativeTabs stack, List list) {
    for (int i = 0; i < BALL_TYPES.length; i++) {
      list.add(new ItemStack(this, 1, i));
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public void addInformation(ItemStack stack, EntityPlayer player, List lines, boolean bool) {
    super.addInformation(stack, player, lines, bool);

    lines.add(StatCollector.translateToLocal("item." + ItemRegister.STORAGE_BALL_KEY + "." +
            Introspector.decapitalize(BALL_TYPES[stack.getItemDamage()]) + ".name"));
  }
}
