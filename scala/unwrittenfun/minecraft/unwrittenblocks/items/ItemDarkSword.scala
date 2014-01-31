package unwrittenfun.minecraft.unwrittenblocks.items

import net.minecraft.item.ItemSword
import unwrittenfun.minecraft.unwrittenblocks.client.gui.creativetabs
import net.minecraft.client.renderer.texture.IconRegister
import unwrittenfun.minecraft.unwrittenblocks._

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
class ItemDarkSword(id: Int, key: String) extends ItemSword(id, UBItems.toolMatDark) {
  setUnlocalizedName(key)
  setCreativeTab(creativetabs.ubTab)

  override def registerIcons(register: IconRegister) {
    itemIcon = register.registerIcon(TEXTURE_LOCATION + ":" + key)
  }
}
