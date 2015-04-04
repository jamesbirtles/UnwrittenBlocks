package unwrittenfun.minecraft.unwrittenblocks.common.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import unwrittenfun.minecraft.unwrittenblocks.common.items.ItemBlockDarkInfusedDiamond;
import unwrittenfun.minecraft.unwrittenblocks.common.items.ItemBlockRefulgentBlock;
import unwrittenfun.minecraft.unwrittenblocks.common.items.ItemRegister;
import unwrittenfun.minecraft.unwrittenblocks.common.recipes.InfuserRecipes;
import unwrittenfun.minecraft.unwrittenblocks.common.tileEntities.TEDarkInfuser;
import unwrittenfun.minecraft.unwrittenblocks.common.tileEntities.TERefulgentFabricator;
import unwrittenfun.minecraft.unwrittenblocks.common.tileEntities.TEWallTeleporterBase;
import unwrittenfun.minecraft.unwrittenblocks.common.tileEntities.TEWallTeleporterWall;

/**
 * Project: UnwrittenBlocks Author: UnwrittenFun Created: 04/11/2014.
 */
public class BlockRegister {
  public static final String PLEATHER_PLANT_KEY = "pleatherPlant";
  public static final String DARK_INFUSER_KEY = "darkInfuser";
  public static final String WT_BASE_KEY = "wallTeleporterBase";
  public static final String WT_WALL_KEY = "wallTeleporterWall";
  public static final String DARK_INFUSED_DIAMOND_KEY = "darkInfusedDiamondBlock";
  public static final String REFULGENT_FABRICATOR_KEY = "refulgentFabricator";
  public static final String REFULGENT_WALL_KEY = "refulgentWall";
  public static final String REFULGENT_BRICK_KEY = "refulgentBrick";

  public static BlockPleatherPlant pleatherPlant;
  public static BlockDarkInfuser darkInfuser;
  public static BlockWallTeleporterBase wallTeleporterBase;
  public static BlockWallTeleporterWall wallTeleporterWall;
  public static BlockDarkInfusedDiamond darkInfusedDiamondBlock;
  public static BlockRefulgentFabricator refulgentFabricator;
  public static BlockRefulgentBlock refulgentWall;
  public static BlockRefulgentBlock refulgentBrick;

  public static void registerBlocks() {
    pleatherPlant = new BlockPleatherPlant(PLEATHER_PLANT_KEY);
    darkInfuser = new BlockDarkInfuser(DARK_INFUSER_KEY);
    wallTeleporterBase = new BlockWallTeleporterBase(WT_BASE_KEY);
    wallTeleporterWall = new BlockWallTeleporterWall(WT_WALL_KEY);
    darkInfusedDiamondBlock = new BlockDarkInfusedDiamond(DARK_INFUSED_DIAMOND_KEY);
    refulgentFabricator = new BlockRefulgentFabricator(REFULGENT_FABRICATOR_KEY);
    refulgentWall = new BlockRefulgentBlock(REFULGENT_WALL_KEY);
    refulgentBrick = new BlockRefulgentBlock(REFULGENT_BRICK_KEY);

    GameRegistry.registerBlock(pleatherPlant, null, PLEATHER_PLANT_KEY);
    GameRegistry.registerBlock(darkInfuser, DARK_INFUSER_KEY);
    GameRegistry.registerBlock(wallTeleporterBase, WT_BASE_KEY);
    GameRegistry.registerBlock(wallTeleporterWall, WT_WALL_KEY);
    GameRegistry.registerBlock(darkInfusedDiamondBlock, ItemBlockDarkInfusedDiamond.class, DARK_INFUSED_DIAMOND_KEY);
    GameRegistry.registerBlock(refulgentFabricator, REFULGENT_FABRICATOR_KEY);
    GameRegistry.registerBlock(refulgentWall, ItemBlockRefulgentBlock.class, REFULGENT_WALL_KEY);
    GameRegistry.registerBlock(refulgentBrick, ItemBlockRefulgentBlock.class, REFULGENT_BRICK_KEY);

    GameRegistry.registerTileEntity(TEDarkInfuser.class, "TE" + DARK_INFUSER_KEY);
    GameRegistry.registerTileEntity(TEWallTeleporterBase.class, "TE" + WT_BASE_KEY);
    GameRegistry.registerTileEntity(TEWallTeleporterWall.class, "TE" + WT_WALL_KEY);
    GameRegistry.registerTileEntity(TERefulgentFabricator.class, "TE" + REFULGENT_FABRICATOR_KEY);
  }

  public static void registerRecipes() {
    GameRegistry.addRecipe(new ItemStack(darkInfuser, 1), "ooo", "dnd", "nnn", 'o', Blocks.obsidian, 'd', Items.diamond, 'n', Blocks.nether_brick);
    GameRegistry.addRecipe(new ItemStack(darkInfusedDiamondBlock, 1), "ddd", "ddd", "ddd", 'd', ItemRegister.darkInfusedDiamond);

    InfuserRecipes.instance.addRecipe(new ItemStack(Blocks.stone), new ItemStack(Blocks.end_stone), 1000);
    InfuserRecipes.instance.addRecipe(new ItemStack(Blocks.diamond_block), new ItemStack(darkInfusedDiamondBlock), 14400);
  }
}
