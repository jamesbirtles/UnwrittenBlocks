package unwrittenfun.minecraft.unwrittenblocks.client.renderers

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.GL11
import unwrittenfun.minecraft.unwrittenblocks
import unwrittenfun.minecraft.unwrittenblocks.blocks.tileentities.TileEntityDarkInfuser
import unwrittenfun.minecraft.unwrittenblocks.client.models.ModelDarkInfuser

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun 
 * Created: 09/08/2014.
 */
object TESRDarkInfuser {
  var texture: ResourceLocation = new ResourceLocation(unwrittenblocks.TEXTURE_LOCATION, "textures/models/darkInfuser.png")
}

class TESRDarkInfuser(model: ModelDarkInfuser) extends TileEntitySpecialRenderer {
  override def renderTileEntityAt(tileEntity: TileEntity, x: Double, y: Double, z: Double, partialTickTime: Float): Unit = {
    tileEntity match {
      case darkInfuser: TileEntityDarkInfuser => renderDarkInfuser(darkInfuser, x, y, z, partialTickTime)
      case _ =>
    }
  }

  def renderDarkInfuser(infuser: TileEntityDarkInfuser, x: Double, y: Double, z: Double, partialTickTime: Float): Unit = {
    GL11.glPushMatrix()

    GL11.glTranslatef(x.asInstanceOf[Float] + 0.5F, y.asInstanceOf[Float] + 0.5F, z.asInstanceOf[Float] + 0.5F)
    GL11.glScalef(0.5F, 0.5F, 0.5F)
    Minecraft.getMinecraft.renderEngine.bindTexture(TESRDarkInfuser.texture)
    model.render(infuser, 0.0625F)

    GL11.glPopMatrix()
  }
}
