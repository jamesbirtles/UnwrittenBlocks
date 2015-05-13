package unwrittenfun.minecraft.unwrittenblocks.common.blocks;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import unwrittenfun.minecraft.unwrittenblocks.common.ModInfo;
import unwrittenfun.minecraft.unwrittenblocks.common.UnwrittenBlocks;
import unwrittenfun.minecraft.unwrittenblocks.common.helpers.InventoryHelpers;
import unwrittenfun.minecraft.unwrittenblocks.common.tileEntities.TERefulgentFabricator;

/**
 * Author: James Birtles
 */
public class BlockRefulgentFabricator extends BlockContainer implements IRefulgentBlock {
  public BlockRefulgentFabricator(String key) {
    super(Material.iron);
    setCreativeTab(UnwrittenBlocks.creativeTabUB);
    setBlockName(key);
    setBlockTextureName(ModInfo.RESOURCE_LOCATION + ":" + key);
    setHardness(1f);
  }

  @Override
  public TileEntity createNewTileEntity(World world, int meta) {
    return new TERefulgentFabricator();
  }

  @Override
  @SideOnly(Side.CLIENT)
  public int getRenderType() {
    return UnwrittenBlocks.proxy.refulgentRenderer.getRenderId();
  }

  public static IIcon refulgent, refulgentFabricatorTop, refulgentFabricatorSide, refulgentFabricatorBottom;

  @Override
  public void registerBlockIcons(IIconRegister iconRegister) {
    refulgent = iconRegister.registerIcon(ModInfo.RESOURCE_LOCATION + ":refulgent");
    refulgentFabricatorTop = iconRegister.registerIcon(ModInfo.RESOURCE_LOCATION + ":refulgentFabricatorTop");
    refulgentFabricatorSide = iconRegister.registerIcon(ModInfo.RESOURCE_LOCATION + ":refulgentFabricatorSide");
    refulgentFabricatorBottom = iconRegister.registerIcon(ModInfo.RESOURCE_LOCATION + ":refulgentFabricatorBottom");
  }

  @Override
  public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
    TileEntity tileEntity = world.getTileEntity(x, y, z);
    if (tileEntity instanceof TERefulgentFabricator) {
      FMLNetworkHandler.openGui(player, UnwrittenBlocks.instance, 2, world, x, y, z);
      return true;
    }
    return false;
  }

  @Override
  public IIcon getIconFromDirection(int direction) {
    if (direction == ForgeDirection.UP.ordinal()) {
      return refulgentFabricatorTop;
    } else if (direction == ForgeDirection.DOWN.ordinal()) {
      return refulgentFabricatorBottom;
    } else {
      return refulgentFabricatorSide;
    }
  }

  @Override
  public int getRGB(IBlockAccess world, int x, int y, int z) {
    return 0xFFFFFF;
  }

  @Override
  public int getRGBForMeta(int metadata) {
    return 0xFFFFFF;
  }

  @Override
  public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
    if (!world.isRemote) {
      InventoryHelpers.dropInventory(world, x, y, z);
    }
  }

  @Override
  public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
    ForgeDirection direction = ForgeDirection.getOrientation(side);
    Block block = world.getBlock(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ);
    return block == null || !block.isOpaqueCube();
  }

}
