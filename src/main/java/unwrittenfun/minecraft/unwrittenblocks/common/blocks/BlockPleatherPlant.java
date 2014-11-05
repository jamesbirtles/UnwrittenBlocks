package unwrittenfun.minecraft.unwrittenblocks.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import unwrittenfun.minecraft.unwrittenblocks.common.ModInfo;
import unwrittenfun.minecraft.unwrittenblocks.common.items.ItemRegister;

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun
 * Created: 04/11/2014.
 */
public class BlockPleatherPlant extends BlockCrops {
    public String key;
    public IIcon[] icons;

    public BlockPleatherPlant(String key) {
        super();
        this.key = key;
        setBlockName(key);
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
        icons = new IIcon[8];
        for (int i = 0; i < icons.length; i++) {
            icons[i] = iconRegister.registerIcon(ModInfo.RESOURCE_LOCATION + ":" + key + "_" + i);
        }
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if (meta < 0 || meta > 7) {
            meta = 7;
        }
        return icons[meta];
    }

    @Override
    protected boolean canPlaceBlockOn(Block block) {
        return block == Blocks.grass || block == Blocks.dirt;
    }

    @Override
    // getSeedItem
    protected Item func_149866_i() {
        return ItemRegister.pleatherBulb;
    }

    @Override
    // getCropItem
    protected Item func_149865_P() {
        return ItemRegister.pleatherStrips;
    }
}
