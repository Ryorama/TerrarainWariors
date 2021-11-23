package com.ryorama.terrarianwarriors.game.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.chunk.ChunkGenerator;

import java.util.Optional;

public class TerrarianWarriorsMapConfig {

    public static final Codec<TerrarianWarriorsMapConfig> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
                ChunkGenerator.CODEC.optionalFieldOf("chunk_generator").forGetter(TerrarianWarriorsMapConfig::getChunkGenerator),
                Identifier.CODEC.fieldOf("settings").forGetter(TerrarianWarriorsMapConfig::getChunkGeneratorSettingsId),
                Codec.INT.optionalFieldOf("x", 16).forGetter(TerrarianWarriorsMapConfig::getX),
                Codec.INT.optionalFieldOf("z", 16).forGetter(TerrarianWarriorsMapConfig::getZ)
        ).apply(instance, TerrarianWarriorsMapConfig::new);
    });

    private final Optional<ChunkGenerator> chunkGenerator;
    private final Identifier chunkGeneratorSettingsId;
    private final int x;
    private final int z;

    public TerrarianWarriorsMapConfig(Optional<ChunkGenerator> chunkGenerator, Identifier chunkGeneratorSettingsId, int x, int z) {
        this.chunkGenerator = chunkGenerator;
        this.chunkGeneratorSettingsId = chunkGeneratorSettingsId;
        this.x = x;
        this.z = z;
    }

    public Optional<ChunkGenerator> getChunkGenerator() {
        return this.chunkGenerator;
    }

    public Identifier getChunkGeneratorSettingsId() {
        return this.chunkGeneratorSettingsId;
    }

    public int getX() {
        return this.x;
    }

    public int getZ() {
        return this.z;
    }
}
