package com.ryorama.terrarianwarriors.game.items.terrarianwarriors.weapons.shortswords;

import com.ryorama.terrarianwarriors.game.items.ItemT;
import net.minecraft.item.Item;

public class LeadShortsword extends ItemT {

    public LeadShortsword(Settings settings, Item virtualItem, int rarity) {
        super(settings, virtualItem, rarity);
        this.damage = 3;
    }
}