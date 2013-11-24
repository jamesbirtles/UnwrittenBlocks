package unwrittenfun.minecraft.unwrittenblocks.items

import net.minecraft.util.{ChatMessageComponent, Icon}
import net.minecraft.item.{ItemStack, Item}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.client.renderer.texture.IconRegister
import unwrittenfun.minecraft.unwrittenblocks.TEXTURE_LOCATION
import net.minecraft.world.World
import net.minecraft.entity.player.EntityPlayer
import java.util
import net.minecraft.creativetab.CreativeTabs
import unwrittenfun.minecraft.unwrittenblocks.client.gui.creativetabs

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
object ItemGpsChip {
  private var linkedIcon: Icon = null
}

class ItemGpsChip(id: Int, key: String) extends Item(id) {
  setMaxStackSize(1)
  setCreativeTab(creativetabs.ubTab)
  setUnlocalizedName(key)

  override def registerIcons(register: IconRegister) {
    itemIcon = register registerIcon TEXTURE_LOCATION + ":" + key
    ItemGpsChip.linkedIcon = register registerIcon TEXTURE_LOCATION + ":" + key + "_linked"
  }

  override def getIconFromDamage(damage: Int): Icon = {
    if (damage != 0) ItemGpsChip.linkedIcon else itemIcon
  }

  override def onItemRightClick(stack: ItemStack, world: World, player: EntityPlayer): ItemStack = {
    if (!world.isRemote) {
      if (stack.getItemDamage == 1) {
        if (player.isSneaking) {
          stack.getTagCompound removeTag "LocationData"
          stack setItemDamage 0
          player sendChatToPlayer ChatMessageComponent.createFromText("GPS Chip unlinked.")
        }
      }
      else if (stack.getItemDamage == 0) {
        val stackCompound: NBTTagCompound = if (stack.hasTagCompound) stack.getTagCompound else new NBTTagCompound
        val compound: NBTTagCompound = new NBTTagCompound
        compound.setString("worldName", player.worldObj.provider.getDimensionName)
        compound.setInteger("worldId", player.worldObj.provider.dimensionId)
        compound.setFloat("locationX", player.posX.asInstanceOf[Float])
        compound.setFloat("locationY", player.posY.asInstanceOf[Float])
        compound.setFloat("locationZ", player.posZ.asInstanceOf[Float])
        compound.setFloat("locationRotation", player.rotationYaw)
        stackCompound.setCompoundTag("LocationData", compound)
        stack setTagCompound stackCompound
        stack setItemDamage 1
        player sendChatToPlayer ChatMessageComponent.createFromText("GPS Chip linked.")
      }
    }
    stack
  }

  override def addInformation(stack: ItemStack, player: EntityPlayer, genericList: util.List[_], par4: Boolean) {
    val list = genericList.asInstanceOf[(util.ArrayList[String])]

    if (hasTeleportData(stack) && stack.getItemDamage == 1) {
      list add "§eSneak right click to unlink"

      val teleportCompound: NBTTagCompound = stack.getTagCompound.getCompoundTag("LocationData")
      list add "World: " + teleportCompound.getString("worldName")
      list add "X: " + teleportCompound.getFloat("locationX").toInt
      list add "Y: " + teleportCompound.getFloat("locationY").toInt
      list add "Z: " + teleportCompound.getFloat("locationZ").toInt
    } else list add "§eRight click to link"
  }

  override def getUnlocalizedName: String = {
    "item." + key + "1"
  }

  override def getUnlocalizedName(stack: ItemStack): String = {
    "item." + key + stack.getItemDamage
  }

  def hasTeleportData(stack: ItemStack): Boolean = {
    stack.hasTagCompound && stack.getTagCompound.hasKey("LocationData")
  }
}
