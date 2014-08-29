package unwrittenfun.minecraft.unwrittenblocks.common.items;

import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun
 * Created: 29/08/2014.
 */

public class ItemRegister {
  public static final ItemUB gpsChip = new ItemGPSChip();
  public static final ItemUB storageBall = new ItemStorageBall();

  public static void registerItems() {
    GameRegistry.registerItem(gpsChip, GPS_CHIP_KEY);
    GameRegistry.registerItem(storageBall, STORAGE_BALL_KEY);
  }

  public static final String GPS_CHIP_KEY = "gpsChip";
  public static final String STORAGE_BALL_KEY = "storageBall";
}
