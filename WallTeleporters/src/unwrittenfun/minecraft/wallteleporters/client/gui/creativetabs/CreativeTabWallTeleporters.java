package unwrittenfun.minecraft.wallteleporters.client.gui.creativetabs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import unwrittenfun.minecraft.wallteleporters.blocks.WTBlocks;

/**
 * Mod: Wall Teleporters
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
final class CreativeTabWallTeleporters extends CreativeTabs {
    CreativeTabWallTeleporters() {
        super(WTCreativeTabs.WT_TAB_LABEL);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getTabIconItemIndex() {
        return WTBlocks.wallTeleporter.blockID;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getTranslatedTabLabel() {
        return WTCreativeTabs.WT_TAB_NAME;
    }
}
