package unwrittenfun.minecraft.unwrittenblocks.client.renderers

import net.minecraft.client.Minecraft
import net.minecraft.item.ItemStack
import net.minecraftforge.client.IItemRenderer
import net.minecraftforge.client.IItemRenderer.{ItemRendererHelper, ItemRenderType}
import org.lwjgl.opengl.GL11
import unwrittenfun.minecraft.unwrittenblocks.client.models.ModelDarkInfuser

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun 
 * Created: 11/08/2014.
 */
class IRDarkInfuser(model: ModelDarkInfuser) extends IItemRenderer {
  override def handleRenderType(item: ItemStack, renderType: ItemRenderType): Boolean = true

  override def shouldUseRenderHelper(renderType: ItemRenderType, item: ItemStack, helper: ItemRendererHelper): Boolean = true

  override def renderItem(renderType: ItemRenderType, item: ItemStack, data: AnyRef*) {
    GL11.glPushMatrix()

    renderType match {
      case ItemRenderType.EQUIPPED_FIRST_PERSON =>
        GL11.glTranslatef(0F, 0.8F, 0.6F)
      case ItemRenderType.EQUIPPED =>
        GL11.glTranslatef(0.6F, 0.5F, 0.5F)
      case ItemRenderType.INVENTORY =>
        GL11.glRotatef(270, 0F, 1F, 0F)
      case _ =>
    }

    GL11.glScalef(0.5F, 0.5F, 0.5F)

    Minecraft.getMinecraft.renderEngine.bindTexture(TESRDarkInfuser.texture)
    model.render(null, 0.0625F)

    GL11.glPopMatrix()
  }
}
