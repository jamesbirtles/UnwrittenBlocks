package unwrittenfun.minecraft.unwrittenblocks.items

import net.minecraft.item.Item
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.client.renderer.texture.IconRegister
import unwrittenfun.minecraft.unwrittenblocks.TEXTURE_LOCATION
import unwrittenfun.minecraft.unwrittenblocks.client.gui.creativetabs

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
class ItemPleatherStrips(id: Int, key: String) extends Item(id) {
  setUnlocalizedName(key)
  setCreativeTab(creativetabs.ubTab)

  override def registerIcons(register: IconRegister) {
    itemIcon = register.registerIcon(TEXTURE_LOCATION + ":" + key)
  }
}
