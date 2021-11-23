package com.ryorama.terrarianwarriors;

import com.ryorama.terrarianwarriors.game.world.TerrarianWarriorsChunkGenerator;
import net.minecraft.block.Blocks;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;
import xyz.nucleoid.fantasy.RuntimeWorldConfig;
import xyz.nucleoid.map_templates.MapTemplate;
import xyz.nucleoid.plasmid.game.GameOpenContext;
import xyz.nucleoid.plasmid.game.GameOpenProcedure;
import xyz.nucleoid.plasmid.game.event.GamePlayerEvents;
import xyz.nucleoid.plasmid.game.rule.GameRuleType;
import xyz.nucleoid.plasmid.game.world.generator.TemplateChunkGenerator;

public class TerrariaWarriorsGame {
    public static GameOpenProcedure open(GameOpenContext<TerrarianWarriorsConfig> context) {
        // get our config that got loaded by Plasmid
        TerrarianWarriorsConfig config = context.config();

        /*
        MapTemplate template = MapTemplate.createEmpty();

        for (int i = 0; i <= 30; i++) {
            for (int k = 0; k <= 30; k++) {
                template.setBlockState(new BlockPos(i, 64, k), Blocks.GRASS_BLOCK.getDefaultState());
                for (int j = 64; j >= 60; j--) {
                    template.setBlockState(new BlockPos(i, j - 1, k), Blocks.DIRT.getDefaultState());
                }
            }
        }

        // create a chunk generator that will generate from this template that we just created
        TemplateChunkGenerator generator = new TemplateChunkGenerator(context.server(), template);
        */

        TerrarianWarriorsChunkGenerator generator = new TerrarianWarriorsChunkGenerator(context.server(), config.getMapConfig());
        RuntimeWorldConfig worldConfig = new RuntimeWorldConfig()
                .setGenerator(generator)
                .setTimeOfDay(6000);

        return context.openWithWorld(worldConfig, (activity, world) -> {
            activity.deny(GameRuleType.FALL_DAMAGE);

            activity.listen(GamePlayerEvents.OFFER, offer -> {
                ServerPlayerEntity player = offer.player();
                return offer.accept(world, new Vec3d(32, 150, 32))
                        .and(() -> {
                            player.changeGameMode(GameMode.ADVENTURE);
                        });
            });

            activity.listen(GamePlayerEvents.ADD, player -> {

            });

            activity.listen(GamePlayerEvents.LEAVE, player -> {
                player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(20);
            });
        });
    }
}
