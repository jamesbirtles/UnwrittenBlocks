package unwrittenfun.minecraft.unwrittenblocks.client.models

import net.minecraft.client.model.{ModelBase, ModelRenderer}
import net.minecraft.client.renderer.entity.{RenderItem, RenderManager}
import org.lwjgl.opengl.GL11
import unwrittenfun.minecraft.unwrittenblocks.blocks.tileentities.TileEntityDarkInfuser

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun 
 * Created: 10/08/2014.
 */
class ModelDarkInfuser extends ModelBase {
  textureWidth = 128
  textureHeight = 64

  val infuser: ModelRenderer = new ModelRenderer(this)

  val pedBaseBot = new ModelRenderer(this, 0, 0)
  pedBaseBot.addBox(-6, -14, -6, 12, 2, 12)
  infuser.addChild(pedBaseBot)

  val pedBaseTop = new ModelRenderer(this, 0, 14)
  pedBaseTop.addBox(-4, -12, -4, 8, 2, 8)
  infuser.addChild(pedBaseTop)

  val pedLeg = new ModelRenderer(this, 48, 0)
  pedLeg.addBox(-3, -10, -3, 6, 20, 6)
  infuser.addChild(pedLeg)

  val pedTopBot = new ModelRenderer(this, 0, 40)
  pedTopBot.addBox(-5, 10, -5, 10, 2, 10)
  infuser.addChild(pedTopBot)

  val pedTopTop = new ModelRenderer(this, 0, 24)
  pedTopTop.addBox(-7, 12, -7, 14, 2, 14)
  infuser.addChild(pedTopTop)

  def render(darkInfuser: TileEntityDarkInfuser, mult: Float) {
    infuser.render(mult)

    if (darkInfuser != null && darkInfuser.itemEntity != null) {
      GL11.glPushMatrix()
      GL11.glTranslatef(0F, 1F, 0F)
      RenderManager.instance.renderEntityWithPosYaw(darkInfuser.itemEntity, 0D, 0D, 0D, 0F, darkInfuser.itemEntity.rotationYaw)
      RenderItem.renderInFrame = false
      GL11.glPopMatrix()
    }
  }
}
