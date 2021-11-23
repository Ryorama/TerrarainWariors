package com.ryorama.terrarianwarriors.game.blocks.ores;

import eu.pb4.polymer.block.VirtualBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public class HealthDustOre extends Block implements VirtualBlock {

    public HealthDustOre(Settings settings) {
        super(settings);
    }

    @Override
    public Block getVirtualBlock() {
        return Blocks.REDSTONE_BLOCK;
    }
}
