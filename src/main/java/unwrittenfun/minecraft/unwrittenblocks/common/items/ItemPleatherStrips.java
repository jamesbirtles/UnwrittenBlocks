package unwrittenfun.minecraft.unwrittenblocks.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

/**
 * Project: UnwrittenBlocks Author: UnwrittenFun Created: 04/11/2014.
 */
public class ItemPleatherStrips extends ItemUB {
  public ItemPleatherStrips(String key) {
    super(key);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void addInformation(ItemStack stack, EntityPlayer player, List lines,
                             boolean bool) {
    Collections.addAll(lines, getLinesFromLang("unwrittenblocks.text.pleatherStrips"));

    super.addInformation(stack, player, lines, bool);
  }
}
