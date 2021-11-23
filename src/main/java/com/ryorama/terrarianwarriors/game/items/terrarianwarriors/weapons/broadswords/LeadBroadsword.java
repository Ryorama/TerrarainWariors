package com.ryorama.terrarianwarriors.game.items.terrarianwarriors.weapons.broadswords;

import com.ryorama.terrarianwarriors.game.items.ItemT;
import net.minecraft.item.Item;

public class LeadBroadsword extends ItemT {

    public LeadBroadsword(Settings settings, Item virtualItem, int rarity) {
        super(settings, virtualItem, rarity);
        this.damage = 6;
    }
}
