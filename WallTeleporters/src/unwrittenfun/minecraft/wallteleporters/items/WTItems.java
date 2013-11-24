package unwrittenfun.minecraft.wallteleporters.items;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import unwrittenfun.minecraft.wallteleporters.info.ItemInfo;

/**
 * Mod: Wall Teleporters
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
public class WTItems {
    public static ItemGpsChip  gpsChip;
    public static ItemDebugger debugger;

    public static void init() {
        gpsChip = new ItemGpsChip(ItemInfo.GPS_CHIP_ID);
        debugger = new ItemDebugger(ItemInfo.DEBUGGER_ID);
    }

    public static void addNames() {
        LanguageRegistry.addName(new ItemStack(gpsChip, 1), ItemInfo.GPS_CHIP_NAME);
        LanguageRegistry.addName(new ItemStack(gpsChip, 1, 1), ItemInfo.GPS_CHIP_LINKED_NAME);
        LanguageRegistry.addName(new ItemStack(debugger, 1), ItemInfo.DEBUGGER_NAME);
    }

    public static void registerRecipes() {
        GameRegistry.addRecipe(new ItemStack(gpsChip),
                new Object[]{ "trt", "clc", "iri", 't', Block.torchRedstoneActive, 'l', Block.redstoneLampIdle, 'r',
                              Item.redstone, 'i', Item.ingotIron, 'c', new ItemStack(Item.dyePowder, 1, 2) });
    }
}
