package unwrittenfun.minecraft.unwrittenblocks.items

import net.minecraft.item.{ItemStack, Item}
import unwrittenfun.minecraft.unwrittenblocks.client.gui.creativetabs
import net.minecraft.client.renderer.texture.IconRegister
import unwrittenfun.minecraft.unwrittenblocks._
import net.minecraft.creativetab.CreativeTabs
import java.util
import net.minecraft.util.Icon

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
class ItemStorageBall(id: Int, keys: Array[String]) extends Item(id) {
  setUnlocalizedName("storageBall")
  setCreativeTab(creativetabs.ubTab)
  hasSubtypes = true

  var icons: Array[Icon] = new Array[Icon](keys.length)

  override def registerIcons(register: IconRegister) {
    for (keyId <- 0 to (keys.length - 1)) {
      icons(keyId) = register.registerIcon(TEXTURE_LOCATION + ":" + keys(keyId))
    }
  }


  override def getIconFromDamage(meta: Int): Icon = icons(meta)

  override def getSubItems(id: Int, tab: CreativeTabs, genericList: util.List[_]) {
    val itemList = genericList.asInstanceOf[(util.ArrayList[ItemStack])]

    for (meta <- 0 to (keys.length - 1)) {
      itemList add new ItemStack(id, 1, meta)
    }
  }

  override def getUnlocalizedName(stack: ItemStack): String = {
    keys(stack.getItemDamage)
  }
}
