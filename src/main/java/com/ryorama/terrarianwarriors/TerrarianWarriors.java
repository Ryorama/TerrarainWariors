package com.ryorama.terrarianwarriors;

import com.ryorama.terrarianwarriors.game.blocks.BlocksT;
import com.ryorama.terrarianwarriors.game.items.ItemsT;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.registry.Registry;
import xyz.nucleoid.plasmid.event.GameEvents;
import xyz.nucleoid.plasmid.game.GameType;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.nucleoid.plasmid.game.player.GamePlayerJoiner;

public class TerrarianWarriors implements ModInitializer {

    public static final String ID = "terrarianwarriors";
    public static final Logger LOGGER = LogManager.getLogger(ID);

    public static final GameType<TerrarianWarriorsConfig> TYPE = GameType.register(
            new Identifier(ID, "terrarianwarriors"),
            TerrarianWarriorsConfig.CODEC,
            TerrariaWarriorsGame::open
    );

    @Override
    public void onInitialize() {
        System.out.println(Registry.CHUNK_GENERATOR.getEntries());
        ItemsT.init();
        BlocksT.init();
    }
}
