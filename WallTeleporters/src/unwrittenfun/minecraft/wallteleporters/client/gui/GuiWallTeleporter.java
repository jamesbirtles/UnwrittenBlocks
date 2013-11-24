package unwrittenfun.minecraft.wallteleporters.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import unwrittenfun.minecraft.wallteleporters.blocks.multiblocks.MultiblockWallTeleporter;
import unwrittenfun.minecraft.wallteleporters.gui.containers.ContainerWallTeleporter;
import unwrittenfun.minecraft.wallteleporters.handlers.PacketHandler;
import unwrittenfun.minecraft.wallteleporters.info.ModInfo;

/**
 * Mod: Wall Teleporters
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
public class GuiWallTeleporter extends GuiContainer {
    public static ResourceLocation texture = new ResourceLocation(ModInfo.TEXTURE_LOCATION,
            "textures/gui/wall_teleporter_gui.png");

    public MultiblockWallTeleporter multiblock;

    public GuiWallTeleporter(InventoryPlayer invPlayer, MultiblockWallTeleporter multiblock) {
        super(new ContainerWallTeleporter(invPlayer, multiblock));

        this.multiblock = multiblock;

        xSize = 176;
        ySize = 157;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GL11.glColor4f(1, 1, 1, 1);

        Minecraft.getMinecraft().func_110434_K().func_110577_a(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        int titleWidth = fontRenderer.getStringWidth(multiblock.getInvName());
        fontRenderer.drawString(multiblock.getInvName(), 85 - (titleWidth / 2), 10, 0x404040);


        if (multiblock.hasDestination()) {
            int infoDy = 25;
            int infoSpacing = 10;

            String worldString = multiblock.destinationWorldName;
            String coordString = "(" + (int) multiblock.destinationX + ", " + (int) multiblock.destinationY + ", " +
                                 (int) multiblock.destinationZ + ")";
            int worldWidth = fontRenderer.getStringWidth(worldString);
            int coordWidth = fontRenderer.getStringWidth(coordString);

            fontRenderer.drawString(worldString, 85 - (worldWidth / 2), infoDy, 0x404040);
            fontRenderer.drawString(coordString, 85 - (coordWidth / 2), infoDy + infoSpacing, 0x404040);
        }
    }

    @Override
    public void initGui() {
        super.initGui();

        initButtons();
    }

    private boolean oldLocked = false;
    private boolean oldRot    = false;

    private void initButtons() {
        buttonList.clear();
        buttonList.add(new GuiIconButton(0, guiLeft + 7, guiTop + 7, 0, 0));

        oldLocked = multiblock.isLocked();
        if (oldLocked) {
            buttonList.add(new GuiIconButton(1, guiLeft + 7, guiTop + 21, 16, 0));
        } else {
            buttonList.add(new GuiIconButton(2, guiLeft + 7, guiTop + 21, 32, 0));
        }

        oldRot = multiblock.shouldUseRotation();
        if (oldRot) {
            buttonList.add(new GuiIconButton(3, guiLeft + 7, guiTop + 35, 48, 0));
        } else {
            buttonList.add(new GuiIconButton(4, guiLeft + 7, guiTop + 35, 64, 0));
        }
    }

    @Override
    public void updateScreen() {
        if (oldLocked != multiblock.isLocked()) {
            initButtons();
        }

        if (oldRot != multiblock.shouldUseRotation()) {
            initButtons();
        }

        super.updateScreen();
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        PacketHandler.sendButtonPacket((byte) 0, (byte) button.id);
    }
}
