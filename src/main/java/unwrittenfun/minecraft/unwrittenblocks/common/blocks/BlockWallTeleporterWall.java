package unwrittenfun.minecraft.unwrittenblocks.common.blocks;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import unwrittenfun.minecraft.unwrittenblocks.common.ModInfo;
import unwrittenfun.minecraft.unwrittenblocks.common.UnwrittenBlocks;
import unwrittenfun.minecraft.unwrittenblocks.common.items.ItemRegister;
import unwrittenfun.minecraft.unwrittenblocks.common.multiblock.WallTeleporterNetwork;
import unwrittenfun.minecraft.unwrittenblocks.common.network.NetworkRegister;
import unwrittenfun.minecraft.unwrittenblocks.common.network.messages.TileEntityStackMessage;
import unwrittenfun.minecraft.unwrittenblocks.common.tileEntities.IWallTeleporterBlock;
import unwrittenfun.minecraft.unwrittenblocks.common.tileEntities.TEWallTeleporter;
import unwrittenfun.minecraft.unwrittenblocks.common.tileEntities.TEWallTeleporterBase;
import unwrittenfun.minecraft.unwrittenblocks.common.tileEntities.TEWallTeleporterWall;

/**
 * Author: James Birtles
 */
public class BlockWallTeleporterWall extends BlockContainer {
  public BlockWallTeleporterWall(String key) {
    super(Material.iron);
    setCreativeTab(UnwrittenBlocks.creativeTabUB);
    setBlockName(key);
    setBlockTextureName(ModInfo.RESOURCE_LOCATION + ":" + key);
    setHardness(2F);
  }

  public IIcon[] icons;

  @Override
  public void registerBlockIcons(IIconRegister register) {
    icons = new IIcon[16];

    icons[0] = register.registerIcon(ModInfo.RESOURCE_LOCATION + ":teleporterWall/0"); // None
    icons[1] = register.registerIcon(ModInfo.RESOURCE_LOCATION + ":teleporterWall/1"); // Left
    icons[2] = register.registerIcon(ModInfo.RESOURCE_LOCATION + ":teleporterWall/2"); // Right
    icons[3] = register.registerIcon(ModInfo.RESOURCE_LOCATION + ":teleporterWall/3"); // Left + Right
    icons[4] = register.registerIcon(ModInfo.RESOURCE_LOCATION + ":teleporterWall/4"); // Top
    icons[5] = register.registerIcon(ModInfo.RESOURCE_LOCATION + ":teleporterWall/5"); // Top + Left
    icons[6] = register.registerIcon(ModInfo.RESOURCE_LOCATION + ":teleporterWall/6"); // Top + Right
    icons[7] = register.registerIcon(ModInfo.RESOURCE_LOCATION + ":teleporterWall/7"); // Top + Left + Right
    icons[8] = register.registerIcon(ModInfo.RESOURCE_LOCATION + ":teleporterWall/8"); // Bottom
    icons[9] = register.registerIcon(ModInfo.RESOURCE_LOCATION + ":teleporterWall/9"); // Bottom + Left
    icons[10] = register.registerIcon(ModInfo.RESOURCE_LOCATION + ":teleporterWall/10"); // Bottom + Right
    icons[11] = register.registerIcon(ModInfo.RESOURCE_LOCATION + ":teleporterWall/11"); // Bottom + Left + Right
    icons[12] = register.registerIcon(ModInfo.RESOURCE_LOCATION + ":teleporterWall/12"); // Top + Bottom
    icons[13] = register.registerIcon(ModInfo.RESOURCE_LOCATION + ":teleporterWall/13"); // Top + Bottom + Left
    icons[14] = register.registerIcon(ModInfo.RESOURCE_LOCATION + ":teleporterWall/14"); // Top + Bottom + Right
    icons[15] = register.registerIcon(ModInfo.RESOURCE_LOCATION + ":teleporterWall/15"); // Top + Bottom + Left + Right

    blockIcon = icons[0];
  }

