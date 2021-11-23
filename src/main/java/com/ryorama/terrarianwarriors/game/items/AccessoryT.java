package com.ryorama.terrarianwarriors.game.items;

import net.minecraft.item.Item;

public abstract class AccessoryT extends ItemT {

    public AccessoryT(Settings settings, Item virtualItem, int rarity) {
        super(settings, virtualItem, rarity);
    }

    public abstract void tick();
}
