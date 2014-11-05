package unwrittenfun.minecraft.unwrittenblocks.common.items;

import net.minecraft.item.Item;
import net.minecraft.util.StatCollector;
import unwrittenfun.minecraft.unwrittenblocks.common.ModInfo;
import unwrittenfun.minecraft.unwrittenblocks.common.UnwrittenBlocks;

/**
 * Project: UnwrittenBlocks Author: UnwrittenFun Created: 29/08/2014.
 */
public class ItemUB extends Item {
  public ItemUB(String key) {
    setCreativeTab(UnwrittenBlocks.creativeTabUB);
    setUnlocalizedName(key);
    setTextureName(ModInfo.RESOURCE_LOCATION + ":" + key);
  }

  public static String[] getLinesFromLang(String address) {
    return StatCollector.translateToLocal(address).split("\\\\n");
  }
}
