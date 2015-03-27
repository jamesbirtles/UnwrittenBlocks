package unwrittenfun.minecraft.unwrittenblocks.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.List;

/**
 * Project: UnwrittenBlocks Author: UnwrittenFun Created: 05/11/2014.
 */
public class ItemDarkInfusedDiamond extends ItemUB {
  public ItemDarkInfusedDiamond(String key) {
    super(key);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void addInformation(ItemStack stack, EntityPlayer player,
                             List lines, boolean bool) {
    lines.add(StatCollector.translateToLocal("unwrittenblocks.text.infusedFrom") + " " +
        StatCollector.translateToLocal("unwrittenblocks.text.diamond"));

    super.addInformation(stack, player, lines, bool);
  }
}
