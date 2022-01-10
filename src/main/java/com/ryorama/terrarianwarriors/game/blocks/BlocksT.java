package com.ryorama.terrarianwarriors.game.blocks;

import com.ryorama.terrarianwarriors.TerrarianWarriors;
import com.ryorama.terrarianwarriors.game.blocks.ores.HealthDustOre;
import com.ryorama.terrarianwarriors.game.blocks.ores.LeadOre;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlocksT {

    public static HealthDustOre HEALTH_DUST_ORE = new HealthDustOre(FabricBlockSettings.of(Material.STONE).strength(2).luminance(5));
    public static LeadOre LEAD_ORE = new LeadOre(FabricBlockSettings.of(Material.STONE).strength(2));

    public static void init() {
        Registry.register(Registry.BLOCK, new Identifier(TerrarianWarriors.ID, "health_dust_ore"), HEALTH_DUST_ORE);
        Registry.register(Registry.BLOCK, new Identifier(TerrarianWarriors.ID, "lead_ore"), LEAD_ORE);
    }
}
