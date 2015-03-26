package unwrittenfun.minecraft.unwrittenblocks.common.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Author: James Birtles
 */
public class ItemDarkInfusedDiamondBlock extends ItemBlock {
  public ItemDarkInfusedDiamondBlock(Block block) {
    super(block);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean idk) {
    list.add("Infused from a Block of Diamond");
  }
}
