package unwrittenfun.minecraft.unwrittenblocks.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

/**
 * Author: James Birtles
 */
public class ItemStorageBallContainer extends ItemUB {
  public ItemStorageBallContainer(String key) {
    super(key);
  }

  @Override
  public void addInformation(ItemStack stack, EntityPlayer player, List lines, boolean idk) {
    Collections.addAll(lines, getLinesFromLang("unwrittenblocks.text.storageBallContainer"));
  }
}
