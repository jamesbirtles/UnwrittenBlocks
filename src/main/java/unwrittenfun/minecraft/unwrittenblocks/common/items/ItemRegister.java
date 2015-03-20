package unwrittenfun.minecraft.unwrittenblocks.common.items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import unwrittenfun.minecraft.unwrittenblocks.common.recipes.InfuserRecipes;

/**
 * Project: UnwrittenBlocks Author: UnwrittenFun Created: 29/08/2014.
 */

public class ItemRegister {
  public static final ItemArmor.ArmorMaterial darkInfusedArmourMaterial =
      EnumHelper.addArmorMaterial("DARK_INFUSED", 50, new int[] {4, 10, 8, 4}, 50);
  public static final Item.ToolMaterial       darkInfusedToolMaterial   =
      EnumHelper.addToolMaterial("DARK", 3, 5000, 25.0F, 6.0F, 50);

  public static final String[] DARK_INFUSED_ARMOUR_KEYS =
      new String[] {"darkInfusedHelm", "darkInfusedChest", "darkInfusedLeggings", "darkInfusedBoots"};

  public static final String DARK_INFUSED_SWORD_KEY = "darkInfusedSword";
  public static final String DARK_INFUSED_PICK_KEY = "darkInfusedPick";
  public static final String DARK_INFUSED_AXE_KEY  = "darkInfusedAxe";
  public static final String DARK_INFUSED_SPADE_KEY = "darkInfusedSpade";
  public static final String DARK_INFUSED_HOE_KEY  = "darkInfusedHoe";

  public static final String DARK_INFUSED_DIAMOND = "darkInfusedDiamond";
  public static final String GPS_CHIP_KEY        = "gpsChip";
  public static final String STORAGE_BALL_KEY    = "storageBall";
  public static final String PLEATHER_STRIPS_KEY = "pleatherStrips";
  public static final String PLEATHER_BULB_KEY   = "pleatherBulb";

  public static final String   UPGRADE_KEY   = "upgrade";
  public static final String[] UPGRADE_TYPES = new String[] {"upgradeBasic", "upgradeSpeed"};

  public static ItemDarkInfusedArmour darkInfusedHelm;
  public static ItemDarkInfusedArmour darkInfusedChest;
  public static ItemDarkInfusedArmour darkInfusedLeggings;
  public static ItemDarkInfusedArmour darkInfusedBoots;

  public static ItemDarkInfusedSword darkInfusedSword;
  public static ItemDarkInfusedPick darkInfusedPick;
  public static ItemDarkInfusedAxe  darkInfusedAxe;
  public static ItemDarkInfusedSpade darkInfusedSpade;
  public static ItemDarkInfusedHoe  darkInfusedHoe;

  public static ItemDarkInfusedDiamond darkInfusedDiamond;
  public static ItemGPSChip        gpsChip;
  public static ItemStorageBall    storageBall;
  public static ItemUpgrade        upgrade;
  public static ItemPleatherStrips pleatherStrips;
  public static ItemPleatherBulb   pleatherBulb;

