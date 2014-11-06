package unwrittenfun.minecraft.unwrittenblocks.client.renderers;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import unwrittenfun.minecraft.unwrittenblocks.client.models.ModelDarkInfuser;

/**
 * Project: UnwrittenBlocks Author: UnwrittenFun Created: 04/11/2014.
 */
public class IRDarkInfuser implements IItemRenderer {
  public ModelDarkInfuser model;

  public IRDarkInfuser(ModelDarkInfuser model) {
    this.model = model;
  }

  @Override public boolean handleRenderType(ItemStack item, ItemRenderType type) {
    return true;
  }

  @Override public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
    return true;
  }

  @Override public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
    GL11.glPushMatrix();

    switch (type) {
      case EQUIPPED_FIRST_PERSON:
        GL11.glTranslatef(0F, 0.8F, 0.6F);
        break;
      case EQUIPPED:
        GL11.glTranslatef(0.6F, 0.5F, 0.5F);
        break;
      case INVENTORY:
        GL11.glRotatef(270, 0F, 1F, 0F);
        break;
    }

    GL11.glScalef(0.5F, 0.5F, 0.5F);

    Minecraft.getMinecraft().renderEngine.bindTexture(TESRDarkInfuser.texture);
    model.render(0.0625F);

    GL11.glPopMatrix();
  }
}
