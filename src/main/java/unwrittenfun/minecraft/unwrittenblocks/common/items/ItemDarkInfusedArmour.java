package unwrittenfun.minecraft.unwrittenblocks.common.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import unwrittenfun.minecraft.unwrittenblocks.common.ModInfo;
import unwrittenfun.minecraft.unwrittenblocks.common.UnwrittenBlocks;

import java.util.List;

/**
 * Project: UnwrittenBlocks Author: UnwrittenFun Created: 29/08/2014.
 */
public class ItemDarkInfusedArmour extends ItemArmor {
  public ItemDarkInfusedArmour(int armourType) {
    super(ItemRegister.darkInfusedArmourMaterial, 4, armourType);

    setCreativeTab(UnwrittenBlocks.creativeTabUB);
    setUnlocalizedName(ItemRegister.DARK_INFUSED_ARMOUR_KEYS[armourType]);
    setTextureName(ModInfo.RESOURCE_LOCATION + ":" + ItemRegister.DARK_INFUSED_ARMOUR_KEYS[armourType]);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void addInformation(ItemStack stack, EntityPlayer player, List lines,
                             boolean bool) {
    lines.add(StatCollector.translateToLocal("unwrittenblocks.text.infusedFrom") + " " +
        StatCollector.translateToLocal("unwrittenblocks.text.armourType" + this.armorType));
    super.addInformation(stack, player, lines, bool);
  }

  @Override
  public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
    if (type == null) {
      return ModInfo.RESOURCE_LOCATION + ":textures/armour/darkInfusedArmour_layer" + (slot == 2 ? 2 : 1) + ".png";
    }

    return super.getArmorTexture(stack, entity, slot, type);
  }
}
