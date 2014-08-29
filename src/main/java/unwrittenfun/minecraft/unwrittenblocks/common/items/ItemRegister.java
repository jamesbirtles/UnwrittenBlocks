package unwrittenfun.minecraft.unwrittenblocks.common.items;

import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun
 * Created: 29/08/2014.
 */

public class ItemRegister {
  public static final ItemUB gpsChip = new ItemGPSChip();

  public static void registerItems() {
    GameRegistry.registerItem(gpsChip, GPS_CHIP_KEY);
  }

  public static final String GPS_CHIP_KEY = "gpsChip";
}