  public static void registerItems() {
    darkInfusedHelm = new ItemDarkInfusedArmour(0);
    darkInfusedChest = new ItemDarkInfusedArmour(1);
    darkInfusedLeggings = new ItemDarkInfusedArmour(2);
    darkInfusedBoots = new ItemDarkInfusedArmour(3);

    darkInfusedSword = new ItemDarkInfusedSword(DARK_INFUSED_SWORD_KEY);
    darkInfusedPick = new ItemDarkInfusedPick(DARK_INFUSED_PICK_KEY);
    darkInfusedAxe = new ItemDarkInfusedAxe(DARK_INFUSED_AXE_KEY);
    darkInfusedSpade = new ItemDarkInfusedSpade(DARK_INFUSED_SPADE_KEY);
    darkInfusedHoe = new ItemDarkInfusedHoe(DARK_INFUSED_HOE_KEY);

    gpsChip = new ItemGPSChip();
    storageBall = new ItemStorageBall();
    upgrade = new ItemUpgrade(UPGRADE_KEY, UPGRADE_TYPES);
    pleatherStrips = new ItemPleatherStrips(PLEATHER_STRIPS_KEY);
    pleatherBulb = new ItemPleatherBulb(PLEATHER_BULB_KEY);
    darkInfusedDiamond = new ItemDarkInfusedDiamond(DARK_INFUSED_DIAMOND);

    GameRegistry.registerItem(darkInfusedHelm, DARK_INFUSED_ARMOUR_KEYS[0]);
    GameRegistry.registerItem(darkInfusedChest, DARK_INFUSED_ARMOUR_KEYS[1]);
    GameRegistry.registerItem(darkInfusedLeggings, DARK_INFUSED_ARMOUR_KEYS[2]);
    GameRegistry.registerItem(darkInfusedBoots, DARK_INFUSED_ARMOUR_KEYS[3]);

    GameRegistry.registerItem(darkInfusedSword, DARK_INFUSED_SWORD_KEY);
    GameRegistry.registerItem(darkInfusedPick, DARK_INFUSED_PICK_KEY);
    GameRegistry.registerItem(darkInfusedAxe, DARK_INFUSED_AXE_KEY);
    GameRegistry.registerItem(darkInfusedSpade, DARK_INFUSED_SPADE_KEY);
    GameRegistry.registerItem(darkInfusedHoe, DARK_INFUSED_HOE_KEY);

    GameRegistry.registerItem(darkInfusedDiamond, DARK_INFUSED_DIAMOND);
    GameRegistry.registerItem(gpsChip, GPS_CHIP_KEY);
    GameRegistry.registerItem(storageBall, STORAGE_BALL_KEY);
    GameRegistry.registerItem(upgrade, UPGRADE_KEY);
    GameRegistry.registerItem(pleatherStrips, PLEATHER_STRIPS_KEY);
    GameRegistry.registerItem(pleatherBulb, PLEATHER_BULB_KEY);
  }

