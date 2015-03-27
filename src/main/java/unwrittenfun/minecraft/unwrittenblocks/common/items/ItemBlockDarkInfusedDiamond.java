package unwrittenfun.minecraft.unwrittenblocks.common.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.List;

/**
 * Author: James Birtles
 */
public class ItemBlockDarkInfusedDiamond extends ItemBlock {
  public ItemBlockDarkInfusedDiamond(Block block) {
    super(block);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void addInformation(ItemStack stack, EntityPlayer player, List lines, boolean idk) {
    lines.add(StatCollector.translateToLocal("unwrittenblocks.text.infusedFrom") + " " + StatCollector.translateToLocal("unwrittenblocks.text.diamondBlock"));
  }
}
