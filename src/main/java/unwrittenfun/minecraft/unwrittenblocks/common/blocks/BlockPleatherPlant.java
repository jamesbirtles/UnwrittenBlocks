package unwrittenfun.minecraft.unwrittenblocks.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import unwrittenfun.minecraft.unwrittenblocks.common.ModInfo;
import unwrittenfun.minecraft.unwrittenblocks.common.helpers.ItemHelpers;
import unwrittenfun.minecraft.unwrittenblocks.common.items.ItemRegister;

import java.util.ArrayList;

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
  protected boolean canPlaceBlockOn(Block block) {
    return block == Blocks.grass || block == Blocks.dirt;
  }

  @Override
  public IIcon getIcon(int side, int meta) {
    if (meta < 0 || meta > 7) {
      meta = 7;
    }
    return icons[meta];
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

  @Override
  public void registerBlockIcons(IIconRegister iconRegister) {
    icons = new IIcon[8];
    for (int i = 0; i < icons.length; i++) {
      icons[i] = iconRegister.registerIcon(ModInfo.RESOURCE_LOCATION + ":" + key + "_" + i);
    }
  }

  @Override
  public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
    if (!world.isRemote) {
      int meta = world.getBlockMetadata(x, y, z);
      if (meta == 7) {
        ArrayList<ItemStack> drops = getDrops(world, x, y, z, meta, 0);
        for (ItemStack drop : drops) {
          ItemHelpers.dropItemStack(drop, world, x, y, z);
        }
        world.setBlockMetadataWithNotify(x, y, z, 0, 3);
        return true;
      }
    }
    return super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
  }
}
