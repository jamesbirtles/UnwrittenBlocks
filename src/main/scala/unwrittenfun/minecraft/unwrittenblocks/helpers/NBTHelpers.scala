package unwrittenfun.minecraft.unwrittenblocks.helpers

import java.util

import net.minecraft.nbt._

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun 
 * Created: 30/07/2014.
 */
object NBTHelpers {
  def compoundToMap(compound: NBTTagCompound): util.HashMap[Object, Object] = {
    import scala.collection.JavaConversions._

    val map: util.HashMap[Object, Object] = new util.HashMap[Object, Object]()
    val tags = compound.getTags
    for (tag <- tags) {
      tag match {
        case nbtTag: NBTBase => map.put(nbtTag.getName, tagToObject(nbtTag))
        case _ =>
      }
    }
    map
  }

  def tagListToMap(tagList: NBTTagList): util.HashMap[Object, Object] = {
    val map: util.HashMap[Object, Object] = new util.HashMap[Object, Object]()
    var i: Int = 0
    while (i < tagList.tagCount()) {
      val nbtTag: NBTBase = tagList.tagAt(i)
      map.put(i.asInstanceOf[Integer], tagToObject(nbtTag))
      i = i + 1
    }
    map
  }

  def tagToObject(tag: NBTBase): Object = {
    tag match {
      case tagCompound: NBTTagCompound => compoundToMap(tagCompound)
      case tagList: NBTTagList => tagListToMap(tagList)
      case _ => tag.toString
    }
  }
}
