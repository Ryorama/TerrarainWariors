package com.ryorama.terrarianwarriors.game.items.terrarianwarriors.weapons.shortswords;

import com.ryorama.terrarianwarriors.game.items.ItemT;
import net.minecraft.item.Item;

public class CopperShortsword extends ItemT {

    public CopperShortsword(Settings settings, Item virtualItem, int rarity) {
        super(settings, virtualItem, rarity);
        this.damage = 3;
    }
}