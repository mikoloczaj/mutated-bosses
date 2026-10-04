package com.example.bossmod.mixin;

import com.example.bossmod.entity.MutatedZombieEntity;
import com.example.bossmod.registry.ModEntities;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.world.LocalWorldAccess;
import net.minecraft.world.ServerWorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ZombieEntity.class)
public class MobSpawnMixin {
    @Inject(method = "initialize", at = @At("TAIL"))
    private void trySpawnMutatedBoss(ServerWorldAccess world, LocalWorldAccess localWorldAccess, SpawnReason spawnReason, 
                                    EntityData entityData, CallbackInfoReturnable<EntityData> cir) {
        ZombieEntity zombie = (ZombieEntity) (Object) this;

        if (!(zombie instanceof MutatedZombieEntity) && spawnReason == SpawnReason.NATURAL) {
            if (world.getRandom().nextFloat() < 0.005f) {
                MutatedZombieEntity boss = ModEntities.MUTATED_ZOMBIE.create(zombie.getWorld());
                if (boss != null) {
                    boss.refreshPositionAndAngles(zombie.getX(), zombie.getY(), zombie.getZ(), zombie.getYaw(), zombie.getPitch());
                    world.spawnEntity(boss);
                    zombie.discard();
                }
            }
        }
    }
}
