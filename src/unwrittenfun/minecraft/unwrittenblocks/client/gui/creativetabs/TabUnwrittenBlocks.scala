package unwrittenfun.minecraft.unwrittenblocks.client.gui.creativetabs

import net.minecraft.creativetab.CreativeTabs
import cpw.mods.fml.relauncher.{SideOnly, Side}
import unwrittenfun.minecraft.unwrittenblocks.blocks.UBBlocks

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
final class TabUnwrittenBlocks extends CreativeTabs(TAB_LABEL) {
  @SideOnly(Side.CLIENT)
  @Override override def getTabIconItemIndex: Int = UBBlocks.wallTeleporter.blockID

  @SideOnly(Side.CLIENT)
  @Override override def getTranslatedTabLabel: String = TAB_NAME
}