package unwrittenfun.minecraft.unwrittenblocks.items

import net.minecraft.item.{ItemStack, Item}
import unwrittenfun.minecraft.unwrittenblocks.client.gui.creativetabs
import net.minecraft.client.renderer.texture.IconRegister
import unwrittenfun.minecraft.unwrittenblocks._
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World
import net.minecraft.nbt.NBTTagCompound
import java.util
import net.minecraft.block.Block
import net.minecraft.util.ChatMessageComponent
import net.minecraft.entity.item.EntityItem
import net.minecraftforge.common.ForgeDirection

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
class ItemRelayer(id: Int, key: String) extends Item(id) {
  setUnlocalizedName(key)
  setCreativeTab(creativetabs.ubTab)

  override def registerIcons(register: IconRegister) {
    itemIcon = register.registerIcon(TEXTURE_LOCATION + ":" + key)
  }

  override def onItemUseFirst(stack: ItemStack, player: EntityPlayer, world: World, x: Int, y: Int, z: Int, side: Int, hitX: Float, hitY: Float, hitZ: Float): Boolean = {
    val target: Array[Int] = getTargetBlock(stack)

    if (player.isSneaking) {
      val targetArray = Array(world.getBlockId(x, y, z), world.getBlockMetadata(x, y, z))

      if (targetArray(0) == target(0) && targetArray(1) == target(1)) {
        stack.getTagCompound.setIntArray("TargetBlock", Array(-1, -1))
        if (world.isRemote) player.sendChatToPlayer(ChatMessageComponent.createFromText("Target block cleared"))
      } else {
        stack.getTagCompound.setIntArray("TargetBlock", targetArray)
        if (world.isRemote) player.sendChatToPlayer(ChatMessageComponent.createFromText("Target block set to " + Block.blocksList(world.getBlockId(x, y, z)).getLocalizedName))
      }
    } else if (target(0) != -1 && target(1) != -1) {
      val origBlock = Array(world.getBlockId(x, y, z), world.getBlockMetadata(x, y, z))
      if (origBlock(0) != target(0) || origBlock(1) != target(1)) {
        for (ddx <- -2 to 2; ddy <- -2 to 2; ddz <- -2 to 2) {
          val dx = if (side == 4 || side == 5) 0 else ddx
          val dy = if (side == 0 || side == 1) 0 else ddy
          val dz = if (side == 2 || side == 3) 0 else ddz

          if (isNextToAir(world, x + dx, y + dy, z + dz) && world.getBlockId(x + dx, y + dy, z + dz) == origBlock(0) && world.getBlockMetadata(x + dx, y + dy, z + dz) == origBlock(1)) {
            if (player.capabilities.isCreativeMode) {
              world.setBlock(x + dx, y + dy, z + dz, target(0), target(1), 3)
            } else {
              var continue = true
              for (i <- 0 until player.inventory.getSizeInventory if continue) {
                val stack = player.inventory.getStackInSlot(i)
                if (stack != null && stack.itemID == target(0) && stack.getItemDamage == target(1)) {
                  player.inventory.decrStackSize(i, 1)

                  val itemDrops: util.ArrayList[ItemStack] = Block.blocksList(world.getBlockId(x + dx, y + dy, z + dz)).getBlockDropped(world, x + dx, y + dy, z + dz, world.getBlockMetadata(x + dx, y + dy, z + dz), 0)
                  for (j <- 0 until itemDrops.size()) {
                    val dropStack = itemDrops.get(j)
                    if (!player.inventory.addItemStackToInventory(dropStack)) {
                      if (!world.isRemote) dropItem(dropStack, world, x, y, z)
                    }
                  }

                  world.setBlock(x + dx, y + dy, z + dz, target(0), target(1), 3)
                  continue = false
                }
              }
            }
          }
        }
      }
    }

    if (!world.isRemote) true else false
  }

  private def isNextToAir(world: World, x: Int, y: Int, z: Int): Boolean = {
    for (dir <- ForgeDirection.VALID_DIRECTIONS) {
      val dx = dir.offsetX + x
      val dy = dir.offsetY + y
      val dz = dir.offsetZ + z

      if (world.isAirBlock(dx, dy, dz) || !world.isBlockOpaqueCube(dx, dy, dz)) return true
    }

    false
  }

  private def dropItem(stack: ItemStack, world: World, x: Int, y: Int, z: Int) {
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
  }

  private def getTargetBlock(stack: ItemStack): Array[Int] = {
    if (!stack.hasTagCompound) stack.setTagCompound(new NBTTagCompound())
    if (!stack.getTagCompound.hasKey("TargetBlock")) stack.getTagCompound.setIntArray("TargetBlock", Array(-1, -1))
    stack.getTagCompound.getIntArray("TargetBlock")
  }

  override def addInformation(stack: ItemStack, player: EntityPlayer, genericList: util.List[_], par4: Boolean) {
    super.addInformation(stack, player, genericList, par4)

    val infoList = genericList.asInstanceOf[(util.ArrayList[String])]
    val target: Array[Int] = getTargetBlock(stack)
    if (target(0) != -1) {
      infoList add "Target Block: " + Block.blocksList(target(0)).getLocalizedName
    }
  }
}
