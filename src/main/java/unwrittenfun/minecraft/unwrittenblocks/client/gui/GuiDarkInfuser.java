package unwrittenfun.minecraft.unwrittenblocks.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;
import unwrittenfun.minecraft.unwrittenblocks.client.gui.components.GuiSidesButton;
import unwrittenfun.minecraft.unwrittenblocks.client.gui.components.HelpItem;
import unwrittenfun.minecraft.unwrittenblocks.common.ModInfo;
import unwrittenfun.minecraft.unwrittenblocks.common.containers.ContainerDarkInfuser;
import unwrittenfun.minecraft.unwrittenblocks.common.tileEntities.TEDarkInfuser;

import java.util.ArrayList;

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun
 * Created: 06/11/2014.
 */
public class GuiDarkInfuser extends GuiUnwrittenBlocks {
  public static ResourceLocation    texture  =
      new ResourceLocation(ModInfo.RESOURCE_LOCATION, "textures/gui/darkInfuserGui.png");
  public static ArrayList<HelpItem> helpList = new ArrayList<HelpItem>();

  static {
    helpList.add(new HelpItem("unwrittenblocks.help.input", 44, 36, 16, 16));
    helpList.add(new HelpItem("unwrittenblocks.help.output", 116, 36, 16, 16));
    helpList.add(new HelpItem("unwrittenblocks.help.upgrades", 152, 8, 16, 16));

    helpList.add(new HelpItem("unwrittenblocks.help.io.bottom", 43, 7, 16, 16));
    helpList.add(new HelpItem("unwrittenblocks.help.io.top", 25, 7, 16, 16));
    helpList.add(new HelpItem("unwrittenblocks.help.io.north", 7, 7, 16, 16));
    helpList.add(new HelpItem("unwrittenblocks.help.io.east", 7, 25, 16, 16));
    helpList.add(new HelpItem("unwrittenblocks.help.io.south", 7, 43, 16, 16));
    helpList.add(new HelpItem("unwrittenblocks.help.io.west", 7, 61, 16, 16));
  }

  public InventoryPlayer playerInventory;
  public TEDarkInfuser   darkInfuser;

  public GuiDarkInfuser(InventoryPlayer playerInventory, TEDarkInfuser darkInfuser) {
    super(new ContainerDarkInfuser(playerInventory, darkInfuser));
    this.playerInventory = playerInventory;
    this.darkInfuser = darkInfuser;

    xSize = 176;
    ySize = 166;
  }

  @Override
  public void initGui() {
    super.initGui();

    initButtons();
  }

  @Override
  protected void drawGuiContainerForegroundLayer(int x, int y) {
    fontRendererObj.drawString(darkInfuser.getInventoryName(), 28, 62, 0x373737);

    if (GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSneak)) {
      fontRendererObj
          .drawString(StatCollector.translateToLocal("unwrittenblocks.gui.darkInfuser.sneakHelp"), 28, 72, 0x6a6a6a);
    } else {
      fontRendererObj.drawString(StatCollector.translateToLocal("unwrittenblocks.gui.sneakForHelp"), 28, 72, 0x6a6a6a);
    }

    if (darkInfuser.getSpeedMultiplier() > 1) {
      fontRendererObj.drawString("Multiplier x" + darkInfuser.getSpeedMultiplier(), 80, 12, 0x6a6a6a);
    }

    GL11.glColor4f(1, 1, 1, 1);
    Minecraft.getMinecraft().renderEngine.bindTexture(texture);

    drawTexturedModalRect(61, 7, 77, 7, 16, 16);

    drawTexturedModalRect(62, 31, xSize, 0, darkInfuser.infuserProgress, 25);

    if (GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSneak)) {
      for (HelpItem helpItem : helpList) {
        helpItem.renderHelp(this, x - guiLeft, y - guiTop);
      }
    }
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
    GL11.glColor4f(1, 1, 1, 1);
    Minecraft.getMinecraft().renderEngine.bindTexture(texture);
    drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
  }

  @Override
  public void updateScreen() {
    super.updateScreen();

    boolean updateButtons = false;
    for (int side = 0; side < darkInfuser.ioSides.length; side++) {
      updateButtons = darkInfuser.ioSides[side] != ((GuiSidesButton) buttonList.get(side)).io;
    }
    if (updateButtons) initButtons();
  }

  @SuppressWarnings("unchecked")
  private void initButtons() {
    buttonList.clear();

    buttonList.add(
        new GuiSidesButton(0, ForgeDirection.DOWN.ordinal(), darkInfuser.ioSides[ForgeDirection.DOWN.ordinal()],
                           guiLeft + 43, guiTop + 7));
    buttonList.add(new GuiSidesButton(1, ForgeDirection.UP.ordinal(), darkInfuser.ioSides[ForgeDirection.UP.ordinal()],
                                      guiLeft + 25, guiTop + 7));
    buttonList.add(
        new GuiSidesButton(2, ForgeDirection.NORTH.ordinal(), darkInfuser.ioSides[ForgeDirection.NORTH.ordinal()],
                           guiLeft + 7, guiTop + 7));
    buttonList.add(
        new GuiSidesButton(3, ForgeDirection.EAST.ordinal(), darkInfuser.ioSides[ForgeDirection.EAST.ordinal()],
                           guiLeft + 7, guiTop + 25));
    buttonList.add(
        new GuiSidesButton(4, ForgeDirection.SOUTH.ordinal(), darkInfuser.ioSides[ForgeDirection.SOUTH.ordinal()],
                           guiLeft + 7, guiTop + 43));
    buttonList.add(
        new GuiSidesButton(5, ForgeDirection.WEST.ordinal(), darkInfuser.ioSides[ForgeDirection.WEST.ordinal()],
                           guiLeft + 7, guiTop + 61));
  }

  @Override
  protected void actionPerformed(GuiButton button) {
    if (button instanceof GuiSidesButton) {
      ((GuiSidesButton) button).buttonClicked(darkInfuser);
    }
  }
}
