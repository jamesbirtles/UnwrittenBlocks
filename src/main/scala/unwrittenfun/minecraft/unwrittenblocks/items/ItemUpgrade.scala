package unwrittenfun.minecraft.unwrittenblocks.items

import java.util

import net.minecraft.client.renderer.texture.IconRegister
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.{ItemStack, Item}
import net.minecraft.util.{EnumChatFormatting, Icon}
import unwrittenfun.minecraft.unwrittenblocks._
import unwrittenfun.minecraft.unwrittenblocks.client.gui.creativetabs

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun 
 * Created: 11/08/2014.
 */
class ItemUpgrade(id: Int, keys: Array[String]) extends Item(id) {
  setUnlocalizedName("upgrade")
  setCreativeTab(creativetabs.ubTab)
  setHasSubtypes(true)

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

  override def addInformation(stack: ItemStack, player: EntityPlayer, genericList: util.List[_], par4: Boolean) {
    val list = genericList.asInstanceOf[(util.ArrayList[String])]

    stack.getItemDamage match {
      case 0 => list.add(EnumChatFormatting.GOLD + "Crafting Component")
      case 1 =>
        list.add(EnumChatFormatting.GOLD + "Increase Speed of Machines")
        list.add(EnumChatFormatting.GRAY + "Stacks up to 8")
      case _ =>
    }
  }
}
