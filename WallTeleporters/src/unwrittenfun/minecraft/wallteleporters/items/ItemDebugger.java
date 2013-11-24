package unwrittenfun.minecraft.wallteleporters.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.world.World;
import unwrittenfun.minecraft.wallteleporters.blocks.tileentities.TileEntityWallTeleporter;
import unwrittenfun.minecraft.wallteleporters.client.gui.creativetabs.WTCreativeTabs;
import unwrittenfun.minecraft.wallteleporters.handlers.PacketHandler;
import unwrittenfun.minecraft.wallteleporters.info.ItemInfo;
import unwrittenfun.minecraft.wallteleporters.info.ModInfo;

import java.util.List;

/**
 * Mod: Wall Teleporters
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
public class ItemDebugger extends Item {
    public ItemDebugger(int id) {
        super(id);

        setCreativeTab(WTCreativeTabs.wtTab);
        setUnlocalizedName(ItemInfo.DEBUGGER_UNLOCAL_NAME);
        setMaxStackSize(1);
    }

    @Override
    public void registerIcons(IconRegister register) {
        itemIcon = register.registerIcon(ModInfo.TEXTURE_LOCATION + ":" + ItemInfo.DEBUGGER_ICON);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        list.add("§e§oUsed for debugging only.");
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
                                  float hitX, float hitY, float hitZ) {
        if (player.isSneaking()) {
            TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

            if (tileEntity instanceof TileEntityWallTeleporter) {
                TileEntityWallTeleporter teleporter = (TileEntityWallTeleporter) tileEntity;

                chatMultiblockInfo(player, teleporter, world.isRemote);
            }
        } else {
            PacketHandler.sendDebuggerUsePacket(x, y, z);
        }
        return true;
    }

    public static void chatMultiblockInfo(EntityPlayer player, TileEntityWallTeleporter teleporter, boolean isRemote) {
        player.sendChatToPlayer(ChatMessageComponent.func_111066_d(""));
        player.sendChatToPlayer(ChatMessageComponent.func_111066_d(
                "§e=== Wall Teleporter Multiblock (" + (isRemote ? "Client" : "Server") + ") ==="));
        player.sendChatToPlayer(ChatMessageComponent.func_111066_d(
                "§eType: " + (teleporter.multiblock.isController(teleporter) ? "Controller" : "Wall")));
        player.sendChatToPlayer(ChatMessageComponent.func_111066_d("§eWall Count: " + teleporter.multiblock.count()));
        if (teleporter.multiblock.hasDestination()) {
            player.sendChatToPlayer(ChatMessageComponent.func_111066_d(
                    "§eDestination: (" + teleporter.multiblock.destinationX + ", " +
                    teleporter.multiblock.destinationY + ", " + teleporter.multiblock.destinationZ + ")"));
        } else {
            player.sendChatToPlayer(ChatMessageComponent.func_111066_d("§eDestination: §oNone set"));
        }
        player.sendChatToPlayer(ChatMessageComponent.func_111066_d("§e====================================="));
    }
}
