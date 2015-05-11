package unwrittenfun.minecraft.unwrittenblocks.common.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import unwrittenfun.minecraft.unwrittenblocks.common.ModInfo;

/**
 * Author: James Birtles
 */
public class NEIUnwrittenBlocksConfig implements IConfigureNEI {
  @Override
  public void loadConfig() {
    API.registerRecipeHandler(new InfuserRecipeHandler());
    API.registerUsageHandler(new InfuserRecipeHandler());
    API.registerRecipeHandler(new StorageBallRecipeHandler());
    API.registerUsageHandler(new StorageBallRecipeHandler());
    API.registerRecipeHandler(new StorageBallReturnRecipeHandler());
    API.registerUsageHandler(new StorageBallReturnRecipeHandler());
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
