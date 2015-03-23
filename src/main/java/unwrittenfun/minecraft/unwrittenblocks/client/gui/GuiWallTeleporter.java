package unwrittenfun.minecraft.unwrittenblocks.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import unwrittenfun.minecraft.unwrittenblocks.common.ModInfo;
import unwrittenfun.minecraft.unwrittenblocks.common.containers.ContainerWallTeleporter;
import unwrittenfun.minecraft.unwrittenblocks.common.network.NetworkRegister;
import unwrittenfun.minecraft.unwrittenblocks.common.network.messages.TileEntityIntegerMessage;
import unwrittenfun.minecraft.unwrittenblocks.common.tileEntities.TEWallTeleporterBase;

/**
 * Author: James Birtles
 */
public class GuiWallTeleporter extends GuiUnwrittenBlocks {
  public static ResourceLocation texture = new ResourceLocation(ModInfo.RESOURCE_LOCATION, "textures/gui/wallTeleporterGui.png");
  public TEWallTeleporterBase teleporter;

  public GuiWallTeleporter(InventoryPlayer playerInventory, TEWallTeleporterBase teleporterBase) {
    super(new ContainerWallTeleporter(playerInventory, teleporterBase));

    teleporter = teleporterBase;
    xSize = 176;
    ySize = 166;
  }

  @Override
  public void initGui() {
    super.initGui();
    initButtons();
  }

  @Override
  protected void drawGuiContainerForegroundLayer(int mx, int my) {
    fontRendererObj.drawString(teleporter.getInventoryName(), 28, 62, 0x373737);

    if (GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSneak)) {
      fontRendererObj.drawString(StatCollector.translateToLocal("unwrittenblocks.gui.sneakHelp"), 28, 72, 0x6a6a6a);
    } else {
      fontRendererObj.drawString(StatCollector.translateToLocal("unwrittenblocks.gui.sneakForHelp"), 28, 72, 0x6a6a6a);
    }

    if (teleporter.getWTNetwork().hasDestination()) {
      float[] destinationData = teleporter.getWTNetwork().destinationData;
      String worldName = teleporter.getWTNetwork().destinationName;
      String coords = "(" + (int) destinationData[0] + ", " + (int) destinationData[1] + ", " + (int) destinationData[2] + ")";
      String yaw = "Yaw: " + (int) destinationData[3];
      fontRendererObj.drawString(worldName, 88 - fontRendererObj.getStringWidth(worldName)/2, 15, 0x404040);
      fontRendererObj.drawString(coords, 88 - fontRendererObj.getStringWidth(coords)/2, 27, 0x404040);
      fontRendererObj.drawString(yaw, 88 - fontRendererObj.getStringWidth(yaw)/2, 39, 0x404040);
    }
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
    GL11.glColor4f(1, 1, 1, 1);
    Minecraft.getMinecraft().renderEngine.bindTexture(texture);
    drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
  }

  @SuppressWarnings("unchecked")
  private void initButtons() {
    buttonList.clear();

    buttonList.add(new GuiButton(0, guiLeft + 151, guiTop + 28, 18, 18, "<-"));
  }

  @Override
  protected void actionPerformed(GuiButton button) {
    NetworkRegister.wrapper.sendToServer(TileEntityIntegerMessage.messageFrom(teleporter.getWorldObj(), teleporter.xCoord, teleporter.yCoord, teleporter.zCoord, 0, button.id));
  }
}
