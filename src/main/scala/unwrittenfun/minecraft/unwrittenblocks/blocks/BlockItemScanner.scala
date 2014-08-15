package unwrittenfun.minecraft.unwrittenblocks.blocks

import net.minecraft.block.BlockContainer
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IconRegister
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World
import unwrittenfun.minecraft.unwrittenblocks._
import unwrittenfun.minecraft.unwrittenblocks.blocks.tileentities.TEItemScanner
import unwrittenfun.minecraft.unwrittenblocks.client.gui.creativetabs

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun 
 * Created: 30/07/2014.
 */
class BlockItemScanner(id: Int, key: String) extends BlockContainer(id, Material.iron) {
  setUnlocalizedName(key)
  setHardness(2f)
  setCreativeTab(creativetabs.ubTab)

  def createNewTileEntity(world: World): TileEntity = new TEItemScanner

  override def registerIcons(register: IconRegister) {
    blockIcon = register.registerIcon(TEXTURE_LOCATION + ":" + key)
  }

  override def breakBlock(world: World, x: Int, y: Int, z: Int, id: Int, meta: Int) {
    UBBlocks.dropItemsFromInventory(world, x, y, z)
    super.breakBlock(world, x, y, z, id, meta)
  }
}
