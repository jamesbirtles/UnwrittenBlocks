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
    items.COBBLE_BALL_ID = config.getItem(items.COBBLE_BALL_KEY, items.COBBLE_BALL_DEFAULT_ID).getInt - 256
    items.RELAYER_ID = config.getItem(items.RELAYER_KEY, items.RELAYER_DEFAULT_ID).getInt - 256

    blocks.PLEATHER_PLANT_ID = config.getBlock(blocks.PLEATHER_PLANT_KEY, blocks.PLEATHER_PLANT_DEFAULT_ID).getInt
    blocks.WALL_TELEPORTER_ID = config.getBlock(blocks.WALL_TELEPORTER_KEY, blocks.WALL_TELEPORTER_DEFAULT_ID).getInt
    blocks.DARK_INFUSER_ID = config.getBlock(blocks.DARK_INFUSER_KEY, blocks.DARK_INFUSER_DEFAULT_ID).getInt

    config.save()
  }
}