  public static void registerRecipes() {
    GameRegistry.addShapelessRecipe(new ItemStack(Items.leather, 1), new ItemStack(pleatherStrips, 4));

    GameRegistry.addRecipe(new ItemStack(gpsChip), "trt", "clc", "iri", 't', Blocks.redstone_torch, 'l', Blocks.redstone_lamp, 'r', Items.redstone, 'i', Items.iron_ingot, 'c', new ItemStack(Items.dye, 1, 2));
    GameRegistry.addRecipe(new ItemStack(Items.blaze_rod), "bb", "bb", "bb", 'b', Items.blaze_powder);

    InfuserRecipes.instance.addRecipe(new ItemStack(storageBall, 1, 0), new ItemStack(Items.slime_ball), 1500);
    InfuserRecipes.instance.addRecipe(new ItemStack(storageBall, 1, 1), new ItemStack(Items.ender_pearl), 2000);
    InfuserRecipes.instance.addRecipe(new ItemStack(storageBall, 1, 2), new ItemStack(Items.blaze_powder), 1000);

    GameRegistry.addShapelessRecipe(new ItemStack(Blocks.cobblestone, 5), new ItemStack(storageBall, 1, 0));
    GameRegistry.addShapelessRecipe(new ItemStack(Blocks.mossy_cobblestone, 5), new ItemStack(storageBall, 1, 1));
    GameRegistry.addShapelessRecipe(new ItemStack(Blocks.netherrack, 5), new ItemStack(storageBall, 1, 2));
    GameRegistry.addShapelessRecipe(new ItemStack(Blocks.log, 5, 0), new ItemStack(storageBall, 1, 3));
    GameRegistry.addRecipe(new ItemStack(storageBall, 1, 0), " c ", "ccc", " c ", 'c', Blocks.cobblestone);
    GameRegistry.addRecipe(new ItemStack(storageBall, 1, 1), " c ", "ccc", " c ", 'c', Blocks.mossy_cobblestone);
    GameRegistry.addRecipe(new ItemStack(storageBall, 1, 2), " c ", "ccc", " c ", 'c', Blocks.netherrack);
    GameRegistry.addRecipe(new ItemStack(storageBall, 1, 3), " c ", "ccc", " c ", 'c', new ItemStack(Blocks.log, 1, 0));

    GameRegistry.addRecipe(new ItemStack(upgrade, 1, 0), "nnn", "ndn", "nnn", 'n', Items.netherbrick, 'd', darkInfusedDiamond);
    GameRegistry.addRecipe(new ItemStack(upgrade, 1, 1), "nsn", "sus", "nsn", 'n', Items.netherbrick, 's', Items.sugar, 'u', new ItemStack(upgrade, 1, 0));

    InfuserRecipes.instance.addRecipe(new ItemStack(Items.diamond), new ItemStack(darkInfusedDiamond), 2000);
    InfuserRecipes.instance.addRecipe(new ItemStack(Items.diamond_helmet), new ItemStack(darkInfusedHelm), 8000);
    InfuserRecipes.instance.addRecipe(new ItemStack(Items.diamond_chestplate), new ItemStack(darkInfusedChest), 12800);
    InfuserRecipes.instance.addRecipe(new ItemStack(Items.diamond_leggings), new ItemStack(darkInfusedLeggings), 11200);
    InfuserRecipes.instance.addRecipe(new ItemStack(Items.diamond_boots), new ItemStack(darkInfusedBoots), 6400);
    InfuserRecipes.instance.addRecipe(new ItemStack(Items.diamond_sword), new ItemStack(darkInfusedSword), 3200);
    InfuserRecipes.instance.addRecipe(new ItemStack(Items.diamond_pickaxe), new ItemStack(darkInfusedPick), 4800);
    InfuserRecipes.instance.addRecipe(new ItemStack(Items.diamond_shovel), new ItemStack(darkInfusedSpade), 1600);
    InfuserRecipes.instance.addRecipe(new ItemStack(Items.diamond_axe), new ItemStack(darkInfusedAxe), 4800);
    InfuserRecipes.instance.addRecipe(new ItemStack(Items.diamond_hoe), new ItemStack(darkInfusedHoe), 3200);

    GameRegistry.addRecipe(new ItemStack(darkInfusedHelm), "ddd", "d d", 'd', darkInfusedDiamond);
    GameRegistry.addRecipe(new ItemStack(darkInfusedChest), "d d", "ddd", "ddd", 'd', darkInfusedDiamond);
    GameRegistry.addRecipe(new ItemStack(darkInfusedLeggings), "ddd", "d d", "d d", 'd', darkInfusedDiamond);
    GameRegistry.addRecipe(new ItemStack(darkInfusedBoots), "d d", "d d", 'd', darkInfusedDiamond);
    GameRegistry.addRecipe(new ItemStack(darkInfusedSword), " d ", " d ", " s ", 'd', darkInfusedDiamond, 's', Items.stick);
    GameRegistry.addRecipe(new ItemStack(darkInfusedPick), "ddd", " s ", " s ", 'd', darkInfusedDiamond, 's', Items.stick);
    GameRegistry.addRecipe(new ItemStack(darkInfusedSpade), " d ", " s ", " s ", 'd', darkInfusedDiamond, 's', Items.stick);
    GameRegistry.addRecipe(new ItemStack(darkInfusedAxe), " dd", " sd", " s ", 'd', darkInfusedDiamond, 's', Items.stick);
    GameRegistry.addRecipe(new ItemStack(darkInfusedHoe), "dd ", " s ", " s ", 'd', darkInfusedDiamond, 's', Items.stick);
  }
}
