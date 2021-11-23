package com.ryorama.terrarianwarriors.game.items;

import com.ryorama.terrarianwarriors.TerrarianWarriors;
import com.ryorama.terrarianwarriors.game.blocks.ores.LeadOre;
import com.ryorama.terrarianwarriors.game.items.terrarianwarriors.misc.HealthDust;
import com.ryorama.terrarianwarriors.game.items.terrarianwarriors.ores.LeadOreItem;
import com.ryorama.terrarianwarriors.game.items.terrarianwarriors.weapons.broadswords.CopperBroadsword;
import com.ryorama.terrarianwarriors.game.items.terrarianwarriors.weapons.broadswords.LeadBroadsword;
import com.ryorama.terrarianwarriors.game.items.terrarianwarriors.weapons.shortswords.CopperShortsword;
import com.ryorama.terrarianwarriors.game.items.terrarianwarriors.weapons.shortswords.LeadShortsword;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemsT {

    public static CopperBroadsword COPPER_BROADSWORD = new CopperBroadsword(new FabricItemSettings().maxCount(1), Items.WOODEN_SWORD, 2);
    public static CopperShortsword COPPER_SHORTSWORD = new CopperShortsword(new FabricItemSettings().maxCount(1), Items.WOODEN_SWORD, 2);
    public static LeadBroadsword LEAD_BROADSWORD = new LeadBroadsword(new FabricItemSettings().maxCount(1), Items.IRON_SWORD, 2);
    public static LeadShortsword LEAD_SHORTSWORD = new LeadShortsword(new FabricItemSettings().maxCount(1), Items.IRON_SWORD, 2);

    public static HealthDust HEALTH_DUST = new HealthDust(new FabricItemSettings(), Items.REDSTONE, 4);

    public static LeadOreItem LEAD_ORE_ITEM = new LeadOreItem(new FabricItemSettings(), Items.IRON_NUGGET, 2);

    public static void init() {
        //Melee weapon registries
        Registry.register(Registry.ITEM, new Identifier(TerrarianWarriors.ID, "copper_broadsword"), COPPER_BROADSWORD);
        Registry.register(Registry.ITEM, new Identifier(TerrarianWarriors.ID, "copper_shortsword"), COPPER_SHORTSWORD);
        Registry.register(Registry.ITEM, new Identifier(TerrarianWarriors.ID, "lead_broadsword"), LEAD_BROADSWORD);
        Registry.register(Registry.ITEM, new Identifier(TerrarianWarriors.ID, "lead_shortsword"), LEAD_SHORTSWORD);


        //Ore item registries
        Registry.register(Registry.ITEM, new Identifier(TerrarianWarriors.ID, "lead_ore"), LEAD_ORE_ITEM);

        //Misc item registries
        Registry.register(Registry.ITEM, new Identifier(TerrarianWarriors.ID, "health_dust"), HEALTH_DUST);
    }
}
