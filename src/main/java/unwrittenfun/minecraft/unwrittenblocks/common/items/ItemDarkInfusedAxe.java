package unwrittenfun.minecraft.unwrittenblocks.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import unwrittenfun.minecraft.unwrittenblocks.common.ModInfo;
import unwrittenfun.minecraft.unwrittenblocks.common.UnwrittenBlocks;

import java.util.List;

/**
 * Project: UnwrittenBlocks Author: UnwrittenFun Created: 04/11/2014.
 */
public class ItemDarkInfusedAxe extends ItemAxe {
  protected ItemDarkInfusedAxe(String key) {
    super(ItemRegister.darkInfusedToolMaterial);
    setCreativeTab(UnwrittenBlocks.creativeTabUB);
    setUnlocalizedName(key);
    setTextureName(ModInfo.RESOURCE_LOCATION + ":" + key);
  }

  @SuppressWarnings("unchecked") @Override public void addInformation(ItemStack stack, EntityPlayer player, List lines,
                                                                      boolean bool) {
    super.addInformation(stack, player, lines, bool);

    lines.add(StatCollector.translateToLocal("unwrittenblocks.text.infusedFrom") + " " +
              StatCollector.translateToLocal("unwrittenblocks.text.diamondAxe"));
  }
}
