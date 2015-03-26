package unwrittenfun.minecraft.unwrittenblocks.client.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import unwrittenfun.minecraft.unwrittenblocks.common.ModInfo;
import unwrittenfun.minecraft.unwrittenblocks.common.items.ItemRegister;

/**
 * Project: UnwrittenBlocks Author: UnwrittenFun Created: 29/08/2014.
 */
public class UBCreativeTab extends CreativeTabs {
  public UBCreativeTab() {
    super(ModInfo.MOD_ID.toLowerCase());
  }

  @Override
  public Item getTabIconItem() {
    return ItemRegister.gpsChip;
  }
}
