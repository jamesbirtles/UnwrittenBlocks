package unwrittenfun.minecraft.wallteleporters.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import unwrittenfun.minecraft.wallteleporters.blocks.tileentities.TileEntityWallTeleporter;
import unwrittenfun.minecraft.wallteleporters.info.BlockInfo;

/**
 * Mod: Wall Teleporters
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
public class WTBlocks {

    public static BlockWallTeleporter wallTeleporter;

    public static void init() {
        wallTeleporter = new BlockWallTeleporter(BlockInfo.WT_ID);

        GameRegistry.registerBlock(wallTeleporter, BlockInfo.WT_KEY);
    }

    public static void addNames() {
        LanguageRegistry.addName(wallTeleporter, BlockInfo.WT_NAME);
    }

    public static void registerTileEntity() {
        GameRegistry.registerTileEntity(TileEntityWallTeleporter.class, BlockInfo.WT_TE_KEY);
    }

    public static void registerRecipes() {
        GameRegistry.addRecipe(new ItemStack(wallTeleporter, 4),
                new Object[]{ "beb", "ygp", "rer", 'b', Item.blazePowder, 'e', Block.whiteStone, 'y', Item.eyeOfEnder,
                              'g', Block.blockGold, 'p', Item.enderPearl, 'r', Item.redstone });
    }
}
