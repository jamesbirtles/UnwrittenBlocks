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
class ItemCobbleBall(id: Int, key: String) extends Item(id) {
  setUnlocalizedName(key)
  setCreativeTab(creativetabs.ubTab)
  hasSubtypes = true

  var mossyCobbleIcon: Icon = null

  override def registerIcons(register: IconRegister) {
    itemIcon = register.registerIcon(TEXTURE_LOCATION + ":" + key)
    mossyCobbleIcon = register.registerIcon(TEXTURE_LOCATION + ":" + key + "Mossy")
  }


  override def getIconFromDamage(meta: Int): Icon = if (meta == 0) mossyCobbleIcon else itemIcon

  override def getSubItems(id: Int, tab: CreativeTabs, genericList: util.List[_]) {
    val itemList = genericList.asInstanceOf[(util.ArrayList[ItemStack])]

    itemList add new ItemStack(id, 1, 0); // Mossy cobble ball
    itemList add new ItemStack(id, 1, 1); // Cobble ball
  }

  override def getUnlocalizedName(stack: ItemStack): String = {
    getUnlocalizedName() + (if (stack.getItemDamage == 0) "Mossy" else "")
  }
}
