package unwrittenfun.minecraft.unwrittenblocks.common.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import java.util.List;

/**
 * Author: James Birtles
 */
public class ItemBlockRefulgentBlock extends ItemBlock {
  public ItemBlockRefulgentBlock(Block block) {
    super(block);
    setHasSubtypes(true);
  }

  @Override
  public int getMetadata(int meta) {
    return meta;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void addInformation(ItemStack stack, EntityPlayer player, List lines, boolean idk) {
    String name = ItemDye.field_150921_b[stack.getItemDamage()];
    String[] nameSplit = name.split("_");
    name = StringUtils.join(nameSplit, " ");
    String formattedName = WordUtils.capitalize(name);
    lines.add(formattedName);
  }
}
