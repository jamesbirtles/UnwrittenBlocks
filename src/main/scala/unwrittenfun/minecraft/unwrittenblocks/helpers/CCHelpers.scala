package unwrittenfun.minecraft.unwrittenblocks.helpers

/**
 * Project: UnwrittenBlocks
 * Author: UnwrittenFun 
 * Created: 30/07/2014.
 */
object CCHelpers {
  def nullArgs: Array[Object] = new Array[Object](0)
  def argsOfOne(obj: Any): Array[Object] = Array(obj.asInstanceOf[Object])
  def argsOfTwo(obj1: Any, obj2: Any): Array[Object] = Array(obj1.asInstanceOf[Object], obj2.asInstanceOf[Object])
}
