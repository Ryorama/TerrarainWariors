package com.ryorama.terrarianwarriors.game.items.terrarianwarriors.weapons.broadswords;

import com.ryorama.terrarianwarriors.game.items.ItemT;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class CopperBroadsword extends ItemT {

    public CopperBroadsword(Settings settings, Item virtualItem, int rarity) {
        super(settings, virtualItem, rarity);
        this.damage = 6;
    }
}
