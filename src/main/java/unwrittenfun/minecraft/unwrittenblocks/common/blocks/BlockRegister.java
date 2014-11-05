package unwrittenfun.minecraft.unwrittenblocks.common.blocks;

import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun
 * Created: 04/11/2014.
 */
public class BlockRegister {
    public static final String PLEATHER_PLANT_KEY = "pleatherPlant";
    public static final String DARK_INFUSER_KEY = "darkInfuser";

    public static BlockPleatherPlant pleatherPlant;
    public static BlockDarkInfuser darkInfuser;

    public static void registerBlocks() {
        pleatherPlant = new BlockPleatherPlant(PLEATHER_PLANT_KEY);
        darkInfuser = new BlockDarkInfuser(DARK_INFUSER_KEY);

        GameRegistry.registerBlock(pleatherPlant, PLEATHER_PLANT_KEY);
        GameRegistry.registerBlock(darkInfuser, DARK_INFUSER_KEY);
    }
}
