package unwrittenfun.minecraft.unwrittenblocks.common.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import unwrittenfun.minecraft.unwrittenblocks.common.ModInfo;

import java.util.Collections;
import java.util.List;

/**
 * Project: UnwrittenBlocks Author: UnwrittenFun Created: 04/11/2014.
 */
public class ItemUpgrade extends ItemUB {
  public String[] keys;
  public IIcon[]  icons;

  public ItemUpgrade(String mainKey, String[] keys) {
    super(mainKey);
    this.keys = keys;
    setHasSubtypes(true);
  }

  @Override public IIcon getIconFromDamage(int meta) {
    return icons[meta];
  }

  @Override public String getUnlocalizedName(ItemStack stack) {
    return "item." + keys[stack.getItemDamage()];
  }

  @Override public void addInformation(ItemStack stack, EntityPlayer player, List lines, boolean bool) {
    Collections.addAll(lines, getLinesFromLang("unwrittenblocks.text.info_" + keys[stack.getItemDamage()]));

    super.addInformation(stack, player, lines, bool);
  }

  @SuppressWarnings("unchecked") @Override public void getSubItems(Item upgrade, CreativeTabs creativeTabs,
                                                                   List items) {
    super.getSubItems(upgrade, creativeTabs, items);
    items.add(new ItemStack(upgrade, 1, 1));
  }

  @Override public void registerIcons(IIconRegister iconRegister) {
    icons = new IIcon[keys.length];
    for (int i = 0; i < icons.length; i++) {
      icons[i] = iconRegister.registerIcon(ModInfo.RESOURCE_LOCATION + ":" + keys[i]);
    }
  }
}
