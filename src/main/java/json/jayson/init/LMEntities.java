package json.jayson.init;

import json.jayson.LMUtil;
import json.jayson.common.entity.coil_head.CoilHeadEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.GeckoLib;

public class LMEntities {

    public static final EntityType<CoilHeadEntity> COIL_HEAD = registerEntity("coil_head", SpawnGroup.MONSTER, CoilHeadEntity::new, 1, 2);

    public static <T extends MobEntity> EntityType<T> registerEntity(String name, SpawnGroup spawnGroup, EntityType.EntityFactory<T> entity,
                                                                  float width, float height) {
        return Registry.register(Registries.ENTITY_TYPE, LMUtil.createLocation(name), FabricEntityTypeBuilder.create(spawnGroup, entity).dimensions(EntityDimensions.changing(width, height)).build());
    }

}