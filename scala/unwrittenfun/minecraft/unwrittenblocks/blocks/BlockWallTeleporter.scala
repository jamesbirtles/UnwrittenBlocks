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
    val tileEntity: TileEntity = world.getBlockTileEntity(x, y, z)
    if (tileEntity.isInstanceOf[TileEntityWallTeleporter]) {
      val inventory: IInventory = tileEntity.asInstanceOf[IInventory]
      var i: Int = 0
      while (i < inventory.getSizeInventory) {
        val stack: ItemStack = inventory.getStackInSlotOnClosing(i)
        if (stack != null) {
          val spawnX: Float = x + world.rand.nextFloat
          val spawnY: Float = y + world.rand.nextFloat
          val spawnZ: Float = z + world.rand.nextFloat
          val droppedItem: EntityItem = new EntityItem(world, spawnX, spawnY, spawnZ, stack)
          val mult: Float = 0.05F
          droppedItem.motionX = (-0.5F + world.rand.nextFloat) * mult
          droppedItem.motionY = (4 + world.rand.nextFloat) * mult
          droppedItem.motionZ = (-0.5F + world.rand.nextFloat) * mult
          world.spawnEntityInWorld(droppedItem)
        }
        i += 1
      }
    }
    super.breakBlock(world, x, y, z, id, meta)
  }
}
