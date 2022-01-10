package com.ryorama.terrarianwarriors;

import com.mojang.brigadier.context.CommandContext;
import com.ryorama.terrarianwarriors.game.blocks.BlocksT;
import com.ryorama.terrarianwarriors.game.gui.AccessoryGUI;
import com.ryorama.terrarianwarriors.game.items.ItemsT;
import eu.pb4.sgui.api.gui.BaseSlotGui;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.source.HorizontalVoronoiBiomeAccessType;
import net.minecraft.world.dimension.DimensionType;
import xyz.nucleoid.plasmid.event.GameEvents;
import xyz.nucleoid.plasmid.game.GameType;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.nucleoid.plasmid.game.player.GamePlayerJoiner;

import java.util.OptionalLong;

import static net.minecraft.server.command.CommandManager.literal;

public class TerrarianWarriors implements ModInitializer {

    public static final DimensionType CUSTOM_DIMENSION = DimensionType.create(OptionalLong.empty(), true, false, false, true, 1.0D, false, false, true, false, true, -256, 256, 256, HorizontalVoronoiBiomeAccessType.INSTANCE, BlockTags.INFINIBURN_OVERWORLD.getId(), DimensionType.OVERWORLD_ID, 0.0F);

    public static final String ID = "terrarianwarriors";
    public static final Logger LOGGER = LogManager.getLogger(ID);

    public static final GameType<TerrarianWarriorsConfig> TYPE = GameType.register(
            new Identifier(ID, "terrarianwarriors"),
            TerrarianWarriorsConfig.CODEC,
            TerrariaWarriorsGame::open
    );

    private static int openAccessoryMenu(CommandContext<ServerCommandSource> objectCommandContext) {
        try {
            ServerPlayerEntity player = objectCommandContext.getSource().getPlayer();
            BaseSlotGui gui = new AccessoryGUI(player, 9);
            gui.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void onInitialize() {
        //System.out.println(Registry.CHUNK_GENERATOR.getEntries());
        ItemsT.init();
        BlocksT.init();

        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            dispatcher.register(
                    literal("openAccessoryMenu").executes(TerrarianWarriors::openAccessoryMenu)
            );
        });
    }
}
