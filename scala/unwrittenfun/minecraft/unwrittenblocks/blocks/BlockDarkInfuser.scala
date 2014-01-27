package unwrittenfun.minecraft.unwrittenblocks.blocks

import net.minecraft.block.BlockContainer
import net.minecraft.block.material.Material
import net.minecraft.world.World
import net.minecraft.tileentity.TileEntity
import net.minecraft.client.renderer.texture.IconRegister
import unwrittenfun.minecraft.unwrittenblocks._
import unwrittenfun.minecraft.unwrittenblocks.blocks.tileentities.TileEntityDarkInfuser
import cpw.mods.fml.common.network.FMLNetworkHandler
import net.minecraft.entity.player.EntityPlayer
import unwrittenfun.minecraft.unwrittenblocks.client.gui.creativetabs

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
class BlockDarkInfuser(id: Int, key: String) extends BlockContainer(id, Material.iron) {
  setUnlocalizedName(key)
  setHardness(2f)
  setCreativeTab(creativetabs.ubTab)

  def createNewTileEntity(world: World): TileEntity = new TileEntityDarkInfuser

  override def registerIcons(register: IconRegister) {
    blockIcon = register.registerIcon(TEXTURE_LOCATION + ":" + key)
  }

  override def onBlockActivated(world: World, x: Int, y: Int, z: Int, player: EntityPlayer, side: Int, hitX: Float, hitY: Float, hitZ: Float): Boolean = {
    world.getBlockTileEntity(x, y, z) match {
      case infuser: TileEntityDarkInfuser => if (!world.isRemote) FMLNetworkHandler.openGui(player, UnwrittenBlocks, 1, world, x, y, z); true
      case _ => false
    }
  }

  override def breakBlock(world: World, x: Int, y: Int, z: Int, id: Int, meta: Int) {
    UBBlocks.dropItemsFromInventory(world, x, y, z)
    super.breakBlock(world, x, y, z, id, meta)
  }
}
