package unwrittenfun.minecraft.unwrittenblocks.items

import net.minecraft.client.renderer.texture.IconRegister
import net.minecraft.item.Item
import unwrittenfun.minecraft.unwrittenblocks._
import unwrittenfun.minecraft.unwrittenblocks.client.gui.creativetabs

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun 
 * Created: 11/08/2014.
 */
class ItemDarkDiamond(id: Int, key: String) extends Item(id) {
  setUnlocalizedName(key)
  setCreativeTab(creativetabs.ubTab)

  override def registerIcons(register: IconRegister) {
    itemIcon = register.registerIcon(TEXTURE_LOCATION + ":" + key)
  }
}
