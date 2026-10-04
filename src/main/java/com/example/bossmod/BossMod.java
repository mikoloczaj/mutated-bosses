package com.example.bossmod;

import com.example.bossmod.entity.MutatedZombieEntity;
import com.example.bossmod.registry.ModEntities;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import software.bernie.geckolib.GeckoLib;

public class BossMod implements ModInitializer {
    public static final String MOD_ID = "bossmod";

    @Override
    public void onInitialize() {
        GeckoLib.initialize();
        ModEntities.register();
        FabricDefaultAttributeRegistry.register(ModEntities.MUTATED_ZOMBIE, MutatedZombieEntity.createMutatedAttributes());
    }
}
