package unwrittenfun.minecraft.wallteleporters.blocks;

import cpw.mods.fml.common.network.FMLNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import unwrittenfun.minecraft.wallteleporters.WallTeleporters;
import unwrittenfun.minecraft.wallteleporters.blocks.tileentities.TileEntityWallTeleporter;
import unwrittenfun.minecraft.wallteleporters.client.gui.creativetabs.WTCreativeTabs;
import unwrittenfun.minecraft.wallteleporters.info.BlockInfo;
import unwrittenfun.minecraft.wallteleporters.info.ModInfo;

/**
 * Mod: Wall Teleporters
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
public class BlockWallTeleporter extends BlockContainer {
    public BlockWallTeleporter(int id) {
        super(id, Material.iron);

        setUnlocalizedName(BlockInfo.WT_UNLOCAL_NAME);
        setHardness(2F);
        setCreativeTab(WTCreativeTabs.wtTab);
    }

    @Override
    public void registerIcons(IconRegister register) {
        blockIcon = register.registerIcon(ModInfo.TEXTURE_LOCATION + ":" + BlockInfo.WT_ICON_NAME);
    }

    @Override
    public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side) {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

        if (tileEntity instanceof TileEntityWallTeleporter) {
            TileEntityWallTeleporter teleporter = ((TileEntityWallTeleporter) tileEntity);

            if (teleporter.mask[0] != 0) {
                Block maskBlock = Block.blocksList[teleporter.mask[0]];
                return maskBlock.getIcon(side, teleporter.mask[1]);
            }
        }

        return blockIcon;
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileEntityWallTeleporter();
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return AxisAlignedBB.getAABBPool().getAABB(0, 0, 0, 0, 0, 0);
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
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        if (!world.isRemote) {
            if (entity instanceof EntityPlayerMP) {
                EntityPlayerMP player = (EntityPlayerMP) entity;
                TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

                if (tileEntity instanceof TileEntityWallTeleporter) {
                    TileEntityWallTeleporter teleporter = (TileEntityWallTeleporter) tileEntity;
                    teleporter.teleportPlayer(player);
                }
            }
        }
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        if (!world.isRemote) {
            TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

            if (tileEntity instanceof TileEntityWallTeleporter) {
                TileEntityWallTeleporter teleporter = (TileEntityWallTeleporter) tileEntity;
                teleporter.notifyNeighboursOfConnectionChanged();
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player,
                                    int side, float hitX, float hitY, float hitZ) {
        boolean result = true;
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

        if (tileEntity instanceof TileEntityWallTeleporter) {
            TileEntityWallTeleporter teleporter = ((TileEntityWallTeleporter) tileEntity);

            ItemStack held = player.inventory.getCurrentItem();
            if (!teleporter.multiblock.isLocked() && held != null && held.getItem() instanceof ItemBlock) {
                if (((ItemBlock) held.getItem()).getBlockID() == WTBlocks.wallTeleporter.blockID &&
                    teleporter.mask[0] == 0) {
                    result = false;
                } else if (!world.isRemote) {
                    teleporter.setMask(((ItemBlock) held.getItem()).getBlockID(), held.getItemDamage());
                }
            } else {
                FMLNetworkHandler.openGui(player, WallTeleporters.instance, 0, world, x, y, z);
            }
        }

        return result;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int id, int meta) {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if (tileEntity instanceof TileEntityWallTeleporter) {
            IInventory inventory = (IInventory) tileEntity;

            for (int i = 0; i < inventory.getSizeInventory(); i++) {
                ItemStack stack = inventory.getStackInSlotOnClosing(i);

                if (stack != null) {
                    float spawnX = x + world.rand.nextFloat();
                    float spawnY = y + world.rand.nextFloat();
                    float spawnZ = z + world.rand.nextFloat();

                    EntityItem droppedItem = new EntityItem(world, spawnX, spawnY, spawnZ, stack);

                    float mult = 0.05F;

                    droppedItem.motionX = (-0.5F + world.rand.nextFloat()) * mult;
                    droppedItem.motionY = (4 + world.rand.nextFloat()) * mult;
                    droppedItem.motionZ = (-0.5F + world.rand.nextFloat()) * mult;

                    world.spawnEntityInWorld(droppedItem);
                }
            }
        }

        super.breakBlock(world, x, y, z, id, meta);
    }
}
