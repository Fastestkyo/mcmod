package net.karnx.random_mod.entity.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class FriendlySkeletonEntity extends SkeletonEntity {

    private final PlayerEntity summoner;

    public FriendlySkeletonEntity(EntityType<? extends SkeletonEntity> entityType, World world, PlayerEntity summoner) {
        super(entityType, world);
        this.summoner = summoner;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.summoner != null) {
            LivingEntity target = this.summoner.getAttacking(); // Get the entity the player is attacking

            if (target != null && target.isAlive()) {
                this.setTarget(target);  // Make the skeleton attack the same target as the player
            }
        }
    }

    @Override
    public boolean canTarget(LivingEntity target) {
        // Prevent the skeleton from targeting the summoner (player)
        if (target.equals(this.summoner)) {
            return false;
        }
        return super.canTarget(target);
    }
}
