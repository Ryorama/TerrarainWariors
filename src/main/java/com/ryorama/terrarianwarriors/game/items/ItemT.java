package com.ryorama.terrarianwarriors.game.items;

import eu.pb4.polymer.item.VirtualItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class ItemT extends Item implements VirtualItem {

    public Item virtualItem;

    public int rarity;

    public boolean melee, throwing, ranged, summon, magic;
    public int mana = 0;
    public int damage = 0;
    public float knockback = -1;
    public float critChance = 4;
    public int useTime = -1;
    public float pick, axe, hammer;
    public ItemT ammo;
    public boolean isAmmo;
    public int stackSize;
    public String tooltip = "";
    public int heal;
    public int manaHeal;
    public boolean consumable;
    public boolean autoReuse;
    public float velocity;
    public int defense;
    public String shoot = "";
    public double scale = 1.0f;
    public double rotX, rotY, rotZ;
    public double offsX, offsY, offsZ;
    public String animation = "";
    public static String BOW_ANIMATION = "bow", STAFF_ANIMATION = "staff", BROADSWORD_ANIMATION = "broadsword", SHORTSWORD_ANIMATION = "shortsword",
            PICKAXE_ANIMATION = "pickaxe", AXE_ANIMATION = "axe", HAMMER_ANIMATION = "hammer", THROWING_ANIMATION = "throwing", BUILDING_ANIMATION = "building",
            GUN_ANIMATION = "gun";

    public int potionSickness, manaSickness;

    public int speed;

    public boolean material;

    public String itemName;

    public ItemT(Settings settings, Item virtualItem, int rarity) {
        super(settings);
        this.virtualItem = virtualItem;
        this.rarity = rarity;
    }

    @Override
    public Item getVirtualItem() {
        return virtualItem;
    }

    public void dummyTestMethod() {
        System.out.println("Dummy test method executed successfully");
    }

    @Override
    public Text getName(ItemStack stack) {
        if (rarity == 1) {
            return new TranslatableText(this.getTranslationKey(stack)).formatted(IRareItem.GREY);
        } else if (rarity == 2) {
            return new TranslatableText(this.getTranslationKey(stack)).formatted(IRareItem.WHITE);
        } else if (rarity == 3) {
            return new TranslatableText(this.getTranslationKey(stack)).formatted(IRareItem.BLUE);
        } else if (rarity == 4) {
            return new TranslatableText(this.getTranslationKey(stack)).formatted(IRareItem.GREEN);
        } else if (rarity == 5) {
            return new TranslatableText(this.getTranslationKey(stack)).formatted(IRareItem.ORANGE);
        } else if (rarity == 6) {
            return new TranslatableText(this.getTranslationKey(stack)).formatted(IRareItem.LIGHT_RED);
        } else if (rarity == 7) {
            return new TranslatableText(this.getTranslationKey(stack)).formatted(IRareItem.LIGHT_PURPLE);
        } else if (rarity == 8) {
            return new TranslatableText(this.getTranslationKey(stack)).formatted(IRareItem.YELLOW);
        } else if (rarity == 9) {
            return new TranslatableText(this.getTranslationKey(stack)).formatted(IRareItem.RED);
        } else if (rarity == 10) {
            return new TranslatableText(this.getTranslationKey(stack)).formatted(IRareItem.PURPLE);
        } else {
            return new TranslatableText(this.getTranslationKey(stack)).formatted(IRareItem.WHITE);
        }
    }

    public ItemT setTooltip(String tooltip) {
        this.tooltip = tooltip;
        return this;
    }

    @Override
    public void appendTooltip(ItemStack stack, World worldIn, List<Text> tooltip, TooltipContext context) {

        if (pick > 0)
            tooltip.add(new TranslatableText(pick + "% pickaxe power"));
        if (axe > 0)
            tooltip.add(new TranslatableText(axe + "% axe power"));
        if (hammer > 0)
            tooltip.add(new TranslatableText(hammer + "% hammer power"));
        if (melee)
            tooltip.add(new TranslatableText(damage + " melee damage"));
        if (ranged)
            tooltip.add(new TranslatableText(damage + " ranged damage"));
        if (throwing)
            tooltip.add(new TranslatableText(damage + " throwing damage"));
        if (summon)
            tooltip.add(new TranslatableText(damage + " summon damage"));
        if (magic)
            tooltip.add(new TranslatableText(damage + " magic damage"));
        if (velocity > 0)
            tooltip.add(new TranslatableText(velocity + " velocity"));
        if (defense > 0)
            tooltip.add(new TranslatableText(defense + " defense"));
        if (mana > 0)
            tooltip.add(new TranslatableText("uses " + damage + " mana"));
        if (critChance > 0 && damage > 0)
            tooltip.add(new TranslatableText(new String(critChance + "% critical strike chance").replace(".0", "")));
        if (isAmmo)
            tooltip.add(new TranslatableText("ammo"));
        if (heal > 0)
            tooltip.add(new TranslatableText("heals " + heal + " health"));
        if (manaHeal > 0)
            tooltip.add(new TranslatableText("restores " + manaHeal + " mana"));
        if (consumable)
            tooltip.add(new TranslatableText("consumable"));
        if (material)
            tooltip.add(new TranslatableText("material"));
        if (!this.tooltip.equals(""))
            tooltip.add(new TranslatableText(""+this.tooltip));
    }

    public int getUseDuration(ItemStack stack) {
        return useTime;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target != null) {
            if (attacker instanceof PlayerEntity)
                if (new Random().nextInt(100) <= critChance) {
                    target.damage(DamageSource.player((PlayerEntity) attacker), (float) (damage * 2));
                }
                else
                    target.damage(DamageSource.player((PlayerEntity) attacker), (float) (damage));
        }
        return true;
    }
}
