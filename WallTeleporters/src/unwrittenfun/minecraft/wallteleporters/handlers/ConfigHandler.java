package unwrittenfun.minecraft.wallteleporters.handlers;

import net.minecraftforge.common.Configuration;
import unwrittenfun.minecraft.wallteleporters.info.BlockInfo;
import unwrittenfun.minecraft.wallteleporters.info.ItemInfo;

import java.io.File;

/**
 * Mod: Wall Teleporters
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
public class ConfigHandler {
    public static void init(File file) {
        Configuration config = new Configuration(file);

        config.load();

        BlockInfo.WT_ID = config.getBlock(BlockInfo.WT_KEY, BlockInfo.WT_DEFAULT_ID).getInt();

        ItemInfo.DEBUGGER_ID = config.getItem(ItemInfo.DEBUGGER_KEY, ItemInfo.DEBUGGER_DEFAULT_ID).getInt() - 256;
        ItemInfo.GPS_CHIP_ID = config.getItem(ItemInfo.GPS_CHIP_KEY, ItemInfo.GPS_CHIP_DEFAULT_ID).getInt() - 256;

        config.save();
    }
}
