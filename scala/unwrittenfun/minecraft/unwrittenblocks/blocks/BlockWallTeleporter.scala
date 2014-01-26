package unwrittenfun.minecraft.unwrittenblocks.blocks

import net.minecraft.block.{Block, BlockContainer}
import net.minecraft.block.material.Material
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.client.renderer.texture.IconRegister
import unwrittenfun.minecraft.unwrittenblocks.{UnwrittenBlocks, TEXTURE_LOCATION}
import net.minecraft.util.{AxisAlignedBB, Icon}
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.{World, IBlockAccess}
import unwrittenfun.minecraft.unwrittenblocks.blocks.tileentities.TileEntityWallTeleporter
import net.minecraft.entity.player.{EntityPlayer, EntityPlayerMP}
import net.minecraft.entity.Entity
import net.minecraft.item.{ItemBlock, ItemStack}
import cpw.mods.fml.common.network.FMLNetworkHandler
import net.minecraft.inventory.IInventory
import net.minecraft.entity.item.EntityItem
import unwrittenfun.minecraft.unwrittenblocks.client.gui.creativetabs

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
class BlockWallTeleporter(id: Int, key: String) extends BlockContainer(id, Material.iron) {
  setUnlocalizedName(key)
  setHardness(2f)
  setCreativeTab(creativetabs.ubTab)

  override def registerIcons(register: IconRegister) {
    blockIcon = register.registerIcon(TEXTURE_LOCATION + ":" + key)
  }

  override def getBlockTexture(world: IBlockAccess, x: Int, y: Int, z: Int, side: Int): Icon = {
    var rtn: Icon = blockIcon
    world.getBlockTileEntity(x, y, z) match {
      case teleporter: TileEntityWallTeleporter =>
        if (teleporter.mask(0) != 0) {
          val maskBlock = Block.blocksList(teleporter mask 0)
          rtn = maskBlock.getIcon(side, teleporter mask 1)
        }
      case _ =>
    }
    rtn
  }

  override def createNewTileEntity(world: World): TileEntity = new TileEntityWallTeleporter

  override def getCollisionBoundingBoxFromPool(world: World, x: Int, y: Int, z: Int): AxisAlignedBB = AxisAlignedBB.getAABBPool.getAABB(0, 0, 0, 0, 0, 0)

  override def isOpaqueCube: Boolean = false

  override def renderAsNormalBlock(): Boolean = false

  override def onEntityCollidedWithBlock(world: World, x: Int, y: Int, z: Int, entity: Entity) {
    if (!world.isRemote) {
      entity match {
        case player: EntityPlayerMP =>
          world.getBlockTileEntity(x, y, z) match {
            case teleporter: TileEntityWallTeleporter => if (teleporter.multiblock.getTrips > 0) teleporter.teleportPlayer(player)
            case _ =>
          }
        case _ =>
      }
    }
  }

  override def onBlockAdded(world: World, x: Int, y: Int, z: Int) {
    if (!world.isRemote) {
      world.getBlockTileEntity(x, y, z) match {
        case teleporter: TileEntityWallTeleporter => teleporter.notifyNeighboursOfConnectionChanged()
        case _ =>
      }
    }
  }

  override def onBlockActivated(world: World, x: Int, y: Int, z: Int, player: EntityPlayer, side: Int, hitX: Float, hitY: Float, hitZ: Float): Boolean = {
    var result: Boolean = true
    world.getBlockTileEntity(x, y, z) match {
      case teleporter: TileEntityWallTeleporter =>
        val held: ItemStack = player.inventory.getCurrentItem
        if (!teleporter.multiblock.locked && held != null && held.getItem.isInstanceOf[ItemBlock]) {
          if (held.getItem.asInstanceOf[ItemBlock].getBlockID == UBBlocks.wallTeleporter.blockID && teleporter.mask(0) == 0) {
            result = false
          }
          else if (!world.isRemote) {
            teleporter.setMask(held.getItem.asInstanceOf[ItemBlock].getBlockID, held.getItemDamage)
          }
        }
        else {
          FMLNetworkHandler.openGui(player, UnwrittenBlocks, 0, world, x, y, z)
        }
      case _ =>
    }
    result
  }

  override def breakBlock(world: World, x: Int, y: Int, z: Int, id: Int, meta: Int) {
    UBBlocks.dropItemsFromInventory(world, x, y, z)
    super.breakBlock(world, x, y, z, id, meta)
  }
}
