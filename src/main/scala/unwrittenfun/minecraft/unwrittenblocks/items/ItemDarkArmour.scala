package unwrittenfun.minecraft.unwrittenblocks.items

import net.minecraft.item.{ItemStack, ItemArmor}
import net.minecraft.client.renderer.texture.IconRegister
import unwrittenfun.minecraft.unwrittenblocks._
import net.minecraft.entity.Entity
import unwrittenfun.minecraft.unwrittenblocks.client.gui.creativetabs

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
class ItemDarkArmour(id: Int, key: String, _armourType: Int) extends ItemArmor(id, UBItems.armourMatDark, 4, _armourType) {
  setUnlocalizedName(key)
  setCreativeTab(creativetabs.ubTab)

  override def registerIcons(register: IconRegister) {
    itemIcon = register.registerIcon(TEXTURE_LOCATION + ":" + key)
  }

  override def getArmorTexture(stack: ItemStack, entity: Entity, slot: Int, subType: String): String = {
    if (subType == null) {
      TEXTURE_LOCATION + ":textures/armour/darkArmour_layer" + (if (slot == 2) 2 else 1) + ".png"
    } else {
      null
    }
  }
}
