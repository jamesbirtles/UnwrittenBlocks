package unwrittenfun.minecraft.unwrittenblocks.common.items;

import cpw.mods.fml.common.registry.GameRegistry;
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
    InfuserRecipes.instance.addRecipe(new ItemStack(Items.diamond), new ItemStack(darkInfusedDiamond), 2000);
  }
}
