package com.example.bossmod.registry;

import com.example.bossmod.BossMod;
import com.example.bossmod.entity.MutatedZombieEntity;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<MutatedZombieEntity> MUTATED_ZOMBIE = Registry.register(
        Registries.ENTITY_TYPE,
        Identifier.of(BossMod.MOD_ID, "mutated_zombie"),
        EntityType.Builder.create(MutatedZombieEntity::new, SpawnGroup.MONSTER)
            .dimensions(1.2f, 2.8f)
            .build()
    );

    public static final Item MUTATED_ZOMBIE_SPAWN_EGG = Registry.register(
        Registries.ITEM,
        Identifier.of(BossMod.MOD_ID, "mutated_zombie_spawn_egg"),
        new SpawnEggItem(MUTATED_ZOMBIE, 0x1A401A, 0x55FF55, new Item.Settings())
    );

    public static void register() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(entries -> {
            entries.add(MUTATED_ZOMBIE_SPAWN_EGG);
        });
    }
}
