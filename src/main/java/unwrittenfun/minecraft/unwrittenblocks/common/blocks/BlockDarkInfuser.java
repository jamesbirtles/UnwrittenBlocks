package unwrittenfun.minecraft.unwrittenblocks.common.blocks;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import unwrittenfun.minecraft.unwrittenblocks.common.ModInfo;
import unwrittenfun.minecraft.unwrittenblocks.common.UnwrittenBlocks;
import unwrittenfun.minecraft.unwrittenblocks.common.tileEntities.TEDarkInfuser;

/**
 * Project: UnwrittenBlocks Author: UnwrittenFun Created: 04/11/2014.
 */
public class BlockDarkInfuser extends BlockContainer {
  protected BlockDarkInfuser(String key) {
    super(Material.rock);
    setCreativeTab(UnwrittenBlocks.creativeTabUB);
    setBlockName(key);
    setBlockTextureName(ModInfo.RESOURCE_LOCATION + ":" + key);
    setHardness(2F);
    setBlockBounds(9F / 32F, 0F, 9F / 32F, 23F / 32F, 30F / 32F, 23F / 32F);
  }

  @Override
  public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
    return new TEDarkInfuser();
  }

  @Override
  public boolean renderAsNormalBlock() {
    return false;
  }

  @Override
  public int getRenderType() {
    return -1;
  }

  @Override
  public boolean isOpaqueCube() {
    return false;
  }

  @Override
  public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX,
                                  float hitY, float hitZ) {
    TileEntity tileEntity = world.getTileEntity(x, y, z);
    if (tileEntity instanceof TEDarkInfuser) {
      FMLNetworkHandler.openGui(player, UnwrittenBlocks.instance, 1, world, x, y, z);
      return true;
    }
    return false;
  }
}
