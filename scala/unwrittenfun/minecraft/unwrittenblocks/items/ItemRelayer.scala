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

    if (target(0) == -1) { // Set a target
      stack.getTagCompound.setIntArray("TargetBlock", Array(world.getBlockId(x, y, z), world.getBlockMetadata(x, y, z)))
      if (world.isRemote) player.sendChatToPlayer(ChatMessageComponent.createFromText("Target block set to " + Block.blocksList(world.getBlockId(x, y, z)).getLocalizedName))
      if (!world.isRemote) return true
    } else {
      if (player.isSneaking) {
        stack.getTagCompound.setIntArray("TargetBlock", Array(-1, -1))
        if (world.isRemote) player.sendChatToPlayer(ChatMessageComponent.createFromText("Target block cleared"))
        if (!world.isRemote) return true
      } else {
        if (player.capabilities.isCreativeMode) {
          world.setBlock(x, y, z, target(0), target(1), 3)
        } else {
          if (world.getBlockId(x, y, z) != target(0) || world.getBlockMetadata(x, y, z) != target(1)) {
            var continue = true
            for (i <- 0 until player.inventory.getSizeInventory if continue) {
              val stack = player.inventory.getStackInSlot(i)
              if (stack != null && stack.itemID == target(0) && stack.getItemDamage == target(1)) {
                player.inventory.decrStackSize(i, 1)

                val itemDrops: util.ArrayList[ItemStack] = Block.blocksList(world.getBlockId(x, y, z)).getBlockDropped(world, x, y, z, world.getBlockMetadata(x, y, z), 0)
                for (j <- 0 until itemDrops.size()) {
                  val dropStack = itemDrops.get(j)
                  if (!player.inventory.addItemStackToInventory(dropStack)) {
                    if (!world.isRemote) dropItem(dropStack, world, x, y, z)
                  }
                }

                world.setBlock(x, y, z, target(0), target(1), 3)
                continue = false
              }
            }
          }
        }
        if (!world.isRemote) return true
      }
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
