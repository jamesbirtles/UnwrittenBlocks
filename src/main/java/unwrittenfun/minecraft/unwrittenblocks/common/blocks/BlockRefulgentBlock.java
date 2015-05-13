package unwrittenfun.minecraft.unwrittenblocks.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import unwrittenfun.minecraft.unwrittenblocks.client.renderers.BlockRenderHelper;
import unwrittenfun.minecraft.unwrittenblocks.common.ModInfo;
import unwrittenfun.minecraft.unwrittenblocks.common.UnwrittenBlocks;

import java.util.List;

/**
 * Author: James Birtles
 */
public class BlockRefulgentBlock extends Block implements IRefulgentBlock, IConnectedTextures {
  public String key;
  public IIcon[] connIcons;
  private boolean hasConnected;

  public BlockRefulgentBlock(String key, boolean hasConnected) {
    super(Material.rock);
    setCreativeTab(UnwrittenBlocks.creativeTabUB);
    setBlockName(key);
    setBlockTextureName(ModInfo.RESOURCE_LOCATION + ":" + key);
    setHardness(1f);
    this.key = key;
    this.hasConnected = hasConnected;
  }

  @Override
  public void registerBlockIcons(IIconRegister register) {
    super.registerBlockIcons(register);

    if (hasConnectedTextures()) {
      connIcons = new IIcon[8];
      for (int i = 0; i < connIcons.length; i++) {
        connIcons[i] = register.registerIcon(ModInfo.RESOURCE_LOCATION + ":" + key + "_conn_" + i);
      }
    }
  }

  @Override
  public boolean hasConnectedTextures() {
    return hasConnected;
  }

  @Override
  public boolean canConnectToBlock(Block block) {
    boolean rtn = Block.isEqualTo(block, BlockRegister.refulgentWall);
    return rtn;
  }

  @Override
  public IIcon getIconForDisconnectedSide(int side) {
    return (connIcons == null || side >= connIcons.length) ? null : connIcons[side];
  }

  @Override
  public IIcon getIconFromDirection(int direction) {
    return blockIcon;
  }

  @Override
  public int getRGB(IBlockAccess world, int x, int y, int z) {
    return ItemDye.field_150922_c[world.getBlockMetadata(x, y, z)];
  }

  @Override
  public int getRGBForMeta(int metadata) {
    return ItemDye.field_150922_c[metadata];
  }

  @Override
  public boolean renderAsNormalBlock() {
    return false;
  }

  @Override
  public int getRenderType() {
    return UnwrittenBlocks.proxy.refulgentRenderer.getRenderId();
  }

  @Override
  public int getRenderBlockPass() {
    return 1;
  }

  @Override
  public boolean canRenderInPass(int pass) {
    BlockRenderHelper.renderPass = pass;
    return true;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void getSubBlocks(Item item, CreativeTabs tab, List stacks) {
    for (int i = 0; i < 16; i++) {
      stacks.add(new ItemStack(this, 1, i));
    }
  }

  @Override
  public int damageDropped(int meta) {
    return meta;
  }

  @Override
  public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
    ForgeDirection direction = ForgeDirection.getOrientation(side);
    Block block = world.getBlock(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ);
    return block == null || !block.isOpaqueCube();
  }

  @Override
  public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_) {


    return false;
  }

  @Override
  public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
    return true;
  }
}
