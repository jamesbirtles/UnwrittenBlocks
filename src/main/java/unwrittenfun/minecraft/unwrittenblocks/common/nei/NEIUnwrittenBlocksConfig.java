package unwrittenfun.minecraft.unwrittenblocks.common.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import net.minecraft.item.ItemStack;
import unwrittenfun.minecraft.unwrittenblocks.common.ModInfo;
import unwrittenfun.minecraft.unwrittenblocks.common.blocks.BlockRegister;

/**
 * Author: James Birtles
 */
public class NEIUnwrittenBlocksConfig implements IConfigureNEI {
  @Override
  public void loadConfig() {
    API.registerRecipeHandler(new InfuserRecipeHandler());
    API.registerUsageHandler(new InfuserRecipeHandler());
  }

  @Override
  public String getName() {
    return "UnwrittenBlocks Plugin";
  }

  @Override
  public String getVersion() {
    return ModInfo.VERSION;
  }
}
