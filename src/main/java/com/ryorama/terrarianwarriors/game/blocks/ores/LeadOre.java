package com.ryorama.terrarianwarriors.game.blocks.ores;

import eu.pb4.polymer.block.VirtualBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public class LeadOre extends Block implements VirtualBlock {
    public LeadOre(Settings settings) {
        super(settings);
    }

    @Override
    public Block getVirtualBlock() {
        return Blocks.COAL_BLOCK;
    }
}
