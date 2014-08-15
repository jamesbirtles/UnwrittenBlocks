package unwrittenfun.minecraft.unwrittenblocks.items

import unwrittenfun.minecraft.unwrittenblocks.client.gui.creativetabs
import net.minecraft.client.renderer.texture.IconRegister
import net.minecraft.item.ItemSpade
import unwrittenfun.minecraft.unwrittenblocks._

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
class ItemDarkSpade(id: Int, key: String) extends ItemSpade(id, UBItems.toolMatDark) {
  setUnlocalizedName(key)
  setCreativeTab(creativetabs.ubTab)

  override def registerIcons(register: IconRegister) {
    itemIcon = register.registerIcon(TEXTURE_LOCATION + ":" + key)
  }
}
