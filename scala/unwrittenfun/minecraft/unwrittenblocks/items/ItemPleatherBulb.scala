package unwrittenfun.minecraft.unwrittenblocks.items

import net.minecraft.item.{ItemStack, Item}
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.client.renderer.texture.IconRegister
import net.minecraftforge.common.{IPlantable, EnumPlantType}
import net.minecraft.world.World
import unwrittenfun.minecraft.unwrittenblocks.TEXTURE_LOCATION
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.block.Block
import unwrittenfun.minecraft.unwrittenblocks.blocks.UBBlocks
import unwrittenfun.minecraft.unwrittenblocks.client.gui.creativetabs

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
class ItemPleatherBulb(id: Int, key: String) extends Item(id) with IPlantable {
  setUnlocalizedName(key)
  setCreativeTab(creativetabs.ubTab)

  override def registerIcons(register: IconRegister) {
    itemIcon = register.registerIcon(TEXTURE_LOCATION + ":" + key)
  }

  override def getPlantType(world: World, x: Int, y: Int, z: Int) = EnumPlantType.Plains

  override def getPlantID(world: World, x: Int, y: Int, z: Int) = UBBlocks.pleatherPlant.blockID

  override def getPlantMetadata(world: World, x: Int, y: Int, z: Int) = 0

  override def onItemUse(stack: ItemStack, player: EntityPlayer, world: World, x: Int, y: Int, z: Int, side: Int, hitX: Float, hitY: Float, hitZ: Float): Boolean = {
    val blockId = world.getBlockId(x, y, z)
    if (((blockId == Block.dirt.blockID) || (blockId == Block.grass.blockID)) && world.isAirBlock(x, y + 1, z)) {
      world.setBlock(x, y + 1, z, UBBlocks.pleatherPlant.blockID);
      stack.stackSize -= 1
      return true
    }

    false
  }
}