package com.ryorama.terrarianwarriors.game.gui;

import com.ryorama.terrarianwarriors.game.items.AccessoryT;
import com.ryorama.terrarianwarriors.game.items.ItemT;
import eu.pb4.sgui.api.gui.BaseSlotGui;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.jetbrains.annotations.Nullable;

public class AccessoryGUI extends BaseSlotGui {

    public AccessoryGUI(ServerPlayerEntity player, int size) {
        super(player, size);
    }

    @Override
    public void onTick() {
        super.onTick();

        for (int i = 0; i < this.size; i++) {
            if (this.getSlot(i).getItemStack().getItem() instanceof AccessoryT) {
                ((AccessoryT) this.getSlot(i).getItemStack().getItem()).tick();
            }
        }
    }

    @Override
    public boolean getLockPlayerInventory() {
        return false;
    }

    @Override
    public void setLockPlayerInventory(boolean value) {

    }

    @Override
    public int getHeight() {
        return 32;
    }

    @Override
    public int getWidth() {
        return 32;
    }

    @Override
    public boolean isIncludingPlayer() {
        return false;
    }

    @Override
    public int getVirtualSize() {
        return 5;
    }

    @Override
    public boolean isRedirectingSlots() {
        return false;
    }

    @Override
    public void setTitle(Text title) {

    }

    @Override
    public @Nullable Text getTitle() {
        return new TranslatableText("Accessories");
    }

    @Override
    public ScreenHandlerType<?> getType() {
        return ScreenHandlerType.GENERIC_3X3;
    }

    @Override
    public int getSyncId() {
        return 0;
    }

    @Override
    public boolean open() {
        return false;
    }

    @Override
    public void close(boolean alreadyClosed) {

    }
}
