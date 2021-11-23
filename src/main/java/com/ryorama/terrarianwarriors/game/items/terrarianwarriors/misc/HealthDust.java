package com.ryorama.terrarianwarriors.game.items.terrarianwarriors.misc;

import com.ryorama.terrarianwarriors.game.items.ItemT;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class HealthDust extends ItemT {

    public HealthDust(Settings settings, Item virtualItem, int rarity) {
        super(settings, virtualItem, rarity);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        if (user.getAttributeBaseValue(EntityAttributes.GENERIC_MAX_HEALTH) <= 50) {
            user.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(user.getAttributeBaseValue(EntityAttributes.GENERIC_MAX_HEALTH) + 5);
            user.setHealth((float) user.getAttributeBaseValue(EntityAttributes.GENERIC_MAX_HEALTH));

            if (!user.isCreative()) {
                user.getMainHandStack().decrement(1);
            }
        }

        return super.use(world, user, hand);
    }
}
