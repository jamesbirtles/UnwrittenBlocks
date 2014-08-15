package unwrittenfun.minecraft.unwrittenblocks.items

import unwrittenfun.minecraft.unwrittenblocks.client.gui.creativetabs
import net.minecraft.client.renderer.texture.IconRegister
import unwrittenfun.minecraft.unwrittenblocks._
import net.minecraft.item.ItemPickaxe

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
class ItemDarkPick(id: Int, key: String) extends ItemPickaxe(id, UBItems.toolMatDark) {
  setUnlocalizedName(key)
  setCreativeTab(creativetabs.ubTab)

  override def registerIcons(register: IconRegister) {
    itemIcon = register.registerIcon(TEXTURE_LOCATION + ":" + key)
  }
}
