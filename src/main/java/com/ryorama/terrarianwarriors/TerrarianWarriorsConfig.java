package com.ryorama.terrarianwarriors;


import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ryorama.terrarianwarriors.game.world.TerrarianWarriorsMapConfig;
import xyz.nucleoid.plasmid.game.common.config.PlayerConfig;

public class TerrarianWarriorsConfig {
    public static final Codec<TerrarianWarriorsConfig> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
                TerrarianWarriorsMapConfig.CODEC.fieldOf("map").forGetter(TerrarianWarriorsConfig::getMapConfig),
                Codec.INT.fieldOf("time_limit_secs").forGetter(config -> config.timeLimitSecs)
        ).apply(instance, TerrarianWarriorsConfig::new);
    });

    public final int timeLimitSecs;
    private final TerrarianWarriorsMapConfig mapConfig;

    public TerrarianWarriorsConfig(TerrarianWarriorsMapConfig mapConfig, int timeLimitSecs) {
        this.mapConfig = mapConfig;
        this.timeLimitSecs = timeLimitSecs;
    }

    public TerrarianWarriorsMapConfig getMapConfig() {
        return this.mapConfig;
    }
}