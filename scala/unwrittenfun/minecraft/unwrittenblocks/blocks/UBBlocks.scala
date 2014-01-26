package unwrittenfun.minecraft.unwrittenblocks.blocks

import net.minecraft.block.Block
import cpw.mods.fml.common.registry.{GameRegistry, LanguageRegistry}
import net.minecraftforge.common.MinecraftForge
import unwrittenfun.minecraft.unwrittenblocks.blocks.tileentities.{TileEntityDarkInfuser, TileEntityWallTeleporter}
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.tileentity.TileEntity
import net.minecraft.inventory.IInventory
import net.minecraft.world.World
import net.minecraft.entity.item.EntityItem
import net.minecraft.nbt.{NBTTagCompound, NBTTagList}
import unwrittenfun.minecraft.unwrittenblocks.recipes.InfuserRecipes

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
object UBBlocks {
  var pleatherPlant: Block = null
  var wallTeleporter: Block = null
  var darkInfuser: Block = null

  def registerBlocks() {
    pleatherPlant = new BlockPleatherPlant(PLEATHER_PLANT_ID, PLEATHER_PLANT_KEY)
    wallTeleporter = new BlockWallTeleporter(WALL_TELEPORTER_ID, WALL_TELEPORTER_KEY)
    darkInfuser = new BlockDarkInfuser(DARK_INFUSER_ID, DARK_INFUSER_KEY)

    GameRegistry.registerBlock(pleatherPlant, PLEATHER_PLANT_KEY)
    GameRegistry.registerBlock(wallTeleporter, WALL_TELEPORTER_KEY)
    GameRegistry.registerBlock(darkInfuser, DARK_INFUSER_KEY)

    MinecraftForge.addGrassPlant(pleatherPlant, 7, 10)
  }

  def registerNames() {
    LanguageRegistry.addName(pleatherPlant, PLEATHER_PLANT_NAME)
    LanguageRegistry.addName(wallTeleporter, WALL_TELEPORTER_NAME)
    LanguageRegistry.addName(darkInfuser, DARK_INFUSER_NAME)
  }

  def registerTileEntities() {
    GameRegistry.registerTileEntity(classOf[TileEntityWallTeleporter], WALL_TELEPORTER_KEY)
    GameRegistry.registerTileEntity(classOf[TileEntityDarkInfuser], DARK_INFUSER_KEY)
  }

  def registerRecipes() {
    GameRegistry.addRecipe(new ItemStack(wallTeleporter, 4), "beb", "ygp", "rer", 'b'.asInstanceOf[Character], Item.blazePowder, 'e'.asInstanceOf[Character], Block.whiteStone, 'y'.asInstanceOf[Character], Item.eyeOfEnder, 'g'.asInstanceOf[Character], Block.blockGold, 'p'.asInstanceOf[Character], Item.enderPearl, 'r'.asInstanceOf[Character], Item.redstone)
    GameRegistry.addRecipe(new ItemStack(darkInfuser, 1), "oco", "dwd", "oco", 'o'.asInstanceOf[Character], Block.obsidian, 'c'.asInstanceOf[Character], Block.coalBlock, 'd'.asInstanceOf[Character], Item.diamond, 'w'.asInstanceOf[Character], Block.workbench)

    InfuserRecipes.addRecipe(Block.stone.blockID, -1, new ItemStack(Block.whiteStone), 1000)
  }

  ////
  // Common code
  ////

  def dropItemsFromInventory(world: World, x: Int, y: Int, z: Int) {
    val tileEntity: TileEntity = world.getBlockTileEntity(x, y, z)
    tileEntity match {
      case inventory: IInventory =>
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
      case _ =>
    }
  }

  def writeItemsToNBT(compound: NBTTagCompound, inventory: IInventory) {
    val items: NBTTagList = new NBTTagList
    var i: Int = 0
    while (i < inventory.getSizeInventory) {
      val stack: ItemStack = inventory.getStackInSlot(i)
      if (stack != null) {
        val item: NBTTagCompound = new NBTTagCompound
        item.setByte("Slot", i.toByte)
        stack.writeToNBT(item)
        items.appendTag(item)
      }
      i += 1
    }
    compound.setTag("Items", items)
  }

  def loadItemsFromNBT(compound: NBTTagCompound, inventory: IInventory) {
    val items: NBTTagList = compound.getTagList("Items")
    var i: Int = 0
    while (i < items.tagCount) {
      val item: NBTTagCompound = items.tagAt(i).asInstanceOf[NBTTagCompound]
      val slot: Int = item getByte "Slot"
      if (slot >= 0 && slot < inventory.getSizeInventory) {
        inventory.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(item))
      }
      i += 1
    }
  }
}
