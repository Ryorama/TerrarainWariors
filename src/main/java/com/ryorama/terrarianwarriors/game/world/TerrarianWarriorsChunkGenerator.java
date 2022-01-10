package com.ryorama.terrarianwarriors.game.world;

import com.ryorama.terrarianwarriors.mixins.ChunkGeneratorAccessor;
import com.ryorama.terrarianwarriors.utils.math.fastnoise.FastNoise;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.VanillaLayeredBiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.SimpleRandom;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.*;
import xyz.nucleoid.plasmid.game.world.generator.GameChunkGenerator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.BitSet;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class TerrarianWarriorsChunkGenerator extends GameChunkGenerator {

    private static final BlockState BARRIER = Blocks.BARRIER.getDefaultState();

    private final TerrarianWarriorsMapConfig mapConfig;
    private final long seed;
    private final ChunkGenerator chunkGenerator;

    public int whichOre;

    public DoublePerlinNoiseSampler terrainNoise;

    public FastNoise noise;
    public Random random;

    private boolean corruption = true;
    private boolean right_jungle = true;

    private float GetTerrainNoise(int x, int y, int z) {
        return noise.GetSimplexFractal(x * 1.25f, y * 2.0f, z * 1.25f) * 5;
    }

    public TerrarianWarriorsChunkGenerator(MinecraftServer server, TerrarianWarriorsMapConfig mapConfig, ChunkGenerator chunkGenerator) {
        super(server);
        this.mapConfig = mapConfig;

        this.seed = ((ChunkGeneratorAccessor) chunkGenerator).getWorldSeed();
        this.chunkGenerator = chunkGenerator;

        whichOre = new Random().nextInt(3);
    }

    public TerrarianWarriorsChunkGenerator(MinecraftServer server, TerrarianWarriorsMapConfig mapConfig) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        this(server, mapConfig, TerrarianWarriorsChunkGenerator.createChunkGenerator(server, mapConfig));
    }

    private static ChunkGenerator createChunkGenerator(MinecraftServer server, TerrarianWarriorsMapConfig mapConfig) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        long seed = server.getOverworld().getRandom().nextLong();
        BiomeSource biomeSource = new VanillaLayeredBiomeSource(seed, false, false, server.getRegistryManager().get(Registry.BIOME_KEY));

        Constructor<?> construct = ChunkGeneratorSettings.class.getDeclaredConstructors()[0];

        construct.setAccessible(true);

        ChunkGeneratorSettings settings = BuiltinRegistries.CHUNK_GENERATOR_SETTINGS.get(mapConfig.getChunkGeneratorSettingsId());

        /*
        ChunkGeneratorSettings settings = (ChunkGeneratorSettings) construct.newInstance(new StructuresConfig(false),
                GenerationShapeConfig.create(-256, 384,
                        new NoiseSamplingConfig(0.9999999814507745D, 0.9999999814507745D, 80.0D, 160.0D),
                        new SlideConfig(-10, 3, 0), new SlideConfig(15, 3, 0), 1, 2, 1.0D, -0.46875D, true, true, false,
                        false),
                Blocks.STONE.getDefaultState(), Blocks.WATER.getDefaultState(), -2147483648, -255, 63, 40, false, true,
                true, true, true, true);
         */

        return new NoiseChunkGenerator(biomeSource, seed,
                () -> settings);
    }

    private boolean isChunkPosWithinArea(ChunkPos chunkPos) {
        return chunkPos.x >= 0 && chunkPos.z >= 0 && chunkPos.x < this.mapConfig.getX() && chunkPos.z < this.mapConfig.getZ();
    }

    private boolean isChunkWithinArea(Chunk chunk) {
        return this.isChunkPosWithinArea(chunk.getPos());
    }

    @Override
    public void populateBiomes(Registry<Biome> registry, Chunk chunk) {
        if (this.isChunkWithinArea(chunk)) {
            this.chunkGenerator.populateBiomes(registry, chunk);
        } else {
            super.populateBiomes(registry, chunk);
        }
    }

    @Override
    public CompletableFuture<Chunk> populateNoise(Executor executor, StructureAccessor structures, Chunk chunk) {
        if (this.isChunkWithinArea(chunk)) {
            return this.chunkGenerator.populateNoise(executor, structures, chunk);
        }
        return super.populateNoise(executor, structures, chunk);
    }

    @Override
    public void buildSurface(ChunkRegion region, Chunk chunk) {
        if (this.isChunkWithinArea(chunk)) {
            this.chunkGenerator.buildSurface(region, chunk);
        }
    }

    @Override
    public BiomeSource getBiomeSource() {
        return this.chunkGenerator.getBiomeSource();
    }

    @Override
    public void generateFeatures(ChunkRegion region, StructureAccessor structures) {
        int chunkX = region.getCenterPos().x;
        int chunkZ = region.getCenterPos().z;

        ChunkPos chunkPos = new ChunkPos(chunkX, chunkZ);
        if (!this.isChunkPosWithinArea(chunkPos)) return;

        BlockPos pos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
        Biome biome = this.chunkGenerator.getBiomeSource().getBiomeForNoiseGen((chunkX << 2) + 2, 2, (chunkZ << 2) + 2);

        ChunkRandom chunkRandom = new ChunkRandom();
        long populationSeed = chunkRandom.setPopulationSeed(this.seed, pos.getX(), pos.getZ());

        biome.generateFeatureStep(structures, this.chunkGenerator, region, populationSeed, chunkRandom, pos);

        BlockPos.Mutable mutablePos = new BlockPos.Mutable();
        Chunk chunk = region.getChunk(chunkPos.getStartPos());

        // Top
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                mutablePos.set(x + pos.getX(), 255, z + pos.getZ());
                chunk.setBlockState(mutablePos, BARRIER, false);
            }
        }

        // North
        if (chunkZ == 0) {
            for (int x = 0; x < 16; x++) {
                for (int y = 0; y < 256; y++) {
                    mutablePos.set(x + pos.getX(), y, pos.getZ());
                    chunk.setBlockState(mutablePos, BARRIER, false);
                }
            }
        }

        // East
        if (chunkX == this.mapConfig.getX() - 1) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < 256; y++) {
                    mutablePos.set(pos.getX() + 15, y, z + pos.getZ());
                    chunk.setBlockState(mutablePos, BARRIER, false);
                }
            }
        }

        // South
        if (chunkZ == this.mapConfig.getZ() - 1) {
            for (int x = 0; x < 16; x++) {
                for (int y = 0; y < 256; y++) {
                    mutablePos.set(x + pos.getX(), y, pos.getZ() + 15);
                    chunk.setBlockState(mutablePos, BARRIER, false);
                }
            }
        }

        // West
        if (chunkX == 0) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < 256; y++) {
                    mutablePos.set(pos.getX(), y, z + pos.getZ());
                    chunk.setBlockState(mutablePos, BARRIER, false);
                }
            }
        }
    }

    public void carve(long seed, BiomeAccess access, Chunk chunk, GenerationStep.Carver carver) {
        if (this.isChunkWithinArea(chunk)) {
            this.chunkGenerator.carve(this.seed, access, chunk, carver);
        }
    }

    @Override
    public int getHeight(int x, int z, Heightmap.Type heightmapType, HeightLimitView world) {
        if (this.isChunkPosWithinArea(new ChunkPos(x >> 4, z >> 4))) {
            return this.chunkGenerator.getHeight(x, z, heightmapType, world);
        }
        return super.getHeight(x, z, heightmapType, world);
    }

    @Override
    public VerticalBlockSample getColumnSample(int x, int z, HeightLimitView world) {
        if (this.isChunkPosWithinArea(new ChunkPos(x >> 4, z >> 4))) {
            return this.chunkGenerator.getColumnSample(x, z, world);
        }
        return super.getColumnSample(x, z, world);
    }

    public boolean placeOre(ChunkRegion worldIn, Random rand, BlockPos pos, int size, BlockState target, BlockState state) {

        if (worldIn.getBlockState(pos) != Blocks.WATER.getDefaultState() && worldIn.getBlockState(pos) != Blocks.LAVA.getDefaultState()) {
            float f = rand.nextFloat() * (float)Math.PI;
            float f1 = (float)size / 8.0F;
            int i = MathHelper.ceil(((float)size / 16.0F * 2.0F + 1.0F) / 2.0F);
            double d0 = (double)((float)pos.getX() + MathHelper.sin(f) * f1);
            double d1 = (double)((float)pos.getX() - MathHelper.sin(f) * f1);
            double d2 = (double)((float)pos.getZ() + MathHelper.cos(f) * f1);
            double d3 = (double)((float)pos.getZ() - MathHelper.cos(f) * f1);
            int j = 2;
            double d4 = (double)(pos.getY() + rand.nextInt(3) - 2);
            double d5 = (double)(pos.getY() + rand.nextInt(3) - 2);
            int k = pos.getX() - MathHelper.ceil(f1) - i;
            int l = pos.getY() - 2 - i;
            int i1 = pos.getZ() - MathHelper.ceil(f1) - i;
            int j1 = 2 * (MathHelper.ceil(f1) + i);
            int k1 = 2 * (2 + i);

            for(int l1 = k; l1 <= k + j1; ++l1) {
                for(int i2 = i1; i2 <= i1 + j1; ++i2) {
                    return func_207803_a(worldIn, rand, target, state, d0, d1, d2, d3, d4, d5, k, l, i1, j1, k1, size);
                }
            }
        }
        return false;
    }

    protected boolean func_207803_a(ChunkRegion worldIn, Random random, BlockState target, BlockState state, double p_207803_4_, double p_207803_6_, double p_207803_8_, double p_207803_10_, double p_207803_12_, double p_207803_14_, int p_207803_16_, int p_207803_17_, int p_207803_18_, int p_207803_19_, int p_207803_20_, int size) {
        int i = 0;
        BitSet bitset = new BitSet(p_207803_19_ * p_207803_20_ * p_207803_19_);
        BlockPos.Mutable blockpos$mutableblockpos = new BlockPos.Mutable();
        double[] adouble = new double[size * 4];

        for(int j = 0; j < size; ++j) {
            float f = (float)j / (float)size;
            double d0 = MathHelper.lerp((double)f, p_207803_4_, p_207803_6_);
            double d2 = MathHelper.lerp((double)f, p_207803_12_, p_207803_14_);
            double d4 = MathHelper.lerp((double)f, p_207803_8_, p_207803_10_);
            double d6 = random.nextDouble() * (double)size / 16.0D;
            double d7 = ((double)(MathHelper.sin((float)Math.PI * f) + 1.0F) * d6 + 1.0D) / 2.0D;
            adouble[j * 4 + 0] = d0;
            adouble[j * 4 + 1] = d2;
            adouble[j * 4 + 2] = d4;
            adouble[j * 4 + 3] = d7;
        }

        for(int l2 = 0; l2 < size - 1; ++l2) {
            if (!(adouble[l2 * 4 + 3] <= 0.0D)) {
                for(int j3 = l2 + 1; j3 < size; ++j3) {
                    if (!(adouble[j3 * 4 + 3] <= 0.0D)) {
                        double d12 = adouble[l2 * 4 + 0] - adouble[j3 * 4 + 0];
                        double d13 = adouble[l2 * 4 + 1] - adouble[j3 * 4 + 1];
                        double d14 = adouble[l2 * 4 + 2] - adouble[j3 * 4 + 2];
                        double d15 = adouble[l2 * 4 + 3] - adouble[j3 * 4 + 3];
                        if (d15 * d15 > d12 * d12 + d13 * d13 + d14 * d14) {
                            if (d15 > 0.0D) {
                                adouble[j3 * 4 + 3] = -1.0D;
                            } else {
                                adouble[l2 * 4 + 3] = -1.0D;
                            }
                        }
                    }
                }
            }
        }

        for(int i3 = 0; i3 < size; ++i3) {
            double d11 = adouble[i3 * 4 + 3];
            if (!(d11 < 0.0D)) {
                double d1 = adouble[i3 * 4 + 0];
                double d3 = adouble[i3 * 4 + 1];
                double d5 = adouble[i3 * 4 + 2];
                int k = Math.max(MathHelper.floor(d1 - d11), p_207803_16_);
                int k3 = Math.max(MathHelper.floor(d3 - d11), p_207803_17_);
                int l = Math.max(MathHelper.floor(d5 - d11), p_207803_18_);
                int i1 = Math.max(MathHelper.floor(d1 + d11), k);
                int j1 = Math.max(MathHelper.floor(d3 + d11), k3);
                int k1 = Math.max(MathHelper.floor(d5 + d11), l);

                for(int l1 = k; l1 <= i1; ++l1) {
                    double d8 = ((double)l1 + 0.5D - d1) / d11;
                    if (d8 * d8 < 1.0D) {
                        for(int i2 = k3; i2 <= j1; ++i2) {
                            double d9 = ((double)i2 + 0.5D - d3) / d11;
                            if (d8 * d8 + d9 * d9 < 1.0D) {
                                for(int j2 = l; j2 <= k1; ++j2) {
                                    double d10 = ((double)j2 + 0.5D - d5) / d11;
                                    if (d8 * d8 + d9 * d9 + d10 * d10 < 1.0D) {
                                        int k2 = l1 - p_207803_16_ + (i2 - p_207803_17_) * p_207803_19_ + (j2 - p_207803_18_) * p_207803_19_ * p_207803_20_;
                                        if (!bitset.get(k2)) {
                                            bitset.set(k2);
                                            blockpos$mutableblockpos.set(l1, i2, j2);
                                            if (target == worldIn.getBlockState(blockpos$mutableblockpos)) {
                                                worldIn.setBlockState(blockpos$mutableblockpos, state, 2);
                                                ++i;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return i > 0;
    }
}