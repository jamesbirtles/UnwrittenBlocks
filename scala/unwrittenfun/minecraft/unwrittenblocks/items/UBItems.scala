package unwrittenfun.minecraft.unwrittenblocks.items

import net.minecraft.item.{EnumToolMaterial, EnumArmorMaterial, ItemStack, Item}
import cpw.mods.fml.common.registry.{GameRegistry, LanguageRegistry}
import net.minecraftforge.common.{EnumHelper, MinecraftForge}
import net.minecraft.block.Block
import unwrittenfun.minecraft.unwrittenblocks.recipes.InfuserRecipes
import cpw.mods.fml.client.registry.RenderingRegistry

/**
 * Mod: UnwrittenBlocks
 * Author: UnwrittenFun
 * License: Minecraft Mod Public License (Version 1.0.1)
 */
object UBItems {
  final var armourMatDark: EnumArmorMaterial = EnumHelper.addArmorMaterial("DARK", 50, Array(4, 10, 8, 4), 50)
  var armourDarkHelm: Item = null
  var armourDarkChest: Item = null
  var armourDarkLeggings: Item = null
  var armourDarkBoots: Item = null

  final var toolMatDark: EnumToolMaterial = EnumHelper.addToolMaterial("DARK", 3, 5000, 25.0F, 6.0F, 50)
  var swordDarkInfused: Item = null
  var pickDarkInfused: Item = null

  var pleatherBulb: Item = null
  var pleatherStrips: Item = null
  var gpsChip: Item = null
  var cobbleBall: Item = null
  var relayerTool: Item = null

  def registerItems() {
    pleatherBulb = new ItemPleatherBulb(PLEATHER_BULB_ID, PLEATHER_BULB_KEY)
    pleatherStrips = new ItemPleatherStrips(PLEATHER_STRIPS_ID, PLEATHER_STRIPS_KEY)
    gpsChip = new ItemGpsChip(GPS_CHIP_ID, GPS_CHIP_KEY)
    cobbleBall = new ItemCobbleBall(COBBLE_BALL_ID, COBBLE_BALL_KEY)
    relayerTool = new ItemRelayer(RELAYER_ID, RELAYER_KEY)

    armourDarkHelm = new ItemDarkArmour(DARK_ARMOUR_IDS(0), DARK_ARMOUR_KEYS(0), 0)
    armourDarkChest = new ItemDarkArmour(DARK_ARMOUR_IDS(1), DARK_ARMOUR_KEYS(1), 1)
    armourDarkLeggings = new ItemDarkArmour(DARK_ARMOUR_IDS(2), DARK_ARMOUR_KEYS(2), 2)
    armourDarkBoots = new ItemDarkArmour(DARK_ARMOUR_IDS(3), DARK_ARMOUR_KEYS(3), 3)

    swordDarkInfused = new ItemDarkSword(DARK_SWORD_ID, DARK_SWORD_KEY)
    pickDarkInfused = new ItemDarkPick(DARK_PICK_ID, DARK_PICK_KEY)
  }

  def registerNames() {
    LanguageRegistry.addName(new ItemStack(pleatherBulb, 1), PLEATHER_BULB_NAME)
    LanguageRegistry.addName(new ItemStack(pleatherStrips, 1), PLEATHER_STRIPS_NAME)
    LanguageRegistry.addName(new ItemStack(gpsChip, 1), GPS_CHIP_NAME)
    LanguageRegistry.addName(new ItemStack(gpsChip, 1, 1), GPS_CHIP_LINKED_NAME)
    LanguageRegistry.addName(new ItemStack(cobbleBall, 1, 0), COBBLE_BALL_NAMES(0))
    LanguageRegistry.addName(new ItemStack(cobbleBall, 1, 1), COBBLE_BALL_NAMES(1))
    LanguageRegistry.addName(new ItemStack(relayerTool, 1), RELAYER_NAME)

    LanguageRegistry.addName(new ItemStack(armourDarkHelm, 1), DARK_ARMOUR_NAMES(0))
    LanguageRegistry.addName(new ItemStack(armourDarkChest, 1), DARK_ARMOUR_NAMES(1))
    LanguageRegistry.addName(new ItemStack(armourDarkLeggings, 1), DARK_ARMOUR_NAMES(2))
    LanguageRegistry.addName(new ItemStack(armourDarkBoots, 1), DARK_ARMOUR_NAMES(3))

    LanguageRegistry.addName(new ItemStack(swordDarkInfused, 1), DARK_SWORD_NAME)
    LanguageRegistry.addName(new ItemStack(pickDarkInfused, 1), DARK_PICK_NAME)
  }

  def registerRecipes() {
    GameRegistry.addShapelessRecipe(new ItemStack(Item.leather, 1), pleatherStrips, pleatherStrips, pleatherStrips, pleatherStrips)
    GameRegistry.addShapelessRecipe(new ItemStack(Block.cobblestoneMossy, 5), new ItemStack(cobbleBall, 1, 0))
    GameRegistry.addShapelessRecipe(new ItemStack(Block.cobblestone, 5), new ItemStack(cobbleBall, 1, 1))

    GameRegistry.addRecipe(new ItemStack(gpsChip), "trt", "clc", "iri", 't'.asInstanceOf[Character], Block.torchRedstoneActive, 'l'.asInstanceOf[Character], Block.redstoneLampIdle, 'r'.asInstanceOf[Character], Item.redstone, 'i'.asInstanceOf[Character], Item.ingotIron, 'c'.asInstanceOf[Character], new ItemStack(Item.dyePowder, 1, 2))
    GameRegistry.addRecipe(new ItemStack(cobbleBall, 1, 0), " c ", "ccc", " c ", 'c'.asInstanceOf[Character], Block.cobblestoneMossy)
    GameRegistry.addRecipe(new ItemStack(cobbleBall, 1, 1), " c ", "ccc", " c ", 'c'.asInstanceOf[Character], Block.cobblestone)
    GameRegistry.addRecipe(new ItemStack(Item.blazeRod), "bb", "bb", "bb", 'b'.asInstanceOf[Character], Item.blazePowder)

    InfuserRecipes.addRecipe(cobbleBall.itemID, 0, new ItemStack(Item.enderPearl), 2000)
    InfuserRecipes.addRecipe(cobbleBall.itemID, 1, new ItemStack(Item.slimeBall), 1500)

    InfuserRecipes.addRecipe(Item.helmetDiamond.itemID, 0, new ItemStack(armourDarkHelm), 5000)
    InfuserRecipes.addRecipe(Item.plateDiamond.itemID, 0, new ItemStack(armourDarkChest), 5000)
    InfuserRecipes.addRecipe(Item.legsDiamond.itemID, 0, new ItemStack(armourDarkLeggings), 5000)
    InfuserRecipes.addRecipe(Item.bootsDiamond.itemID, 0, new ItemStack(armourDarkBoots), 5000)
    InfuserRecipes.addRecipe(Item.swordDiamond.itemID, 0, new ItemStack(swordDarkInfused), 3000)
    InfuserRecipes.addRecipe(Item.pickaxeDiamond.itemID, 0, new ItemStack(pickDarkInfused), 3000)
  }
}
