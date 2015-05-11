package unwrittenfun.minecraft.unwrittenblocks.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;
import unwrittenfun.minecraft.unwrittenblocks.client.gui.components.GuiAutoEjectButton;
import unwrittenfun.minecraft.unwrittenblocks.common.ModInfo;
import unwrittenfun.minecraft.unwrittenblocks.common.containers.ContainerRefulgentInfuser;
import unwrittenfun.minecraft.unwrittenblocks.common.tileEntities.TERefulgentFabricator;

/**
 * Author: James Birtles
 */
public class GuiRefulgentFabricator extends GuiContainer {
  public static ResourceLocation texture = new ResourceLocation(ModInfo.RESOURCE_LOCATION, "textures/gui/refulgentFabricator.png");
  public TERefulgentFabricator fabricator;

  public GuiRefulgentFabricator(InventoryPlayer playerInventory, TERefulgentFabricator fabricator) {
    super(new ContainerRefulgentInfuser(playerInventory, fabricator));

    this.fabricator = fabricator;
    xSize = 176;
    ySize = 166;
  }

  @Override
  protected void drawGuiContainerForegroundLayer(int mX, int mY) {
    if (mX >= guiLeft + 80 && mX <= guiLeft + 96 && mY >= guiTop + 50 && mY <= guiTop + 66) {
      String ctrlString = "CTRL-Click to take last stack";
      fontRendererObj.drawString(ctrlString, 87 - fontRendererObj.getStringWidth(ctrlString) / 2, 72, 4210752);
    } else if (mX >= guiLeft - 15 && mX <= guiLeft && mY >= guiTop + 13 && mY <= guiTop + 28) {
      String string = "Eject Up";
      fontRendererObj.drawString(string, 87 - fontRendererObj.getStringWidth(string) / 2, 72, 4210752);
    } else if (mX >= guiLeft - 15 && mX <= guiLeft && mY >= guiTop + 31 && mY <= guiTop + 46) {
      String string = "Eject Down";
      fontRendererObj.drawString(string, 87 - fontRendererObj.getStringWidth(string) / 2, 72, 4210752);
    } else if (mX >= guiLeft - 15 && mX <= guiLeft && mY >= guiTop + 49 && mY <= guiTop + 64) {
      String string = "Eject North";
      fontRendererObj.drawString(string, 87 - fontRendererObj.getStringWidth(string) / 2, 72, 4210752);
    } else if (mX >= guiLeft - 15 && mX <= guiLeft && mY >= guiTop + 67 && mY <= guiTop + 82) {
      String string = "Eject East";
      fontRendererObj.drawString(string, 87 - fontRendererObj.getStringWidth(string) / 2, 72, 4210752);
    } else if (mX >= guiLeft - 15 && mX <= guiLeft && mY >= guiTop + 85 && mY <= guiTop + 100) {
      String string = "Eject South";
      fontRendererObj.drawString(string, 87 - fontRendererObj.getStringWidth(string) / 2, 72, 4210752);
    } else if (mX >= guiLeft - 15 && mX <= guiLeft && mY >= guiTop + 103 && mY <= guiTop + 118) {
      String string = "Eject West";
      fontRendererObj.drawString(string, 87 - fontRendererObj.getStringWidth(string) / 2, 72, 4210752);
    } else if (mX >= guiLeft - 19 && mX <= guiLeft + 2 && mY >= guiTop + 8 && mY <= guiTop + 124) {
      String string = "Auto Eject Items";
      fontRendererObj.drawString(string, 87 - fontRendererObj.getStringWidth(string) / 2, 72, 4210752);
    } else {
      String nameString = "Refulgent Fabricator";
      fontRendererObj.drawString(nameString, 87 - fontRendererObj.getStringWidth(nameString) / 2, 72, 4210752);
    }
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
    GL11.glColor4f(1, 1, 1, 1);
    Minecraft.getMinecraft().renderEngine.bindTexture(texture);
    drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

    // IO
    drawTexturedModalRect(guiLeft - 19, guiTop + 8, xSize, 0, 22, 116);
  }

  @Override
  public void initGui() {
    super.initGui();

    initButtons();
  }

  @Override
  public void updateScreen() {
    super.updateScreen();

    boolean updateButtons = false;
    for (int side = 0; side < fabricator.ioSides.length; side++) {
      updateButtons = fabricator.ioSides[side] != ((GuiAutoEjectButton) buttonList.get(side)).io;
    }
    if (updateButtons) initButtons();
  }

  @SuppressWarnings("unchecked")
  private void initButtons() {
    buttonList.clear();

    buttonList.add(new GuiAutoEjectButton(0, ForgeDirection.DOWN.ordinal(), fabricator.ioSides[ForgeDirection.DOWN.ordinal()], guiLeft - 15, guiTop + 31));
    buttonList.add(new GuiAutoEjectButton(1, ForgeDirection.UP.ordinal(), fabricator.ioSides[ForgeDirection.UP.ordinal()], guiLeft - 15, guiTop + 13));
    buttonList.add(new GuiAutoEjectButton(2, ForgeDirection.NORTH.ordinal(), fabricator.ioSides[ForgeDirection.NORTH.ordinal()], guiLeft - 15, guiTop + 49));
    buttonList.add(new GuiAutoEjectButton(3, ForgeDirection.EAST.ordinal(), fabricator.ioSides[ForgeDirection.EAST.ordinal()], guiLeft - 15, guiTop + 67));
    buttonList.add(new GuiAutoEjectButton(4, ForgeDirection.SOUTH.ordinal(), fabricator.ioSides[ForgeDirection.SOUTH.ordinal()], guiLeft - 15, guiTop + 85));
    buttonList.add(new GuiAutoEjectButton(5, ForgeDirection.WEST.ordinal(), fabricator.ioSides[ForgeDirection.WEST.ordinal()], guiLeft - 15, guiTop + 103));
  }

  @Override
  protected void actionPerformed(GuiButton button) {
    if (button instanceof GuiAutoEjectButton) {
      ((GuiAutoEjectButton) button).buttonClicked(fabricator);
    }
  }
}
