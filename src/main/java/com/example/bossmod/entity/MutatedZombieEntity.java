package com.example.bossmod.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossEvent;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class MutatedZombieEntity extends ZombieEntity {
    private final ServerBossEvent bossBar = new ServerBossEvent(
        Text.literal("Zmutowany Zombie Boss"),
        BossBar.Color.GREEN,
        BossBar.Style.NOTCHED_10
    );

    public MutatedZombieEntity(EntityType<? extends ZombieEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createMutatedAttributes() {
        return ZombieEntity.createZombieAttributes()
            .add(EntityAttributes.MAX_HEALTH, 150.0D)
            .add(EntityAttributes.ATTACK_DAMAGE, 14.0D)
            .add(EntityAttributes.MOVEMENT_SPEED, 0.26D)
            .add(EntityAttributes.KNOCKBACK_RESISTANCE, 0.8D);
    }

    @Override
    public void mobTick() {
        super.mobTick();
        this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());

        if (!this.getWorld().isClient() && this.isAlive() && this.age % 200 == 0) {
            executeGroundSlam();
        }
    }

    private void executeGroundSlam() {
        this.getWorld().getEntitiesByClass(LivingEntity.class, this.getBoundingBox().expand(6.0D),
            target -> target != this && !(target instanceof ZombieEntity)
        ).forEach(target -> {
            target.damage(this.getDamageSources().mobAttack(this), 10.0F);
            target.takeKnockback(1.8D, this.getX() - target.getX(), this.getZ() - target.getZ());
        });
    }

    @Override
    public void onStartedTrackingBy(ServerPlayerEntity player) {
        super.onStartedTrackingBy(player);
        this.bossBar.addPlayer(player);
    }

    @Override
    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        super.onStoppedTrackingBy(player);
        this.bossBar.removePlayer(player);
    }
}
