package unwrittenfun.minecraft.wallteleporters.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import unwrittenfun.minecraft.wallteleporters.info.ModInfo;

/**
 * Mod: Wall Teleporters
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
public class GuiIconButton extends GuiButton {
    public static ResourceLocation texture = new ResourceLocation(ModInfo.TEXTURE_LOCATION,
            "textures/gui/gui.png");
    public static ResourceLocation iconSheet = new ResourceLocation(ModInfo.TEXTURE_LOCATION,
            "textures/gui/icons.png");

    public int uX;
    public int uY;

    public GuiIconButton(int id, int x, int y, int uX, int uY) {
        super(id, x, y, 12, 12, "");

        this.uX = uX;
        this.uY = uY;
    }

    @Override
    public void drawButton(Minecraft minecraft, int mX, int mY) {
        if (drawButton) {
            minecraft.func_110434_K().func_110577_a(texture);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            drawTexturedModalRect(xPosition, yPosition, 0, 0, 12, 12);

            GL11.glPushMatrix();
            GL11.glScalef(0.5F, 0.5F, 1F);
            minecraft.func_110434_K().func_110577_a(iconSheet);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            drawTexturedModalRect((xPosition + 2) * 2, (yPosition + 2) * 2, uX, uY, 16, 16);
            GL11.glPopMatrix();
        }
    }
}