  @Override
  public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
    TEWallTeleporter teleporter = (TEWallTeleporter) world.getTileEntity(x, y, z);
    ItemStack mask = teleporter.mask;
    if (mask.isItemEqual(ItemRegister.wallStack)) {
      int connectedSides = 0; // Left 1, Right 2, Up 4, Down 8

      ForgeDirection direction = ForgeDirection.getOrientation(side);
      if (direction.offsetX == 0) {
        for (int dx = -1; dx <= 1; dx += 2) {
          connectedSides = connectedSides | BlockWallTeleporterBase.connectedSidesForDelta(side, world, x, y, z, dx, 0, 0);
        }
      }

      if (direction.offsetY == 0) {
        for (int dy = -1; dy <= 1; dy += 2) {
          connectedSides = connectedSides | BlockWallTeleporterBase.connectedSidesForDelta(side, world, x, y, z, 0, dy, 0);
        }
      }

      if (direction.offsetZ == 0) {
        for (int dz = -1; dz <= 1; dz += 2) {
          connectedSides = connectedSides | BlockWallTeleporterBase.connectedSidesForDelta(side, world, x, y, z, 0, 0, dz);
        }
      }

//      System.out.println(connectedSides);
      return icons[connectedSides];

    } else {
      Block block = Block.getBlockFromItem(mask.getItem());
      return block.getIcon(side, mask.getItemDamage());
    }
  }

  @Override
  public boolean isOpaqueCube() {
    return false;
  }

  @Override
  public boolean renderAsNormalBlock() {
    return false;
  }

  @Override
  public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
    TEWallTeleporterWall teleporterWall = (TEWallTeleporterWall) world.getTileEntity(x, y, z);
    if (teleporterWall != null && teleporterWall.hasWTNetwork() && teleporterWall.getWTNetwork().hasDestination() && teleporterWall.getWTNetwork().fuel > 0) return AxisAlignedBB.getBoundingBox(0, 0, 0, 0, 0, 0);
    return super.getCollisionBoundingBoxFromPool(world, x, y, z);
  }

  @Override
  public TileEntity createNewTileEntity(World world, int idk) {
    return new TEWallTeleporterWall();
  }

  @Override
  public boolean canPlaceBlockAt(World world, int x, int y, int z) {
    WallTeleporterNetwork networkTemp = null;
    for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
      TileEntity tileEntity = world.getTileEntity(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ);
      if (tileEntity instanceof IWallTeleporterBlock) {
        IWallTeleporterBlock teleporter = (IWallTeleporterBlock) tileEntity;
        if (teleporter.hasWTNetwork()) {
          if (networkTemp == null) {
            networkTemp = teleporter.getWTNetwork();
          } else if (networkTemp != teleporter.getWTNetwork()) {
            return false;
          }
        }
      }
    }
    return super.canPlaceBlockAt(world, x, y, z);
  }

  @Override
  public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
    TEWallTeleporterWall teleporterWall = (TEWallTeleporterWall) world.getTileEntity(x, y, z);

    if (!player.isSneaking() && teleporterWall.hasWTNetwork() && (player.getHeldItem() == null || !(player.getHeldItem().getItem() instanceof ItemBlock))) {
      TileEntity base = teleporterWall.getWTNetwork().base;
      FMLNetworkHandler.openGui(player, UnwrittenBlocks.instance, 0, world, base.xCoord, base.yCoord, base.zCoord);
      return true;
    }

    if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemBlock) {
      if (!(player.getHeldItem().isItemEqual(ItemRegister.wallStack) && teleporterWall.mask.isItemEqual(ItemRegister.wallStack))) {
        if (!world.isRemote) teleporterWall.setMask(player.getHeldItem().copy());
        return true;
      }
    }

    return false;
  }

  @Override
  public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
    TEWallTeleporterWall teleporterWall = (TEWallTeleporterWall) world.getTileEntity(x, y, z);
    teleporterWall.setIgnoreWT(true);
    if (teleporterWall.hasWTNetwork()) teleporterWall.getWTNetwork().refreshNetwork();
    super.breakBlock(world, x, y, z, block, meta);
  }

  @Override
  public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
    if (!world.isRemote) {
      if (entity instanceof EntityPlayerMP) {
        TEWallTeleporterWall teleporterWall = (TEWallTeleporterWall) world.getTileEntity(x, y, z);
        if (teleporterWall.hasWTNetwork()) teleporterWall.getWTNetwork().playerCollided(world, (EntityPlayerMP) entity);
      }
    }
    super.onEntityCollidedWithBlock(world, x, y, z, entity);
  }
}
