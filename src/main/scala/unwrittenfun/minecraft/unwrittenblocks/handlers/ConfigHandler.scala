package unwrittenfun.minecraft.unwrittenblocks.handlers

import net.minecraftforge.common.Configuration
import java.io.File
import unwrittenfun.minecraft.unwrittenblocks.{blocks, items}

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
object ConfigHandler {
  def init(file: File) {
    val config: Configuration = new Configuration(file)

    config.load()

    items.PLEATHER_BULB_ID = config.getItem(items.PLEATHER_BULB_KEY, items.PLEATHER_BULB_DEFAULT_ID).getInt - 256
    items.PLEATHER_STRIPS_ID = config.getItem(items.PLEATHER_STRIPS_KEY, items.PLEATHER_STRIPS_DEFAULT_ID).getInt - 256
    items.GPS_CHIP_ID = config.getItem(items.GPS_CHIP_KEY, items.GPS_CHIP_DEFAULT_ID).getInt - 256
    items.STORAGE_BALL_ID = config.getItem("storageBall", items.STORAGE_BALL_DEFAULT_ID).getInt - 256
    items.RELAYER_ID = config.getItem(items.RELAYER_KEY, items.RELAYER_DEFAULT_ID).getInt - 256
    items.UPGRADE_ID = config.getItem("upgrade", items.UPGRADE_ID).getInt - 256

    items.DARK_ARMOUR_IDS(0) = config.getItem(items.DARK_ARMOUR_KEYS(0), items.DARK_ARMOUR_DEFAULT_IDS(0)).getInt - 256
    items.DARK_ARMOUR_IDS(1) = config.getItem(items.DARK_ARMOUR_KEYS(1), items.DARK_ARMOUR_DEFAULT_IDS(1)).getInt - 256
    items.DARK_ARMOUR_IDS(2) = config.getItem(items.DARK_ARMOUR_KEYS(2), items.DARK_ARMOUR_DEFAULT_IDS(2)).getInt - 256
    items.DARK_ARMOUR_IDS(3) = config.getItem(items.DARK_ARMOUR_KEYS(3), items.DARK_ARMOUR_DEFAULT_IDS(3)).getInt - 256

    items.DARK_SWORD_ID = config.getItem(items.DARK_SWORD_KEY, items.DARK_SWORD_DEFAULT_ID).getInt - 256
    items.DARK_PICK_ID = config.getItem(items.DARK_PICK_KEY, items.DARK_PICK_DEFAULT_ID).getInt - 256
    items.DARK_SPADE_ID = config.getItem(items.DARK_SPADE_KEY, items.DARK_SPADE_DEFAULT_ID).getInt - 256
    items.DARK_AXE_ID = config.getItem(items.DARK_AXE_KEY, items.DARK_AXE_DEFAULT_ID).getInt - 256
    items.DARK_HOE_ID = config.getItem(items.DARK_HOE_KEY, items.DARK_HOE_DEFAULT_ID).getInt - 256
    items.DARK_DIAMOND_ID = config.getItem(items.DARK_DIAMOND_KEY, items.DARK_DIAMOND_DEFAULT_ID).getInt - 256

    blocks.PLEATHER_PLANT_ID = config.getBlock(blocks.PLEATHER_PLANT_KEY, blocks.PLEATHER_PLANT_DEFAULT_ID).getInt
    blocks.WALL_TELEPORTER_ID = config.getBlock(blocks.WALL_TELEPORTER_KEY, blocks.WALL_TELEPORTER_DEFAULT_ID).getInt
    blocks.DARK_INFUSER_ID = config.getBlock(blocks.DARK_INFUSER_KEY, blocks.DARK_INFUSER_DEFAULT_ID).getInt
    blocks.ITEM_SCANNER_ID = config.getBlock(blocks.ITEM_SCANNER_KEY, blocks.ITEM_SCANNER_DEFAULT_ID).getInt

    config.save()
  }
}
